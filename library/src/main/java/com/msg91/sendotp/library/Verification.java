package com.msg91.sendotp.library;

public abstract interface Verification {
  public abstract void initiate();

  public abstract void verify(String paramString);
}


