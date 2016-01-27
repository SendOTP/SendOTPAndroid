package com.msg91.sendotp.library;

import com.msg91.sendotp.library.internal.DefaultConfigBuilder;
import com.msg91.sendotp.library.internal.SmsVerificationFactory;

public final class SendOtpVerification {

  public static ConfigBuilder config() {
    return new DefaultConfigBuilder();
  }

  public static Verification createSmsVerification(Config config, String number, VerificationListener listener, String country, String keyWord) {
    return SmsVerificationFactory.create(config, number, listener, country, keyWord);
  }
}
