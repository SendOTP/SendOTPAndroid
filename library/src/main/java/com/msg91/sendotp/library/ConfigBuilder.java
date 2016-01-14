package com.msg91.sendotp.library;

import android.content.Context;

public abstract interface ConfigBuilder {
  public abstract ConfigBuilder context(Context paramContext);

  public abstract ConfigBuilder applicationKey(String paramString);

  public abstract ConfigBuilder environmentHost(String paramString);

  public abstract Config build();
}


