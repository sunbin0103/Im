package com.raon.im.application;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.Toast;

import com.raon.lee.im.R;

/**
 * Created by lee on 2016-03-19.
 */

@SuppressWarnings("deprecation")
public class TabMainActivity extends TabActivity {

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);

        TabHost mTab = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        // First Tab - Personal Information
        intent = new Intent(this, MainActivity.class);
        spec = mTab.newTabSpec("PersonalDataTab").setIndicator("Personal Data").setContent(intent);
        mTab.addTab(spec);

        // Second Tab - Company/Permission List
        intent = new Intent(this, PermissionActivity.class);
        spec = mTab.newTabSpec("PermissionTab").setIndicator("Company List").setContent(intent);
        mTab.addTab(spec);

        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

}
