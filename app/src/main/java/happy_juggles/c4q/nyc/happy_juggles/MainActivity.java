package happy_juggles.c4q.nyc.happy_juggles;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity implements OnMapReadyCallback, LocationListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    GoogleMap map;
    ArrayList<LatLng> markerPoints;
    TextView tvDistanceDuration;
    RadioButton rbDriving;
    RadioButton rbBiCycling;
    RadioButton rbWalking;
    RadioGroup rgModes;

    int mMode=0;
    final int MODE_DRIVING=0;
    final int MODE_BICYCLING=1;
    final int MODE_WALKING=2;

//    AutoCompleteTextView atvPlaces;
//
//    DownloadTaskTwo placesDownloadTask;
//    DownloadTaskTwo placeDetailsDownloadTask;
//    ParserTaskTwo placesParserTask;
//    ParserTaskTwo placeDetailsParserTask;
//
//    final int PLACES=0;
//    final int PLACES_DETAILS=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
       // mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);

      //  String[] myDataset = {"one", "two", "three"} ;

        // specify an adapter (see also next example)
//        mAdapter = new MyAdapter(myDataset);
//        mRecyclerView.setAdapter(mAdapter);

//        MapFragment mapFragment = (MapFragment) getFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);


        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if(status!= ConnectionResult.SUCCESS){ // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        }else { // Google Play Services are available

            // Getting reference to the SupportMapFragment of activity_main.xml
            //SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            // Getting GoogleMap object from the fragment
            map = mapFragment.getMap();

            // Enabling MyLocation Layer of Google Map
            map.setMyLocationEnabled(true);

            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location
            Location location = locationManager.getLastKnownLocation(provider);

            if(location!=null){
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(provider, 20000000, 0, this);
        }


        // Getting reference to rb_driving
        rbDriving = (RadioButton) findViewById(R.id.rb_driving);

        // Getting reference to rb_bicylcing
        rbBiCycling = (RadioButton) findViewById(R.id.rb_bicycling);

        // Getting reference to rb_walking
        rbWalking = (RadioButton) findViewById(R.id.rb_walking);

        // Getting Reference to rg_modes
        rgModes = (RadioGroup) findViewById(R.id.rg_modes);

        rgModes.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // Checks, whether start and end locations are captured
                if(markerPoints.size() >= 2){
                    LatLng origin = markerPoints.get(0);
                    LatLng dest = markerPoints.get(1);

                    // Getting URL to the Google Directions API
                    String url = getDirectionsUrl(origin, dest);

                    DownloadTask downloadTask = new DownloadTask();

                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                }
            }
        });

        // Initializing
        markerPoints = new ArrayList<LatLng>();

//        // Getting reference to SupportMapFragment of the activity_main
//        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
//
//        // Getting Map for the SupportMapFragment
//        map = fm.getMap();
//
//        // Enable MyLocation Button in the Map
//        map.setMyLocationEnabled(true);

        // Setting onclick event listener for the map
        map.setOnMapClickListener(new OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                // Already two locations
                if (markerPoints.size() > 1) {
                    markerPoints.clear();
                    map.clear();
                }

                // Adding new item to the ArrayList
                markerPoints.add(point);

                // Draws Start and Stop markers on the Google Map
                drawStartStopMarkers();

                // Checks, whether start and end locations are captured
                if (markerPoints.size() >= 2) {
                    LatLng origin = markerPoints.get(0);
                    LatLng dest = markerPoints.get(1);

                    // Getting URL to the Google Directions API
                    String url = getDirectionsUrl(origin, dest);

                    DownloadTask downloadTask = new DownloadTask();

                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                }
            }
        });




//auto complete and marker----------------------------------------------//
//        // Getting a reference to the AutoCompleteTextView
//        atvPlaces = (AutoCompleteTextView) findViewById(R.id.atv_places);
//        atvPlaces.setThreshold(1);
//
//        // Adding textchange listener
//        atvPlaces.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // Creating a DownloadTask to download Google Places matching "s"
//                placesDownloadTask = new DownloadTaskTwo(PLACES);
//
//                // Getting url to the Google Places Autocomplete api
//                String url = getAutoCompleteUrl(s.toString());
//
//                // Start downloading Google Places
//                // This causes to execute doInBackground() of DownloadTask class
//                placesDownloadTask.execute(url);
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//            }
//        });
//
//        // Setting an item click listener for the AutoCompleteTextView dropdown list
//        atvPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int index,
//                                    long id) {
//
//                ListView lv = (ListView) arg0;
//                SimpleAdapter adapter = (SimpleAdapter) arg0.getAdapter();
//
//                HashMap<String, String> hm = (HashMap<String, String>) adapter.getItem(index);
//
//                // Creating a DownloadTask to download Places details of the selected place
//                placeDetailsDownloadTask = new DownloadTaskTwo(PLACES_DETAILS);
//
//                // Getting url to the Google Places details api
//                String url = getPlaceDetailsUrl(hm.get("reference"));
//
//                // Start downloading Google Place Details
//                // This causes to execute doInBackground() of DownloadTask class
//                placeDetailsDownloadTask.execute(url);
//
//            }
//        });

