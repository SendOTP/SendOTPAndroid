package com.msg91.sendotp.library.internal;

import android.content.Context;

import com.msg91.sendotp.library.Config;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

public class SmsVerificationFactory {

    public static Verification create(Context config, String number, VerificationListener listener, String country, Boolean useHttp) {
        ApiService apiService = new ApiService(config, useHttp);
        return new VerificationMethod(config, number, apiService, listener, new AndroidCallbackHandler(), country);
    }
}
