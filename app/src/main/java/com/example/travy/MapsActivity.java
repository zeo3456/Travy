package com.example.travy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;

import com.example.travy.model.DataSource;
import com.example.travy.model.Site;
import com.example.travy.model.SiteSource;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

//import com.google.android.gms.location.places.Place;


public class MapsActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private static final double coli_lat = 43.084994;
    private static final double coli_lng = -89.400412;
    private static final float DEFAULTZOOM = 15;
    Marker marker;
    // AutoCompleteTextView actv;
    private static final String TAG = "Show Place";
    private DataSource source;
    protected GoogleApiClient mGoogleApiClient;
    private static final LatLng COLI = new LatLng(43.084994, -89.400412);
    private ArrayList<String> placeIdList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        gotoLocation(coli_lat, coli_lng);
//        placeIdList = new ArrayList<String>();
//        placeIdList.add("ChIJ4zGFAZpYwokRGUGph3Mf37k");
//        placeIdList.add("ChIJb8Jg9pZYwokR-qHGtvSkLzs");
//        placeIdList.add("ChIJKxDbe_lYwokRVf__s8CPn-o");
//        SiteSource siteDataSource = new SiteSource();
        ArrayList<Site> theList = SiteSource.returnList();
        for (int i = 0; i < SiteSource.getSize(); i++) {
            placeIdList.add(theList.get(i).getPlaceId());
        }
        mMap.setMyLocationEnabled(true);//the button that enables the current location
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, 0, this).addApi(Places.GEO_DATA_API).build();
        //Marker coli = mMap.addMarker(new MarkerOptions().position(COLI));
        showMultipleMarker(placeIdList);
/*
         mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });
        //drop pin function
       /* mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng ll) {
                Geocoder gc = new Geocoder(MapsActivity.this);
                List<Address> list = null;
                try {
                    list = gc.getFromLocation(ll.latitude, ll.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();

                }
                Address add = list.get(0);
                String premise;
                if (add.getFeatureName() != null) {
                    premise = add.getFeatureName();
                } else {
                    premise = "Placeholder";
                }
                MapsActivity.this.setMarker(add.getLocality(), premise, ll.latitude, ll.longitude);

            }
        });*/
    }

    private void showMultipleMarker(ArrayList<String> places) {
        for (int i = 0; i < places.size(); i++) {
            PendingResult<PlaceBuffer> placeBuffer = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeIdList.get(i));
            placeBuffer.setResultCallback(mPlaceCallback);

        }
    }

    private ResultCallback<PlaceBuffer> mPlaceCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());

                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);

            // Format details of the place for display and show it in a TextView.
            Marker cur = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(String.valueOf(place.getName())).snippet(String.valueOf(place.getAddress())).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin)));
//////TODO: save place
            CameraUpdate update = CameraUpdateFactory.newLatLng(place.getLatLng());
            mMap.moveCamera(update);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
            //CameraPosition cameraPosition = new CameraPosition.Builder().target(place.getLatLng()).zoom(12).build();
            //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            Log.i(TAG, "Place details received: " + place.getName());
            places.close();
        }
    };

    /*
        public void geoLocate(View v) throws IOException {
            EditText et = (EditText) findViewById(R.id.editText1);
            String location = et.getText().toString();
            if (location.length() == 0) {
                Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show();
                return;
            }
            hideSoftKeyboard(v);
            Geocoder gc = new Geocoder(this);
            List<Address> list = gc.getFromLocationName(location, 1);
            Address add = list.get(0);
            String locality = add.getLocality();
            double lat = add.getLatitude();
            double lng = add.getLongitude();
            gotoLocation(lat, lng);
            setMarker(locality, add.getAdminArea(), lat, lng);
        }

        private void hideSoftKeyboard(View v) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    */
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setMarker(String locality, String country, double lat, double lng) {
        if (marker != null) {
            marker.remove();
        }
        MarkerOptions options = new MarkerOptions()
                .title(locality)
                .position(new LatLng(lat, lng))
                .icon(BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_BLUE
                ));
        if (country.length() > 0) {
            options.snippet(country);
        }
        marker = mMap.addMarker(options);

    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
//            if (mMap != null) {
//                setUpMap();
//            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    private void gotoLocation(double lat, double lng) {
        LatLng latlng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(latlng);
        mMap.moveCamera(update);
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void onBackPressed() {
        Intent intent = new Intent(this, TripDetailActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.back_in, R.anim.back_out);
    }
/*
    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }*/
}
