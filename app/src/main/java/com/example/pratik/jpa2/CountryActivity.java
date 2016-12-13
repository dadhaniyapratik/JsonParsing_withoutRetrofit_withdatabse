package com.example.pratik.jpa2;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pratik.jpa2.Adapter.CountryAdapter;
import com.example.pratik.jpa2.DatabaseHandler.CountryDatabaseHandler;
import com.example.pratik.jpa2.Model.Worldpopulation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pratik on 08-Dec-16.
 */

public class CountryActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    CountryAdapter countryAdapter;
    private ProgressDialog pDialog;
    private ListView list_view;
    CountryDatabaseHandler countryDatabaseHandler;
    ArrayList<Worldpopulation> worldpopulationArrayList;
    ArrayList<Worldpopulation> worldpopulationArrayList1;

    // URL to get contacts JSON
    private static String url = "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        worldpopulationArrayList = new ArrayList<Worldpopulation>();
        worldpopulationArrayList1 = new ArrayList<Worldpopulation>();
        countryDatabaseHandler = new CountryDatabaseHandler(CountryActivity.this);
        list_view = (ListView) findViewById(R.id.list_view);

        new GetContacts().execute();
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(CountryActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response

            if (isNetworkAvailable()) {
                String jsonStr = sh.makeServiceCall(url);

                Log.e(TAG, "Response from url: " + jsonStr);

                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        // Getting JSON Array node

                        JSONArray worldpopulation1 = jsonObj.getJSONArray("worldpopulation");

                        // looping through All Contacts
                        for (int i = 0; i < worldpopulation1.length(); i++) {
                            JSONObject c = worldpopulation1.getJSONObject(i);

                            Worldpopulation worldpopulationObj = new Worldpopulation();
                            String id = c.getString("rank");
                            worldpopulationObj.setRank(id);
                            String country1 = c.getString("country");
                            worldpopulationObj.setCountry(country1);
                            String population = c.getString("population");
                            worldpopulationObj.setPopulation(population);
                            String flag = c.getString("flag");
                            worldpopulationObj.setFlag(flag);

                            worldpopulationArrayList.add(worldpopulationObj);


                        }

                        countryDatabaseHandler.addContact(worldpopulationArrayList);
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
                            countryAdapter = new CountryAdapter(CountryActivity.this, worldpopulationArrayList1);
                            list_view.setAdapter(countryAdapter);
                            countryAdapter.notifyDataSetChanged();
                        }
                    });

                }
            } else {
                worldpopulationArrayList = (ArrayList<Worldpopulation>) countryDatabaseHandler.getAllContacts();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */


            countryAdapter = new CountryAdapter(CountryActivity.this, worldpopulationArrayList);
            list_view.setAdapter(countryAdapter);
            countryAdapter.notifyDataSetChanged();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}