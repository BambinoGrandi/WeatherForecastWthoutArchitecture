package grandi.bambino.weatherforecast;

import grandi.bambino.weatherforecast.model.WeatherModel;

public interface WeatherParseListener {
    void updateWeather(WeatherModel model);
}
