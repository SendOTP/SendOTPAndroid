package com.msg91.sendotp.library.internal;

public class CodeInterceptionException extends VerificationException {
  public CodeInterceptionException(String customMessage) {
    super(customMessage);
  }
}
