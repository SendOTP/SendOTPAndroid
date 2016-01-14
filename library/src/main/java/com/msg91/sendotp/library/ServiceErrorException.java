package com.msg91.sendotp.library;

import com.msg91.sendotp.library.internal.VerificationException;

public class ServiceErrorException extends VerificationException {
  private static String DEFAULT_MESSAGE = "General sendOtp backend server error.";
  private int mStatusCode;

  public ServiceErrorException() {
    super(DEFAULT_MESSAGE);
  }

  public ServiceErrorException(String customMessage) {
    super(customMessage);
  }

  public ServiceErrorException(int statusCode, String customMessage) {
    super(customMessage);
    this.mStatusCode = statusCode;
  }

  public int getStatusCode() {
    return this.mStatusCode;
  }
}
