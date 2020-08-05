package com.example.codecamp2020.startscreen;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.codecamp2020.map.MapsActivity;
import com.example.codecamp2020.R;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView textView3;
    private ImageView imageView2;
    private TextView location;
    private TextView infectionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);

        textView3 = (TextView) findViewById(R.id.Localisation);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        location = (TextView) findViewById(R.id.advices);
        infectionTextView = (TextView) findViewById(R.id.infectionTextView);
        findViewById(R.id.emergencyButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.emergencyButton:
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                //Intent i = new Intent(MainActivity.this, displayDoctorsActivity.class);
                startActivity(i);
                //TODO implement
                break;
        }
    }
}
