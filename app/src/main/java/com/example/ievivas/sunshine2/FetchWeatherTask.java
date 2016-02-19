package com.example.ievivas.sunshine2;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ievivas on 19-02-16.
 */
public class FetchWeatherTask extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... params) {
        final String TAG = this.getClass().getSimpleName();
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        String apiKey = "58002e215dd2b326c9847ee6a1cdbf7d";

        //Santiago ID
        String cityID = "3871336";
        //Formato de respuesta en json:
        String format = "json";

        //Unidades metricas
        String units = "metric";

        //7 días de datos
        String daysOfData = "7";

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://api.openweathermap.org/data/2.5/forecast/city?id=3871336&APPID=58002e215dd2b326c9847ee6a1cdbf7d&units=metric&cnt=7&mode=json
            final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/city?";
            final String CITY_PARAM = "id";
            final String FORMAT_PARAM = "mode";
            final String UNITS_PARAM = "units";
            final String DAYS_PARAM = "cnt";
            final String APPID_PARAM = "APPID";


            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(CITY_PARAM, params[0])//Este es el parámetro que se pasa en execute
                    .appendQueryParameter(APPID_PARAM, apiKey)
                    .appendQueryParameter(UNITS_PARAM, units)
                    .appendQueryParameter(DAYS_PARAM, daysOfData)
                    .appendQueryParameter(FORMAT_PARAM, format)
                    .build();

            URL url = new URL(builtUri.toString());
            Log.v(TAG, "URL: " + url.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();


            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
            Log.v(TAG, "forecastJsonStr: " + forecastJsonStr);

        }
        catch (IOException e) {
            //Log.e("PlaceholderFragment", "Error ", e);
            Log.e(TAG, "PlaceholderFragment: " + e.getMessage());
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return null;
    }
}
