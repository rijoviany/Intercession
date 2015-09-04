package com.aetos.intercession;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;


public class PrayerRequest extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_prayer_request);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //ParseAnalytics.trackAppOpenedInBackground(getIntent());
        Parse.initialize(getApplicationContext(), "IvVwtSwglXXDrwffWiWroeyNwSfI6yNkKQpLJNs8", "W9I7XC4HTBK1RX661sf7ar8jvGJ0AUJJ21wSdyyh");
        //

        ParseObject.registerSubclass(Model.class);
        final EditText txtName = (EditText) findViewById(R.id.txtName);
        final EditText txtPlace = (EditText) findViewById(R.id.txtPlace);
        final EditText txtIntention = (EditText) findViewById(R.id.txtIntention);

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model m = new Model();
                if (txtIntention.getText().toString().trim().equals("") || txtPlace.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Don't keep place or intention empty.", Toast.LENGTH_SHORT).show();
                } else {
                    m.setName(txtName.getText().toString());
                    m.setPlace(txtPlace.getText().toString());
                    m.setPrayer(txtIntention.getText().toString().trim());
                    m.setCount(0);
                    m.saveInBackground();
                    // m.save();
                    txtIntention.setText("");
                    Toast.makeText(getApplicationContext(), "Your prayer request will be submitted soon.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_prayer_request, menu);
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


}
