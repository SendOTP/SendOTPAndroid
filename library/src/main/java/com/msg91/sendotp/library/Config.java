package com.msg91.sendotp.library;

import android.content.Context;

public abstract interface Config {
  public abstract Context getContext();

  public abstract String getApplicationKey();

}
