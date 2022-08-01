package com.appupdate.appupdateproject;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.appupdate.reactnativeappupdatelib.AppUpdateClass;
import com.appupdate.reactnativeappupdatelib.UpdateCallBack;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppUpdateClass.setAppId("86f3fe02-59ff-4ce4-a002-06b281e20ee1", true);
        AppUpdateClass.checkForAppUpdate(this, new UpdateCallBack() {
            @Override
            public void onSuccess(String response) {
                Log.e("mye", ""+response);
            }

            @Override
            public void onFailure(String message) {
                Log.e("mye", "onFailure"+message);

            }
        });
    }
}