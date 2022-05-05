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
        AppUpdateClass.setAppId("2f94b314-9626-4fb7-aa29-3dd2e7d54de3", true);
//
//        AppUpdateClass.checkForAppUpdate(reactApplicationContext, callback);
    }
}