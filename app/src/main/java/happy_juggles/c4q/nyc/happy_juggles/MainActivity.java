package happy_juggles.c4q.nyc.happy_juggles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dexafree.materialList.cards.BasicButtonsCard;
import com.dexafree.materialList.cards.BasicImageButtonsCard;
import com.dexafree.materialList.cards.BasicListCard;
import com.dexafree.materialList.cards.BigImageButtonsCard;
import com.dexafree.materialList.cards.BigImageCard;
import com.dexafree.materialList.cards.OnButtonPressListener;
import com.dexafree.materialList.cards.SimpleCard;
import com.dexafree.materialList.cards.WelcomeCard;
import com.dexafree.materialList.controller.OnDismissCallback;
import com.dexafree.materialList.controller.RecyclerItemClickListener;
import com.dexafree.materialList.model.Card;
import com.dexafree.materialList.model.CardItemView;
import com.dexafree.materialList.view.MaterialListView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
//implements OnMapReadyCallback, LocationListener
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
    Location location;
    String filename = "myfile";
    public static final String PREFS_NAME = "MyPrefsFile";

    private Context mContext;
    private MaterialListView mListView;

    String googleStaticMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Save a reference to the context
        mContext = this;

        // Bind the MaterialListView to a variable
        mListView = (MaterialListView) findViewById(R.id.material_listview);

        // Fill the array with mock content
        fillArray();

        // Set the dismiss listener
        mListView.setOnDismissCallback(new OnDismissCallback() {
            @Override
            public void onDismiss(Card card, int position) {

                // Recover the tag linked to the Card
                String tag = card.getTag().toString();

                // Show a toast
                Toast.makeText(mContext, "You have dismissed a " + tag, Toast.LENGTH_SHORT).show();
            }
        });

        // Add the ItemTouchListener
        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(CardItemView view, int position) {
                Log.d("CARD_TYPE", view.getTag().toString());
            }

            @Override
            public void onItemLongClick(CardItemView view, int position) {
                Log.d("LONG_CLICK", view.getTag().toString());
            }
        });


        //map--------------------

//        // Getting Google Play availability status
//        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
//
//        // Showing status
//        if(status!= ConnectionResult.SUCCESS){ // Google Play Services are not available
//
//            int requestCode = 10;
//            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
//            dialog.show();
//
//        }else { // Google Play Services are available
//
//            // Enabling MyLocation Layer of Google Map
//            map.setMyLocationEnabled(true);
//
//            // Getting LocationManager object from System Service LOCATION_SERVICE
//            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//            // Creating a criteria object to retrieve provider
//            Criteria criteria = new Criteria();
//
//            // Getting the name of the best provider
//            String provider = locationManager.getBestProvider(criteria, true);
//
//            // Getting Current Location
//            location = locationManager.getLastKnownLocation(provider);
//
//            if(location!=null){
//                MapActivity.onLocationChanged(location);
//            }
//            locationManager.requestLocationUpdates(provider, 2000, 0, (LocationListener) MainActivity.this);
//        }
//
//        double latitude = location.getLatitude();
//
//        // Getting longitude of the current location
//        double longitude = location.getLongitude();
//
//        // Creating a LatLng object for the current location
//        LatLng latLng = new LatLng(latitude, longitude);
//
//
//        LatLng origin = latLng;



//        http://maps.google.com/maps/api/staticmap?center=40.6900081,-73.9771259&zoom=15&size=300x300&sensor=false



        //map--------------------


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

