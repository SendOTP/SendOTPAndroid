# SendOtp
The SendOtp Verification SDK makes verifying phone numbers easy. This SDK supports the verification of phone numbers via SMS &amp; calls.

![alt tag](https://cloud.githubusercontent.com/assets/8371249/13195073/bcf22e40-d7cd-11e5-9891-f1f656d9ff45.png)
![alt tag](https://cloud.githubusercontent.com/assets/8371249/13195075/bcf7b6b2-d7cd-11e5-8e58-0a0c8e8849de.png)
![alt tag](https://cloud.githubusercontent.com/assets/8371249/13195074/bcf257f8-d7cd-11e5-970e-78ee034df112.png)

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




