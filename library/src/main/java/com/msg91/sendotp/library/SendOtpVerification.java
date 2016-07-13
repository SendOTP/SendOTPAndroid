package com.msg91.sendotp.library;

import android.content.Context;

import com.msg91.sendotp.library.internal.DefaultConfigBuilder;
import com.msg91.sendotp.library.internal.SmsVerificationFactory;

public final class SendOtpVerification {

    public static ConfigBuilder config() {
        return new DefaultConfigBuilder();
    }

    public static Verification createSmsVerification(Context config, String number, VerificationListener listener, String country, Boolean useHttp) {
        return SmsVerificationFactory.create(config, number, listener, country, useHttp);
    }

    public static Verification createSmsVerification(Context config, String number, VerificationListener listener, String country) {
        return SmsVerificationFactory.create(config, number, listener, country, false);
    }
}
