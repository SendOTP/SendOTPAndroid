package com.msg91.sendotp.library.internal;

import android.content.Context;

import com.msg91.sendotp.library.Config;

public class DefaultConfig implements Config {
  private Context mContext;
  private String mApplicationKey;
  private String mEnvironmentHost;

  public DefaultConfig(Context context, String applicationKey, String environmentHost) {
    this.mContext = context;
    this.mApplicationKey = applicationKey;
    this.mEnvironmentHost = environmentHost;
  }

  public Context getContext() {
    return this.mContext;
  }

  public String getApplicationKey() {
    return this.mApplicationKey;
  }

  public String getEnvironmentHost() {
    return this.mEnvironmentHost;
  }
}
