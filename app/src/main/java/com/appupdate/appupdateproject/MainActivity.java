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
        AppUpdateClass.setAppId("eafde253-9b4e-49af-ae2d-af98db4bb67a", true);
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