//map----------------------------------------------------------------------------------------------------
//        // Getting Google Play availability status
//        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
//
//        // Showing status
//        if(status!= ConnectionResult.SUCCESS){ // Google Play Services are not available
//
//            int requestCode = 10;
//            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
//            dialog.show();
//
//        }else { // Google Play Services are available
//
//            // Getting reference to the SupportMapFragment of activity_main.xml
//            //SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//            MapFragment mapFragment = (MapFragment) getFragmentManager()
//                .findFragmentById(R.id.map);
//            mapFragment.getMapAsync(this);
//            // Getting GoogleMap object from the fragment
//            map = mapFragment.getMap();
//
//            // Enabling MyLocation Layer of Google Map
//            map.setMyLocationEnabled(true);
//
//            // Getting LocationManager object from System Service LOCATION_SERVICE
//            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//            // Creating a criteria object to retrieve provider
//            Criteria criteria = new Criteria();
//
//            // Getting the name of the best provider
//            String provider = locationManager.getBestProvider(criteria, true);
//
//            // Getting Current Location
//            location = locationManager.getLastKnownLocation(provider);
//
//            if(location!=null){
//                onLocationChanged(location);
//            }
//            locationManager.requestLocationUpdates(provider, 2000, 0, this);
//        }
//
//
//        // Getting reference to rb_driving
//        rbDriving = (RadioButton) findViewById(R.id.rb_driving);
//
//        // Getting reference to rb_bicylcing
//        rbBiCycling = (RadioButton) findViewById(R.id.rb_bicycling);
//
//        // Getting reference to rb_walking
//        rbWalking = (RadioButton) findViewById(R.id.rb_walking);
//
//        // Getting Reference to rg_modes
//        rgModes = (RadioGroup) findViewById(R.id.rg_modes);
//
//        rgModes.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                // Checks, whether start and end locations are captured
//                if(markerPoints.size() >= 2){
//                    LatLng origin = markerPoints.get(0);
//                    LatLng dest = markerPoints.get(1);
//
//                    // Getting URL to the Google Directions API
//                    String url = getDirectionsUrl(origin, dest);
//
//                    DownloadTask downloadTask = new DownloadTask();
//
//                    // Start downloading json data from Google Directions API
//                    downloadTask.execute(url);
//                }
//            }
//        });
//
//        // Initializing
//        markerPoints = new ArrayList<LatLng>();
//
////        // Getting reference to SupportMapFragment of the activity_main
////        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
////
////        // Getting Map for the SupportMapFragment
////        map = fm.getMap();
////
////        // Enable MyLocation Button in the Map
////        map.setMyLocationEnabled(true);
//
//        // Setting onclick event listener for the map
//        map.setOnMapClickListener(new OnMapClickListener() {
//
//            @Override
//            public void onMapClick(LatLng point) {
//
//                // Already two locations
//                if (markerPoints.size() > 1) {
//                    markerPoints.clear();
//                    map.clear();
//                }
//
//                // Adding new item to the ArrayList
//                markerPoints.add(point);
//
//                // Draws Start and Stop markers on the Google Map
//                drawStartStopMarkers();
//
//                // Checks, whether start and end locations are captured
//                if (markerPoints.size() >= 2) {
//                    LatLng origin = markerPoints.get(0);
//                    LatLng dest = markerPoints.get(1);
//
//                    // Getting URL to the Google Directions API
//                    String url = getDirectionsUrl(origin, dest);
//
//                    DownloadTask downloadTask = new DownloadTask();
//
//                    // Start downloading json data from Google Directions API
//                    downloadTask.execute(url);
//                }
//            }
//        });
//
//
//
//
//
//        final EditText homeAddress = (EditText)findViewById(R.id.home_add);
//        Button saveButton = (Button)findViewById(R.id.home_add_save);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String location = homeAddress.getText().toString();
//                Toast.makeText(getBaseContext(), location, Toast.LENGTH_SHORT).show();
//                if(location==null || location.equals("")){
//                    Toast.makeText(getBaseContext(), "No Place is entered", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                String url = "https://maps.googleapis.com/maps/api/geocode/json?";
//
//                try {
//                    // encoding special characters like space in the user input place
//                    location = URLEncoder.encode(location, "utf-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//
//                String address = "address=" + location;
//
//                String sensor = "sensor=false";
//
//                // url , from where the geocoding data is fetched
//                url = url + address + "&" + sensor;
//
//                // Instantiating DownloadTask to get places from Google Geocoding service
//                // in a non-ui thread
//                AddDownloadTask downloadTask = new AddDownloadTask();
//
//                // Start downloading the geocoding places
//                downloadTask.execute(url);
//            }
//        });
//
//
//
//        Button getDirection = (Button)findViewById(R.id.get_direction);
//        getDirection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Getting latitude of the current location
//                double latitude = location.getLatitude();
//
//                // Getting longitude of the current location
//                double longitude = location.getLongitude();
//
//                // Creating a LatLng object for the current location
//                LatLng latLng = new LatLng(latitude, longitude);
//
//
//                LatLng origin = latLng;
//
//
//                String result = null;
//                try {
////                    FileInputStream inputStream = openFileInput(filename);
////                    BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
////                    StringBuilder total = new StringBuilder();
////                    String line;
////                    while ((line = r.readLine()) != null) {
////                        total.append(line);
////                    }
////                    r.close();
////                    inputStream.close();
////                    //Log.d("File", "File contents: " + total);
////                    result = total.toString();
//
//
//                    SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//                    // Writing data to SharedPreferences
//                    SharedPreferences.Editor editor = preferences.edit();
//                    result = preferences.getString("LatLngStr", "");
//
//                    Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();
//                    //return;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                String[] latandlng =  result.split(",");
//                double deslatitude = Double.parseDouble(latandlng[0]);
//                double deslongitude = Double.parseDouble(latandlng[1]);
//
//                LatLng dest = new LatLng(deslatitude, deslongitude);
//
//
//
//                // Getting URL to the Google Directions API
//                String url = getDirectionsUrl(origin, dest);
//
//                DownloadTask downloadTask = new DownloadTask();
//
//                // Start downloading json data from Google Directions API
//                downloadTask.execute(url);
//
//            }
//        });

        //map----------------------------------------------------------------------------------------------------


    }

    //map----------------------------------------------------------------------------------------------------

