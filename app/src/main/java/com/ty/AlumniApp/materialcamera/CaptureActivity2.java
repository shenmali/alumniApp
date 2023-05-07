package com.ty.AlumniApp.materialcamera;

import android.app.Fragment;
import androidx.annotation.NonNull;

import com.ty.AlumniApp.materialcamera.internal.BaseCaptureActivity;
import com.ty.AlumniApp.materialcamera.internal.Camera2Fragment;


public class CaptureActivity2 extends BaseCaptureActivity {

  @Override
  @NonNull
  public Fragment getFragment() {
    return Camera2Fragment.newInstance();
  }
}
