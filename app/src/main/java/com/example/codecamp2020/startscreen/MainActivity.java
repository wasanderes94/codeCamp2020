package com.example.codecamp2020.startscreen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.codecamp2020.emergency.displayDoctorsActivity;
import com.example.codecamp2020.R;
import com.example.codecamp2020.util.newsfeed.Publication;
import com.example.codecamp2020.util.XMLParser.XMLParser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView textView3;
    private ImageView imageView2;
    private TextView location;
    private TextView infectionTextView;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);

        textView3 = (TextView) findViewById(R.id.Localisation);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        location = (TextView) findViewById(R.id.advices);
        infectionTextView = (TextView) findViewById(R.id.infectionTextView);
        findViewById(R.id.emergencyButton).setOnClickListener(this);




        class DownloadXMLTask extends AsyncTask<String, Void, List<Publication>>{

            @Override
            protected List<Publication> doInBackground(String... urls) {
                List<Publication> list = null;
                try {
                    list = this.loadXML(urls[0]);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } finally {
                    return list;
                }
            }

            @Override
            protected void onPostExecute(List<Publication> publications) {
                int x = 0;
                super.onPostExecute(publications);
            }

            private List<Publication> loadXML(String urlString) throws IOException, ParseException, XmlPullParserException {
                InputStream in = null;
                XMLParser parser = new XMLParser();
                List<Publication> entries = null;
                try {
                    in = downloadURL(urlString);
                    entries = parser.parse(in);
                } finally {
                    if(in != null) in.close();
                }
                return entries;
            }

            private InputStream downloadURL(String urlString) throws IOException {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                return conn.getInputStream();
            }
        }
        DownloadXMLTask d = new DownloadXMLTask();
        d.execute("https://export.arxiv.org/api/query?search_query=all:COVID-19");

        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://newsapi.org/v2/everything?q=corona&apiKey=86eeacda224b4c92a023e316d1404de5&language=de", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("articles");
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject article = jsonArray.getJSONObject(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.emergencyButton:
                //Intent i = new Intent(MainActivity.this, MapsActivity.class);
                Intent i = new Intent(MainActivity.this, displayDoctorsActivity.class);
                startActivity(i);
                //TODO implement
                break;
        }
    }
}
