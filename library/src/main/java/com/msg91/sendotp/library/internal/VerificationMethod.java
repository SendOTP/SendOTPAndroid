package com.msg91.sendotp.library.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.msg91.sendotp.library.InvalidInputException;
import com.msg91.sendotp.library.ServiceErrorException;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Response;

public class VerificationMethod implements Verification {
  private static final String TAG = "VerificationMethod";
  protected Context mContext;
  protected VerificationListener mListener;
  protected ApiService mApiService;
  protected String mNumber, mCountry, mKeyWord;
  protected CallbackHandler mCallbackHandler;
  protected Boolean mVerified;
  protected ConnectionDetector cd;
  protected BroadcastReceiver receiver;

  public VerificationMethod(Context context, String number, ApiService apiService, VerificationListener listener, CallbackHandler handler, String country, String keyWord) throws IllegalArgumentException {
    this.mContext = context;
    this.mListener = listener;
    this.mNumber = number;
    this.mApiService = apiService;
    this.mCallbackHandler = handler;
    this.mCountry = country;
    this.mKeyWord = keyWord;
    this.mVerified = false;
    cd = new ConnectionDetector(mContext);
    if (this.mNumber == null) {
      throw new IllegalArgumentException("Number string cannot be null.");
    } else if (this.mListener == null) {
      throw new IllegalArgumentException("Verification listener cannot be null.");
    }
  }


  public void initiate() {
    if (cd.isNetworkAvailable()) {
      IntentFilter filter = new IntentFilter();
      filter.addAction("android.provider.Telephony.SMS_RECEIVED");
      filter.addAction(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED);
      mContext.registerReceiver(setUpReceiver(), filter);
      new AsyncTask<Void, Void, Void>() {
        Response response;

        @Override
        protected Void doInBackground(Void... params) {
          response = mApiService.generateRequest(mNumber, mCountry);
          return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
          super.onPostExecute(aVoid);
          if (response.code() == 200) {
            try {
              callbackInitiated(response.body().string());
            } catch (IOException e) {
              e.printStackTrace();
              Log.e("IOException", e.getMessage());
            }
          } else {
            callbackInitiationFailed(new Exception(response.message()));
          }
          response.body().close();
        }
      }.execute();
    } else {
      Log.e("No Internet", "Please check Network");
    }
  }


  //TODO internet check
  public void verify(String code) {
    if (cd.isNetworkAvailable()) {
      this.verify(code, "manual");
    } else {
      Log.e("No Internet", "Please check Network");
    }
  }

  protected void verify(final String code, final String source) {
    if (this.mNumber.isEmpty()) {
      this.callbackVerificationFailed(new InvalidInputException("Number cannot be empty."));
    } else if (code != null && !code.isEmpty() && !mVerified) {
      new AsyncTask<Void, Void, Void>() {
        Response response;

        @Override
        protected Void doInBackground(Void... params) {
          response = mApiService.verifyRequest(mNumber, mCountry, code);
          return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
          super.onPostExecute(aVoid);
          if (response.code() == 200) {
            try {
              mVerified = true;
              VerificationMethod.this.onVerificationResult(response);
            } catch (Exception e) {
              callbackVerificationFailed(new VerificationException(response.message()));
              e.printStackTrace();
            }
          } else {
            callbackVerificationFailed(new InvalidInputException("Invalid verification code"));
            response.body().close();

          }
        }
      }.execute();
    } else {
      this.callbackVerificationFailed(new InvalidInputException("Verification code cannot be null or empty."));
    }
  }

  protected void onVerificationResult(Response response) {
    try {
      if (response.code() == 200)
        this.callbackVerified(response);
    } catch (Exception var2) {
      this.callbackVerificationFailed(new ServiceErrorException("SendOtp backend service error: cannot parse success reply from server."));
    }
  }

  protected void callbackInitiationFailed(final Exception e) {
    this.runOnCallbackHandler(new Runnable() {
      public void run() {
        VerificationMethod.this.mListener.onInitiationFailed(e);
      }
    });
  }

