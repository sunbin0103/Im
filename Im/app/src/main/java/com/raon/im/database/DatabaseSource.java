package com.raon.im.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by Na Young on 2016-01-20.
 */
public class DatabaseSource {
    private SQLiteDatabase database;
    private DatabaseHelper helper;
    long insertID_1, insertID_2;

    // 필드 메타데이터
    // Field metadata
    private String[] allColumns_1 = {
            DatabaseHelper.FIELD_ID_1
            , DatabaseHelper.FIELD_EMAIL
            , DatabaseHelper.FIELD_PASSWORD
            , DatabaseHelper.FIELD_NAME
            , DatabaseHelper.FIELD_COUNTRY
            , DatabaseHelper.FIELD_CITY
            , DatabaseHelper.FIELD_ADDRESS
            , DatabaseHelper.FIELD_PHONE
            , DatabaseHelper.FIELD_BIRTHDAYYEAR
            , DatabaseHelper.FIELD_BIRTHDAYMONTH
            , DatabaseHelper.FIELD_BIRTHDAYDAY
    };

    private String[] allColumns_2 = {
            DatabaseHelper.FIELD_ID_2
            , DatabaseHelper.FIELD_APPPW
    };

    public DatabaseSource(Context context){
        helper = new DatabaseHelper(context);
    }

    // method to open database
    public void open() throws SQLException{
        database = helper.getWritableDatabase();
    }

    // method to close database
    public void close(){
        database.close();
    }

