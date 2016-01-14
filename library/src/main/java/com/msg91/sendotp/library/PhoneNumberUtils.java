package com.msg91.sendotp.library;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.msg91.sendotp.library.internal.PhoneNumberUtilsReflective;

import java.util.Locale;

public class PhoneNumberUtils {
  public PhoneNumberUtils() {
  }

  public static String formatNumberToE164(String number, String countryIso) {
    return PhoneNumberUtilsReflective.numberInE164Format(number, countryIso);
  }


  public static boolean isPossibleNumber(String number, String countryIso) {
    return PhoneNumberUtilsReflective.isPossibleNumber(number, countryIso);
  }

  public static String getDefaultCountryIso(Context tm) {
    TelephonyManager tm1;
    return (tm1 = (TelephonyManager) tm.getSystemService("phone")).getSimState() == 5 ? tm1.getSimCountryIso().toUpperCase() : Locale.getDefault().getCountry();
  }
}
