package grandi.bambino.weatherforecast;

import android.app.Activity;
import android.content.Context;
import android.os.Build;


public class WeatherSetting implements Constants {
    private long id;
    private String cityName;
    private String temp;
    private boolean showWind;
    private boolean showPressure;

    static WeatherSetting instance;


    public WeatherSetting(Activity activity) {
        cityName = activity.getSharedPreferences(PREFERENCE_APP, Context.MODE_PRIVATE).getString(PREFERENCE_CITY_NAME, "Санкт-Петербург");
        showPressure = activity.getSharedPreferences(PREFERENCE_APP, Context.MODE_PRIVATE).getBoolean(PREFERENCE_PRESSURE, true);
        showWind = activity.getSharedPreferences(PREFERENCE_APP, Context.MODE_PRIVATE).getBoolean(PREFERENCE_WIND, true);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public static WeatherSetting getInstance(Activity activity) {
        instance = instance == null ? new WeatherSetting(activity) : instance;
        return instance;
    }

    public synchronized String getCityName() {
        return cityName;
    }

    public synchronized void setCityName(Activity activity, String cityName) {
        this.cityName = cityName;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            activity.getSharedPreferences(PREFERENCE_APP, Context.MODE_PRIVATE).edit().putString(PREFERENCE_CITY_NAME, getCityName()).apply();
        }
    }

    public boolean isShowWind() {
        return showWind;
    }

    public void setShowWind(Activity activity, boolean showWind) {
        this.showWind = showWind;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            activity.getSharedPreferences(PREFERENCE_APP, Context.MODE_PRIVATE).edit().putBoolean(PREFERENCE_WIND, isShowWind()).apply();
        }
    }

    public boolean isShowPressure() {
        return showPressure;
    }

    public void setShowPressure(Activity activity, boolean showPressure) {
        this.showPressure = showPressure;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            activity.getSharedPreferences(PREFERENCE_APP, Context.MODE_PRIVATE).edit().putBoolean(PREFERENCE_PRESSURE, isShowPressure()).apply();
        }
    }
}