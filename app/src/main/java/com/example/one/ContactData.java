package com.example.one;

import android.util.Log;

/**
 * Created by liuhuan1 on 2018/6/8.
 */

public class ContactData {//将多条数据结合起来存放
    String contactName;
    String phoneNumber;
    public ContactData(String contactName,String phoneNumber){
        this.contactName=contactName;
        this.phoneNumber=phoneNumber;
        Log.d("Tag","ContactData初始化");
    }

    public boolean equal(ContactData contactData){
        if ((contactName.equals(contactData.contactName))&&(phoneNumber.equals(contactData.phoneNumber))){
            return true;
        }
        else{
            return false;
        }
    }
}
