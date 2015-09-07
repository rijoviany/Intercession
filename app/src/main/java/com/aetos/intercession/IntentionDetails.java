package com.aetos.intercession;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class IntentionDetails extends Activity {
    int count;
    ParseQuery<ParseObject> query;
    Button btnPrayed;
    String objectId;
    TextView tvCount, tvName, tvIntention, tvPlace, tvComma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setTheme(android.R.style.Theme_Dialog);
        // ab.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_intention_details);
        //ActionBar ab=getActionBar();
        Intent i = getIntent();
        tvName = (TextView) findViewById(R.id.tvName);
        tvPlace = (TextView) findViewById(R.id.tvPlace);
        tvIntention = (TextView) findViewById(R.id.tvIntention);
        tvCount = (TextView) findViewById(R.id.tvCount);
        tvComma = (TextView) findViewById(R.id.tvComma);
        //Toast.makeText(getApplicationContext(),i.getStringExtra("prayer"),Toast.LENGTH_SHORT).show();
        btnPrayed = (Button) findViewById(R.id.btnPrayed);
        tvName.setText(i.getStringExtra("name").toString());
        if (tvName.getText().toString().trim().equals("")) {
            tvComma.setVisibility(View.INVISIBLE);
        }
        tvPlace.setText(i.getStringExtra("place").toString());
        tvIntention.setText(i.getStringExtra("prayer").toString());
        objectId = i.getStringExtra("objectId").toString();
        count = i.getIntExtra("Count", 0);
        tvCount.setText("" + count);
        btnPrayed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prayed();
            }
        });
    }

    private void prayed() {
        {
            btnPrayed.setVisibility(View.INVISIBLE);
            tvCount.setText("" + (count + 1));
            query = ParseQuery.getQuery("Intercessions");
            // Retrieve the object by id
            query.getInBackground(objectId, new GetCallback<ParseObject>() {
                public void done(ParseObject intercessions, ParseException e) {
                    if (e == null) {
                        intercessions.increment("Count");
                        intercessions.saveInBackground();
                    }
                    if (count + 1 > 20) {
                        intercessions.deleteInBackground();

                        finish();
                    }
                    Toast.makeText(getApplicationContext(), "Thanks for praying for " + tvName.getText().toString().trim() + ".", Toast.LENGTH_LONG).show();
                }
            });

        }
    }


}
