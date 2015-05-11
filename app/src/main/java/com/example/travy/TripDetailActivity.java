package com.example.travy;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travy.model.Site;
import com.example.travy.model.SiteSource;
import com.example.travy.model.Trip;
import com.example.travy.model.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TripDetailActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    /**
     * GoogleApiClient wraps our service connection to Google Play Services and provides access
     * to the user's sign in state as well as the Google's APIs.
     */
    protected GoogleApiClient mGoogleApiClient;
    private static final String TAG = "PlaceAutocomplete";
    private PlaceAutocompleteAdapter mAdapter;
    private SiteSource siteDataSource;
    private AutoCompleteTextView mAutocompleteView;

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(41.00, -89.00), new LatLng(42.00, -88.00));

    //connect with model:
    private List<Site> siteList;
    private ListView listView;
    //    private SiteSource siteDataSource;siteDataSource
    private ArrayList<String> placeNameList;
    private ArrayList<String> placeNameList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set up the Google API Client if it has not been initialised yet.
        if (mGoogleApiClient == null) {
            rebuildGoogleApiClient();
        }
        siteDataSource = new SiteSource();
        setContentView(R.layout.activity_tripdetail);
//        siteDataSource = new SiteSource();
        listView = (ListView) findViewById(android.R.id.list);
        placeNameList = new ArrayList<String>();
//        String fileName = User.getID(LoginActivity.getCurrentUser())+TripActivity.SelectedTripName+".txt";
//        for(int i = 0; i < SiteSource.getSize(); i++){
//            Log.i("ABC!", "OnCreate has :" + SiteSource.returnList().get(i).getPlaceId());
//        }
        refreshSiteList();


        // Retrieve the AutoCompleteTextView that will display Place suggestions.
        mAutocompleteView = (AutoCompleteTextView)
                findViewById(R.id.autocomplete_places);

        // Register a listener that receives callbacks when a suggestion has been selected
        mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);

        // Retrieve the TextView that will display details of the selected place.
//        mPlaceDetailsText = (TextView) findViewById(R.id.place_details);

        // Set up the adapter that will retrieve suggestions from the Places Geo Data API that cover
        // the entire world.
        mAdapter = new PlaceAutocompleteAdapter(this, android.R.layout.simple_list_item_1, BOUNDS_GREATER_SYDNEY, null);

        mAutocompleteView.setAdapter(mAdapter);

        // Set up the 'clear text' button that clears the text in the autocomplete view

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshSiteList();
    }

    private void refreshSiteList() {
        siteList = siteDataSource.findAllSite();
        convertIdtoPlaceName(siteList);

//        File stored = Utility.GetFilePlace("PlaceNameList.txt");
//        String allInfo = "";
//        try {
//            allInfo = Utility.GetAllInfoFromFile(stored);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        allInfo = allInfo.replace("[", "");
//        allInfo = allInfo.replace("]", "");
//        String[] EachPlace = allInfo.split(",");
//        for (int i = 0; i < EachPlace.length; i++) {
//            placeNameList.add(EachPlace[i].trim());
//        }
//        if (Utility.GetFilePlace("PlaceNameList.txt").exists()) {
//            Utility.GetFilePlace("PlaceNameList.txt").delete();
//        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, placeNameList);
        listView.setAdapter(adapter);

    }

    //convert list of place id to place name for listView
    private void convertIdtoPlaceName(List<Site> sites) {
        for (Site s : sites) {
            PendingResult<PlaceBuffer> buffer = Places.GeoDataApi.getPlaceById(mGoogleApiClient, s.getPlaceId());
            buffer.setResultCallback(mPlaceCallBack);
//                @Override
//                public void onResult(PlaceBuffer places) {
//                    if (!places.getStatus().isSuccess()) {
//                        Log.e("DEBUG", "WE FAILED");
//                        return;
//                    }
//                    final Place place = places.get(0);
//                    placeNameList2.add(String.valueOf(place.getName()));
//                    try {
//                        Utility.AddToFileWithoutCopy(Utility.GetFilePlace("PlaceNameList.txt"), Arrays.toString(placeNameList2.toArray()));
////                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
////                    Log.i("DEBUG", "ADD PLACE NAME TO LIST" + String.valueOf(place.getName()));
////                    Log.i("DEBUG", "WTF" + Arrays.toString(placeNameList.toArray()));
//                    places.close();
//                }
//            });
        }
    }

    private ResultCallback<PlaceBuffer> mPlaceCallBack = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e("PLACE BUFFER", "WE FAILED");
                return;
            }
            final Place place = places.get(0);
            placeNameList.add(String.valueOf(place.getName()));
            Log.i("ADD PLACE NAME TO LIST", String.valueOf(place.getName()));
            places.close();
        }
    };
    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data API
     * to retrieve more details about the place.
     *
     * @see com.google.android.gms.location.places.GeoDataApi#getPlaceById(com.google.android.gms.common.api.GoogleApiClient,
     * String...)
     */
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a PlaceAutocomplete object from which we
             read the place ID.
              */
            final PlaceAutocompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Autocomplete item selected: " + item.description);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
              details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            Toast.makeText(getApplicationContext(), "Clicked: " + item.description,
                    Toast.LENGTH_SHORT).show();

            Log.i(TAG, "Called getPlaceById to get Place details for " + item.placeId);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());

                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);
            String PlaceID = place.getId();
            Site currentSite = new Site(PlaceID);
            siteDataSource.addSite(currentSite);
            String TripName = TripActivity.SelectedTripName;
            String UserID = User.getID(LoginActivity.getCurrentUser());
            String fileName = UserID + TripName + ".txt";
            try {
                Utility.AddToFile(Utility.GetFilePlace(fileName), PlaceID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            refreshSiteList();

            // Format details of the place for display and show it in a TextView.
            // mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
            //      place.getId(), place.getAddress(), place.getPhoneNumber(),
            //    place.getWebsiteUri()));

        }
    };

    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }

    /**
     * Construct a GoogleApiClient for the {@link Places#GEO_DATA_API} using AutoManage
     * functionality.
     * This automatically sets up the API client to handle Activity lifecycle events.
     */
    protected synchronized void rebuildGoogleApiClient() {
        // When we build the GoogleApiClient we specify where connected and connection failed
        // callbacks should be returned, which Google APIs our app uses and which OAuth 2.0
        // scopes our app requests.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addConnectionCallbacks(this)
                .addApi(Places.GEO_DATA_API)
                .build();
    }

    /**
     * Called when the Activity could not connect to Google Play services and the auto manager
     * could resolve the error automatically.
     * In this case the API is not available and notify the user.
     *
     * @param connectionResult can be inspected to determine the cause of the failure
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(this,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();

        // Disable API access in the adapter because the client was not initialised correctly.
        mAdapter.setGoogleApiClient(null);
    }

    @Override
    public void onConnected(Bundle bundle) {
        // Successfully connected to the API client. Pass it to the adapter to enable API access.
        mAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(TAG, "GoogleApiClient connected.");

    }

    @Override
    public void onConnectionSuspended(int i) {
        // Connection to the API client has been suspended. Disable API access in the client.
        mAdapter.setGoogleApiClient(null);
        Log.e(TAG, "GoogleApiClient connection suspended.");
    }

    public void toMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void backToTrip(View v) {
        Intent intent = new Intent(this, TripActivity.class);
        startActivity(intent);
    }
}