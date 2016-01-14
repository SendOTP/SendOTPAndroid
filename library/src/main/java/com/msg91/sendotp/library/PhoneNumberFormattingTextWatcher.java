package com.msg91.sendotp.library;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;

import com.msg91.sendotp.library.internal.PhoneNumberUtilsReflective;

@SuppressLint({"NewApi"})
public class PhoneNumberFormattingTextWatcher implements TextWatcher {
  private android.telephony.PhoneNumberFormattingTextWatcher mDelegate;

  public PhoneNumberFormattingTextWatcher(String countryIso) {
    if(isApi21AndLater()) {
      this.mDelegate = new android.telephony.PhoneNumberFormattingTextWatcher(countryIso);
    } else {
      this.mDelegate = new android.telephony.PhoneNumberFormattingTextWatcher();
      PhoneNumberUtilsReflective.setFormatterCountryIso(this.mDelegate, countryIso);
    }
  }

  public void afterTextChanged(Editable s) {
    this.mDelegate.afterTextChanged(s);
  }

  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    this.mDelegate.beforeTextChanged(s, start, count, after);
  }

  public void onTextChanged(CharSequence s, int start, int before, int count) {
    this.mDelegate.onTextChanged(s, start, before, count);
  }

  private static boolean isApi21AndLater() {
    return Build.VERSION.SDK_INT >= 21;
  }
}
