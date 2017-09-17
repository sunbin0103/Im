package com.raon.im.listview;

/**
 * Created by lee on 2016-03-11.
 */
public class CompanyData {
    String name;    // 회사명
    String[] permissionItem;    // 제공하는 정보
    int permissionSize;         // 제공하는 정보량
    boolean isDataProvision; // 정보제공 동의여부

    public CompanyData(String name, int permissionSize, boolean isDataProvision) {
        this.name = name;
        this.permissionSize = permissionSize;
        this.isDataProvision = isDataProvision;
        // this.permissionItem = 제공하기로 한 정보 목록
    }

    public String getName() { return name; }

    public int getPermissionSize() { return  permissionSize; }

    public boolean getDataProvision() {
        return isDataProvision;
    }
    public void setDataProvision(boolean DataProvision) {
        this.isDataProvision = DataProvision;
    }
}
