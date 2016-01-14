package com.msg91.sendotp.library.internal;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiService {
  String mAppKey;
  public static final MediaType JSON
      = MediaType.parse("application/json; charset=utf-8");

  public ApiService(String appKey) {
    mAppKey = appKey;
  }

  public Response generateRequest(String mobileNumber, String countryCode) {

    Uri.Builder builder = new Uri.Builder();
    builder.scheme("http")
        .authority("sendotp.msg91.com")
        .appendPath("api")
        .appendPath("generateOTP");
    //.appendQueryParameter("countryCode", countryCode)
    //.appendQueryParameter("mobileNumber", mobileNumber);
    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("countryCode", countryCode);
      jsonObject.put("mobileNumber", mobileNumber);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    String url = builder.build().toString();
    return getResponse(url, jsonObject);
  }

  public Response verifyRequest(String mobileNumber, String countryCode, String oneTimePassword) {

    Uri.Builder builder = new Uri.Builder();
    builder.scheme("http")
        .authority("sendotp.msg91.com")
        .appendPath("api")
        .appendPath("verifyOTP");
    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("oneTimePassword", oneTimePassword);
      jsonObject.put("countryCode", countryCode);
      jsonObject.put("mobileNumber", mobileNumber);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    String url = builder.build().toString();
    return getResponse(url, jsonObject);
  }


  private Response getResponse(String url, JSONObject json) {
    RequestBody body = RequestBody.create(JSON, String.valueOf(json));
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
        .url(url)
        .post(body)
        .addHeader("application-Key", mAppKey)
        .build();
    Response httpResponse = null;
    try {
      httpResponse = client.newCall(request).execute();
      httpResponse.code();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Log.e("Response", "" + httpResponse.toString());
    return httpResponse;
  }
}
