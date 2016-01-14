package com.msg91.sendotp.library.internal;


import android.os.Handler;
import android.os.Looper;

public class AndroidCallbackHandler implements CallbackHandler {
  Handler mHandler;

  public AndroidCallbackHandler() {
    Looper looper;
    if ((looper = Looper.myLooper()) == null) {
      throw new IllegalThreadStateException("A Looper must be associated with this thread.");
    } else {
      this.mHandler = new Handler(looper);
    }
  }

  public boolean post(Runnable r) {
    return this.mHandler.post(r);
  }

  public void postDelayed(Runnable r, int delay) {
    this.mHandler.postDelayed(r, (long) delay);
  }

  public void removeCallbacksAndMessages() {
    this.mHandler.removeCallbacksAndMessages((Object) null);
  }
}
