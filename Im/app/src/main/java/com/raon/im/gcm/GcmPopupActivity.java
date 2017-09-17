package com.raon.im.gcm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.raon.im.application.AppLockActivity;
import com.raon.im.application.MainActivity;
import com.raon.im.application.TabMainActivity;
import com.raon.lee.im.R;

/**
 * Created by lee on 2016-03-18.
 */
public class GcmPopupActivity extends Activity {

    Button btnPopupYes, btnPopupNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                            | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.activity_gcmpopup);

        btnPopupYes = (Button) findViewById(R.id.btn_PopupYes);
        btnPopupYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GcmPopupActivity.this, TabMainActivity.class));
                finish();
            }
        });
        btnPopupNo = (Button) findViewById(R.id.btn_PopupNo);
        btnPopupNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
