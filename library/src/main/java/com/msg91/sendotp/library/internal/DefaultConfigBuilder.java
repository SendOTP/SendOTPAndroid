package com.msg91.sendotp.library.internal;


import android.content.Context;

import com.msg91.sendotp.library.Config;
import com.msg91.sendotp.library.ConfigBuilder;

public class DefaultConfigBuilder implements ConfigBuilder {
  private Context mContext;
  private String mApplicationKey;
  private String mEnvironmentHost = "https://api.sinch.com";

  public DefaultConfigBuilder() {
  }

  public ConfigBuilder context(Context newContext) {
    if (newContext == null) {
      throw new IllegalArgumentException("Context cannot be null.");
    } else {
      this.mContext = newContext;
      return this;
    }
  }

  public ConfigBuilder applicationKey(String newApplicationKey) {
    if (newApplicationKey != null && !newApplicationKey.isEmpty()) {
      this.mApplicationKey = newApplicationKey;
      return this;
    } else {
      throw new IllegalArgumentException("Application key cannot be null or empty.");
    }
  }

  public ConfigBuilder environmentHost(String newEnvironmentHost) {
    if (newEnvironmentHost != null && !newEnvironmentHost.isEmpty()) {
      this.mEnvironmentHost = newEnvironmentHost;
      return this;
    } else {
      throw new IllegalArgumentException("Environment host cannot be null or empty.");
    }
  }

  public Config build() {
    return new DefaultConfig(this.mContext, this.mApplicationKey, this.mEnvironmentHost);
  }
}
