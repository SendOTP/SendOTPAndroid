package com.msg91.sendotp.library;

public abstract interface VerificationListener {
  public abstract void onInitiated(String response);

  public abstract void onInitiationFailed(Exception paramException);

  public abstract void onVerified(String response);

  public abstract void onVerificationFailed(Exception paramException);
}

