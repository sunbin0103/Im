package com.raon.im.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.raon.im.database.DatabaseSource;
import com.raon.lee.im.R;

import java.sql.SQLException;

/**
 * Created by Na Young on 2016-03-20.
 */
public class SetNewAppPWActivity extends Activity {
    private int[] inputPassword = {-1, -1, -1, -1};
    private int pwCursor = 0;
    private String oldPW, newPW;

    Button[] btn_appPW = new Button[11];
    EditText[] btn_edtPW = new EditText[4];

    TextView txt_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        txt_desc = (TextView) findViewById(R.id.txt_desc);
        txt_desc.setText("New Password");
        txt_desc.setTextSize(30);

        btn_appPW[0] = (Button) findViewById(R.id.btn_appPW_0);
        btn_appPW[1] = (Button) findViewById(R.id.btn_appPW_1);
        btn_appPW[2] = (Button) findViewById(R.id.btn_appPW_2);
        btn_appPW[3] = (Button) findViewById(R.id.btn_appPW_3);
        btn_appPW[4] = (Button) findViewById(R.id.btn_appPW_4);
        btn_appPW[5] = (Button) findViewById(R.id.btn_appPW_5);
        btn_appPW[6] = (Button) findViewById(R.id.btn_appPW_6);
        btn_appPW[7] = (Button) findViewById(R.id.btn_appPW_7);
        btn_appPW[8] = (Button) findViewById(R.id.btn_appPW_8);
        btn_appPW[9] = (Button) findViewById(R.id.btn_appPW_9);
        btn_appPW[10] = (Button) findViewById(R.id.btn_appPW_back);

        btn_edtPW[0] = (EditText) findViewById(R.id.edt_PW_1);
        btn_edtPW[1] = (EditText) findViewById(R.id.edt_PW_2);
        btn_edtPW[2] = (EditText) findViewById(R.id.edt_PW_3);
        btn_edtPW[3] = (EditText) findViewById(R.id.edt_PW_4);

        Intent intent = getIntent();
        oldPW = intent.getStringExtra("OLD_PW");
    }

    public void mOnClick(View view){

        Button btn = (Button) view;

        // Password input
        switch (btn.getId()) {
            case R.id.btn_appPW_back:
                if(pwCursor > 0) {
                    btn_edtPW[pwCursor].setText("");
                    pwCursor--;
                }
                break;
            case R.id.btn_appPW_0:
                inputPassword[pwCursor] = 0;
                btn_edtPW[pwCursor].setText("0");
                pwCursor++;
                break;
            case R.id.btn_appPW_1:
                inputPassword[pwCursor] = 1;
                btn_edtPW[pwCursor].setText("1");
                pwCursor++;
                break;
            case R.id.btn_appPW_2:
                inputPassword[pwCursor] = 2;
                btn_edtPW[pwCursor].setText("2");
                pwCursor++;
                break;
            case R.id.btn_appPW_3:
                inputPassword[pwCursor] = 3;
                btn_edtPW[pwCursor].setText("3");
                pwCursor++;
                break;
            case R.id.btn_appPW_4:
                inputPassword[pwCursor] =4;
                btn_edtPW[pwCursor].setText("4");
                pwCursor++;
                break;
            case R.id.btn_appPW_5:
                inputPassword[pwCursor] = 5;
                btn_edtPW[pwCursor].setText("5");
                pwCursor++;
                break;
            case R.id.btn_appPW_6:
                inputPassword[pwCursor] = 6;
                btn_edtPW[pwCursor].setText("6");
                pwCursor++;
                break;
            case R.id.btn_appPW_7:
                inputPassword[pwCursor] = 7;
                btn_edtPW[pwCursor].setText("7");
                pwCursor++;
                break;
            case R.id.btn_appPW_8:
                inputPassword[pwCursor] = 8;
                btn_edtPW[pwCursor].setText("8");
                pwCursor++;
                break;
            case R.id.btn_appPW_9:
                inputPassword[pwCursor] = 9;
                btn_edtPW[pwCursor].setText("9");
                pwCursor++;
                break;
        }

        // Password Check
        if(pwCursor == 4) {
            newPW = "";
            for(int i=0; i<4; i++)
                newPW += inputPassword[i];

            Intent intent = new Intent(this, CheckNewAppPWActivity.class);
            intent.putExtra("NEW_PW", newPW);
            intent.putExtra("OLD_PW", oldPW);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onBackPressed();
    }
}
