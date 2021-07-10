package com.develop.appprotov1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Formularios_Code extends AppCompatActivity implements View.OnClickListener {
    static int arrayCont = 10;
    static int auxiliar;
    public static String ruta = "";
    File file;
    String result = "";
    public static ArrayList<String> arrayImagenes = new ArrayList<>();
    public static ArrayList<TextView> arrayTViews = new ArrayList<TextView>();
    ArrayList<String> arrayRespuestas = new ArrayList<>();
    public static RadioGroup[] arrRButtons = new RadioGroup[arrayCont];
    LocationManager locationManager;
    double longps, latgps;

    public static String longitud="";
    public static String latitud="";
    Button btnguardar;
    EditText editText;
    static String direccion = Environment.getExternalStorageDirectory() + "";

    private LinearLayout layout;


    public static String[] arrayPreguntas = {
            "Pregunta 1",
            "Pregunta 2",
            "Pregunta 3",
            "Pregunta 4",
            "Pregunta 5",
            "Pregunta 6",
            "Pregunta 7",
            "Pregunta 8",
            "Pregunta 9",
            "Pregunta 10"
    };
    public static String[] arrayRespuestas1 = {
            "Si",
            "No"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formularios__code);

        arrayTViews = new ArrayList<>();
        arrayRespuestas = new ArrayList<>();

        layout = findViewById(R.id.layoutContenedor);

        btnguardar = findViewById(R.id.btnGuardar);
        editText = findViewById(R.id.editObservaciones);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        initComponents();

        location();

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();

            }
        });
    }


    public void initComponents() {


        for (int i = 0, c = 2; i < arrayCont; i++) {

            TextView textView = new TextView(Formularios_Code.this);
            arrayTViews.add(textView);

            createTextView(arrayTViews.get(i), layout, i);
            createRespuesta(layout, i, c);
        }
    }

    @Override
    public void onClick(View v) {

    }

    private void createTextView(TextView textView, LinearLayout layout, int i) {

        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(arrayPreguntas[i]);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD_ITALIC);
        textView.setPadding(10, 0, 10, 10);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 0, 10, 10);
        textView.setLayoutParams(lp);
        System.out.println("Elemento padre "+textView.getParent());
        /**
         if(textView.getParent()!= null){
         ((LinearLayout)textView.getParent()).removeView(textView);
         }
         *
         */



        layout.addView(textView);
        //textView = findViewById(temp);

    }

    private void createRespuesta(LinearLayout layout, int i, int c) {

        arrRButtons[i] = new RadioGroup(Formularios_Code.this);
        for (int aux = 0; aux < c; aux++) {
            RadioButton button = new RadioButton(Formularios_Code.this);
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setText(arrayRespuestas1[aux]);
            button.setTextSize(15);
            button.setTypeface(null, Typeface.NORMAL);
            button.setPadding(20, 0, 10, 10);
            //temp = getResources().getIdentifier(idRespuestas[i][aux], "id", getPackageName());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 0, 10, 10);
            button.setLayoutParams(lp);
            //button.setId(aux);
            arrRButtons[i].addView(button);

        }
        layout.addView(arrRButtons[i]);

    }

    public void guardar() {
        boolean condicion = true;
        auxiliar = 0;
        try {
            while (auxiliar < arrayCont) {
                arrRButtons[auxiliar].getCheckedRadioButtonId();

                System.out.println("Radiogroup: " + auxiliar);
                System.out.println(arrRButtons[auxiliar].getCheckedRadioButtonId());
                if (arrRButtons[auxiliar].getCheckedRadioButtonId() == -1 ) {
                    condicion = false;
                    Toast.makeText(Formularios_Code.this, "Faltan casillas por rellenar, compruebe", Toast.LENGTH_SHORT).show();
                    break;
                }
                String temporal = String.valueOf(editText.getText());
                if(temporal.equals("")){
                    System.out.println("Si esta vacio");
                    condicion = false;
                    Toast.makeText(Formularios_Code.this, "Faltan casillas por rellenar, compruebe", Toast.LENGTH_SHORT).show();
                    break;
                }
                auxiliar++;
            }

            if (condicion) {
                arrayRespuestas = new ArrayList<>();
                //Ciclo for para respuestas de arrayButtons
                for(int i = 0; i<arrayCont; i++){
                    arrRButtons[i].getCheckedRadioButtonId();
                    System.out.println("Radiogroup: " + i);
                    System.out.println(arrRButtons[i].getCheckedRadioButtonId());
                    RadioButton radioButton = (RadioButton) arrRButtons[i].findViewById(arrRButtons[i].getCheckedRadioButtonId());
                    String respuesta = radioButton.getText()+"";
                    arrayRespuestas.add(respuesta);

                }
                //Respuestas y campo observaciones
                String respObs = editText.getText()+"";
                arrayRespuestas.add(respObs);
                arrayRespuestas.add(longitud+"");
                System.out.println("------------"+longitud+"----------------------");
                arrayRespuestas.add(latitud+"");
                System.out.println("------------"+latitud+"----------------------");
                //array de imagenes de vista previa
                arrayImagenes = Preview.arrayImagenes;

                //ciclo for para añadir ruta de imagenes al arrayRespuestas

                for(int i= 0; i<arrayImagenes.size(); i++){
                    String imagen = arrayImagenes.get(i);
                    arrayRespuestas.add(imagen);
                }
                //Metodo para siguiente ventana
                //createFile();

                Intent intent = new Intent(this, A7.class);
                intent.putExtra("arrayResp", arrayRespuestas);
                startActivity(intent);
                //finish();
            }
        } catch (NullPointerException e) {
            Toast.makeText(Formularios_Code.this, "Hay casillas sin contester", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error " + e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            Toast.makeText(Formularios_Code.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }
    }


    public void crearAchivoRutas(File gxpFile, String sBody){
        try {
            //File gxpFile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gxpFile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            System.out.println("Archivo de rutas no se encontro pero se ha creado");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void sobreescribir(File newGpxfile, File gpxfile, String newFileName ){
        try {

            String datos = "";
            FileInputStream fis = new FileInputStream(newGpxfile);
            DataInputStream in = new DataInputStream(fis);

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while((strLine = br.readLine()) != null){

                datos = datos+strLine+ "\n";
            }
            in.close();

            FileWriter writer = new FileWriter(newGpxfile);
            writer.append(datos);
            writer.flush();
            String nRuta = gpxfile.getAbsolutePath()+"\n";
            writer.append(nRuta);
            writer.flush();
            writer.close();
            System.out.println("Se ha añadido una nueva ruta");

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void location(){
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ){
            if(ActivityCompat.checkSelfPermission(Formularios_Code.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Formularios_Code.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                System.out.println("-----------No hay permisos de gps ------------------------");
                ActivityCompat.requestPermissions(Formularios_Code.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
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
        @Override
        public void onLocationChanged(@NonNull Location location) {
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
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(Formularios_Code.this, "Permiso habilitado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Formularios_Code.this, "Permiso denegado, active el permiso para utilizar la aplicación", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

}