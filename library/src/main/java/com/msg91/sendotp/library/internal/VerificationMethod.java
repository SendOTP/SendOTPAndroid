package com.msg91.sendotp.library.internal;

import android.content.Context;
import android.os.AsyncTask;

import com.msg91.sendotp.library.InvalidInputException;
import com.msg91.sendotp.library.ServiceErrorException;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

import okhttp3.Response;

public class VerificationMethod implements Verification {
  private static final String TAG = "VerificationMethod";
  protected Context mContext;
  protected VerificationListener mListener;
  protected ApiService mApiService;
  protected String mNumber, mCountry;
  protected CallbackHandler mCallbackHandler;

  public VerificationMethod(Context context, String number, ApiService apiService, VerificationListener listener, CallbackHandler handler, String country) throws IllegalArgumentException {
    this.mContext = context;
    this.mListener = listener;
    this.mNumber = number;
    this.mApiService = apiService;
    this.mCallbackHandler = handler;
    this.mCountry = country;
    if (this.mNumber == null) {
      throw new IllegalArgumentException("Number string cannot be null.");
    } else if (this.mListener == null) {
      throw new IllegalArgumentException("Verification listener cannot be null.");
    }
  }

  public void initiate() {
    if (this.mNumber.isEmpty()) {
      this.callbackInitiationFailed(new InvalidInputException("Number cannot be empty"));
    } else {
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
            //TODO temp exception
            callbackInitiated();
//            callbackVerificationFailed(new CodeInterceptionException("not found"));
          } else {
            callbackInitiationFailed(new Exception(response.message()));
          }
        }
      }.execute();
    }
  }

  public void verify(String code) {
    this.verify(code, "manual");
  }

  protected void verify(final String code, final String source) {
    if (this.mNumber.isEmpty()) {
      this.callbackVerificationFailed(new InvalidInputException("Number cannot be empty."));
    } else if (code != null && !code.isEmpty()) {
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
              VerificationMethod.this.onVerificationResult(response);
            } catch (Exception e) {
              callbackVerificationFailed(new VerificationException(response.message()));
              e.printStackTrace();
            }
          } else {
            callbackVerificationFailed(new InvalidInputException("Invalid verification code"));
          }

        }
      }.execute();
    } else {
      this.callbackVerificationFailed(new InvalidInputException("Verification code cannot be null or empty."));
    }
  }

//  protected abstract JSONObject createVerifyParameters(String var1, String var2);
////TODO broadcast handled
//  public void onVerificationCode(String code, String source) {
//    this.verify(code);
//  }


  protected void onVerificationResult(Response response) {
    try {
      if (response.code() == 200)
        this.callbackVerified();
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

  protected void callbackVerificationFailed(final Exception e) {
    this.runOnCallbackHandler(new Runnable() {
      public void run() {
        VerificationMethod.this.mListener.onVerificationFailed(e);
      }
    });
  }

  protected void callbackVerified() {
    this.runOnCallbackHandler(new Runnable() {
      public void run() {
        VerificationMethod.this.mListener.onVerified();
      }
    });
  }

  protected void callbackInitiated() {
    this.runOnCallbackHandler(new Runnable() {
      public void run() {
        VerificationMethod.this.mListener.onInitiated();
      }
    });
  }

  private void runOnCallbackHandler(Runnable runnable) {
    this.mCallbackHandler.post(runnable);
  }
}
