package com.msg91.sendotp.sample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.msg91.sendotp.library.Config;
import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;


public class VerificationActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, VerificationListener {

  private static final String TAG = Verification.class.getSimpleName();
  private final String APPLICATION_KEY = "your-app-key";
  private Verification mVerification;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_verification);
    // showProgress();
    enableInputField(true);
    initiateVerification();
  }

  void createVerification(String phoneNumber, boolean skipPermissionCheck, String countryCode) {
    Config config = SendOtpVerification.config().applicationKey(APPLICATION_KEY).context(getApplicationContext())
        .build();
    if (!skipPermissionCheck && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) ==
        PackageManager.PERMISSION_DENIED) {
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 0);
      hideProgressBar();
    } else {
      mVerification = SendOtpVerification.createSmsVerification(config, phoneNumber, this, countryCode);
      mVerification.initiate();
    }
  }

  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

    } else {
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
        Toast.makeText(this, "This application needs permission to read your SMS to automatically verify your "
            + "phone, you may disable the permission once you have been verified.", Toast.LENGTH_LONG)
            .show();
      }
      enableInputField(true);
    }
    initiateVerificationAndSuppressPermissionCheck();
  }

  void initiateVerification() {
    initiateVerification(false);
  }

  void initiateVerificationAndSuppressPermissionCheck() {
    initiateVerification(true);
  }

  void initiateVerification(boolean skipPermissionCheck) {
    Intent intent = getIntent();
    if (intent != null) {
      String phoneNumber = intent.getStringExtra(MainActivity.INTENT_PHONENUMBER);
      String countryCode = intent.getStringExtra(MainActivity.INTENT_COUNTRY_CODE);
      TextView phoneText = (TextView) findViewById(R.id.numberText);
      phoneText.setText("+" + countryCode + phoneNumber);
      createVerification(phoneNumber, skipPermissionCheck, countryCode);
    }
  }

  public void onSubmitClicked(View view) {
    String code = ((EditText) findViewById(R.id.inputCode)).getText().toString();
    if (!code.isEmpty()) {
      if (mVerification != null) {
        mVerification.verify(code);
        showProgress();
        TextView messageText = (TextView) findViewById(R.id.textView);
        messageText.setText("Verification in progress");
        enableInputField(false);
      }
    }
  }

  void enableInputField(boolean enable) {
    View container = findViewById(R.id.inputContainer);
    if (enable) {
      container.setVisibility(View.VISIBLE);
      EditText input = (EditText) findViewById(R.id.inputCode);
      input.requestFocus();
    } else {
      container.setVisibility(View.GONE);
    }
  }

  void hideProgressBarAndShowMessage(int message) {
    hideProgressBar();
    TextView messageText = (TextView) findViewById(R.id.textView);
    messageText.setText(message);
  }

  void hideProgressBar() {
    ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressIndicator);
    progressBar.setVisibility(View.INVISIBLE);
    TextView progressText = (TextView) findViewById(R.id.progressText);
    progressText.setVisibility(View.INVISIBLE);
  }

  void showProgress() {
    ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressIndicator);
    progressBar.setVisibility(View.VISIBLE);
  }

  void showCompleted() {
    ImageView checkMark = (ImageView) findViewById(R.id.checkmarkImage);
    checkMark.setVisibility(View.VISIBLE);
  }

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
  public void onVerified() {
    Log.d(TAG, "Verified!");
    hideProgressBarAndShowMessage(R.string.verified);
    showCompleted();
  }

  @Override
  public void onVerificationFailed(Exception exception) {
    Log.e(TAG, "Verification failed: " + exception.getMessage());
    hideProgressBarAndShowMessage(R.string.failed);
    enableInputField(true);
  }

}
