package com.example.pratik.jpa2.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pratik.jpa2.Model.Worldpopulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pratik on 11-Dec-16.
 */

public class CountryDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_RANK = "name";
    private static final String KEY_COUNTRYNAME = "countryname";
    private static final String KEY_POPULATION = "population";
    private static final String KEY_FLAG = "flag";

    public CountryDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_RANK + " TEXT,"
                + KEY_COUNTRYNAME + " TEXT,"
                + KEY_POPULATION + " TEXT,"
                + KEY_FLAG + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addContact(ArrayList<Worldpopulation> worldpopulations) {
        SQLiteDatabase db = this.getWritableDatabase();


        for (int i = 0; i <worldpopulations.size() ; i++) {
            ContentValues values = new ContentValues();

            values.put(KEY_RANK,worldpopulations.get(i).getRank());
            values.put(KEY_COUNTRYNAME,worldpopulations.get(i).getCountry());
            values.put(KEY_POPULATION,worldpopulations.get(i).getPopulation());
            values.put(KEY_FLAG,worldpopulations.get(i).getFlag());
            db.insert(TABLE_CONTACTS, null, values);
        }


        // Inserting Row

        db.close(); // Closing database connection
    }



    public List<Worldpopulation> getAllContacts() {
        List<Worldpopulation> worldpopulationList = new ArrayList<Worldpopulation>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Worldpopulation worldpopulation = new Worldpopulation();
                worldpopulation.set_id(Integer.parseInt(cursor.getString(0)));
                worldpopulation.setRank(cursor.getString(1));
                worldpopulation.setCountry(cursor.getString(2));
                worldpopulation.setPopulation(cursor.getString(3));
                worldpopulation.setFlag(cursor.getString(4));
                // Adding contact to list
                worldpopulationList.add(worldpopulation);
            } while (cursor.moveToNext());
        }

        // return contact list
        return worldpopulationList;
    }


}
