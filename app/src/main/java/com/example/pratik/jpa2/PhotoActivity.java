package com.example.pratik.jpa2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pratik.jpa2.Adapter.PhotoAdapter;
import com.example.pratik.jpa2.Adapter.PostAdapter;
import com.example.pratik.jpa2.Model.Contacts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView list_view;
    ArrayList<Contacts> contactsArrayList;
    PhotoAdapter photoAdapter;

    // URL to get contacts JSON
    private static String url = "https://jsonplaceholder.typicode.com/photos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        contactsArrayList= new ArrayList<Contacts>();
        list_view = (ListView)findViewById(R.id.list_view);
        new GetContacts().execute();
        photoAdapter = new PhotoAdapter(PhotoActivity.this,contactsArrayList);
    }
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(PhotoActivity.this);
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
                    JSONArray jsonArray = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject c = jsonArray.getJSONObject(i);

                        Contacts contacts = new Contacts();
                        String albumId = c.getString("albumId");
                        contacts.setAlbumId(albumId);
                        String id = c.getString("id");
                        contacts.setId(id);
                        String title1 = c.getString("title");
                        contacts.setTitle1(title1);
                        String url = c.getString("url");
                        contacts.setUrl(url);
                        String thumbnailUrl = c.getString("thumbnailUrl");
                        contacts.setThumbnailUrl(thumbnailUrl);
                        // adding contact to contact list
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
            list_view.setAdapter(photoAdapter);
            photoAdapter.notifyDataSetChanged();
            /**
             * Updating parsed JSON data into ListView
             * */

        }

    }
}


