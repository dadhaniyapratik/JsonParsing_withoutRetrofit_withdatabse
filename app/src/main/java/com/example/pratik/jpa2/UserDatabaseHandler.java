


package com.example.pratik.jpa2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pratik.jpa2.Model.Address;
import com.example.pratik.jpa2.Model.Company;
import com.example.pratik.jpa2.Model.Contacts;
import com.example.pratik.jpa2.Model.Geo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pratik on 08-Dec-16.
 */

public class UserDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_STREET = "street";
    private static final String KEY_SUITE = "suite";
    private static final String KEY_CITY = "city";
    private static final String KEY_ZIPCODE = "zipcode";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_WEBSITE = "website";
    private static final String KEY_COMPANY_NAME = "companyname";
    private static final String KEY_CATCTPHRASE = "catchphrase";
    private static final String KEY_BS = "bs";

    public UserDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_USERNAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_STREET + " TEXT,"
                + KEY_SUITE + " TEXT,"
                + KEY_CITY + " TEXT,"
                + KEY_ZIPCODE + " TEXT,"
                + KEY_LAT + " TEXT,"
                + KEY_LNG + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + KEY_WEBSITE + " TEXT,"
                + KEY_COMPANY_NAME + " TEXT,"
                + KEY_CATCTPHRASE + " TEXT,"
                + KEY_BS + " TEXT" + ")";
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

    void addContact(ArrayList<Contacts> contactses) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        for (int i = 0; i <contactses.size() ; i++) {
            values.put(KEY_ID, contactses.get(i).getId());
            values.put(KEY_NAME, contactses.get(i).getName());
            values.put(KEY_USERNAME, contactses.get(i).getUsername());
            values.put(KEY_EMAIL, contactses.get(i).getEmail());

            for (int j = 0; j <contactses.get(i).getAddresses().size() ; j++) {
                values.put(KEY_STREET, contactses.get(i).getAddresses().get(j).getStreet());
                values.put(KEY_SUITE,  contactses.get(i).getAddresses().get(j).getSuite());
                values.put(KEY_CITY,  contactses.get(i).getAddresses().get(j).getCity());
                values.put(KEY_ZIPCODE, contactses.get(i).getAddresses().get(j).getZipcode());

                for (int k = 0; k <contactses.get(i).getAddresses().get(j).getGeos().size() ; k++) {
                    values.put(KEY_LAT, contactses.get(i).getAddresses().get(j).getGeos().get(k).getLat());
                    values.put(KEY_LNG, contactses.get(i).getAddresses().get(j).getGeos().get(k).getLng());
                }
            }

            values.put(KEY_PHONE, contactses.get(i).getId());
            values.put(KEY_WEBSITE, contactses.get(i).getId());

            for (int j = 0; j <contactses.get(i).getCompanies().size() ; j++) {
                values.put(KEY_COMPANY_NAME, contactses.get(i).getCompanies().get(j).getName());
                values.put(KEY_CATCTPHRASE, contactses.get(i).getCompanies().get(j).getCatchPhrase());
                values.put(KEY_BS, contactses.get(i).getCompanies().get(j).getBs());
            }
        }

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }


    public List<Contacts> getAllContacts() {
        List<Contacts> contactList = new ArrayList<Contacts>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contacts contact = new Contacts();
                contact.setId(cursor.getString(0));
                contact.setName(cursor.getString(1));
                contact.setUsername(cursor.getString(2));
                contact.setEmail(cursor.getString(3));

                Address address = new Address();
                address.setStreet(cursor.getString(4));
                address.setSuite(cursor.getString(5));
                address.setCity(cursor.getString(6));
                address.setZipcode(cursor.getString(7));


                Geo geo = new Geo();
                geo.setLat(cursor.getString(8));
                geo.setLng(cursor.getString(9));
                ArrayList<Geo> geos = new ArrayList<>();
                geos.add(geo);

                contact.setPhone(cursor.getString(10));
                contact.setWebsite(cursor.getString(11));

                Company company = new Company();
                company.setName(cursor.getString(12));
                company.setCatchPhrase(cursor.getString(13));
                company.setBs(cursor.getString(14));

                ArrayList<Address> addresses = new ArrayList<>();
                address.setGeos(geos);
                addresses.add(address);

                ArrayList<Company> companies = new ArrayList<>();
                companies.add(company);

                contact.setAddresses(addresses);
                contact.setCompanies(companies);
                contactList.add(contact);

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
}