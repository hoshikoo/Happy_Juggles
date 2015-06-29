package happy_juggles.c4q.nyc.happy_juggles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by c4q-joshelynvivas on 6/27/15.
 *
 * This is Joshelyn's Sports Card (parsing the JSON from the Women FIFA 2015)--http://worldcup.sfg.io/
 *
 */
public class JvSports {
    private static final String JSON_URL_ENDPOINT = "http://worldcup.sfg.io/teams/results";



    // Goal 1
    private static String getJsonString() {
     String result = "";
        // TODO : Step 1 - create an URL instance with Step 0's result
        try {
            URL url = new URL(JSON_URL_ENDPOINT);

            // TODO : Step 2 - create a Http Url Connection with Step 1's URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(0);
            connection.setReadTimeout(0);

            // TODO : Step 3 - get inputstream from Step 2 and create an instance of BufferedReader
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            // TODO : Step 4 - create a string builder instance
            StringBuilder stringBuilder = new StringBuilder();

            // TODO : Step 5 - read json file until the end of the line and write into string builder and save it into String variable "result"
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            result = stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Goal 2
    private static JSONObject getCountryAndWins() {
        String country = "";

        HashMap <String, Integer> countryAndWins = new HashMap<>();
        String jsonString = getJsonString();

        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray array = object.getJSONArray("0");

            JvSportsWins jvSportsWins = new JvSportsWins();

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                country = jsonObject.getString("country");
                int wins = jsonObject.getInt("wins");

                jvSportsWins.getCountry();
                jvSportsWins.getWins();
                return object;
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}