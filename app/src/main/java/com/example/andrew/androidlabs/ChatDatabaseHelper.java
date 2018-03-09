package com.example.andrew.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Andrew on 3/9/2018.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    static String DATABASE_NAME="Messages.db";
    static int VERSION_NUMBER=5;
    static String KEY_MESSAGE="Message";
    static String KEY_ID="id";
    private static String TABLE_NAME="MessageTable";
    static String ACTIVITY_NAME="ChatWindow";
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " + KEY_ID
            + " integer primary key autoincrement, " + KEY_MESSAGE
            + " VARCHAR2(20) );";


    public ChatDatabaseHelper(Context ctx) {

        super(ctx,DATABASE_NAME, null, VERSION_NUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(DATABASE_CREATE);
        Log.i("ChatDatabaseHelper", "Calling onCreate");


    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion= " + oldVersion + " newVersion= " + newVersion);
        onCreate(db);

    }
    public static String getTableName(){
        return TABLE_NAME;
    }
}
