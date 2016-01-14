package com.msg91.sendotp.library.internal;

import android.telephony.PhoneNumberFormattingTextWatcher;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PhoneNumberUtilsReflective {
  private static final String TAG = "PhoneNumberUtils";
  private static final String METHOD_GETASYOUTYPEFORMATTER = "getAsYouTypeFormatter";
  private static final String FIELD_FORMATTER = "mFormatter";
  private static final String CLASS_PHONENUMBERUTIL = "com.android.i18n.phonenumbers.PhoneNumberUtil";
  private static final String METHOD_FORMAT = "format";
  private static final String METHOD_PARSE = "parse";
  private static final String METHOD_ISPOSSIBLENUMBER = "isPossibleNumber";
  private static final String ENUM_PHONENUMBERFORMAT = "com.android.i18n.phonenumbers.PhoneNumberUtil$PhoneNumberFormat";
  private static final String CLASS_PHONENUMBER = "com.android.i18n.phonenumbers.Phonenumber$PhoneNumber";
  private static final String VALUE_E164 = "E164";
  private static final String METHOD_GETINSTANCE = "getInstance";

  public PhoneNumberUtilsReflective() {
  }

  public static void setFormatterCountryIso(PhoneNumberFormattingTextWatcher textWatcher, String formatter) {
    try {
      Class e = getClass("com.android.i18n.phonenumbers.PhoneNumberUtil");
      Object instance = getPhoneNumberUtilInstance();
      Object formatter1 = invokeMethod(getMethod(e, "getAsYouTypeFormatter", new Class[]{String.class}), instance, new Object[]{formatter});
      Field e1 = getField(PhoneNumberFormattingTextWatcher.class, "mFormatter");
      setField(textWatcher, e1, formatter1);
    } catch (PhoneNumberUtilsReflective.VerificationReflectionException var4) {
      (new StringBuilder("Failed to set formatter country code, reflection exception: ")).append(var4.toString());
    } catch (Exception var5) {
      (new StringBuilder("Failed to set formatter country code, exception: ")).append(var5.toString());
    }
  }

  public static String numberInE164Format(String phoneNumber, String countryIso) {
    String formatted = null;
    try {
      Class e = getClass("com.android.i18n.phonenumbers.PhoneNumberUtil");
      Object instance = getPhoneNumberUtilInstance();
      Class phoneNumberFormatClass = getClass("com.android.i18n.phonenumbers.PhoneNumberUtil$PhoneNumberFormat");
      Class phoneNumberClass = getClass("com.android.i18n.phonenumbers.Phonenumber$PhoneNumber");
      Method e1 = getMethod(e, "format", new Class[]{phoneNumberClass, phoneNumberFormatClass});
      Object phoneNumber1 = parsePhoneNumber(phoneNumber, countryIso);
      formatted = (String) invokeMethod(e1, instance, new Object[]{phoneNumber1, Enum.valueOf(phoneNumberFormatClass, "E164")});
    } catch (PhoneNumberUtilsReflective.VerificationReflectionException var7) {
      (new StringBuilder("Format number to E164, reflection exception: ")).append(var7.toString());
    } catch (Exception var8) {
      (new StringBuilder("Format number to E164, exception: ")).append(var8.toString());
    }

    return formatted;
  }

  public static boolean isPossibleNumber(String isPossible, String e) {
    Object isPossible1;
    if ((isPossible1 = parsePhoneNumber(isPossible, e)) == null) {
      return false;
    } else {
      try {
        Class e1 = getClass("com.android.i18n.phonenumbers.PhoneNumberUtil");
        Object instance = getPhoneNumberUtilInstance();
        Class phoneNumberClass = getClass("com.android.i18n.phonenumbers.Phonenumber$PhoneNumber");
        if ((isPossible1 = invokeMethod(getMethod(e1, "isPossibleNumber", new Class[]{phoneNumberClass}), instance, new Object[]{isPossible1})) == null) {
          return false;
        }

        return ((Boolean) isPossible1).booleanValue();
      } catch (PhoneNumberUtilsReflective.VerificationReflectionException var4) {
        (new StringBuilder("isPossibleNumber, reflection exception: ")).append(var4.toString());
      } catch (Exception var5) {
        (new StringBuilder("isPossibleNumber, exception: ")).append(var5.toString());
      }

      return false;
    }
  }

  private static Object parsePhoneNumber(String number, String countryIso) {
    Object phoneNumber = null;

    try {
      Class e = getClass("com.android.i18n.phonenumbers.PhoneNumberUtil");
      Object instance = getPhoneNumberUtilInstance();
      phoneNumber = invokeMethod(getMethod(e, "parse", new Class[]{String.class, String.class}), instance, new Object[]{number, countryIso});
    } catch (PhoneNumberUtilsReflective.VerificationReflectionException var5) {
      (new StringBuilder("Parse phone number, reflection exception: ")).append(var5.toString());
    } catch (Exception var6) {
      (new StringBuilder("Parse phone number, exception: ")).append(var6.toString());
    }

    return phoneNumber;
  }


  private static Object getPhoneNumberUtilInstance() throws PhoneNumberUtilsReflective.VerificationReflectionException {
    return invokeMethod(getMethod(getClass("com.android.i18n.phonenumbers.PhoneNumberUtil"), "getInstance", new Class[0]), (Object) null, new Object[0]);
  }

  private static Object invokeMethod(Method ret, Object instance, Object... params) throws PhoneNumberUtilsReflective.VerificationReflectionException {
    try {
      Object ret1 = ret.invoke(instance, params);
      return ret1;
    } catch (IllegalAccessException var3) {
      throw new PhoneNumberUtilsReflective.VerificationReflectionException(var3);
    } catch (InvocationTargetException var4) {
      throw new PhoneNumberUtilsReflective.VerificationReflectionException(var4.getTargetException());
    }
  }

  private static Method getMethod(Class method, String methodName, Class... params) throws PhoneNumberUtilsReflective.VerificationReflectionException {
    try {
      Method method1 = method.getMethod(methodName, params);
      return method1;
    } catch (NoSuchMethodException var3) {
      throw new PhoneNumberUtilsReflective.VerificationReflectionException(var3);
    }
  }

  private static Class getClass(String clazz) throws PhoneNumberUtilsReflective.VerificationReflectionException {
    try {
      Class clazz1 = Class.forName(clazz);
      return clazz1;
    } catch (ClassNotFoundException var1) {
      throw new PhoneNumberUtilsReflective.VerificationReflectionException(var1);
    }
  }

  private static Field getField(Class field, String fieldName) throws PhoneNumberUtilsReflective.VerificationReflectionException {
    try {
      Field field1 = field.getDeclaredField(fieldName);
      return field1;
    } catch (NoSuchFieldException var2) {
      throw new PhoneNumberUtilsReflective.VerificationReflectionException(var2);
    }
  }

  private static void setField(Object e, Field field, Object value) throws PhoneNumberUtilsReflective.VerificationReflectionException {
    field.setAccessible(true);

    try {
      if (value != null) {
        field.set(e, value);
      }

    } catch (IllegalAccessException var3) {
      throw new PhoneNumberUtilsReflective.VerificationReflectionException(var3);
    }
  }

  private static class VerificationReflectionException extends Exception {
    VerificationReflectionException(Throwable cause) {
      super(cause);
    }
  }
}
