package com.msg91.sendotp.library.internal;

public class VerificationException extends Exception {
  public VerificationException(String message) {
    super(message);
  }

  protected VerificationException(String message, Throwable cause) {
    super(message, cause);
  }
}