//auto complete and marker----------------------------------------------//




        final EditText homeAddress = (EditText)findViewById(R.id.home_add);
        Button saveButton = (Button)findViewById(R.id.home_add_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String location = homeAddress.getText().toString();
                Toast.makeText(getBaseContext(), location, Toast.LENGTH_SHORT).show();
                if(location==null || location.equals("")){
                    Toast.makeText(getBaseContext(), "No Place is entered", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = "https://maps.googleapis.com/maps/api/geocode/json?";

                try {
                    // encoding special characters like space in the user input place
                    location = URLEncoder.encode(location, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String address = "address=" + location;

                String sensor = "sensor=false";

                // url , from where the geocoding data is fetched
                url = url + address + "&" + sensor;

                // Instantiating DownloadTask to get places from Google Geocoding service
                // in a non-ui thread
                AddDownloadTask downloadTask = new AddDownloadTask();

                // Start downloading the geocoding places
                downloadTask.execute(url);
            }
        });


    }


    private String downloadUrlAdd(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }
    /** A class, to download Places from Geocoding webservice */
    private class AddDownloadTask extends AsyncTask<String, Integer, String>{

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrlAdd(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){

            // Instantiating ParserTask which parses the json data from Geocoding webservice
            // in a non-ui thread
            AddParserTask parserTask = new AddParserTask();

            // Start parsing the places in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            parserTask.execute(result);
        }
    }

    /** A class to parse the Geocoding Places in non-ui thread */
    class AddParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String,String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            GeocodeJSONParser parser = new GeocodeJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                /** Getting the parsed data as a an ArrayList */
                places = parser.parse(jObject);

            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String,String>> list){

            // Clears all the existing markers
            map.clear();

            for(int i=0;i<list.size();i++){

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Getting a place from the places list
                HashMap<String, String> hmPlace = list.get(i);

                // Getting latitude of the place
                double lat = Double.parseDouble(hmPlace.get("lat"));

                // Getting longitude of the place
                double lng = Double.parseDouble(hmPlace.get("lng"));

                // Getting name
                String name = hmPlace.get("formatted_address");

                LatLng latLng = new LatLng(lat, lng);

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker
                markerOptions.title(name);

                // Placing a marker on the touched position
                map.addMarker(markerOptions);

                // Locate the first location
                if(i==0)
                    map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    }




//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

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



//    @Override
//    public void onMapReady(final GoogleMap map) {
//
//        map.getUiSettings().setCompassEnabled(true);
//        map.getUiSettings().setZoomControlsEnabled(true);
//
//        // Setting a click event handler for the map
//        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//
//            @Override
//            public void onMapClick(LatLng latLng) {
//
//                // Creating a marker
//                MarkerOptions markerOptions = new MarkerOptions();
//
//                // Setting the position for the marker
//                markerOptions.position(latLng);
//
//                // Setting the title for the marker.
//                // This will be displayed on taping the marker
//                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//
////                // Clears the previously touched position
////                map.clear();
//
//                // Animating to the touched position
//                map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                // Placing a marker on the touched position
//                map.addMarker(markerOptions);
//            }
//        });
//
//
////        LatLng sydney = new LatLng(-33.867, 151.206);
//
//        map.setMyLocationEnabled(true);
//        map.getMyLocation();
//
//
//
//        /////----------------------------------Zooming camera to position user-----------------
//
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//
//        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
//
//
//        if (location != null) {
//            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                    new LatLng(location.getLatitude(), location.getLongitude()), 13));
//
//            CameraPosition cameraPosition = new CameraPosition.Builder()
//                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
//                    .zoom(17)                   // Sets the zoom
//                    .bearing(90)                // Sets the orientation of the camera to east
//                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
//                    .build();                   // Creates a CameraPosition from the builder
//            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//
//        }
//    }


    @Override
    public void onLocationChanged(Location location) {

        TextView tvLocation = (TextView) findViewById(R.id.tv_location);

        // Getting latitude of the current location
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Showing the current location in Google Map
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        map.animateCamera(CameraUpdateFactory.zoomTo(15));

        // Setting latitude and longitude in the TextView tv_location
        tvLocation.setText("Latitude:" +  latitude  + ", Longitude:"+ longitude );

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }



    // Drawing Start and Stop locations
    private void drawStartStopMarkers(){

        for(int i=0;i<markerPoints.size();i++){

            // Creating MarkerOptions
            MarkerOptions options = new MarkerOptions();

            // Setting the position of the marker
            options.position(markerPoints.get(i) );

            /**
             * For the start location, the color of marker is GREEN and
             * for the end location, the color of marker is RED.
             */
            if(i==0){
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            }else if(i==1){
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }

            // Add new marker to the Google Map Android API V2
            map.addMarker(options);
        }
    }
    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Travelling Mode
        String mode = "mode=driving";

        if(rbDriving.isChecked()){
            mode = "mode=driving";
            mMode = 0 ;
        }else if(rbBiCycling.isChecked()){
            mode = "mode=bicycling";
            mMode = 1;
        }else if(rbWalking.isChecked()){
            mode = "mode=walking";
            mMode = 2;
        }

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{



        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(2);

                // Changing the color polyline according to the mode
                if(mMode==MODE_DRIVING) {
                    lineOptions.color(Color.RED);
                    lineOptions.width(7);
                }
                else if(mMode==MODE_BICYCLING) {
                    lineOptions.color(Color.BLUE);
                    lineOptions.width(7);
                }
                else if(mMode==MODE_WALKING) {
                    lineOptions.color(Color.GREEN);
                    lineOptions.width(7);
                }
            }

            if(result.size()<1){
                Toast.makeText(getBaseContext(), "No Points", Toast.LENGTH_SHORT).show();
                return;
            }

            // Drawing polyline in the Google Map for the i-th route
            map.addPolyline(lineOptions);
        }
    }



    //code to get the auto complete text and marker-------------------------------------//

