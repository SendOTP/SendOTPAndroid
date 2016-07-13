SendOTP Android Sdk!
===================


The  **SendOtp** Verification SDK makes verifying phone numbers easy. SDK supports the verification of phone numbers via **SMS & Calls**.

----------

Getting started
===============

Gradle
------

Just add the "compile 'com.msg91.sendotp.library:library:2.7'" in your build.gradle of your module.

    dependencies {
    ...
    compile 'com.msg91.sendotp.library:library:2.7'
    ...
    }
Maven
------
grab via Maven:

    <dependency>
       <groupId>com.msg91.sendotp.library</groupId>
       <artifactId>library</artifactId>
       <version>2.7</version>
       <type>pom</type>
    </dependency>

> **Note:**

> - Login at [sendOTP](sendotp.msg91.com) 
> - or to create account Sign Up at [MSG91](https://msg91.com/) to use sendOTP services.

#### <i class="icon-file"></i> Create New Application

After login at [sendOTP](sendotp.msg91.com) click on <i class="icon-folder-open"></i> create new application fill app details


> **Note:**

> - Select Android as Application Type
> - Package Name is unique and it's not <i class="icon-pencil"></i> editable.
> - In the **key** field set your debug version key hash while you are in **development** mode.
 > - In the **key** field set your release version key hash while you are in **production** mode.
#### <i class="icon-folder-open"></i> To send OTP request
> **Note:**
>  implement '**VerificationListener**' in your activity or fragment to get result call backs.
 
create instance of Verification as a class variable and initialise it by passing country code and mobile number.

    mVerification = SendOtpVerification.createSmsVerification(this, "set_phone_number", this, "set_country_code");
    
             mVerification.initiate(); //sending otp on given number

#### <i class="icon-pencil"></i> Verify OTP request

Use instance of Verification to call verify method pass received OTP here.

    mVerification.verify("verification_code"); //verifying otp for given number

#### <i class="icon-file"></i> Getting result call backs

As a Result of each request any one of method will triggered.

      @Override
     public void onInitiated(String response) {
       Log.d(TAG, "Initialized!" + response);
       //control comes when OTP successfully sent.
     }

	   @Override
	   public void onInitiationFailed(Exception exception) {
	     Log.e(TAG, "Verification initialization failed: " + exception.getMessage());
	      //control comes when OTP  failed successfully sent.
	   }
	
	 @Override
	   public void onVerified(String response) {
	     Log.d(TAG, "Verified!\n" + response);
	        //control comes when OTP  verified successfully.
	   }
	
	   @Override
	   public void onVerificationFailed(Exception exception) {
	     Log.e(TAG, "Verification failed: " + exception.getMessage());
	     //control comes when OTP  verification failed.
	   }
FAQ
------

> - If OTP not working in release mode but working in debug/test environment please replace your secret key of release version at [sendOTP](sendotp.msg91.com) [to get secret key search logs of android studio by 'UserHeaders' or use [this](http://help.msg91.com/article/181-how-to-generate-key-hash-for-android) code snippet to generate]
> - If you are getting exception message 'Request Unsuccessful Try Again' it is because device not supporting 'https' , Solution is to set extra parameter **true** in createSmsVerification like :
> 
> mVerification = SendOtpVerification.createSmsVerification(this, phoneNumber, this, countryCode, **true**);

<img src="https://cloud.githubusercontent.com/assets/8371249/13195073/bcf22e40-d7cd-11e5-9891-f1f656d9ff45.png" width="270">    <img src="https://cloud.githubusercontent.com/assets/8371249/13195075/bcf7b6b2-d7cd-11e5-8e58-0a0c8e8849de.png" width="270">  <img src="https://cloud.githubusercontent.com/assets/8371249/13195074/bcf257f8-d7cd-11e5-970e-78ee034df112.png" width="270">










