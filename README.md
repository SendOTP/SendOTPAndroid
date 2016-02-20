# SendOtp
The SendOtp Verification SDK makes verifying phone numbers easy. This SDK supports the verification of phone numbers via SMS &amp; calls.
![alt tag](http://www.androidhive.info/wp-content/uploads/2015/07/android-sms-verification-like-whatsapp-viber-2.png)

Follow these steps and change your application's build.gradle


Step 1. Add in repositories 
```javascript

repositories {
    maven {
        url "http://dl.bintray.com/rakshitsoni02/Android-Libs"
    }
}
 ```

Step 2. Add below in dependency
 ```javascript
   compile 'com.msg91.sendotp.library:library:2.2'
```

Step 3. Sync gradle

After successfully adding dependency Refer below sample 

Step 4. Register your Application at http://sendotp.msg91.com/ then implement 'VerificationListener'
        in your activity or fragment
        

Step 5. On your button click (Requesting OTP) (get result in Initiate callback)
```javascript
Config config = SendOtpVerification.config().context(getApplicationContext())
        .build();
         mVerification = SendOtpVerification.createSmsVerification(config, phoneNumber, this, countryCode, KEYWORD);
             mVerification.initiate();
```     
        keyword is optional you can also pass blank
        
Step 6. On verify button click call below method (get result in verification callback)

            mVerification.verify(code); 
        
Step 7. You will get result of request in callbacks like:

```javascript
   @Override
   public void onInitiated() {
     Log.d(TAG, "Initialized!");
     //  showProgress();
   }
 
   @Override
   public void onInitiationFailed(Exception exception) {
     Log.e(TAG, "Verification initialization failed: " + exception.getMessage());
     hideProgressBarAndShowMessage(R.string.failed);
   }
 
 @Override
   public void onVerified(String response) {
     Log.d(TAG, "Verified!\n" + response);
     hideProgressBarAndShowMessage(R.string.verified);
     showCompleted();
   }
 
   @Override
   public void onVerificationFailed(Exception exception) {
     Log.e(TAG, "Verification failed: " + exception.getMessage());
     hideProgressBarAndShowMessage(R.string.failed);
   }




