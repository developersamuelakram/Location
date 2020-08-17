package com.example.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView cityname, countryname, longtitude, lattitude;
    Button getLocation;
    FusedLocationProviderClient fusedLocation;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    String latitude;
    String longit;

    String APILINK = "http://api.openweathermap.org/data/2.5/weather?";
    // I want to add the values of longitude and lattitude in this link above...

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityname = findViewById(R.id.cityname);
        countryname = findViewById(R.id.countryname);
        longtitude = findViewById(R.id.longtitude);
        lattitude = findViewById(R.id.lattitude);
        getLocation = findViewById(R.id.getLocation);





        fusedLocation = LocationServices.getFusedLocationProviderClient(this);

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newurl();

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
                    wantLocation();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }


            }
        });

    }

        private void wantLocation() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        fusedLocation.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Location location = task.getResult();

                if (location != null) {

                    try {


                        Geocoder geocoder = new Geocoder(
                                MainActivity.this, Locale.getDefault());

                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        cityname.setText(Html.fromHtml("<font color='#6200EE'><b>City Name:</b><br></font>" + addresses.get(0).getAdminArea()));
                        longtitude.setText(Html.fromHtml("<font color='#6200EE'><b>Longtitude:</b><br></font>" + addresses.get(0).getLongitude()));
                        lattitude.setText(Html.fromHtml("<font color='#6200EE'><b>Lattitude:</b><br></font>" + addresses.get(0).getLatitude()));
                        countryname.setText(Html.fromHtml("<font color='#6200EE'><b>Country Name:</b><br></font>" + addresses.get(0).getCountryName()));





                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }


        });

    }



    private void newurl() {


        longit = longtitude.getText().toString();
        latitude = lattitude.getText().toString();


        String url = APILINK + String.valueOf(longit);
        Toast.makeText(this, String.valueOf(url), Toast.LENGTH_SHORT).show();
    }

}