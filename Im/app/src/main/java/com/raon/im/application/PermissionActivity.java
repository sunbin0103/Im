package com.raon.im.application;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.raon.im.listview.CompanyData;
import com.raon.im.listview.CompanyListAdapter;
import com.raon.lee.im.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by lee on 2016-03-11.
 */
public class PermissionActivity extends Activity {

    private final String SERVER_ADDRESS = "http://172.30.1.49/Raon_Im/"; //서버 아이피와 서버에서 받을 DB 파일 이름
    //private final String DB_ADDRESS = Environment.getDataDirectory().getAbsolutePath() + "/data/" + getPackageName() + "/files/Android_SQLite_Test.db"; //앙드로이드 내부에 저장할 DB파일의 디렉토리와 파일 이름, 이것을 클래스단에 놓으면 익셉션 에러가 발생한다. 따라서 이것은 onCreate 안에 넣었다.

    ProgressDialog dialog;
    Handler handler;
    //안드로이드 쓰레드를 위한 ProgressDialog와 핸들러

    URL url;
    URLConnection conn;
    InputStream is;
    FileOutputStream fos;
    BufferedOutputStream bos;
    File target;
    byte[] buffer;
    int bufferLength;
    //DB 파일을 받기 위한 변수들

    SQLiteDatabase db;
    Cursor cursor;
    //DB 파일에다 쿼리를 처리하기 위한 변수들

    ArrayList<CompanyData> datas = new ArrayList<CompanyData>();
    ListView list_CompanyList;
    CompanyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissionlist);

//        final String DB_ADDRESS = Environment.getDataDirectory().getAbsolutePath() + "/data/" + getPackageName() + "/databases/PermissionList.db"; // 안드로이드 내부에 저장할 DB파일의 디렉토리와 파일 이름

//        handler = new Handler();
        /*
         * DB를 받아오는 과정, 핸들러를 사용하여 ProgressDialog를 호출
         * 쓰레드를 사용하지 않으면 UI 쓰레드 때문에 DB파일이 완전하게 받아지지 않는다.
         */
/*        runOnUiThread(new Runnable() {

            public void run() {
                // TODO Auto-generated method stub

                handler.post(new Runnable() {

                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            url = new URL(SERVER_ADDRESS);
                            conn = url.openConnection();
                            is = conn.getInputStream();

                            target = new File(DB_ADDRESS);
                            fos = new FileOutputStream(target);
                            bos = new BufferedOutputStream(fos);

                            bufferLength = 0;
                            buffer = new byte[1024];

                            while((bufferLength = is.read(buffer)) > 0)
                                bos.write(buffer);

                            bos.close();
                            fos.close();

                            Toast.makeText(PermissionActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        } catch(Exception e) {
                            Log.e("DB URL Error", "Error initializing sensors in GameScene", e);
                        } finally {
                        }
                    }
                });
            }
        });
*/
        list_CompanyList = (ListView) findViewById(R.id.list_CompanyList);
        adapter = new CompanyListAdapter(getLayoutInflater(), datas);
        list_CompanyList.setAdapter(adapter);

    }
}