//    private String downloadUrlAdd(String strUrl) throws IOException{
//        String data = "";
//        InputStream iStream = null;
//        HttpURLConnection urlConnection = null;
//        try{
//            URL url = new URL(strUrl);
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
//            StringBuffer sb = new StringBuffer();
//
//            String line = "";
//            while( ( line = br.readLine()) != null){
//                sb.append(line);
//            }
//
//            data = sb.toString();
//            br.close();
//
//        }catch(Exception e){
//            Log.d("Exception while downloading url", e.toString());
//        }finally{
//            iStream.close();
//            urlConnection.disconnect();
//        }
//
//        return data;
//    }
//    /** A class, to download Places from Geocoding webservice */
//    private class AddDownloadTask extends AsyncTask<String, Integer, String>{
//
//        String data = null;
//
//        // Invoked by execute() method of this object
//        @Override
//        protected String doInBackground(String... url) {
//            try{
//                data = downloadUrlAdd(url[0]);
//            }catch(Exception e){
//                Log.d("Background Task",e.toString());
//            }
//            return data;
//        }
//
//        // Executed after the complete execution of doInBackground() method
//        @Override
//        protected void onPostExecute(String result){
//
//            // Instantiating ParserTask which parses the json data from Geocoding webservice
//            // in a non-ui thread
//            AddParserTask parserTask = new AddParserTask();
//
//            // Start parsing the places in JSON format
//            // Invokes the "doInBackground()" method of the class ParseTask
//            parserTask.execute(result);
//        }
//    }
//
//    /** A class to parse the Geocoding Places in non-ui thread */
//    class AddParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{
//
//        JSONObject jObject;
//
//        // Invoked by execute() method of this object
//        @Override
//        protected List<HashMap<String,String>> doInBackground(String... jsonData) {
//
//            List<HashMap<String, String>> places = null;
//            GeocodeJSONParser parser = new GeocodeJSONParser();
//
//            try{
//                jObject = new JSONObject(jsonData[0]);
//
//                /** Getting the parsed data as a an ArrayList */
//                places = parser.parse(jObject);
//
//            }catch(Exception e){
//                Log.d("Exception",e.toString());
//            }
//            return places;
//        }
//
//        // Executed after the complete execution of doInBackground() method
//        @Override
//        protected void onPostExecute(List<HashMap<String,String>> list){
//
//            // Clears all the existing markers
//            map.clear();
//
//            for(int i=0;i<list.size();i++){
//
//                // Creating a marker
//                MarkerOptions markerOptions = new MarkerOptions();
//
//                // Getting a place from the places list
//                HashMap<String, String> hmPlace = list.get(i);
//
//                // Getting latitude of the place
//                double lat = Double.parseDouble(hmPlace.get("lat"));
//
//                // Getting longitude of the place
//                double lng = Double.parseDouble(hmPlace.get("lng"));
//
//                // Getting name
//                String name = hmPlace.get("formatted_address");
//
//                LatLng latLng = new LatLng(lat, lng);
//
//
//                //saving to internal storage
//                String savedLat = String.valueOf(lat) +"," + String.valueOf(lng);
//
//
//                try {
////                    FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
////                    outputStream.write(savedLat.getBytes());
//////                    outputStream.write(savedLng.getBytes());
////                    outputStream.close();
//
//
//                    SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("LatLngStr",savedLat);
//                    editor.commit();
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//                // Setting the position for the marker
//                markerOptions.position(latLng);
//
//                // Setting the title for the marker
//                markerOptions.title(name);
//
//                // Placing a marker on the touched position
//                map.addMarker(markerOptions);
//
//                // Locate the first location
//                if(i == 0)
//                    map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//            }
//        }
//    }
//
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.menu_main, menu);
////        return true;
////    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//
//
//
//    @Override
//    public void onLocationChanged(Location location) {
//
//        TextView tvLocation = (TextView) findViewById(R.id.tv_location);
//
//        // Getting latitude of the current location
//        double latitude = location.getLatitude();
//
//        // Getting longitude of the current location
//        double longitude = location.getLongitude();
//
//        // Creating a LatLng object for the current location
//        LatLng latLng = new LatLng(latitude, longitude);
//
//        // Showing the current location in Google Map
//        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//
//        // Zoom in the Google Map
//        map.animateCamera(CameraUpdateFactory.zoomTo(15));
//
//        // Setting latitude and longitude in the TextView tv_location
//        tvLocation.setText("Latitude:" +  latitude  + ", Longitude:"+ longitude );
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//        // TODO Auto-generated method stub
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//        // TODO Auto-generated method stub
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//        // TODO Auto-generated method stub
//    }
//
//
//
//    // Drawing Start and Stop locations
//    private void drawStartStopMarkers(){
//
//        for(int i=0;i<markerPoints.size();i++){
//
//            // Creating MarkerOptions
//            MarkerOptions options = new MarkerOptions();
//
//            // Setting the position of the marker
//            options.position(markerPoints.get(i) );
//
//            /**
//             * For the start location, the color of marker is GREEN and
//             * for the end location, the color of marker is RED.
//             */
//            if(i==0){
//                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//            }else if(i==1){
//                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//            }
//
//            // Add new marker to the Google Map Android API V2
//            map.addMarker(options);
//        }
//    }
//    private String getDirectionsUrl(LatLng origin,LatLng dest){
//
//        // Origin of route
//        String str_origin = "origin="+origin.latitude+","+origin.longitude;
//
//        // Destination of route
//        String str_dest = "destination="+dest.latitude+","+dest.longitude;
//
//        // Sensor enabled
//        String sensor = "sensor=false";
//
//        // Travelling Mode
//        String mode = "mode=driving";
//
//        if(rbDriving.isChecked()){
//            mode = "mode=driving";
//            mMode = 0 ;
//        }else if(rbBiCycling.isChecked()){
//            mode = "mode=bicycling";
//            mMode = 1;
//        }else if(rbWalking.isChecked()){
//            mode = "mode=walking";
//            mMode = 2;
//        }
//
//        // Building the parameters to the web service
//        String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+mode;
//
//        // Output format
//        String output = "json";
//
//        // Building the url to the web service
//        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
//
//        return url;
//    }
//
//    /** A method to download json data from url */
//    private String downloadUrl(String strUrl) throws IOException {
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
//            StringBuffer sb = new StringBuffer();
//
//            String line = "";
//            while( ( line = br.readLine()) != null){
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
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        map.getUiSettings().setCompassEnabled(true);
//        map.getUiSettings().setZoomControlsEnabled(true);
//    }
//
//    // Fetches data from url passed
//    private class DownloadTask extends AsyncTask<String, Void, String> {
//
//        // Downloading data in non-ui thread
//        @Override
//        protected String doInBackground(String... url) {
//
//            // For storing data from web service
//            String data = "";
//
//            try{
//                // Fetching the data from web service
//                data = downloadUrl(url[0]);
//            }catch(Exception e){
//                Log.d("Background Task",e.toString());
//            }
//            return data;
//        }
//
//        // Executes in UI thread, after the execution of
//        // doInBackground()
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            ParserTask parserTask = new ParserTask();
//
//            // Invokes the thread for parsing the JSON data
//            parserTask.execute(result);
//        }
//    }
//
//    /** A class to parse the Google Places in JSON format */
//    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
//
//        // Parsing the data in non-ui thread
//        @Override
//        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
//
//            JSONObject jObject;
//            List<List<HashMap<String, String>>> routes = null;
//
//            try{
//                jObject = new JSONObject(jsonData[0]);
//                DirectionsJSONParser parser = new DirectionsJSONParser();
//
//                // Starts parsing data
//                routes = parser.parse(jObject);
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//            return routes;
//        }
//
//        // Executes in UI thread, after the parsing process
//        @Override
//        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
//            ArrayList<LatLng> points = null;
//            PolylineOptions lineOptions = null;
//            MarkerOptions markerOptions = new MarkerOptions();
//
//            // Traversing through all the routes
//            for(int i=0;i<result.size();i++){
//                points = new ArrayList<LatLng>();
//                lineOptions = new PolylineOptions();
//
//                // Fetching i-th route
//                List<HashMap<String, String>> path = result.get(i);
//
//                // Fetching all the points in i-th route
//                for(int j=0;j<path.size();j++){
//                    HashMap<String,String> point = path.get(j);
//
//                    double lat = Double.parseDouble(point.get("lat"));
//                    double lng = Double.parseDouble(point.get("lng"));
//                    LatLng position = new LatLng(lat, lng);
//
//                    points.add(position);
//                }
//
//                // Adding all the points in the route to LineOptions
//                lineOptions.addAll(points);
//
//
//                // Changing the color polyline according to the mode
//                if(mMode==MODE_DRIVING) {
//                    lineOptions.color(Color.RED);
//                    lineOptions.width(7);
//                }
//                else if(mMode==MODE_BICYCLING) {
//                    lineOptions.color(Color.BLUE);
//                    lineOptions.width(7);
//                }
//                else if(mMode==MODE_WALKING) {
//                    lineOptions.color(Color.GREEN);
//                    lineOptions.width(7);
//                }
//            }
//
//            if(result.size()<1){
//                Toast.makeText(getBaseContext(), "No Points", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            // Drawing polyline in the Google Map for the i-th route
//            map.addPolyline(lineOptions);
//        }
//    }
//


