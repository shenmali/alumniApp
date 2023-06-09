package com.ty.AlumniApp.Profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import com.ty.AlumniApp.R;
import com.ty.AlumniApp.Share.ShareActivity;
import com.ty.AlumniApp.Utils.FirebaseMethods;
import com.ty.AlumniApp.Utils.UniversalImageLoader;
import com.ty.AlumniApp.dialogs.ConfirmPasswordDialog;
import com.ty.AlumniApp.models.User;
import com.ty.AlumniApp.models.UserAccountSettings;
import com.ty.AlumniApp.models.UserSettings;


public class EditProfileFragment extends Fragment implements
        ConfirmPasswordDialog.OnConfirmPasswordListener {


    @Override
    public void onConfirmPassword(String password) {
        Log.d(TAG, "onConfirmPassword: got the password: " + password);

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(mAuth.getCurrentUser().getEmail(), password);

        ///////////////////// Prompt the user to re-provide their sign-in credentials
        mAuth.getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User re-authenticated.");

                            ///////////////////////check to see if the email is not already present in the database
                            mAuth.fetchSignInMethodsForEmail(mEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    if (task.isSuccessful()) {
                                        try {
//                                            if (task.getResult().getProviders().size() == 1) {
                                            if (task.getResult().getSignInMethods().size() == 1) {
                                                Log.d(TAG, "onComplete: that email is already in use.");
                                                Toast.makeText(getActivity(), "That email is already in use", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Log.d(TAG, "onComplete: That email is available.");

                                                //////////////////////the email is available so update it
                                                mAuth.getCurrentUser().updateEmail(mEmail.getText().toString())
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Log.d(TAG, "User email address updated.");
                                                                    Toast.makeText(getActivity(), "email updated", Toast.LENGTH_SHORT).show();
                                                                    mFirebaseMethods.updateEmail(mEmail.getText().toString());
                                                                }
                                                            }
                                                        });
                                            }
                                        } catch (NullPointerException e) {
                                            Log.e(TAG, "onComplete: NullPointerException: " + e.getMessage());
                                        }
                                    }
                                }
                            });


                        } else {
                            Log.d(TAG, "onComplete: re-authentication failed.");
                        }

                    }
                });
    }

    private static final String TAG = "EditProfileFragment";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private String userID;


    //EditProfile Fragment widgets
    private EditText mDisplayName, mUsername, mWhatsapp, mEducation, mEmail, mPhoneNumber, mPassword, mWork, mSocialMedia, mCommunication;
    private TextView mChangeProfilePhoto;
    private CircleImageView mProfilePhoto;


    //vars
    private UserSettings mUserSettings;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        mProfilePhoto = (CircleImageView) view.findViewById(R.id.profile_photo);
        mDisplayName = (EditText) view.findViewById(R.id.display_name);
        mUsername = (EditText) view.findViewById(R.id.username);
        mWhatsapp = (EditText) view.findViewById(R.id.whatsapp);
        mEducation = (EditText) view.findViewById(R.id.description);
        mEmail = (EditText) view.findViewById(R.id.email);
        mPhoneNumber = (EditText) view.findViewById(R.id.phoneNumber);
        mPassword = (EditText) view.findViewById(R.id.password);
        mWork = (EditText) view.findViewById(R.id.work);
        mSocialMedia = (EditText) view.findViewById(R.id.socialMedia);
        mCommunication = (EditText) view.findViewById(R.id.communication);
        mChangeProfilePhoto = (TextView) view.findViewById(R.id.changeProfilePhoto);
        mFirebaseMethods = new FirebaseMethods(getActivity());


        //setProfileImage();
        setupFirebaseAuth();

        //back arrow for navigating back to "ProfileActivity"
        ImageView backArrow = (ImageView) view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to ProfileActivity");
                getActivity().finish();
            }
        });

        ImageView checkmark = (ImageView) view.findViewById(R.id.saveChanges);
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to save changes.");
                saveProfileSettings();
            }
        });

        return view;
    }


    /**
     * Retrieves the data contained in the widgets and submits it to the database
     * Before donig so it chekcs to make sure the username chosen is unqiue
     */
    private void saveProfileSettings() {
        final String displayName = mDisplayName.getText().toString();
        final String username = mUsername.getText().toString();
        final String whatsapp = mWhatsapp.getText().toString();
        final String description = mEducation.getText().toString();
        final String email = mEmail.getText().toString();
        final String work = mWork.getText().toString();
        final String socialMedia = mSocialMedia.getText().toString();
        final String communication = mCommunication.getText().toString();
        final String phoneNumber = mPhoneNumber.getText().toString();



        //case1: if the user made a change to their username
        if (!mUserSettings.getUser().getUsername().equals(username)) {

            checkIfUsernameExists(username);
        }
        //case2: if the user made a change to their email
        if (!mUserSettings.getUser().getEmail().equals(email)) {

            ConfirmPasswordDialog dialog = new ConfirmPasswordDialog();
            dialog.show(getFragmentManager(), getString(R.string.confirm_password_dialog));
            dialog.setTargetFragment(EditProfileFragment.this, 1);

        }

        /**
         * change the rest of the settings that do not require uniqueness
         */
        if (!mUserSettings.getSettings().getDisplay_name().equals(displayName)) {
            //update displayname
            mFirebaseMethods.updateUserAccountSettings(displayName, null, null, null);
        }
        if (!mUserSettings.getSettings().getWhatsapp().equals(whatsapp)) {
            //update whatsapp
            mFirebaseMethods.updateUserAccountSettings(null, whatsapp, null, null);
        }
        if (!mUserSettings.getSettings().getDescription().equals(description)) {
            //update description
            mFirebaseMethods.updateUserAccountSettings(null, null, description, null);
        }
        if (!mUserSettings.getSettings().getWork().equals(work)) {
            //update work
            mFirebaseMethods.updateUserAccountSettings(null, null, null, work);
        }
        if (!mUserSettings.getSettings().getSocialMedia().equals(socialMedia)) {
            //update socialMedia
            mFirebaseMethods.updateUserAccountSettings(null, null, null, socialMedia);
        }
        if (!mUserSettings.getSettings().getCommunication().equals(communication)) {
            //update communication
            mFirebaseMethods.updateUserAccountSettings(null, null, null, communication);
        }
        if (!mUserSettings.getSettings().getProfile_photo().equals(phoneNumber)) {
            //update phoneNumber
            mFirebaseMethods.updateUserAccountSettings(null, null, null, phoneNumber);
        }


    }


    /**
     * Check is @param username already exists in teh database
     *
     * @param username
     */
    private void checkIfUsernameExists(final String username) {
        Log.d(TAG, "checkIfUsernameExists: Checking if  " + username + " already exists.");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_users))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()) {
                    //add the username
                    mFirebaseMethods.updateUsername(username);
                    Toast.makeText(getActivity(), "saved username.", Toast.LENGTH_SHORT).show();

                }
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    if (singleSnapshot.exists()) {
                        Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + singleSnapshot.getValue(User.class).getUsername());
                        Toast.makeText(getActivity(), "That username already exists.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setProfileWidgets(UserSettings userSettings) {
            Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());
            Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getUser().getEmail());
            Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getUser().getPhone_number());
            Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getUser().getSocialMedia());

            mUserSettings = userSettings;
            //User user = userSettings.getUser();
           UserAccountSettings settings = userSettings.getSettings();

            if(userSettings.getUser().getPhone_number() != 0){
                mPhoneNumber.setText(String.valueOf(userSettings.getUser().getPhone_number()));
            }else{
                mPhoneNumber.setText("");
            }
            if(userSettings.getUser().getSocialMedia() != null){
                mSocialMedia.setText(userSettings.getUser().getSocialMedia());
            }else{
                mSocialMedia.setText("");
            }
            if(userSettings.getUser().getCommunication() != null){
                mCommunication.setText(userSettings.getUser().getCommunication());
            }else{
                mCommunication.setText("");
            }
            if(userSettings.getUser().getWork() != null){
                mWork.setText(userSettings.getUser().getWork());
            }else{
                mWork.setText("");
            }
            if(userSettings.getUser().getEmail() != null){
                mEmail.setText(userSettings.getUser().getEmail());
            }else{
                mEmail.setText("");
            }


             mDisplayName.setText(userSettings.getSettings().getDisplay_name());
                mUsername.setText(userSettings.getSettings().getUsername());
                mWhatsapp.setText(userSettings.getSettings().getWhatsapp());
                mEducation.setText(userSettings.getSettings().getDescription());
                mEmail.setText(userSettings.getUser().getEmail());
                mPhoneNumber.setText(String.valueOf(userSettings.getUser().getPhone_number()));
                mWork.setText(userSettings.getUser().getWork());
                mSocialMedia.setText(userSettings.getUser().getSocialMedia());
                mCommunication.setText(userSettings.getUser().getCommunication());
                UniversalImageLoader.setImage(userSettings.getSettings().getProfile_photo(), mProfilePhoto, null, "");

            mChangeProfilePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: changing profile photo");
                    Intent intent = new Intent(getActivity(), ShareActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //268435456
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            });
    }
       /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        userID = mAuth.getCurrentUser().getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //retrieve user information from the database
                setProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));

                //retrieve images for the user in question

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }




}