  public void disconnectCall() {
    try {

      String serviceManagerName = "android.os.ServiceManager";
      String serviceManagerNativeName = "android.os.ServiceManagerNative";
      String telephonyName = "com.android.internal.telephony.ITelephony";
      Class<?> telephonyClass;
      Class<?> telephonyStubClass;
      Class<?> serviceManagerClass;
      Class<?> serviceManagerNativeClass;
      Method telephonyEndCall;
      Object telephonyObject;
      Object serviceManagerObject;
      telephonyClass = Class.forName(telephonyName);
      telephonyStubClass = telephonyClass.getClasses()[0];
      serviceManagerClass = Class.forName(serviceManagerName);
      serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
      Method getService = // getDefaults[29];
          serviceManagerClass.getMethod("getService", String.class);
      Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
      Binder tmpBinder = new Binder();
      tmpBinder.attachInterface(null, "fake");
      serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
      IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
      Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
      telephonyObject = serviceMethod.invoke(null, retbinder);
      telephonyEndCall = telephonyClass.getMethod("endCall");
      telephonyEndCall.invoke(telephonyObject);

    } catch (Exception e) {
      //   e.printStackTrace();
      Log.e("IncomingCallReceiver",
          "FATAL ERROR: could not connect to telephony subsystem");
      Log.e("IncomingCallReceiver", "Exception object: " + e);
    }
  }

  protected void callbackVerificationFailed(final Exception e) {
    this.runOnCallbackHandler(new Runnable() {
      public void run() {
        try {
          mContext.unregisterReceiver(receiver);
        } catch (IllegalArgumentException e) {
          // e.printStackTrace();
        }
        VerificationMethod.this.mListener.onVerificationFailed(e);
      }
    });
  }

  protected void callbackVerified(final Response response) {
    this.runOnCallbackHandler(new Runnable() {
      public void run() {
        try {
          mContext.unregisterReceiver(receiver);
        } catch (IllegalArgumentException e) {
          //e.printStackTrace();
        }

        try {
          VerificationMethod.this.mListener.onVerified(response.body().string());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }

  protected void callbackInitiated(final String body) {
    this.runOnCallbackHandler(new Runnable() {
      public void run() {
        VerificationMethod.this.mListener.onInitiated(body);
      }
    });
  }

  private void runOnCallbackHandler(Runnable runnable) {
    this.mCallbackHandler.post(runnable);
  }

  private void handleSmsCallBack(Intent intent) {
    final Bundle bundle = intent.getExtras();
    try {
      if (bundle != null) {
        final Object[] pdusObj = (Object[]) bundle.get("pdus");
        for (int i = 0; i < pdusObj.length; i++) {
          String format = intent.getStringExtra("format");
          SmsMessage currentMessage;
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            currentMessage =
                SmsMessage.createFromPdu((byte[]) pdusObj[i], format);
          } else {
            Object pdus[] = (Object[]) bundle.get("pdus");
            currentMessage = SmsMessage.createFromPdu((byte[]) pdus[0]);
          }

          String phoneNumber = currentMessage.getDisplayOriginatingAddress();
          String senderNum = phoneNumber;
          String message = currentMessage.getDisplayMessageBody();
          try {
            if (senderNum.endsWith(mKeyWord)) {
              try {
                Pattern p = Pattern.compile("(\\d+)");
                Matcher m = p.matcher(message);
                m.find();
                verify(m.group(1).trim());
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected BroadcastReceiver setUpReceiver() {
    receiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.provider.Telephony.SMS_RECEIVED")) {
          handleSmsCallBack(intent);
        } else if (action.equals(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
          String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
          if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER).replaceAll("[-+.^:,]", "").trim();
            incomingNumber = incomingNumber.substring(incomingNumber.length() - 6);
            verify(incomingNumber);
            disconnectCall();
          }
        }
      }
    };
    return receiver;
  }
}
