package com.develop.appprotov1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class A6 extends AppCompatActivity {
    Button btnBackA6, btnSiguienteA6;
    EditText res71;
    RadioGroup rGroup81;

    private String res81 = "";
    private String titulo = "A6";

    private ArrayList<String> arrayA5 = new ArrayList<>();
    private ArrayList<String> arrayA6 = new ArrayList<>();
    
    LocationManager locationManager;
    double longps, latgps;

    public static String longitud="-";
    public static String latitud="-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a6);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location();

        arrayA5 = (ArrayList<String>) getIntent().getSerializableExtra("arrayA5");




        btnBackA6 = findViewById(R.id.btnBackA6);
        btnSiguienteA6 = findViewById(R.id.btnSiguienteA6);

        res71 = findViewById(R.id.res71);
        rGroup81 = findViewById(R.id.rGroup81);

        btnBackA6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSiguienteA6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguiente();
            }
        });

        rGroup81.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioB = (RadioButton) rGroup81.findViewById(checkedId);
                res81 = radioB.getText()+"";
                System.out.println("-------------------------------------------");
                System.out.println("Se presionó un radio Buton "+res81);
            }
        });
    }

    private void siguiente(){
        arrayA6 = new ArrayList<>();
        arrayA6.addAll(arrayA5);
        arrayA6.add(titulo);
        //comprobar Array

        for (int i=0; i<arrayA6.size(); i++){
            System.out.println("---------------------------------------------------------------");
            System.out.println("respuesta: "+i+" : "+arrayA6.get(i));
        }
        String strinRes = res71.getText().toString();
        strinRes = validateString(strinRes);
        arrayA6.add(strinRes);

        res81 = validateString(res81);
        arrayA6.add(res81);

        Intent intent = new Intent(this, A7.class);
        intent.putExtra("arrayA6", arrayA6);
        intent.putExtra("lati", latitud);
        intent.putExtra("long", longitud);
        startActivity(intent);
    }
    private String validateString(String value){
        if(value.equals("")){
            value = "-";
        }
        return value;
    }
    private void location(){
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ){
            if(ActivityCompat.checkSelfPermission(A6.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(A6.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                System.out.println("-----------No hay permisos de gps ------------------------");
                ActivityCompat.requestPermissions(A6.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }else{
                System.out.println("--------------------GPS ON-----------");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2*20*1000, 10, locationListenerGPS);
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                criteria.setAltitudeRequired(false);
                criteria.setBearingRequired(false);
                criteria.setCostAllowed(true);
                criteria.setPowerRequirement(Criteria.POWER_LOW);

                locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria, true),  2*20*1000, 10, locationListenerGPS);

            }
        }
    }

    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            longps = location.getLongitude();
            latgps = location.getLatitude();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    longitud = String.valueOf(longps);
                    System.out.println("------------"+longitud+"----------------------");

                    latitud = ""+latgps;
                    System.out.println("------------"+latitud+"----------------------");
                }
            });
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(A6.this, "Permiso habilitado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(A6.this, "Permiso denegado, active el permiso para utilizar la aplicación", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}