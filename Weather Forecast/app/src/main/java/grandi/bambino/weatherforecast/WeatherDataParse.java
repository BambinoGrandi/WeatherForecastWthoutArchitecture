package grandi.bambino.weatherforecast;

import android.app.Activity;

import android.os.Handler;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import grandi.bambino.weatherforecast.model.WeatherModel;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherDataParse implements Constants{
    private Set<WeatherParseListener> listeners;
    Handler handler = new Handler();
    private Timer timer = null;
    private static WeatherDataParse instance = null;
    private Retrofit retrofit;
    private WeatherData weatherAPI;

    public static WeatherDataParse getInstance(Activity activity){
        instance = instance == null?new WeatherDataParse(activity) : instance;
        return instance;
    }
    public void addListener(WeatherParseListener listener){
        if (!listeners.contains(listener)){
            listeners.add(listener);
        }
    }
    public void removeListener(WeatherParseListener listener){
        if (listeners.contains(listener)){
            listeners.remove(listener);
        }
    }

    public WeatherDataParse(Activity activity){
        listeners = new HashSet<>();
        retrofit = new Retrofit.Builder().baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create()).build();
        weatherAPI = retrofit.create(WeatherData.class);
        startTimer(activity);
    }

    interface WeatherData{
        @GET("data/2.5/weather")
        Call<WeatherModel> getWeather(@Query("q")String q, @Query("appid")String key);
    }

    private WeatherModel getWeather(String cityName) throws Exception {


        Call<WeatherModel> call = weatherAPI.getWeather(cityName + ",ru", API_KEY);

        Response<WeatherModel> response = call.execute();

        if (response.isSuccessful()) return response.body();
        else throw new Exception(response.errorBody().string(), null);
    }

    private void startTimer(Activity activity) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    WeatherModel weatherModel = getWeather(WeatherSetting.getInstance(activity).getCityName());

                    if (weatherModel == null) return;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            for (WeatherParseListener listener : listeners) {
                                listener.updateWeather(weatherModel);
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2000, 10000);
    }
    private void stopTimer () {
        if (timer != null) timer.cancel();
        listeners.clear();
    }
}
