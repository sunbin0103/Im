package com.raon.im.application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.raon.im.database.DatabaseSource;
import com.raon.lee.im.R;

import java.sql.SQLException;

public class MainActivity extends Activity implements  View.OnClickListener{

    public DatabaseSource databaseSource;
    private BackPressCloseHandler backPressCloseHandler;

    TextView txt_UserName, txt_UserCountry, txt_UserCity, txt_UserAddress, txt_UserPhone, txt_UserBirthday;
    Button btn_Modify;
    ImageButton img_btn_Change_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_UserName = (TextView) findViewById(R.id.txt_UserName);
        txt_UserCountry = (TextView) findViewById(R.id.txt_UserCountry);
        txt_UserCity = (TextView) findViewById(R.id.txt_UserCity);
        txt_UserAddress = (TextView) findViewById(R.id.txt_UserAddress);
        txt_UserPhone = (TextView) findViewById(R.id.txt_UserPhone);
        txt_UserBirthday = (TextView) findViewById(R.id.txt_UserBirthday);

        btn_Modify = (Button) findViewById(R.id.btn_modify);
        btn_Modify.setOnClickListener(this);

        img_btn_Change_Password = (ImageButton) findViewById(R.id.img_btn_change_password);
        img_btn_Change_Password.setOnClickListener(this);

        databaseSource = new DatabaseSource(this);
        try {
            databaseSource.open();
            txt_UserName.setText(databaseSource.getData_1().getName());
            txt_UserCountry.setText(databaseSource.getData_1().getCountry());
            txt_UserCity.setText(databaseSource.getData_1().getCity());
            txt_UserAddress.setText(databaseSource.getData_1().getAddress());
            txt_UserPhone.setText(databaseSource.getData_1().getPhone());

            String birthday = null;
            if(databaseSource.getData_1().getBirthday_year() == null
                    && databaseSource.getData_1().getBirthday_month() == null
                    && databaseSource.getData_1().getBirthday_day() == null)
                txt_UserBirthday.setText("Set Your Birthday");
            else {
                birthday = databaseSource.getData_1().getBirthday_year() + "." + databaseSource.getData_1().getBirthday_month() + "." + databaseSource.getData_1().getBirthday_day();
                txt_UserBirthday.setText(birthday);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_modify:
                Intent intent = new Intent(this, ModifyDataActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.img_btn_change_password:
                Intent intent2 = new Intent(this, CheckCurrentPWActivity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
            backPressCloseHandler.onBackPressed();
    }
}
