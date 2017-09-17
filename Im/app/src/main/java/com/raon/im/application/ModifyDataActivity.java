package com.raon.im.application;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.raon.im.database.DatabaseSource;
import com.raon.lee.im.R;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by Na Young on 2016-03-22.
 */
public class ModifyDataActivity extends Activity implements View.OnClickListener {

    EditText edit_UserName, edit_UserCity, edit_UserAddress, edit_UserPhone, edit_BirthdayYear, edit_BirthdayMonth, edit_BirthdayDay;
    TextView btn_UserCountry;
    Button btn_Confirm;
    DatabaseSource databaseSource;
    BackPressCloseHandler backPressCloseHandler;

    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        edit_UserName = (EditText) findViewById(R.id.edit_UserName);
        btn_UserCountry = (TextView) findViewById(R.id.txt_UserCountry);
        btn_UserCountry.setOnClickListener(this);
        edit_UserCity = (EditText) findViewById(R.id.edit_UserCity);
        edit_UserAddress = (EditText) findViewById(R.id.edit_UserAddress);
        edit_UserPhone = (EditText) findViewById(R.id.edit_UserPhone);
        edit_BirthdayYear = (EditText) findViewById(R.id.edit_BirthdayYear);
        edit_BirthdayMonth = (EditText) findViewById(R.id.edit_BirthdayMonth);
        edit_BirthdayDay = (EditText) findViewById(R.id.edit_BirthdayDay);

        btn_Confirm = (Button) findViewById(R.id.btn_confirm);
        btn_Confirm.setOnClickListener(this);

        databaseSource = new DatabaseSource(this);
        try {
            databaseSource.open();
            edit_UserName.setText(databaseSource.getData_1().getName());
            btn_UserCountry.setText(databaseSource.getData_1().getCountry());
            edit_UserCity.setText(databaseSource.getData_1().getCity());
            edit_UserAddress.setText(databaseSource.getData_1().getAddress());
            edit_UserPhone.setText(databaseSource.getData_1().getPhone());
            edit_BirthdayYear.setText(databaseSource.getData_1().getBirthday_year());
            edit_BirthdayMonth.setText(databaseSource.getData_1().getBirthday_month());
            edit_BirthdayDay.setText(databaseSource.getData_1().getBirthday_day());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_confirm:
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure about the updated data?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
            /* User clicked OK so do some stuff */
                                String inputName = edit_UserName.getText().toString();
                                String inputCounty = btn_UserCountry.getText().toString();
                                String inputCity = edit_UserCity.getText().toString();
                                String inputAddress = edit_UserAddress.getText().toString();
                                String inputPhone = edit_UserPhone.getText().toString();
                                String inputBirthday_year = edit_BirthdayYear.getText().toString();
                                String inputBirthday_month = edit_BirthdayMonth.getText().toString();
                                String inputBirthday_day = edit_BirthdayDay.getText().toString();
                                String email = databaseSource.getData_1().getEmail();
                                databaseSource.updateData(inputName, inputCounty, inputCity, inputAddress, inputPhone, inputBirthday_year, inputBirthday_month, inputBirthday_day, email);
                                databaseSource.close();

                                Intent intent = new Intent(ModifyDataActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent = new Intent(ModifyDataActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .create().show();
                break;
            case R.id.txt_UserCountry:
                final ArrayList<String> nations = getNationList();
                final String[] items = nations.toArray(new String[nations.size()]);
                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyDataActivity.this);
                builder.setTitle("Select your nation");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        btn_UserCountry.setText(items[item]);
                        dialog.dismiss();
                    }
                }).create().show();
                break;
        }
    }

    public ArrayList<String> getNationList(){
        ArrayList<String> nationList = new ArrayList<String>();

        try {
            AssetManager am = getAssets();
            InputStream is = am.open("nation_name.xls");
            Workbook wb = Workbook.getWorkbook(is);
            Sheet s = wb.getSheet(0);
            int row = s.getRows();
            int col = s.getColumns();

            for(int i=0; i<row; i++){
                for(int j=0; j<col; j++){
                    Cell c = s.getCell(j, i);
                    nationList.add(c.getContents());
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return nationList;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("You don't save. Do you want to save the updated data?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    /* User clicked OK so do some stuff */
                        String inputName = edit_UserName.getText().toString();
                        String inputCounty = btn_UserCountry.getText().toString();
                        String inputCity = edit_UserCity.getText().toString();
                        String inputAddress = edit_UserAddress.getText().toString();
                        String inputPhone = edit_UserPhone.getText().toString();
                        String inputBirthday_year = edit_BirthdayYear.getText().toString();
                        String inputBirthday_month = edit_BirthdayMonth.getText().toString();
                        String inputBirthday_day = edit_BirthdayDay.getText().toString();
                        String email = databaseSource.getData_1().getEmail();
                        databaseSource.updateData(inputName, inputCounty, inputCity, inputAddress, inputPhone, inputBirthday_year, inputBirthday_month, inputBirthday_day, email);
                        databaseSource.close();

                        Intent intent = new Intent(ModifyDataActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(ModifyDataActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .create().show();
    }
}
