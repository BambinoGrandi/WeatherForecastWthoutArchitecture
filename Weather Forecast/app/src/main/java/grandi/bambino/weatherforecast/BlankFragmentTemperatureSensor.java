package grandi.bambino.weatherforecast;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.content.Context.SENSOR_SERVICE;

public class BlankFragmentTemperatureSensor extends Fragment {
    private static final String TAG = "Sensor";

    private TextView temSensor;
    private TextView humiditySensor;
    private SensorManager sensorManager;
    private List<Sensor> sensors;
    private Sensor sensorTemp;
    private Sensor sensorHumd;
    private String valueTemp;
    private String valueHumd;

    public String getValueTemp() {
        return valueTemp;
    }

    public void setValueTemp(String valueTemp) {
        this.valueTemp = valueTemp;
    }

    public String getValueHumd() {
        return valueHumd;
    }

    public void setValueHumd(String valueHumd) {
        this.valueHumd = valueHumd;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();


        temSensor = getActivity().findViewById(R.id.temperatureSensor);
        humiditySensor = getActivity().findViewById(R.id.humiditySensor);


        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);

        sensorTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorHumd = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        sensorManager.registerListener(eventListenerTemp, sensorTemp, 100);
        sensorManager.registerListener(eventListenerHumd, sensorHumd, 100);
    }

    SensorEventListener eventListenerTemp = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            getDataSensor(event, temSensor, getValueTemp());
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    SensorEventListener eventListenerHumd = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            getDataSensor(event, humiditySensor, getValueHumd());
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void getDataSensor(SensorEvent event, TextView textView, String value) {
        value = (String.valueOf(event.values[0]));
        textView.setText(value);
    }


    //если приложение свернуто, отключаем датчики
    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(eventListenerTemp);
        sensorManager.unregisterListener(eventListenerHumd);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank_fragment_temperature_sensor, container, false);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
