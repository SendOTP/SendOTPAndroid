package com.msg91.sendotp.library.internal;

public interface VerificationCodeListener {
  void onVerificationCode(String var1, String var2);

  void onVerificationCodeError(Exception var1);
}
