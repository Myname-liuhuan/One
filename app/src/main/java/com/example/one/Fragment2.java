package com.example.one;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {

    List<ContactData> mContactData=new ArrayList<>();
    LinearLayout linearLayout;
    public boolean add=true;

    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Log.d("Tag","Fragment2");
        linearLayout=(LinearLayout)getActivity().findViewById(R.id.linearLayout_fragment2);

        readMessage();
        insertMessage();

    }

    public void readMessage(){//读取数据库并加载到页面
        ContactsDataBaseHelper dataBaseHelper=new ContactsDataBaseHelper(getContext(),"contactData.db",null,1);
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        Cursor cursor=db.query("message",null,null,null,null,null,null);
        mContactData.clear();
        if (cursor.moveToFirst()){
            do{
                String Name=cursor.getString(cursor.getColumnIndex("Name"));
                String phoneNumber=cursor.getString(cursor.getColumnIndex("phoneNumber"));
                ContactData contactData=new ContactData(Name,phoneNumber);
                mContactData.add(contactData);
                //生成控件

                if(add){
                    TextView textView=new TextView(getContext());
                    LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(params);
                    textView.setText(Name+"\n"+phoneNumber);
                    textView.setTextSize(20);
                    linearLayout.addView(textView);

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(getContext(),TalkActivity.class);
                            startActivity(intent);
                        }
                    });

                    Log.d("liu","初始化TextView");

                }

            }while(cursor.moveToNext());
        }
        add=false;
        db.close();
    }

    public void insertMessage(){
        if (MyRecyclerViewAdapter.contactDataToFragmentTwo!=null){
            ContactData contactData=MyRecyclerViewAdapter.contactDataToFragmentTwo;
            String Name=contactData.contactName;
            String phoneNumber=contactData.phoneNumber;
            if (mContactData.size()==0){
                insertDatabaseAndAddTextView(Name,phoneNumber);
                Log.d("liu","list为空");
            }else{
                int j=0;
                for (int i=0;i<mContactData.size();i++){
                    if ((mContactData.get(i).contactName).equals(Name)){
                        j++;
                    }
                }
                if (j==0){
                    insertDatabaseAndAddTextView(Name,phoneNumber);
                    Log.d("liu","没有重复的");
                }
            }
        }
    }


    public void insertDatabaseAndAddTextView(String Name,String phoneNumber){
//                    mContactData.add(contactData);
        //生成控件
        TextView textView=new TextView(getContext());
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        textView.setText(Name+"\n"+phoneNumber);
        textView.setTextSize(20);
        linearLayout.addView(textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),TalkActivity.class);
                startActivity(intent);
            }
        });

        //添加到数据库
        ContactsDataBaseHelper dataBaseHelper=new ContactsDataBaseHelper(getContext(),"contactData.db",null,1);
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("Name",Name);
        values.put("phoneNumber",phoneNumber);
        db.insert("message",null,values);
        values.clear();
        db.close();
        MyRecyclerViewAdapter.contactDataToFragmentTwo=null;
    }


}
