package grandi.bambino.weatherforecast;

import android.app.Activity;

import java.util.Map;

public class MapImageCity {


    public Map<String, String> add(Activity activity, Map<String, String> installImageCity){
        installImageCity.put(activity.getResources().getString(R.string.city_name_spb), activity.getResources().getString(R.string.spb));
        installImageCity.put(activity.getResources().getString(R.string.city_name_msc), activity.getResources().getString(R.string.msk));
        installImageCity.put(activity.getResources().getString(R.string.city_name_ekb), activity.getResources().getString(R.string.ekb));
        installImageCity.put(activity.getResources().getString(R.string.city_name_rnd), activity.getResources().getString(R.string.rnd));

        return installImageCity;
    }
}

