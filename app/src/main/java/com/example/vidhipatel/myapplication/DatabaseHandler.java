package com.example.vidhipatel.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vidhi.patel on 6/22/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    static final String DB_NAME = "userdb";
    private static final String TB_NAME = "users";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";

    SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TB_NAME + "(" + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT," + USERNAME + " TEXT," + EMAIL + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addUser(User user) {

        ContentValues values = new ContentValues();
        values.put(NAME, user.getName());
        values.put(USERNAME, user.getUsername());
        values.put(EMAIL, user.getEmail());

        // Inserting Row
        db.insert(TB_NAME, null, values);
    }

    // Getting All Contacts
    List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TB_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user =
                        new User(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))),
                                cursor.getString(cursor.getColumnIndex(NAME)),
                                cursor.getString(cursor.getColumnIndex(USERNAME)),
                                cursor.getString(cursor.getColumnIndex(EMAIL)));
                // Adding contact to list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        // return contact list
        return userList;
    }

    // Deleting single contact
    void deleteUser(User user) {
        db.delete(TB_NAME, ID + " = ?",
                new String[]{String.valueOf(user.getId())});

    }

    void deleteAllUsers(){
        db.delete(TB_NAME,null,null);
    }
}
