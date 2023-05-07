package com.ty.AlumniApp.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.ty.AlumniApp.Home.HomeActivity;
import com.ty.AlumniApp.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final Boolean CHECK_IF_VERIFIED = false;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Context mContext;
    private ProgressBar mProgressBar;
    private EditText mEmail, mPassword;
    private TextView mPleaseWait;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mPleaseWait = (TextView) findViewById(R.id.pleaseWait);
        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);
        mContext = LoginActivity.this;
        Log.d(TAG, "onCreate: started.");

        mPleaseWait.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);

        setupFirebaseAuth();
        init();

    }

    private boolean isStringNull(String string){
        Log.d(TAG, "isStringNull: checking string if null.");

        return string.equals("");
    }

     /*
    ------------------------------------ Firebase ---------------------------------------------
     */

     private void init(){

         //initialize the button for logging in
         Button btnLogin = (Button) findViewById(R.id.btn_login);
         btnLogin.setOnClickListener(v -> {
             Log.d(TAG, "onClick: attempting to log in.");

             String email = mEmail.getText().toString();
             String password = mPassword.getText().toString();

             if(isStringNull(email) && isStringNull(password)){
                 Toast.makeText(mContext, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
             }else{
                 mProgressBar.setVisibility(View.VISIBLE);
                 mPleaseWait.setVisibility(View.VISIBLE);

                 mAuth.signInWithEmailAndPassword(email, password)
                         .addOnCompleteListener(LoginActivity.this, task -> {
                             Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                             FirebaseUser user = mAuth.getCurrentUser();

                             // If sign in fails, display a message to the user. If sign in succeeds
                             // the auth state listener will be notified and logic to handle the
                             // signed in user can be handled in the listener.
                             if (!task.isSuccessful()) {
                                 Log.w(TAG, "signInWithEmail:failed", task.getException());

                                 Toast.makeText(LoginActivity.this, getString(R.string.auth_failed),
                                         Toast.LENGTH_SHORT).show();
                                 mProgressBar.setVisibility(View.GONE);
                                 mPleaseWait.setVisibility(View.GONE);
                             }
                             else{
                                 Log.d(TAG, "onComplete: success. email is verified.");
                                 Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                 startActivity(intent);
                             }
                         });
             }

         });

         TextView linkSignUp = (TextView) findViewById(R.id.link_signup);
         linkSignUp.setOnClickListener(v -> {
             Log.d(TAG, "onClick: navigating to register screen");
             Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
             startActivity(intent);
         });

         /*
         If the user is logged in then navigate to HomeActivity and call 'finish()'
          */
         if(mAuth.getCurrentUser() != null){
             Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
             startActivity(intent);
             finish();
         }
     }

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {
                // User is signed in
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
            // ...
        };
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
























