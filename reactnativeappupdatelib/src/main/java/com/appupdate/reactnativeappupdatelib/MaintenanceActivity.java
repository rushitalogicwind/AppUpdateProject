package com.appupdate.reactnativeappupdatelib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

public class MaintenanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        try{
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle1 = ai.metaData;
            int icon = bundle1.getInt("com.appupdate.icon");

            Bundle bundle = this.getIntent().getExtras();
            String data = bundle.getString("res"); // NullPointerException.
            JSONObject jsonObject = new JSONObject(data);
            Boolean isMaintenance = jsonObject.getBoolean("isMaintenance");
            if(isMaintenance){
                JSONObject maintenanceData = jsonObject.getJSONObject("maintenanceData");
                Boolean maintenanceDataFlag = maintenanceData.isNull("maintenanceData");

                String title = maintenanceData.getString("title");
                String description = maintenanceData.getString("description");
                String image = maintenanceData.getString("image");
                String textColorCode = maintenanceData.getString("textColorCode");
                String backgroundColorCode = maintenanceData.getString("backgroundColorCode");

//                LinearLayout maintenance_layout = findViewById(R.id.maintenance_layout);
//                maintenance_layout.setBackgroundColor(Color.parseColor(backgroundColorCode));
                ImageView img_icon = findViewById(R.id.img_icon);
                TextView txt_app_name = findViewById(R.id.txt_app_name);

                ImageView maintenance_img = findViewById(R.id.maintenance_img);
                TextView txt_title_maintain = findViewById(R.id.txt_title_maintain);
                TextView txt_des_maintain = findViewById(R.id.txt_des_maintain);

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
                    txt_des_maintain.setVisibility(View.VISIBLE);
                    txt_des_maintain.setText(description);
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

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}