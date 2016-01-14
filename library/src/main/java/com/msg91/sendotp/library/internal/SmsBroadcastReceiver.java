package com.msg91.sendotp.library.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

class SmsBroadcastReceiver extends BroadcastReceiver {
  private final String mSmsKeyWord;

  SmsBroadcastReceiver(String mSmsKeyWord) {
    this.mSmsKeyWord = mSmsKeyWord;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    final Bundle bundle = intent.getExtras();
    try {
      if (bundle != null) {
        final Object[] pdusObj = (Object[]) bundle.get("pdus");
        for (int i = 0; i < pdusObj.length; i++) {
          String format = intent.getStringExtra("format");
          SmsMessage currentMessage =
              SmsMessage.createFromPdu((byte[]) pdusObj[i],format);
          String phoneNumber = currentMessage.getDisplayOriginatingAddress();
          String senderNum = phoneNumber;
          String message = currentMessage.getDisplayMessageBody();
          try {
            if (senderNum.endsWith(mSmsKeyWord)) {
              int length = message.length();
              try {
                //TODO here change
                // LoginActivity.setCode(message.substring(length - 4, length));
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
}
