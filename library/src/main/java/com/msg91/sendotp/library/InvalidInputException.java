package com.msg91.sendotp.library;

import com.msg91.sendotp.library.internal.VerificationException;

public class InvalidInputException extends VerificationException {
  private static String DEFAULT_MESSAGE = "The number or verification code is invalid.";

  public InvalidInputException() {
    super(DEFAULT_MESSAGE);
  }

  public InvalidInputException(String customMessage) {
    super(customMessage);
  }
}
