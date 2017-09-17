package com.raon.im.gcm;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.raon.im.connection.ConnectThread;
import com.raon.im.database.DataField;
import com.raon.im.database.DatabaseSource;
import com.raon.lee.im.R;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by JiSoo on 2016-02-04.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegistrationIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    /**
     * GCM을 위한 Instance ID의 토큰을 생성하여 가져온다.
     * @param intent
     */
    @SuppressLint("LongLogTag")
    @Override
    protected void onHandleIntent(Intent intent) {

        // GCM Instance ID의 토큰을 가져오는 작업이 시작되면 LocalBoardcast로 GENERATING 액션을 알려 ProgressBar가 동작하도록 한다.
//        LocalBroadcastManager.getInstance(this)
//                .sendBroadcast(new Intent(QuickstartPreferences.REGISTRATION_GENERATING));

        // GCM을 위한 Instance ID를 가져온다.
        InstanceID instanceID = InstanceID.getInstance(this);
        String gcmID = null;
        try {
            synchronized (TAG) {
                // GCM 앱을 등록하고 획득한 설정파일인 google-services.json을 기반으로 SenderID를 자동으로 가져온다.
                String default_senderId = getString(R.string.gcm_defaultSenderId);
                // GCM 기본 scope는 "GCM"이다.
                String scope = GoogleCloudMessaging.INSTANCE_ID_SCOPE;
                // Instance ID에 해당하는 토큰을 생성하여 가져온다.
                gcmID = instanceID.getToken(default_senderId, scope, null);

                Log.i(TAG, "GCM Registration Token: " + gcmID);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        requestSignup(gcmID);
    }


    private void requestSignup(String gcmID) {
        DatabaseSource databaseSource = new DatabaseSource(this);

        try {
            databaseSource.open();
            DataField dataField = databaseSource.getData();

            Intent intent = new Intent();
            intent.putExtra("userID", dataField.getEmail());
            intent.putExtra("userPW", dataField.getPassword());
            intent.putExtra("gcmID", gcmID);

            ConnectThread thread = new ConnectThread("signup", intent);
            thread.start();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}