//    public static Bitmap getGoogleMapThumbnail(String result){
//        String URL = "http://maps.google.com/maps/api/staticmap?center="+result+"&zoom=17&size=1800x500&sensor=&maptype=roadmap" +
//                "&markers=color:blue%7Clabel:S%7C"+result;
//        Bitmap bmp = null;
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpGet request = new HttpGet(URL);
//
//        InputStream in = null;
//        try {
//            in = httpclient.execute(request).getEntity().getContent();
//            bmp = BitmapFactory.decodeStream(in);
//            in.close();
//        } catch (IllegalStateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return bmp;
//    }
//
//    private void SaveImage(Bitmap finalBitmap) {
//
//        String root = Environment.getExternalStorageDirectory().toString();
//        File myDir = new File(root + "/map_images");
//        myDir.mkdirs();
//        String fname = "map.jpg";
//        File file = new File (myDir, fname);
//        if (file.exists ()) file.delete ();
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//            out.flush();
//            out.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    //map----------------------------------------------------------------------------------------------------


    //cards--------------------------------------------------------------------------

    private void fillArray() {
        for (int i = 0; i < 35; i++) {
            Card card = getRandomCard(i);
            mListView.add(card);
        }
    }

    private Card getRandomCard(final int position) {
        String title = "Card number " + (position + 1);
        String description = "Lorem ipsum dolor sit amet";

        int type = position % 6;

        SimpleCard card;
        Drawable icon;

        switch (type) {

            case 0:

                card = new WelcomeCard(this);
                card.setTitle("Welcome to Happy Juggles");
                card.setDescription("Swipe through the cards!");
                card.setTag("WELCOME_CARD");
                ((WelcomeCard) card).setSubtitle("created by H(oshiko) and 2Js(Janneisy & Joshelyn");
                ((WelcomeCard) card).setButtonText("Okay!");
                ((WelcomeCard) card).setOnButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {
                        Toast.makeText(mContext, "Welcome!", Toast.LENGTH_SHORT).show();
                        mListView.remove(card);

                    }
                });

                if (position % 2 == 0)
                    ((WelcomeCard) card).setBackgroundColorRes(R.color.background_material_dark);
                card.setDismissible(true);
                return card;



        case 1:
                card = new BigImageCard(this);
                card.setDescription(description);
                card.setTitle("Map Card");
                String result = null;
                try {

                    SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    // Writing data to SharedPreferences
                    SharedPreferences.Editor editor = preferences.edit();
                    result = preferences.getString("LatLngStr", "");


                } catch (Exception e) {
                    e.printStackTrace();
                }

    //
    //                String[] latandlng =  result.split(",");
    //                double latitude = Double.parseDouble(latandlng[0]);
    //                double longitude = Double.parseDouble(latandlng[1]);


                googleStaticMap = "http://maps.google.com/maps/api/staticmap?center="+result+"&zoom=17&size=1800x500&sensor=&maptype=roadmap" +
                        "&markers=color:blue%7Clabel:S%7C"+result;
                card.setDrawable(googleStaticMap);
                card.setTag("BIG_IMAGE_CARD");
                return card;

            case 2:
                BigImageButtonsCard bigcard = new BigImageButtonsCard(this);
                bigcard.setDescription(description);
                bigcard.setTitle("Direction Card");
