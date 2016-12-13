//package com.example.pratik.jpa2;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.os.Environment;
//
//import com.example.pratik.jpa2.Model.Contacts;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Pratik on 18-Nov-16.
// */
//
//public class DatabaseHandler extends SQLiteOpenHelper {
//    private static final int DATABASE_VERSION = 1;
//
//    // Database Name
//    private static final String DATABASE_NAME = "contactsManager";
//
//    // Contacts table name
//    private static final String TABLE_POSTS = "posts";
//
//    // Contacts Table Columns names
//    private static final String KEY_RAW_ID = "rawid";
//    private static final String KEY_USER_ID = "userId";
//    private static final String KEY_ID = "id";
//    private static final String KEY_BODY = "body";
//    private static final String KEY_TITLE = "title";
//
//        public DatabaseHandler(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
////    public DatabaseHandler(final Context context) {
////        super(context, Environment.getExternalStorageDirectory()
////                + File.separator + "/DataBase/" + File.separator
////                + DATABASE_NAME, null, DATABASE_VERSION);
////    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_POSTS + "("
//                + KEY_RAW_ID + " INTEGER PRIMARY KEY,"
//                + KEY_USER_ID + " TEXT,"
//                + KEY_ID + " TEXT,"
//                + KEY_TITLE + " TEXT,"
//                + KEY_BODY + " TEXT" + ")";
//        db.execSQL(CREATE_CONTACTS_TABLE);
//    }
//
//    // Upgrading database
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
//
//        // Create tables again
//        onCreate(db);
//    }
//
//    /**
//     * All CRUD(Create, Read, Update, Delete) Operations
//     */
//
//    // Adding new contact
//    void addContact(ArrayList<Contacts> contactc) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        for (int i = 0; i < contactc.size(); i++) {
//
//
//        values.put(KEY_USER_ID, contactc.get(i).getUserId()); // Contact Name
//        values.put(KEY_ID, contactc.get(i).getId()); // Contact Phone
//        values.put(KEY_TITLE, contactc.get(i).getTitle1()); // Contact Phone
//        values.put(KEY_BODY, contactc.get(i).getBody()); // Contact Phone
//
//        // Inserting Row
//        db.insert(TABLE_POSTS, null, values);
//    }
//        db.close(); // Closing database connection
//    }
//
//    // Getting All Contacts
//    public List<Contacts> getAllContacts() {
//        List<Contacts> contactList = new ArrayList<Contacts>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_POSTS;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Contacts contact = new Contacts();
//                contact.setRaw_id(Integer.parseInt(cursor.getString(0)));
//                contact.setUserId(cursor.getString(1));
//                contact.setId(cursor.getString(2));
//                contact.setTitle1(cursor.getString(3));
//                contact.setBody(cursor.getString(4));
//                // Adding contact to list
//                contactList.add(contact);
//            } while (cursor.moveToNext());
//        }
//
//        // return contact list
//        return contactList;
//    }
//}
