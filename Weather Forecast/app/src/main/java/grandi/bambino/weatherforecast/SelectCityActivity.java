package grandi.bambino.weatherforecast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;


public class SelectCityActivity extends AppCompatActivity implements Constants{


    private static final String TAG = "SelectCityActivity";
    private TextInputEditText cityName;
    private CheckBox pressure;
    private CheckBox wind;

    RecyclerView mRecyclerViewCityName;
    EditText mCityNameEditText;

    Pattern pattern = Pattern.compile("[A-Z-a-z ]+$|[А-Я-а-я ]+$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        findViewById(R.id.back_select_city_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBack();
            }
        });


        pressure = findViewById(R.id.check_box_pressure);
        cityName = findViewById(R.id.city_name_text_edit);
        wind = findViewById(R.id.check_box_wind_speed);

        cityName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String error = getResources().getString(R.string.error_input_city_name);
                if (hasFocus) return;
                TextView textView = (TextView)v;
                if (pattern.matcher(textView.getText().toString()).matches()){
                    (textView).setError(null);
                }else (textView).setError(error);
            }
        });

        loadFromSetting();
//        selectCity();
        saveData();
    }

    @Override
    protected void onResume() {
        String[] cityName = getResources().getStringArray(R.array.city_items);

        mCityNameEditText = findViewById(R.id.city_name_text_edit);
        mRecyclerViewCityName = findViewById(R.id.recycler_view_city_name);
        mRecyclerViewCityName.setHasFixedSize(true);
        mRecyclerViewCityName.setLayoutManager(new LinearLayoutManager(SelectCityActivity.this, LinearLayoutManager.VERTICAL, false));

        mRecyclerViewCityName.setAdapter(new ChooseCityAdapter(cityName, new OnCityClickListener() {
            @Override
            public void onClick(String data) {
                Toast.makeText(SelectCityActivity.this, data, Toast.LENGTH_SHORT).show();
                mCityNameEditText.setText(data);
            }
        }));
        super.onResume();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    onBack();
                    return true;
                case R.id.setting_button:
                    return true;
                case R.id.author:
                    starAuthorActivity();
                    onBack();
                    return true;
            }
            return false;
        }
    };
    void starAuthorActivity(){
        startActivity(new Intent(this, InfoActivity.class));
    }


    void saveData(){
        WeatherSetting.getInstance(SelectCityActivity.this).setCityName(SelectCityActivity.this, cityName.getText().toString());
        WeatherSetting.getInstance(SelectCityActivity.this).setShowPressure(SelectCityActivity.this, pressure.isChecked());
        WeatherSetting.getInstance(SelectCityActivity.this).setShowWind(SelectCityActivity.this, wind.isChecked());
    }


    void loadFromSetting(){
        cityName.setText(WeatherSetting.getInstance(SelectCityActivity.this).getCityName());
        pressure.setChecked(WeatherSetting.getInstance(SelectCityActivity.this).isShowPressure());
        wind.setChecked(WeatherSetting.getInstance(SelectCityActivity.this).isShowWind());
    }

    void onBack(){
        saveData();
        Intent intent = new Intent();
        intent.putExtra(CITY_NAME, WeatherSetting.getInstance(SelectCityActivity.this).getCityName());
        setResult(REQUEST_CODE_SELECT_CITY, intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
