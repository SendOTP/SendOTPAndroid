package com.msg91.sendotp.library.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiService {
  String secretKey, packageName, deviceId;
  Context context;
  public static final MediaType JSON
      = MediaType.parse("application/json; charset=utf-8");

  public ApiService(Context context) {
    this.context = context;
    this.packageName = context.getPackageName().trim();
    this.deviceId = getDeviceId().trim();
    this.secretKey = getSecretKey().trim();
  }

  public Response generateRequest(String mobileNumber, String countryCode) {
    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("countryCode", countryCode);
      jsonObject.put("mobileNumber", mobileNumber);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return getResponse("https://sendotp.msg91.com/api/generateOTP", jsonObject);
  }

  public Response verifyRequest(String mobileNumber, String countryCode, String oneTimePassword) {
    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("oneTimePassword", oneTimePassword);
      jsonObject.put("countryCode", countryCode);
      jsonObject.put("mobileNumber", mobileNumber);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return getResponse("https://sendotp.msg91.com/api/verifyOTP", jsonObject);
  }


  private String getDeviceId() {
    return
        Settings.Secure.getString(context.getContentResolver(),
            Settings.Secure.ANDROID_ID);
  }

  private String getSecretKey() {
    MessageDigest md = null;
    try {
      PackageInfo info = context.getPackageManager().getPackageInfo(
          context.getPackageName(),
          PackageManager.GET_SIGNATURES);
      for (Signature signature : info.signatures) {
        md = MessageDigest.getInstance("SHA");
        md.update(signature.toByteArray());
      }
    } catch (PackageManager.NameNotFoundException e) {

    } catch (NoSuchAlgorithmException e) {

    }
    Log.i("SecretKey = ",Base64.encodeToString(md.digest(), Base64.DEFAULT));
    return Base64.encodeToString(md.digest(), Base64.DEFAULT);
  }

  private Response getResponse(String url, JSONObject json) {
    RequestBody body = RequestBody.create(JSON, String.valueOf(json));
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
        .url(url)
        .post(body)
        .addHeader("Package-Name", packageName)
        .addHeader("Device-Id", deviceId)
        .addHeader("Secret-Key", secretKey)
        .build();
    Response httpResponse = null;
    try {
      httpResponse = client.newCall(request).execute();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return httpResponse;
  }
}
