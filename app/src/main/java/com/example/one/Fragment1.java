package com.example.one;


import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.

 */
public class Fragment1 extends Fragment {

    List<ContactData> contactDataList=new ArrayList<>();
    private Context mContext;
    private View mView;

    public Fragment1() {
        // Required empty public constructor
    }

    @Override//加载Fragment的页面布局
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_1, container, false);
        mContext=mView.getContext();
        // Inflate the layout for this fragment
        return mView;
    }


    @Override
    public void onActivityCreated(Bundle bundle) {//在这进行数据获取Contact，并保存到数据库
        super.onActivityCreated(bundle);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.READ_CONTACTS},1);//不管是否同意请求，都会返回requestCode,传入到回调方法
        }else{
            readContact();
        }
//        Log.d("huan","readContact");
        RecyclerView recyclerView=mView.findViewById(R.id.fragment_recyclerView01);

        //在Fragment里面设置RecyclerView一定要设置LayoutManager
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);

        MyRecyclerViewAdapter adapter=new MyRecyclerViewAdapter(contactDataList);
        recyclerView.setAdapter(adapter);

        //将通讯录存入到数据库
        if (contactDataList!=null){
            ContactsDataBaseHelper dataBaseHelper=new ContactsDataBaseHelper(mContext,"contactData.db",null,1);
            SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
            db.delete("contactsData",null,null);//防止多次调用方法插入相同的值重复，所以每次存储前先清空表
            for (int i=0;i<contactDataList.size();i++){
                ContentValues values=new ContentValues();
                String Name=contactDataList.get(i).contactName;
                String phoneNumber=contactDataList.get(i).phoneNumber;
                values.put("id",(i+1));
                values.put("Name",Name);
                values.put("phoneNumber",phoneNumber);
                db.insert("contactsData",null,values);
            }
            db.close();
        }
    }


    public void readContact(){//读取通讯录到数据库
        Cursor cursor=null;
        try{
            Log.d("Tag","try");
            cursor= mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if (cursor.moveToFirst()){
                Log.d("Tag","readContact onActivityCreated");
                contactDataList.clear();//每次读取全部的通讯录的时候清空，免得重复
                do {
                    Log.d("Tag","进入循环");
                    String contactName=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    ContactData contactData=new ContactData(contactName,phoneNumber);
                    contactDataList.add(contactData);
                    Log.d("Tag","读取Cursor");
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if (cursor!=null){//关闭cursor是基本素养
                cursor.close();
            }
        }
    }

    //动态权限申请的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permission,int[] grantResult){
        switch(requestCode){
            case 1:
                if(grantResult.length!=0&&grantResult[0]==PackageManager.PERMISSION_GRANTED) {
                    readContact();
                }else{
                    Toast.makeText(getContext(),"请给相应权限",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}