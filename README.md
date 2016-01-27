# SendOtp
The SendOtp Verification SDK makes verifying phone numbers easy. This SDK supports the verification of phone numbers via SMS &amp; calls.

Add following in you application's build.gradle


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
   compile 'com.msg91.sendotp.library:library:1.0'
```

Step 3. Sync gradle

After successfully adding dependency Refer below sample 

Step 4. implement 'VerificationListener' and change APPLICATION_KEY in you activity
        Register and get Application Key from http://sendotp.msg91.com/

Step 5. On your button click (Requesting OTP) and get result in Initiate callback
```javascript
Config config = SendOtpVerification.config().context(getApplicationContext())
        .build();
         mVerification = SendOtpVerification.createSmsVerification(config, phoneNumber, this, countryCode, KEYWORD);
             mVerification.initiate();
```     
        keyword is optional you can also pass blank
Step 6. On verify button click or you detect sms call below method and get result in verification callback
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




