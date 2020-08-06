package com.example.codecamp2020.startscreen;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.codecamp2020.map.MapsActivity;
import com.example.codecamp2020.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView textView3;
    private ImageView imageView2;
    private ImageSlider imageSlider;
    private TextView infectionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);

        textView3 = (TextView) findViewById(R.id.Localisation);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageSlider = (ImageSlider) findViewById(R.id.image_slider);
        infectionTextView = (TextView) findViewById(R.id.infectionTextView);
        findViewById(R.id.emergencyButton).setOnClickListener(this);


        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.mask,"Always Wear Mask Outside Home", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.distance,"Keep At least 2 meter Distance From Others",ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.washing_hands,"After Every 30 Minutes Wash Those Hands", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.mobile_phone,"Clean Your belongings Frequently", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.family,"Spend More Time With Your Family", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_CROP);



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
