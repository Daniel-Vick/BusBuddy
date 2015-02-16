package com.example.jeremy.realfinal;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

public class Activity2 extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);
        //setUpMapIfNeeded();

        Bundle extras = getIntent().getExtras();
        String destination = extras.getString("BLARG");


    //    Double[] latLong = getLatLongFromAddress(destination);
        LatLng latlng = new LatLng(100,200);
        //System.out.println(getLatLongFromAddress("939 Berkshire Ct"));
        MapFragment mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, mMapFragment);
        fragmentTransaction.remove(mMapFragment);
        fragmentTransaction.commit();

        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();


        CameraPosition cameraPosition = new CameraPosition.Builder().target(latlng).zoom(14.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.moveCamera(cameraUpdate);

       // map.addMarker(new MarkerOptions().position(new LatLng(latLong[0], latLong[1])));
    }


    public static Double[] getLatLongFromAddress(String youraddress) {
        String uri, uri1;
        HttpGet httpGet;
        HttpClient client;
        HttpResponse response;
        StringBuilder builder = new StringBuilder();
        try {
            uri1 = "http://maps.google.com/maps/api/geocode/json?address=" +
                    youraddress + "&sensor=false";
            uri = URLEncoder.encode(uri1, "UTF-8");
            client = new DefaultHttpClient();
            httpGet = new HttpGet(uri);
            String value= httpGet.toString();
            Log.d(value,"h");
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();

            int b;
            while ((b = stream.read()) != -1) {
                builder.append((char) b);
            }

            } catch (ClientProtocolException e) {
            e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
            }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(builder.toString());

            double lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            double lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

            Log.d("latitude", "" + lat);
            Log.d("longitude", "" + lng);
            Double[] latLong = {lat, lng};
            return latLong;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    return new Double[]{0.0, 0.0};
    }



    public void moveToHomeScreen(View view) {

        Intent intent = new Intent(Activity2.this, MainActivity.class);
        startActivity(intent);
    }

    public void moveToFinalScreen(View view) {

        //Intent intent = new Intent(Activity2.this, FinalActivity.class);
        //startActivity(intent);
    }
}