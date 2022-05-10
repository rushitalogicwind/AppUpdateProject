package com.appupdate.reactnativeappupdatelib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class AppUpdateActivity extends AppCompatActivity {

    Boolean activityClose = false;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle1 = ai.metaData;
            int icon = bundle1.getInt("com.appupdate.icon");
            String name = bundle1.getString("com.appupdate.name");

            Bundle bundle = this.getIntent().getExtras();
            String data = bundle.getString("res"); // NullPointerException.
            JSONObject jsonObject = new JSONObject(data);
            JSONObject updateData = jsonObject.getJSONObject("updateData");
            Boolean isAndroidForcedUpdate = updateData.getBoolean("isAndroidForcedUpdate");
            Boolean isAndroidUpdate = updateData.getBoolean("isAndroidUpdate");
            String  androidBuildNumber= updateData.getString("androidBuildNumber");
            PackageInfo info = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionCode = info.versionCode;
            int buildNum = 0;

            if(!(androidBuildNumber.equals(null))){
                buildNum =  Integer.parseInt(androidBuildNumber);
            }
            boolean isUpdate = versionCode < buildNum;
            Log.d("isUpdate", String.valueOf(isUpdate));

            AlertDialog alertDialog = new AlertDialog.Builder(this).setCancelable(false).create();
            View customLayout = getLayoutInflater().inflate(R.layout.activity_app_update, null);
            alertDialog.setView(customLayout);
            LinearLayout update_layout = customLayout.findViewById(R.id.update_layout);
            ImageView img_icon = customLayout.findViewById(R.id.img_icon);

            if ((isAndroidForcedUpdate || isAndroidUpdate) && (isUpdate)) {
                TextView txt_title = customLayout.findViewById(R.id.txt_title);
                TextView txt_des = customLayout.findViewById(R.id.txt_des);
                TextView txt_no_thanks = customLayout.findViewById(R.id.txt_no_thanks);
                Button btn_update = customLayout.findViewById(R.id.btn_update);
                img_icon.setImageResource(icon);
                txt_title.setText(name + " " + getString(R.string.update_title));
                if(isAndroidForcedUpdate){
                    txt_no_thanks.setVisibility(View.GONE);
                    txt_des.setText(getString(R.string.update_force_dsc));
                } else{
                    txt_no_thanks.setVisibility(View.VISIBLE);
                    txt_des.setText(getString(R.string.update_dsc));
                    txt_no_thanks.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            activityClose = true;
                            onBackPressed();
                            alertDialog.dismiss();
                        }
                    });

                }
                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activityClose = true;
                        onBackPressed();
                        String uri = "https://play.google.com/store/apps/details?id=" + info.packageName;
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(marketIntent);
                        alertDialog.dismiss();
                    }
                });
            }

            alertDialog.show();

        } catch (JSONException | PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        if (activityClose) {
            super.onBackPressed();
        }
    }
}