//    private String getAutoCompleteUrl(String place){
//
//        // Obtain browser key from https://code.google.com/apis/console
//        String key = "key=AIzaSyCK04MU5ZSAgxXFVwr9QVKh7z5u7PVUGvcY";
//
//        // place to be be searched
//        String input = "input="+place;
//
//        // place type to be searched
//        String types = "types=geocode";
//
//        // Sensor enabled
//        String sensor = "sensor=false";
//
//        // Building the parameters to the web service
//        String parameters = input+"&"+types+"&"+sensor+"&"+key;
//
//        // Output format
//        String output = "json";
//
//        // Building the url to the web service
//        String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"+output+"?"+parameters;
//
//        return url;
//    }
//
//    private String getPlaceDetailsUrl(String ref){
//
//        // Obtain browser key from https://code.google.com/apis/console
//        String key = "key=AIzaSyCK04MU5ZSAgxXFVwr9QVKh7z5u7PVUGvc";
//
//        // reference of place
//        String reference = "reference="+ref;
//
//        // Sensor enabled
//        String sensor = "sensor=false";
//
//        // Building the parameters to the web service
//        String parameters = reference+"&"+sensor+"&"+key;
//
//        // Output format
//        String output = "json";
//
//        // Building the url to the web service
//        String url = "https://maps.googleapis.com/maps/api/place/details/"+output+"?"+parameters;
//
//        return url;
//    }
//
//    /** A method to download json data from url */
//    private String downloadUrlTwo(String strUrl) throws IOException{
//        String data = "";
//        InputStream iStream = null;
//        HttpURLConnection urlConnection = null;
//        try{
//            URL url = new URL(strUrl);
//
//            // Creating an http connection to communicate with url
//            urlConnection = (HttpURLConnection) url.openConnection();
//
//            // Connecting to url
//            urlConnection.connect();
//
//            // Reading data from url
//            iStream = urlConnection.getInputStream();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
//
//            StringBuffer sb  = new StringBuffer();
//
//            String line = "";
//            while( ( line = br.readLine())  != null){
//                sb.append(line);
//            }
//
//            data = sb.toString();
//
//            br.close();
//
//        }catch(Exception e){
//            Log.d("Exception while downloading url", e.toString());
//        }finally{
//            iStream.close();
//            urlConnection.disconnect();
//        }
//        return data;
//    }
//
//    // Fetches data from url passed
//    private class DownloadTaskTwo extends AsyncTask<String, Void, String>{
//
//        private int downloadType=0;
//
//        // Constructor
//        public DownloadTaskTwo(int type){
//            this.downloadType = type;
//        }
//
//        @Override
//        protected String doInBackground(String... url) {
//
//            // For storing data from web service
//            String data = "";
//
//            try{
//                // Fetching the data from web service
//                data = downloadUrlTwo(url[0]);
//            }catch(Exception e){
//                Log.d("Background Task",e.toString());
//            }
//            return data;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            switch(downloadType){
//                case PLACES:
//                    // Creating ParserTask for parsing Google Places
//                    placesParserTask = new ParserTaskTwo(PLACES);
//
//                    // Start parsing google places json data
//                    // This causes to execute doInBackground() of ParserTask class
//                    placesParserTask.execute(result);
//
//                    break;
//
//                case PLACES_DETAILS :
//                    // Creating ParserTask for parsing Google Places
//                    placeDetailsParserTask = new ParserTaskTwo(PLACES_DETAILS);
//
//                    // Starting Parsing the JSON string
//                    // This causes to execute doInBackground() of ParserTask class
//                    placeDetailsParserTask.execute(result);
//            }
//        }
//    }
//
//    /** A class to parse the Google Places in JSON format */
//    private class ParserTaskTwo extends AsyncTask<String, Integer, List<HashMap<String,String>>>{
//
//        int parserType = 0;
//
//        public ParserTaskTwo(int type){
//            this.parserType = type;
//        }
//
//        @Override
//        protected List<HashMap<String, String>> doInBackground(String... jsonData) {
//
//            JSONObject jObject;
//            List<HashMap<String, String>> list = null;
//
//            try{
//                jObject = new JSONObject(jsonData[0]);
//
//                switch(parserType){
//                    case PLACES :
//                        PlaceJSONParser placeJsonParser = new PlaceJSONParser();
//                        // Getting the parsed data as a List construct
//                        list = placeJsonParser.parse(jObject);
//                        break;
//                    case PLACES_DETAILS :
//                        PlaceDetailsJSONParser placeDetailsJsonParser = new PlaceDetailsJSONParser();
//                        // Getting the parsed data as a List construct
//                        list = placeDetailsJsonParser.parse(jObject);
//                }
//
//            }catch(Exception e){
//                Log.d("Exception",e.toString());
//            }
//            return list;
//        }
//
//        @Override
//        protected void onPostExecute(List<HashMap<String, String>> result) {
//
//            switch(parserType){
//                case PLACES :
//                    String[] from = new String[] { "description"};
//                    int[] to = new int[] { android.R.id.text1 };
//
//                    // Creating a SimpleAdapter for the AutoCompleteTextView
//                    SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), result, android.R.layout.simple_list_item_1, from, to);
//
//                    // Setting the adapter
//                    atvPlaces.setAdapter(adapter);
//                    break;
//                case PLACES_DETAILS :
//                    HashMap<String, String> hm = result.get(0);
//
//                    // Getting latitude from the parsed data
//                    double latitude = Double.parseDouble(hm.get("lat"));
//
//                    // Getting longitude from the parsed data
//                    double longitude = Double.parseDouble(hm.get("lng"));
//
////                    // Getting reference to the SupportMapFragment of the activity_main.xml
////                    SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
////
////                    // Getting GoogleMap from SupportMapFragment
////                    googleMap = fm.getMap();
//
//                    MapFragment mapFragment = (MapFragment) getFragmentManager()
//                            .findFragmentById(R.id.map);
//                    mapFragment.getMapAsync(MainActivity.this);
//                    // Getting GoogleMap object from the fragment
//                    map = mapFragment.getMap();
//
//                    LatLng point = new LatLng(latitude, longitude);
//
//                    CameraUpdate cameraPosition = CameraUpdateFactory.newLatLng(point);
//                    CameraUpdate cameraZoom = CameraUpdateFactory.zoomBy(5);
//
//                    // Showing the user input location in the Google Map
//                    map.moveCamera(cameraPosition);
//                    map.animateCamera(cameraZoom);
//
//                    MarkerOptions options = new MarkerOptions();
//                    options.position(point);
//                    options.title("Position");
//                    options.snippet("Latitude:"+latitude+",Longitude:"+longitude);
//
//                    // Adding the marker in the Google Map
//                    map.addMarker(options);
//
//                    break;
//            }
//        }
//    }

    //end of code to get the auto complete text and marker-------------------------------------//


}

