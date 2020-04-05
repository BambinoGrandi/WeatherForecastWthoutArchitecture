package grandi.bambino.weatherforecast;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import grandi.bambino.weatherforecast.model.WeatherModel;


public class BlankFragmentWeatherData extends Fragment implements WeatherParseListener{
        public BlankFragmentWeatherData() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank_fragment_weather_data, container, false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        WeatherDataParse.getInstance(getActivity()).addListener(this);
        getActivity().findViewById(R.id.pressure_data_select_day).setVisibility(WeatherSetting.getInstance(getActivity()).isShowPressure() ? View.VISIBLE : View.GONE);
        getActivity().findViewById(R.id.pressure_select_day).setVisibility(WeatherSetting.getInstance(getActivity()).isShowPressure() ? View.VISIBLE : View.GONE);
        getActivity().findViewById(R.id.wind_speed_data_select_day).setVisibility(WeatherSetting.getInstance(getActivity()).isShowWind() ? View.VISIBLE : View.GONE);
        getActivity().findViewById(R.id.wind_speed_select_day).setVisibility(WeatherSetting.getInstance(getActivity()).isShowWind() ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void updateWeather(WeatherModel model) {
        String temperatureDouble = Double.toString(model.getMain().getTemp() - 274.15);
        String temperatureResult = temperatureDouble.substring(0, temperatureDouble.indexOf("."));
        WeatherSetting.getInstance(getActivity()).setTemp(temperatureResult);
        String pressureMBar = Double.toString(model.getMain().getPressure() * 0.75);
        String pressureResult = pressureMBar.substring(0,pressureMBar.indexOf("."));

        ((TextView)getActivity().findViewById(R.id.wind_speed_data_select_day)).setText(Integer.toString(model.getWind().getSpeed()));
        ((TextView)getActivity().findViewById(R.id.pressure_data_select_day)).setText(pressureResult);
        ((TextView)getActivity().findViewById(R.id.temperature_data_select_day)).setText(temperatureResult);
        ((TextView)getActivity().findViewById(R.id.humidity_data_select_day)).setText(Integer.toString(model.getMain().getHumidity()));
    }

    @Override
    public void onPause() {
        WeatherDataParse.getInstance(getActivity()).removeListener(this);
        super.onPause();
    }

}
