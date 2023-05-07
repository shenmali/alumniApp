package com.ty.AlumniApp.materialcamera;

import android.app.Fragment;
import androidx.annotation.NonNull;

import com.ty.AlumniApp.materialcamera.internal.BaseCaptureActivity;
import com.ty.AlumniApp.materialcamera.internal.CameraFragment;

public class CaptureActivity extends BaseCaptureActivity {

  @Override
  @NonNull
  public Fragment getFragment() {
    return CameraFragment.newInstance();
  }
}
