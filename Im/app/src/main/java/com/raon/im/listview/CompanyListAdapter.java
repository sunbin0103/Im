package com.raon.im.listview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.raon.lee.im.R;

import java.util.ArrayList;

/**
 * Created by lee on 2016-03-11.
 */

public class CompanyListAdapter extends BaseAdapter {

    ArrayList<CompanyData> datas;
    LayoutInflater inflater;

    public CompanyListAdapter(LayoutInflater inflater, ArrayList<CompanyData> datas) {
        this.datas = datas;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView == null){
            convertView= inflater.inflate(R.layout.list_row, null);
        }

        ImageView img_CompanyLogo = (ImageView) convertView.findViewById(R.id.img_CompanyLogo);
        ImageView img_PermissionItem1 = (ImageView) convertView.findViewById(R.id.img_PermissionItem1);
        ImageView img_PermissionItem2 = (ImageView) convertView.findViewById(R.id.img_PermissionItem2);
        ImageView img_PermissionItem3 = (ImageView) convertView.findViewById(R.id.img_PermissionItem3);
        ImageView img_PermissionItem4 = (ImageView) convertView.findViewById(R.id.img_PermissionItem4);
        CheckBox ch_DataProvision = (CheckBox) convertView.findViewById(R.id.cb_DataProvision);

        CompanyData cd = datas.get(position);

        setCompanyLogo(cd.getName(), img_CompanyLogo);

        for(int i = 0; i < cd.getPermissionSize(); i++) {
            if(i > 4) { // 제공해야하는 정보가 4개 이상이면
                img_PermissionItem4.setImageResource(R.drawable.permission_ellipsis);
                break;
            }
            // setPermissionItem();
        }

        ch_DataProvision.setChecked(cd.getDataProvision());
        ch_DataProvision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) // 정보제공 허용
                    CompanyListAdapter.this.datas.get(position).setDataProvision(true);
                else // 정보제공 불허용
                    CompanyListAdapter.this.datas.get(position).setDataProvision(false);
            }
        });

        return convertView;
    }

    public void clear() {
        datas.clear();
    }

    public void addAll(ArrayList<CompanyData> list) {
        datas.addAll(list);
    }

    public void setCompanyLogo (String CompanyName, View view) {
        ImageView imgView = (ImageView) view;

        switch (CompanyName) {
            case "Im":
                //해당 기업의 이미지 로고 set
                //imgView.setImageDrawable();
                break;
            default:
                break;
        }
    }

    public void setPermissionItem (String data, View view) {
        ImageView imgView = (ImageView) view;

        switch (data) {
            case "name":
                break;
            case "address":
                break;
            case "phone":
                break;
            default:
                break;
        }
    }
}
