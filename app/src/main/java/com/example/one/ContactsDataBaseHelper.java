package com.example.one;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liuhuan1 on 2018/6/10.
 */

public class ContactsDataBaseHelper extends SQLiteOpenHelper {

    //用来存放通讯录
    public static final String CREATE_CONTACTSDATA="create table contactsData("
            +"id integer primary key autoincrement,"
            +"Name text,"
            +"phoneNumber text)";

    //用来存放Message
    public static final String CREATE_MESSAGE="create table message("
            +"id integer primary key autoincrement,"
            +"Name text,"
            +"phoneNumber text)";


    public ContactsDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTSDATA);
        db.execSQL(CREATE_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
