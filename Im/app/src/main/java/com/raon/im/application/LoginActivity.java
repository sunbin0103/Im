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
 * Created by Na Young on 2016-01-24.
 * Last Update 2016-02-04.
 */
public class LoginActivity extends Activity implements View.OnClickListener{

    EditText editEmail;
    EditText editPassword;
    Button btnSignIn;
    TextView btnSignUp;

    private DatabaseSource databaseSource;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = (EditText) findViewById(R.id.edit_Login_Email);
        editPassword = (EditText) findViewById(R.id.edit_Login_Password);
        btnSignIn = (Button) findViewById(R.id.btn_SignIn);
        btnSignIn.setOnClickListener(this);
        btnSignUp = (TextView) findViewById(R.id.txt_SignUp);
        btnSignUp.setOnClickListener(this);

        databaseSource = new DatabaseSource(this);
        try{
            databaseSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_SignIn :
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();

                // 데이터 조회연산을 통해 데이터베이스에 있는 이메일일 경우 로그인 가능하도록 한다
                if(databaseSource.isRightPassword(email, password)) {
                    Intent intent = new Intent(this, TabMainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(this, "Wrong Email/Password!!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.txt_SignUp :
                Intent intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
                //finish();
        }
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
