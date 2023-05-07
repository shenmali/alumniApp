package com.ty.AlumniApp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.ty.AlumniApp.Home.HomeActivity;
import com.ty.AlumniApp.Likes.LikesActivity;
import com.ty.AlumniApp.Profile.ProfileActivity;
import com.ty.AlumniApp.R;
import com.ty.AlumniApp.Search.SearchActivity;
import com.ty.AlumniApp.Share.ShareActivity;


public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationView BottomNavigationView){
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");
//        BottomNavigationView.enableAnimation(false);
//        BottomNavigationView.enableItemShiftingMode(false);
//        BottomNavigationView.enableShiftingMode(false);
//        BottomNavigationView.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, final Activity callingActivity, BottomNavigationView view){
        view.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.ic_house:
                    Intent intent1 = new Intent(context, HomeActivity.class);//ACTIVITY_NUM = 0
                    context.startActivity(intent1);
                    callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;

                case R.id.ic_search:
                    Intent intent2  = new Intent(context, SearchActivity.class);//ACTIVITY_NUM = 1
                    context.startActivity(intent2);
                    callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;

                case R.id.ic_circle:
                    Intent intent3 = new Intent(context, ShareActivity.class);//ACTIVITY_NUM = 2
                    context.startActivity(intent3);
                    callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;

                case R.id.ic_alert:
                    Intent intent4 = new Intent(context, LikesActivity.class);//ACTIVITY_NUM = 3
                    context.startActivity(intent4);
                    callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;

                case R.id.ic_android:
                    Intent intent5 = new Intent(context, ProfileActivity.class);//ACTIVITY_NUM = 4
                    context.startActivity(intent5);
                    callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }


            return false;
        });
    }
}