//                String photoPath = Environment.getExternalStorageDirectory()+"/map_images/map.jpg";
//                Bitmap bitmap1 = BitmapFactory.decodeFile(photoPath);
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
//                card.setDrawable("http://maps.google.com/maps/api/staticmap?center=40.6900081,-73.9771259&zoom=15&size=300x300&sensor=false");

//                bigcard.setBitmap(bitmap1);
                bigcard.setTag("BASIC_IMAGE_BUTTON_CARD");
                bigcard.setLeftButtonText("Check directions");
                bigcard.setRightButtonText("Set Locations");

                if (position % 2 == 0)
                    bigcard.setDividerVisible(true);

                bigcard.setOnLeftButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {
//                        Toast.makeText(mContext, "You have pressed the left button", Toast.LENGTH_SHORT).show();
//                        ((SimpleCard) card).setTitle("CHANGED ON RUNTIME");
                        Intent mapIntent = new Intent(MainActivity.this, MapActivity.class);
                        MainActivity.this.startActivity(mapIntent);
                    }
                });

                bigcard.setOnRightButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {
                        Toast.makeText(mContext, "You have pressed the right button on card " + ((SimpleCard) card).getTitle(), Toast.LENGTH_SHORT).show();
                        mListView.remove(card);
                    }
                });
                bigcard.setDismissible(true);

                return bigcard;

            case 3:
                card = new BasicButtonsCard(this);
                card.setDescription(description);
                card.setTitle(title);
                card.setTag("BASIC_BUTTONS_CARD");
                ((BasicButtonsCard) card).setLeftButtonText("LEFT");
                ((BasicButtonsCard) card).setRightButtonText("RIGHT");
                ((BasicButtonsCard) card).setRightButtonTextColorRes(R.color.accent_material_dark);

                if (position % 2 == 0)
                    ((BasicButtonsCard) card).setDividerVisible(true);

                ((BasicButtonsCard) card).setOnLeftButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {
                        Toast.makeText(mContext, "You have pressed the left button", Toast.LENGTH_SHORT).show();
                    }
                });

                ((BasicButtonsCard) card).setOnRightButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {
                        Toast.makeText(mContext, "You have pressed the right button", Toast.LENGTH_SHORT).show();
                    }
                });
                card.setDismissible(true);


                return card;

            case 4:
                card = new WelcomeCard(this);
                card.setTitle("Welcome Card");
                card.setDescription("I am the description");
                card.setTag("WELCOME_CARD");
                ((WelcomeCard) card).setSubtitle("My subtitle!");
                ((WelcomeCard) card).setButtonText("Okay!");
                ((WelcomeCard) card).setOnButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {
                        Toast.makeText(mContext, "Welcome!", Toast.LENGTH_SHORT).show();
                    }
                });

                if (position % 2 == 0)
                    ((WelcomeCard) card).setBackgroundColorRes(R.color.background_material_dark);
                card.setDismissible(true);

                return card;

            case 5:
                card = new BasicListCard(this);
                card.setTitle("ToDo List Card");
                card.setDescription("Please click on the card to edit the list!");
                BasicListAdapter adapter = new BasicListAdapter(this);
                adapter.add("Text1");
                adapter.add("Text2");
                adapter.add("Text3");
                card.setTag("LIST_CARD");

                ((BasicListCard) card).setAdapter(adapter);

                ((BasicListCard) card).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(mContext, "Let's edit the card!", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                        // set title
                        alertDialogBuilder.setTitle("Edit the todo list");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Click yes to exit!")
                                .setCancelable(false)
                                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        MainActivity.this.finish();
                                    }
                                })
                                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    }
                });


                card.setDismissible(true);

                return card;

            case 6:

            card = new BasicButtonsCard(this);
            card.setDescription(description);
            card.setTitle("Sports ");
            card.setTag("BASIC_BUTTONS_CARD");
            ((BasicButtonsCard) card).setLeftButtonText("LEFT");
            ((BasicButtonsCard) card).setRightButtonText("RIGHT");
            ((BasicButtonsCard) card).setRightButtonTextColorRes(R.color.accent_material_dark);

            if (position % 2 == 0)
                ((BasicButtonsCard) card).setDividerVisible(true);

            ((BasicButtonsCard) card).setOnLeftButtonPressedListener(new OnButtonPressListener() {
                @Override
                public void onButtonPressedListener(View view, Card card) {
                    Toast.makeText(mContext, "You have pressed the left button", Toast.LENGTH_SHORT).show();
                }
            });

            ((BasicButtonsCard) card).setOnRightButtonPressedListener(new OnButtonPressListener() {
                @Override
                public void onButtonPressedListener(View view, Card card) {
                    Toast.makeText(mContext, "You have pressed the right button", Toast.LENGTH_SHORT).show();
                }
            });
            card.setDismissible(true);


            return card;

            default:
                card = new BigImageButtonsCard(this);
                card.setDescription(description);
                card.setTitle(title);
                card.setDrawable(R.drawable.photo);
                card.setTag("BIG_IMAGE_BUTTONS_CARD");
                ((BigImageButtonsCard) card).setLeftButtonText("ADD CARD");
                ((BigImageButtonsCard) card).setRightButtonText("RIGHT BUTTON");

                if (position % 2 == 0) {
                    ((BigImageButtonsCard) card).setDividerVisible(true);
                }

                ((BigImageButtonsCard) card).setOnLeftButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {
                        Log.d("ADDING", "CARD");

                        mListView.add(generateNewCard());
                        Toast.makeText(mContext, "Added new card", Toast.LENGTH_SHORT).show();
                    }
                });

                ((BigImageButtonsCard) card).setOnRightButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {
                        Toast.makeText(mContext, "You have pressed the right button", Toast.LENGTH_SHORT).show();
                    }
                });
                card.setDismissible(true);


                return card;

        }

    }

    private Card generateNewCard() {
        SimpleCard card = new BasicImageButtonsCard(this);
        card.setDrawable(R.drawable.dog);
        card.setTitle("I'm new");
        card.setDescription("I've been generated on runtime!");
        card.setTag("BASIC_IMAGE_BUTTONS_CARD");

        return card;
    }

    private void addMockCardAtStart(){
        BasicImageButtonsCard card = new BasicImageButtonsCard(this);
        card.setDrawable(R.drawable.dog);
        card.setTitle("Hi there");
        card.setDescription("I've been added on top!");
        card.setLeftButtonText("LEFT");
        card.setRightButtonText("RIGHT");
        card.setTag("BASIC_IMAGE_BUTTONS_CARD");

        card.setDismissible(true);

        mListView.addAtStart(card);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_clear:
                mListView.clear();
                break;
            case R.id.action_add_at_start:
                addMockCardAtStart();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    //cards--------------------------------------------------------------------------






}

