package com.example.pratik.jpa2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pratik.jpa2.Adapter.CustomBaseAdapter;
import com.example.pratik.jpa2.Model.Address;
import com.example.pratik.jpa2.Model.Company;
import com.example.pratik.jpa2.Model.Contacts;
import com.example.pratik.jpa2.Model.Geo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class UserActivity extends AppCompatActivity {

    ArrayList<Contacts> contactsArrayList;
    CustomBaseAdapter customBaseAdapter;
    private String TAG = MainActivity.class.getSimpleName();
    UserDatabaseHandler userDatabaseHandler;

    private ProgressDialog pDialog;
    ListView listView;

    // URL to get contacts JSON
    private static String url = "https://jsonplaceholder.typicode.com/users";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        userDatabaseHandler = new UserDatabaseHandler(UserActivity.this);
        listView = (ListView)findViewById(R.id.list_view);
        contactsArrayList = new ArrayList<>();
        new GetContacts().execute();
    }


    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(UserActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }


        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONArray jsonArray = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject c = jsonArray.getJSONObject(i);

                        Contacts contacts = new Contacts();
                        String id = c.getString("id");
                        contacts.setId(id);
                        String name = c.getString("name");
                        contacts.setName(name);
                        String username = c.getString("username");
                        contacts.setUsername(username);
                        String email = c.getString("email");
                        contacts.setEmail(email);

                        Address addressObj = new Address();
                        JSONObject address = c.getJSONObject("address");
                        String street = address.getString("street");
                        addressObj.setStreet(street);
                        String suite = address.getString("suite");
                        addressObj.setSuite(suite);
                        String city = address.getString("city");
                        addressObj.setCity(city);
                        String zipcode = address.getString("zipcode");
                        addressObj.setZipcode(zipcode);

                        Geo geoObj = new Geo();
                        JSONObject geo = address.getJSONObject("geo");
                        String lat = geo.getString("lat");
                        geoObj.setLat(lat);
                        String lng = geo.getString("lng");
                        geoObj.setLng(lng);


                        String phone = c.getString("phone");
                        contacts.setPhone(phone);
                        String website = c.getString("website");
                        contacts.setWebsite(website);

                        Company companyObj = new Company();
                        JSONObject company = c.getJSONObject("company");
                        String companyname = company.getString("name");
                        companyObj.setName(companyname);
                        String catchPhrase = company.getString("catchPhrase");
                        companyObj.setCatchPhrase(catchPhrase);
                        String bs = company.getString("bs");
                        companyObj.setBs(bs);

                        ArrayList<Geo> geos = new ArrayList<Geo>();
                        geos.add(geoObj);
                        // tmp hash map for single contact
                        ArrayList<Address> addresses = new ArrayList<Address>();
                        addressObj.setGeos(geos);
                        addresses.add(addressObj);

                        ArrayList<Company> companies = new ArrayList<>();
                        companies.add(companyObj);

                        // adding contact to contact list
                        contacts.setCompanies(companies);
                        contacts.setAddresses(addresses);

                        contactsArrayList.add(contacts);

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

//            userDatabaseHandler.addContact(contactsArrayList);
//            List<Contacts> contactsArrayList = userDatabaseHandler.getAllContacts();
            customBaseAdapter = new CustomBaseAdapter(UserActivity.this,contactsArrayList);
            listView.setAdapter(customBaseAdapter);
            customBaseAdapter.notifyDataSetChanged();


        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
