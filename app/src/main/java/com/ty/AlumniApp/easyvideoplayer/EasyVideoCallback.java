package com.ty.AlumniApp.easyvideoplayer;

import android.net.Uri;

public interface EasyVideoCallback {

  void onStarted(EasyVideoPlayer player);

  void onPaused(EasyVideoPlayer player);

  void onPreparing(EasyVideoPlayer player);

  void onPrepared(EasyVideoPlayer player);

  void onBuffering(int percent);

  void onError(EasyVideoPlayer player, Exception e);

  void onCompletion(EasyVideoPlayer player);

  void onRetry(EasyVideoPlayer player, Uri source);

  void onSubmit(EasyVideoPlayer player, Uri source);

  void onClickVideoFrame(EasyVideoPlayer player);

  void addToStory(EasyVideoPlayer player, Uri source);

  void saveStory(EasyVideoPlayer player, Uri source);
}
