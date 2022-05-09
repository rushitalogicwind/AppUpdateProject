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
            Boolean isMaintenance = jsonObject.getBoolean("isMaintenance");
            JSONObject updateData = jsonObject.getJSONObject("updateData");
            Boolean isAndroidForcedUpdate = updateData.getBoolean("isAndroidForcedUpdate");
            Boolean isAndroidUpdate = updateData.getBoolean("isAndroidUpdate");
            String  androidBuildNumber= updateData.getString("androidBuildNumber");
            PackageInfo info = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionCode = info.versionCode;
            int buildNum = 0;
            if((androidBuildNumber.equals(null))){
                buildNum =  Integer.parseInt(androidBuildNumber);
            }
            boolean isUpdate = versionCode < buildNum;

            AlertDialog alertDialog = new AlertDialog.Builder(this).setCancelable(false).create();
            View customLayout = getLayoutInflater().inflate(R.layout.activity_app_update, null);
            alertDialog.setView(customLayout);
            LinearLayout maintenance_layout = customLayout.findViewById(R.id.maintenance_layout);
            LinearLayout update_layout = customLayout.findViewById(R.id.update_layout);
            ImageView img_icon = customLayout.findViewById(R.id.img_icon);
            TextView txt_app_name = customLayout.findViewById(R.id.txt_app_name);

            if(isMaintenance){
                update_layout.setVisibility(View.GONE);
                maintenance_layout.setVisibility(View.VISIBLE);
                JSONObject maintenanceData = jsonObject.getJSONObject("maintenanceData");
                Boolean maintenanceDataFlag = maintenanceData.isNull("maintenanceData");

                String title = maintenanceData.getString("title");
                String description = maintenanceData.getString("description");
                String image = maintenanceData.getString("image");
                String textColorCode = maintenanceData.getString("textColorCode");
                String backgroundColorCode = maintenanceData.getString("backgroundColorCode");
                ImageView maintenance_img = customLayout.findViewById(R.id.maintenance_img);
                TextView txt_title_maintain = customLayout.findViewById(R.id.txt_title_maintain);
                TextView txt_des_maintain = customLayout.findViewById(R.id.txt_des_maintain);

                if(maintenanceDataFlag){
                    maintenance_img.setVisibility(View.GONE);
                    txt_app_name.setVisibility(View.VISIBLE);
                    if(!image.equals(null)){
                        img_icon.setImageResource(icon);
                    }else {
                        img_icon.setImageURI(Uri.parse(image));
                    }
                    txt_title_maintain.setText(title);
                    txt_title_maintain.setTextSize(20);
                    txt_title_maintain.setTextColor(Color.parseColor(textColorCode));
//                    txt_title_maintain.setTextColor(txt_title_maintain.getContext().getColor(R.color.text_color));
                    txt_des_maintain.setVisibility(View.VISIBLE);
                    txt_des_maintain.setText(description);
//                    txt_des_maintain.setTextColor(Color.parseColor(backgroundColorCode));
                }else{
                    img_icon.setVisibility(View.GONE);
                    txt_app_name.setVisibility(View.GONE);
                    txt_des_maintain.setVisibility(View.GONE);
                    maintenance_img.setVisibility(View.VISIBLE);
                    txt_title_maintain.setText(getString(R.string.maintenance));
                    txt_title_maintain.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    txt_title_maintain.setGravity(Gravity.CENTER);
                }
            }

            if ((isAndroidForcedUpdate || isAndroidUpdate) && (isUpdate)) {
                update_layout.setVisibility(View.VISIBLE);
                maintenance_layout.setVisibility(View.GONE);
                txt_app_name.setVisibility(View.GONE);
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