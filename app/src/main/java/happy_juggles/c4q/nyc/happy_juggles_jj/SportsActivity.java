package happy_juggles.c4q.nyc.happy_juggles_jj;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by c4q-joshelynvivas on 7/2/15.
 */
public class SportsActivity extends ListActivity {

    private ProgressDialog pDialog;

    private static String url = "http://worldcup.sfg.io/teams/results/";

    //JSON node names
    private static final String TAG_RESULTS = "results";
    private static final String TAG_COUNTRY = "country";
    private static final String TAG_WINS = "wins";

    //contact JSON Array
    JSONArray results = null;

    //Hashmap for ListView

    ArrayList<HashMap<String, String>> resultList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sports_activity_main);

        resultList = new ArrayList<HashMap<String, String>>();

        ListView resultView = getListView();

        // Calling async task to get json
        new GetResults().execute();

    }

    private class GetResults extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(SportsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            //Creating service handler class instance

            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONArray results = new JSONArray(jsonStr);

                    // Getting JSON Array node
                    // results = jsonObj.getJSONArray(TAG_RESULTS);

                    // looping through All Contacts
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject scores = results.getJSONObject(i);

                        String country = scores.getString(TAG_COUNTRY);
                        String wins = scores.getString(TAG_WINS);


                        // tmp hashmap for single contact
                        HashMap<String, String> resultScoresandCountry = new HashMap<String, String>();
                        // adding each child node to HashMap key => value
                        resultScoresandCountry.put(TAG_COUNTRY, country);
                        resultScoresandCountry.put(TAG_WINS, wins);

                        // adding contact to results list
                        resultList.add(resultScoresandCountry);
                    }
                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    SportsActivity.this, resultList,
                    R.layout.sports_list_row, new String[]{TAG_COUNTRY, TAG_WINS,
            }, new int[]{R.id.country,
                    R.id.wins});

            setListAdapter(adapter);
        }

    }

}
