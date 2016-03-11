package com.msg91.sendotp.library.internal;

import com.msg91.sendotp.library.Config;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

public class SmsVerificationFactory {

  public static Verification create(Config config, String number, VerificationListener listener, String country) {
    ApiService apiService = new ApiService(config.getContext());
    return new VerificationMethod(config.getContext(), number, apiService, listener, new AndroidCallbackHandler(), country);
  }
}
