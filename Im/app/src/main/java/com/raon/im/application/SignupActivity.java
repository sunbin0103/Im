package com.raon.im.application;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.raon.im.connection.ConnectThread;
import com.raon.im.database.DataField;
import com.raon.im.database.DatabaseSource;
import com.raon.im.gcm.InitGcm;
import com.raon.lee.im.R;

import java.sql.SQLException;
import java.util.Random;

/**
 * Created by Na Young on 2016-01-25.
 */
public class SignupActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "SignupActivity";

    EditText editEmail;
    EditText editPassword;
    EditText editPasswordCheck;
    EditText editCode;
    TextView txtTimer;
    Button btnConfirm;
    Button btnSendCode;
    Button btnCodeOk;
    DatabaseSource databaseSource;
    boolean check_code_sent, check_right_code;
    boolean time_over = false;
    String code, email_to;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editEmail = (EditText) findViewById(R.id.edit_SignUp_Email);
        editPassword = (EditText) findViewById(R.id.edit_SignUp_Password);
        editPasswordCheck = (EditText) findViewById(R.id.edit_SignUp_Confirm);
        editCode = (EditText) findViewById(R.id.edit_Code);
        txtTimer = (TextView) findViewById(R.id.txt_Timer);
        btnConfirm = (Button) findViewById(R.id.btn_SignUp);
        btnConfirm.setOnClickListener(this);
        btnSendCode = (Button) findViewById(R.id.btn_SendCode);
        btnSendCode.setOnClickListener(this);
        btnCodeOk = (Button) findViewById(R.id.btn_Code_OK);
        btnCodeOk.setOnClickListener(this);
        btnCodeOk.setVisibility(View.INVISIBLE);

        check_code_sent = false;
        check_right_code = false;

        // new CountDownTimer(length_of_the_timer, time_interval)
        timer = new CountDownTimer(3 * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long second = (millisUntilFinished/1000)%60;
                long minute = millisUntilFinished/(60*1000);

                if(second < 10)
                    txtTimer.setText(minute + ":0" + second);
                else
                    txtTimer.setText(minute + ":" + second);
            }

            @Override
            public void onFinish() {
                time_over = true;
                txtTimer.setText("0:00");
                makeRandomCode();
            }
        };

        databaseSource = new DatabaseSource(this);
        try {
            databaseSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_SendCode :
                if(!editEmail.equals(null)) {
                    Toast.makeText(this, "The authentication code is sent to your email.\nCheck your email and enter the code.", Toast.LENGTH_SHORT).show();
                    makeRandomCode();
                    email_to = editEmail.getText().toString();
                    btnSendCode.setVisibility(View.INVISIBLE);
                    btnCodeOk.setVisibility(View.VISIBLE);
                    check_code_sent = true;

                    timer.start();

                    GMailSender sender = new GMailSender("raon.im.test@gmail.com", "raon1111");
                    try {
                        sender.sendMail(
                                "Authentication Code"
                                , code.toString()
                                , "raon.im.test@gmail.com"
                                , email_to
                        );
                    } catch (Exception e) {
                        Log.e("Exception", e.toString());
                    }
                } else {
                    Toast.makeText(this, "Blanks are not allowed.\nPlease fill in the blanks.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_Code_OK :
                if(time_over){
                    SimpleDialog("Time Over", "Time over.\nSend another code.");
                } else {
                    if (check_code_sent && !editCode.getText().equals("")) {
                        if (editCode.getText().toString().equals(code)) {
                            check_right_code = true;
                            timer.cancel();
                            txtTimer.setVisibility(View.INVISIBLE);
                            SimpleDialog("Success", "Authentication Success.");
                            btnCodeOk.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(this, "Wrong Code.", Toast.LENGTH_SHORT).show();
                        }
                    } else if (!check_code_sent) {
                        Toast.makeText(this, "Send Code First.", Toast.LENGTH_SHORT).show();
                    } else if (editCode.getText().equals("")) {
                        Toast.makeText(this, "Write Code First.", Toast.LENGTH_SHORT).show();
                    } else {
                        Exception e = new Exception();
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_SignUp :
                if(check_code_sent && check_right_code) {
                    email_to = editEmail.getText().toString();
                    String password = editPassword.getText().toString();
                    String passwordCheck = editPasswordCheck.getText().toString();
                    if(password.length() < 8){
                        Toast.makeText(this, "Password is too short.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (password.equals(passwordCheck)) {
                            databaseSource.insertEmailData(email_to, password);

                            Intent intent = new Intent(this, InitGcm.class);
                            startActivity(intent);
                            finish();
                        } else {
                            SimpleDialog("Password Incorrect", "Password is incorrect.\nCheck your password.");
                            editPassword.setText(null);
                            editPassword.setHint("Password");
                            editPasswordCheck.setText(null);
                            editPasswordCheck.setHint("Check Password");
                        }
                    }
                } else if(!check_code_sent){
                    Toast.makeText(this, "Send Code First", Toast.LENGTH_SHORT).show();
                } else if(!check_right_code){
                    SimpleDialog("Wrong Code", "The Authentication code is wrong.\nCheck your code.");
                    editCode.setText("");
                    editCode.setHint("Code");
                } else{
                    // 에러 처리
                    ;
                }
                break;
        }
    }

    // 이메일 전송 메소드
    // method used to send authentication code to written email
    public void makeRandomCode(){
        // stmp 찾아보기

        // 실제로는 랜덤으로 값을 생성한 후 전송 + 문구는 string에 지정해야할듯
        Random random = new Random();

        code = Integer.toString(random.nextInt(10));
        for(int i=0; i<5; i++){
            code += random.nextInt(10);
        }
    }

    // Show dialog for alert
    private void SimpleDialog(String title, String text){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(text).setCancelable(false).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = dialog.create();

        alert.setTitle(title);

        alert.setIcon(R.drawable.im_logo);
        alert.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
