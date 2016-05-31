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
   compile 'com.msg91.sendotp.library:library:2.5'
```

Step 3. Register your Application at http://sendotp.msg91.com/
        Set secretKey on panel according to Debug/Release environment
        To get SecretKey visit http://help.msg91.com/article/181-how-to-generate-key-hash-for-android 
        
Step 4. implement 'VerificationListener'
                 in your activity or fragment.

Step 5. To Generate OTP  (Requesting OTP) (get result in Initiate callbacks) mVerification is instance of Verification
```javascript
Config config = SendOtpVerification.config().context(getApplicationContext())
        .build();
         mVerification = SendOtpVerification.createSmsVerification(config, phoneNumber, this, countryCode);
             mVerification.initiate();
```
get countryCode info https://en.wikipedia.org/wiki/List_of_country_calling_codes

Step 6. To Verify OTP (get result in verification callback)

            mVerification.verify(code); 
        
Step 7. You will get result of request in callbacks like:

```javascript
   @Override
     public void onInitiated(String response) {
       Log.d(TAG, "Initialized!" + response);
     }
 
   @Override
   public void onInitiationFailed(Exception exception) {
     Log.e(TAG, "Verification initialization failed: " + exception.getMessage());
   }
 
 @Override
   public void onVerified(String response) {
     Log.d(TAG, "Verified!\n" + response);
   }
 
   @Override
   public void onVerificationFailed(Exception exception) {
     Log.e(TAG, "Verification failed: " + exception.getMessage());
   }




