package com.example.ievivas.sunshine2;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    final String TAG = this.getClass().getSimpleName();

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This allow fragment to handle menu events
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_refresh){
            Log.i(TAG, "Refresh selected");
            FetchWeatherTask fwt = new FetchWeatherTask();
            String cityID = "3871336";
            fwt.execute(cityID);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        String[] weekForecast = new String[7];
        weekForecast[0] = "Lunes - Soleado : 30/25 ºC";
        weekForecast[1] = "Martes - Soleado : 33/26 ºC";
        weekForecast[2] = "Miércoles - Nublado : 25/20 ºC";
        weekForecast[3] = "Jueves - Parcialmente nublado : 31/26 ºC";
        weekForecast[4] = "Viernes - Lluvia : 24/19 ºC";
        weekForecast[5] = "Sábado - Soleado : 30/25 ºC";
        weekForecast[6] = "Domingo - Soleado : 33/28 ºC";



        ArrayAdapter forecastAdapter = new ArrayAdapter<String>(
                // Context
                getActivity(),
                // ID of list_item_layout
                R.layout.list_item_forecast,
                // ID of text view to populate
                R.id.list_item_forecast_textview,
                // forecast data
                weekForecast
        );

        ListView listView = (ListView) rootView.findViewById(R.id.listView_forecast);
        listView.setAdapter(forecastAdapter);

        return rootView;
    }
}
