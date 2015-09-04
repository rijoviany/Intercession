package com.aetos.intercession;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.NumberFormat;
import java.util.List;

public class Intentions extends ActionBarActivity {
    ListView lstIntentions;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ArrayAdapter<String> adapter;
    SwipeRefreshLayout swipeLayout;
    Boolean isFirstLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ab.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_intentions);
        Parse.initialize(getApplicationContext(), "IvVwtSwglXXDrwffWiWroeyNwSfI6yNkKQpLJNs8", "W9I7XC4HTBK1RX661sf7ar8jvGJ0AUJJ21wSdyyh");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        isFirstLoad = true;
        new RemoteDataTask().execute();
        //swipeLayout.setRefreshing(true);

        // swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
        //        android.R.color.holo_green_light,
        //       android.R.color.holo_orange_light,
        //       android.R.color.holo_red_light);
        //Parse.enableLocalDatastore(this);
        //ParseObject.registerSubclass(Model.class);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_intentions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Intentions.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please wait");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setProgressPercentFormat(NumberFormat.getIntegerInstance());
            // Show progressdialog
            if (isFirstLoad) {
                mProgressDialog.show();
                isFirstLoad = false;
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Locate the class table named "Country" in Parse.com
            swipeLayout.setRefreshing(true);
            swipeLayout.animate();
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "Intercessions");
            query.orderByDescending("createdAt");
            try {
                ob = query.find();
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            lstIntentions = (ListView) findViewById(R.id.lstIntentions);
            // Pass the results into an ArrayAdapter
            adapter = new ArrayAdapter<String>(Intentions.this,
                    R.layout.listview_item);
            // Retrieve object "name" from Parse.com database
            for (ParseObject country : ob) {
                adapter.add((String) country.get("prayer"));
            }
            // Binds the Adapter to the ListView
            lstIntentions.setAdapter(adapter);

            // Close the progressdialog
            mProgressDialog.dismiss();
            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    Toast.makeText(getApplicationContext(), "Refreshing", Toast.LENGTH_LONG).show();
                    new RemoteDataTask().execute();
                }
            });
            swipeLayout.setRefreshing(false);
            swipeLayout.clearAnimation();

            // Capture button clicks on ListView items
            lstIntentions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // Send single item click data to SingleItemView Class
                    Intent i = new Intent(Intentions.this,
                            IntentionDetails.class);
                    // Pass data "name" followed by the position
                    i.putExtra("prayer", ob.get(position).getString("prayer").toString());
                    i.putExtra("name", ob.get(position).getString("name").toString());
                    i.putExtra("place", ob.get(position).getString("place").toString());
                    i.putExtra("Count", ob.get(position).getInt("Count"));
                    i.putExtra("objectId", ob.get(position).getObjectId().toString());
                    // Open SingleItemView.java Activity
                    startActivity(i);
                    // new RemoteDataTask().execute();
                }
            });
        }
    }
}
