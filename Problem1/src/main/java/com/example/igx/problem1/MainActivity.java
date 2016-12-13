package com.example.igx.problem1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, SensorEventListener {

    private SensorManager sm;
    private Sensor sensor_gravity, sensor_accelerometer,
            sensor_linear_acceleration, sensor_gyroscope;

    private GoogleMap map;

    static LatLng curPoint;

    static Double latitude = 0.0;
    static Double longitude = 0.0;

    static String gravityString = "";
    static String accelerometerString = "";
    static String linearString = "";
    static String gyroscopeString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_getLocation = (Button) findViewById(R.id.btn_getLocation);
        Button btn_getSensors = (Button) findViewById(R.id.btn_getSensors);
        Button btn_sendMessage = (Button) findViewById(R.id.btn_sendMessage);

        final TextView text_selectedData = (TextView) findViewById(R.id.text_selectedData);
        final TextView text_selectedType = (TextView) findViewById(R.id.text_selectedType);
        final EditText edit_phoneNumber = (EditText) findViewById(R.id.edit_phoneNumber);

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor_accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensor_gravity = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensor_linear_acceleration = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensor_gyroscope = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        btn_getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationService();
                text_selectedType.setText("LOCATION");
                text_selectedData.setText("위도 : " + latitude + ", " + "경도 : " + longitude);

            }
        });

        btn_getSensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_selectedType.setText("SENSORS");
                text_selectedData.setText(gravityString + "\n" + accelerometerString + "\n"
                                            + linearString + "\n" + gyroscopeString);

            }
        });

        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String myData = edit_phoneNumber.getText().toString();
                    Intent myActivity2 = new Intent(Intent.CATEGORY_APP_MESSAGING, Uri.parse(myData));
                    startActivity(myActivity2);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this, sensor_gravity, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, sensor_accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, sensor_linear_acceleration, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, sensor_gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng curPoint = new LatLng(latitude, longitude);

        map = googleMap;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch(sensorEvent.sensor.getType()) {
            case Sensor.TYPE_GRAVITY:
                gravityString = "Gravity : " + sensorEvent.values[0] + ", " + sensorEvent.values[1]
                        + ", " + sensorEvent.values[2];
                break;
            case Sensor.TYPE_ACCELEROMETER:
                accelerometerString = "Acclerometer : " + sensorEvent.values[0] + ", " + sensorEvent.values[1]
                        + ", " + sensorEvent.values[2];
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                linearString = "Linear : " + sensorEvent.values[0] + ", " + sensorEvent.values[1]
                        + ", " + sensorEvent.values[2];
                break;
            case Sensor.TYPE_GYROSCOPE:
                gyroscopeString = "GyroScope : " + sensorEvent.values[0] + ", " + sensorEvent.values[1]
                        + ", " + sensorEvent.values[2];
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        GPSListener gpsListener = new GPSListener();
        long minTime = 0;
        float minDistance = 0;

        try {
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);
        } catch(SecurityException ex) {
            ex.printStackTrace();
        }
    }

    private class GPSListener implements android.location.LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
            }
        }
    }
}




















