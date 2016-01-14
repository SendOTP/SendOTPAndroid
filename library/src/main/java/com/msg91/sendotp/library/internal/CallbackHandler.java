package com.msg91.sendotp.library.internal;


public interface CallbackHandler {
  boolean post(Runnable var1);

  void postDelayed(Runnable var1, int var2);

  void removeCallbacksAndMessages();
}
