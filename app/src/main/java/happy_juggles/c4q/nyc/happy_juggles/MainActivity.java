package happy_juggles.c4q.nyc.happy_juggles;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private CurrentWeather mCurrentWeather;
    private DailyWeather mDailyWeather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//--------------------Location Variables ----------------------------------------------//

        String apiKey = "58adb87d9f294398e108116d51d0f030";
        //New York, NY
        double latitude = 40.71;
        double longitude = -74.01;
        String forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                "/" + latitude + "," + longitude;


//-------------Check if Network is available------------------------------//

        if(isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {

                    try {
                        String jsonData =response.body().string();
                        Log.d(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mCurrentWeather = getCurrentDetails(jsonData);
                           mDailyWeather = getDailyDetails(jsonData);// Current Weather
                            Log.v(TAG, jsonData);

                        }else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.d(TAG, "Exception caught", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught", e);
                    }

                }

            });
        }
        else {
            Toast.makeText(this,"Network is unavailable!",Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "Main UI code is running");

    }

    //---------------------------- JSON for Weather----------------------------------//

    private DailyWeather getDailyDetails(String jsonData) throws JSONException{
        JSONObject forecast2 = new JSONObject(jsonData);
        String timezone = forecast2.getString("timezone");
        Log.i(TAG,"From JSON: " + timezone);

        JSONObject daily = forecast2.getJSONObject("daily");

        DailyWeather dailyWeather = new DailyWeather();
        dailyWeather.setSummary(daily.getString("summary"));
        dailyWeather.setIcon(daily.getString("icon"));
        dailyWeather.setTimezone(timezone);

        Log.i(TAG,"From JSON: " + dailyWeather);
        return dailyWeather;
    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG,"From JSON: " + timezone);

        JSONObject currently = forecast.getJSONObject("currently");

        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setHumidity(currently.getDouble("humidity"));
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setIcon(currently.getString("icon"));
        currentWeather.setPrecipChance(currently.getDouble("precipProbability"));
        currentWeather.setTemperature(currently.getDouble("temperature"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setTimezone(timezone);

        Log.d(TAG, currentWeather.getFormattedTime());
        return  currentWeather;

    }

    //------------------------Network Method Check -----------------------//
    private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    //------------------------Alert the User  -----------------------//
    private void alertUserAboutError() {

        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(),"error_dialog");
    }

}
