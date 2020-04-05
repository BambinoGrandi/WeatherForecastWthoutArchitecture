package grandi.bambino.weatherforecast;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements Constants{

    private static final String TAG = "MainActivity";
    private Map<String, String> installImageCity;
    MapImageCity mapImageCity = new MapImageCity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        installImageCity = new HashMap<>();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);


    }

    public void imageCity(Map<String, String> installImageCity, String cityName){

        if (installImageCity.get(cityName) != null) {
            Picasso.with(MainActivity.this)
                    .load(installImageCity.get(cityName))
                    .into((ImageView) findViewById(R.id.imageView));
        }else{
            Picasso.with(MainActivity.this)
                    .load(R.drawable.spb_image)
                    .into((ImageView) findViewById(R.id.imageView));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_setting){
            startSelectCity();
            return true;
        }
        if (id == R.id.menu_search){
            startSelectCity();
            return true;
        }
        if (id == R.id.menu_to_browser){
            Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://denvistorii.ru/"));
            startActivity(browser);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.setting_button:
                    startSelectCity();
                    return true;
                case R.id.author:
                    starAuthorActivity();
                    return true;
            }
            return false;
        }
    };
    void dataFromSelectCity(Intent data){
        ((TextView) findViewById(R.id.city_name)).setText(data.getStringExtra(CITY_NAME));

    }

    void starAuthorActivity(){
        startActivity(new Intent(this, InfoActivity.class));
    }

    void startSelectCity(){
        startActivityForResult(new Intent(this, SelectCityActivity.class), REQUEST_CODE_SELECT_CITY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE_SELECT_CITY) return;
        dataFromSelectCity(data);
    }

    @Override
    protected void onResume() {
        ((TextView) findViewById(R.id.city_name)).setText(WeatherSetting.getInstance(MainActivity.this).getCityName());
        imageCity(mapImageCity.add(this, installImageCity), WeatherSetting.getInstance(MainActivity.this).getCityName());
        super.onResume();
    }

}