    // 앱 비밀번호 저장
    public DataField insertAppPW(String appPW){
        Cursor cursor = null;
        try{
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.FIELD_APPPW, appPW);

            insertID_2 = database.insert(DatabaseHelper.TABLE_2_NAME, null, values);

            Log.i("APP PW", appPW);

            cursor = database.query(DatabaseHelper.TABLE_2_NAME
                    , allColumns_2, DatabaseHelper.FIELD_ID_2 + "=" + insertID_2
                    , null, null, null, null);

            cursor.moveToFirst();

            return cursorToData_2(cursor);
        } finally {
            closeCursor(cursor);
        }
    }

    // 변경된 앱 비밀번호
    public void updateAppPW(String appPW, String newAppPW){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_APPPW, newAppPW);

        database.update(DatabaseHelper.TABLE_2_NAME, values, "appPW=?", new String[] {appPW});
    }

    // 회원가입시 이메일, 비밀번호 삽입하는 메소드
    // method used to insert email and password when signing up
    public DataField insertEmailData(String email, String password){
        Cursor cursor = null;
        try{
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.FIELD_EMAIL, email);
            values.put(DatabaseHelper.FIELD_PASSWORD, password);

            insertID_1 = database.insert(DatabaseHelper.TABLE_1_NAME, null, values);

            cursor = database.query(DatabaseHelper.TABLE_1_NAME
                    , allColumns_1, DatabaseHelper.FIELD_ID_1 + "=" + insertID_1
                    , null, null, null, null);

            cursor.moveToFirst();

            return cursorToData_1(cursor);
        } finally {
            closeCursor(cursor);
        }
    }

    // 정보 수정시 사용하는 메소드
    // method used to update personal data
    public void updateData(String name, String country, String city, String address, String phone, String birthday_year, String birthday_month, String birthday_day, String email){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_NAME, name);
        values.put(DatabaseHelper.FIELD_COUNTRY, country);
        values.put(DatabaseHelper.FIELD_CITY, city);
        values.put(DatabaseHelper.FIELD_ADDRESS, address);
        values.put(DatabaseHelper.FIELD_PHONE, phone);
        values.put(DatabaseHelper.FIELD_BIRTHDAYYEAR, birthday_year);
        values.put(DatabaseHelper.FIELD_BIRTHDAYMONTH, birthday_month);
        values.put(DatabaseHelper.FIELD_BIRTHDAYDAY, birthday_day);

        database.update(DatabaseHelper.TABLE_1_NAME, values, "email=?", new String[]{ email });
    }

    // 레코드 삭제하는 메소드. => 아직 사용 안되기 때문에 후에 사용할 떄 고칠 예정
    // method used to remove record from database => not used yet.
    public void deleteData(DataField field){
        String email = field.getEmail();

        database.delete(DatabaseHelper.TABLE_1_NAME
                , DatabaseHelper.FIELD_EMAIL + " = " + email
                , null);
    }

    // 로그인시 이메일 확인하는 메소드 => 서버로 보내서 서버에서 처리할 일인거 같음
    // method used to check whether email is in the server => need to fix
    public boolean doDataExist(String email){
        Cursor cursor = null;

        try{
            cursor = database.query(DatabaseHelper.TABLE_1_NAME
                    , allColumns_1
                    , null, null, null, null, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                int index = cursor.getColumnIndex(DatabaseHelper.FIELD_EMAIL);
                if(cursor.getString(index).equals(email))
                    return false;
                else continue;
            }
        }finally {
            return true;
        }
    }

    // 로그인할 때 이메일과 비밀번호 확인 메소드 => 고쳐야함
    // method used to check whether email and password are right
    public boolean isRightPassword(String email, String password){
        Cursor cursor = null;
        int emailIndex = 0;
        int passwdIndex = 0;

        try{
            cursor = database.query(DatabaseHelper.TABLE_1_NAME
                    , allColumns_1
                    , null, null, null, null, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                emailIndex = cursor.getColumnIndex(DatabaseHelper.FIELD_EMAIL);
                passwdIndex = cursor.getColumnIndex(DatabaseHelper.FIELD_PASSWORD);
                if(cursor.getString(emailIndex).equals(email)){
                    Log.i("EMAIL", cursor.getString(emailIndex));
                    if(cursor.getString(passwdIndex).equals(password)) {
                        Log.i("PASSWORD", cursor.getString(passwdIndex));
                        return true;
                    }
                    else continue;
                }
            }
        }catch (Exception e){}
        return false;
    }

    // 데이터베이스에 있는 레코드를 가져오는 메소드
    // 정보 변경시 해당 이메일에 그 정보를 저장해야하기 때문에 그 이메일을 불러올 때 사용함.
    // method used to fetch record from database
    public DataField getData_1(){
        DataField data = new DataField();
        Cursor cursor = null;

        try{
            cursor = database.query(DatabaseHelper.TABLE_1_NAME
                    , allColumns_1
                    , null, null, null, null, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                data = cursorToData_1(cursor);
                cursor.moveToNext();
            }

            return data;
        } finally {
            closeCursor(cursor);
        }
    }

    public DataField getData_2(){
        DataField data = new DataField();
        Cursor cursor = null;

        try{
            cursor = database.query(DatabaseHelper.TABLE_2_NAME
                    , allColumns_2
                    , null, null, null, null, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                data = cursorToData_2(cursor);
                cursor.moveToNext();
            }

            return data;
        } finally {
            closeCursor(cursor);
        }
    }

    // 커서의 값을 데이터로 변경하여 반환하는 메소드
    // method to change the value of the cursor to data type and return the data
    private DataField cursorToData_1(Cursor cursor){
        DataField field = new DataField();

        int idIndex = cursor.getColumnIndex(DatabaseHelper.FIELD_ID_1);
        int emailIndex = cursor.getColumnIndex(DatabaseHelper.FIELD_EMAIL);
        int passwdIndex = cursor.getColumnIndex(DatabaseHelper.FIELD_PASSWORD);
        int nameIndex = cursor.getColumnIndex(DatabaseHelper.FIELD_NAME);
        int countryIndex = cursor.getColumnIndex(DatabaseHelper.FIELD_COUNTRY);
        int cityIndex = cursor.getColumnIndex(DatabaseHelper.FIELD_CITY);
        int addressIndex = cursor.getColumnIndex(DatabaseHelper.FIELD_ADDRESS);
        int phoneIndex = cursor.getColumnIndex(DatabaseHelper.FIELD_PHONE);
        int birthdayYearIndex = cursor.getColumnIndex(DatabaseHelper.FIELD_BIRTHDAYYEAR);
        int birthdayMonthIndex = cursor.getColumnIndex(DatabaseHelper.FIELD_BIRTHDAYMONTH);
        int birthdayDayIndex = cursor.getColumnIndex(DatabaseHelper.FIELD_BIRTHDAYDAY);

        field.setId_1(cursor.getLong(idIndex));
        field.setEmail(cursor.getString(emailIndex));
        field.setPassword(cursor.getString(passwdIndex));
        field.setName(cursor.getString(nameIndex));
        field.setCountry(cursor.getString(countryIndex));
        field.setCity(cursor.getString(cityIndex));
        field.setAddress(cursor.getString(addressIndex));
        field.setPhone(cursor.getString(phoneIndex));
        field.setBirthday_year(cursor.getString(birthdayYearIndex));
        field.setBirthday_month(cursor.getString(birthdayMonthIndex));
        field.setBirthday_day(cursor.getString(birthdayDayIndex));

        return field;
    }

    private DataField cursorToData_2(Cursor cursor){
        DataField field = new DataField();

        int idIndex_2 = cursor.getColumnIndex(DatabaseHelper.FIELD_ID_2);
        int appPWIndex = cursor.getColumnIndex(DatabaseHelper.FIELD_APPPW);

        field.setId_2(cursor.getLong(idIndex_2));
        field.setAppPW(cursor.getString(appPWIndex));

        return field;
    }

    // Cursor를 닫아서 데이터를 보호하는 메소드
    // method to close cursor
    private void closeCursor(Cursor cursor)
    {
        try
        {
            if(cursor != null)
                cursor.close();
        }
        catch (Exception e) { }
    }
}
