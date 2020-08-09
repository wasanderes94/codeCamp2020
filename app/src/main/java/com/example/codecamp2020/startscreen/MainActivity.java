package com.example.codecamp2020.startscreen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.codecamp2020.map.MapsActivity;
import com.example.codecamp2020.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements LocationListener {

    private TextView appBAr;
    private ImageView imageView2;
    private ImageSlider imageSlider;
    TextView state;
    LocationManager locationManager;
    private double latitude;
    private double longitude;
    public String locality;
    public String subLocality;
    public String region;
    public String mainLocation;
    private RequestQueue mQueue;
    TextView totalCases;
    TextView totalDeath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);

        initViews();
        setImageSlider();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1 );

        }
        else
        {
            detectCurrentLocation();

        }


        mQueue= Volley.newRequestQueue(this);
        jsonParse();



    }

    public void initViews() {
        appBAr = (TextView) findViewById(R.id.appBar);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageSlider = (ImageSlider) findViewById(R.id.image_slider);
        state=(TextView)findViewById(R.id.titleName);
        totalCases=(TextView)findViewById(R.id.tvCases);
        totalDeath=(TextView)findViewById(R.id.tvTotalDeaths);
    }

    public void setImageSlider() {
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.mask, "Always Wear Mask Outside Home", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.distance, "Keep At least 2 meter Distance From Others", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.washing_hands, "After Every 30 Minutes Wash Those Hands", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.mobile_phone, "Clean Your belongings Frequently", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.family, "Spend More Time With Your Family", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_CROP);

    }

    @SuppressLint("MissingPermission")
    private void detectCurrentLocation() {
        Toast.makeText(this,"getting current Location",Toast.LENGTH_SHORT).show();
        locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude=location.getLatitude();
        longitude=location.getLongitude();
        findAddress();
    }

    private void findAddress() {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder=new Geocoder(this, Locale.getDefault());
        try{

            addresses=geocoder.getFromLocation(latitude,longitude,1);

            String st=addresses.get(0).getAdminArea();
            String area=addresses.get(0).getLocality();
            String dist=addresses.get(0).getSubAdminArea();
            //String fullAddress=addresses.get(0).getAddressLine(0);
            locality=area;
            if(dist==null){subLocality="cant find";}else {subLocality=dist;}
            region=st;





        } catch (Exception e) {
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public  void onProviderDisabled(@NonNull String provider){

        Toast.makeText(this,"Please Turn on Location",Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults){
        if(requestCode==1)
        {
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                detectCurrentLocation();
            }
            else{
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }

    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        /* This is called when the GPS status alters */
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
            case LocationProvider.AVAILABLE:


                break;
        }}
    private void jsonParse() {

        final String url ="https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/RKI_Landkreisdaten/FeatureServer/0/query?where=1%3D1&outFields=OBJECTID,death_rate,cases,deaths,BL,county,GEN&returnGeometry=false&outSR=4326&f=json";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("features");

                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject data = jsonArray.getJSONObject(j).getJSONObject("attributes");
                                String gen=data.getString("GEN");
                                if(gen.equals(locality)){
                                    mainLocation=locality;
                                    break;
                                }else if (gen.equals(subLocality)){
                                    mainLocation=subLocality;

                                }


                            }
                            String merge=mainLocation+" IN "+region;
                            if (mainLocation==null){merge="Please Restart";}
                            state.setText(merge);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i).getJSONObject("attributes");
                                String gen=data.getString("GEN");
                                String cases=data.getString("cases");
                                String deaths=data.getString("deaths");


                                if (gen.equals(mainLocation)){
                                    if (totalCases.getText().toString().equals("0")){
                                        totalCases.setText(cases);}
                                    else {
                                        String cal=totalCases.getText().toString();
                                        int total=Integer.parseInt(cal)+Integer.parseInt(cases);

                                        totalCases.append(" | "+cases+" = "+total);
                                    }
                                    if (totalDeath.getText().toString().equals("0")){
                                        totalDeath.setText(deaths);}
                                    else {
                                        String cal=totalDeath.getText().toString();
                                        int total=Integer.parseInt(cal)+Integer.parseInt(deaths);

                                        totalDeath.append(" | "+deaths+" = "+total);
                                    }


                                }






                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

}

