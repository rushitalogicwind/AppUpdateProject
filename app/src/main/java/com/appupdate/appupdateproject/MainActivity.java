package com.appupdate.appupdateproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.appupdate.reactnativeappupdatelib.AppUpdateClass;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;

public class MainActivity extends AppCompatActivity {

    ReactApplicationContext reactApplicationContext;
    Callback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppUpdateClass.setAppId("86f3fe02-59ff-4ce4-a002-06b281e20ee1", true);
//        AppUpdateClass.checkForAppUpdate(this, callback);
    }
}