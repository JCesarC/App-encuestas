package com.develop.appprotov1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class A7 extends AppCompatActivity {
    Bitmap bitMap;
    Button btnClearA7;
    Button btnBackA7;
    Button btnSaveA7;
    String path;
    SignatureView signatureView;
    EditText nombreA7;
    private File signature;
    private static final String IMAGE_DIRECTORY = "/signapp";
    private String ruta;
    static int arrayCont = 10;
    private ArrayList<String> arrayA6 = new ArrayList<>();
    private ArrayList<String> arrayA7 = new ArrayList<>();
    private Boolean isSignatured = false;

    LocationManager locationManager;
    double longps, latgps;

    public static String longitud="";
    public static String latitud="";

    private String titulo = "A7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a7);
        nombreA7 = findViewById(R.id.nombreA7);
        btnClearA7 = findViewById(R.id.btnClearA7);
        btnSaveA7 = findViewById(R.id.btnSaveA7);
        btnBackA7 = findViewById(R.id.btnBackA7);
        signatureView = (SignatureView) findViewById(R.id.signature_view);

        arrayA6 = (ArrayList<String>) getIntent().getSerializableExtra("arrayA6");
        latitud = (String) getIntent().getSerializableExtra("lati");
        longitud = (String) getIntent().getSerializableExtra("long");



        btnBackA7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnClearA7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });

        btnSaveA7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String productor = nombreA7.getText().toString();
                if(productor.equals("") || productor.length()<8 ){
                    Toast.makeText(A7.this, "Por favor escriba el nombre del productor", Toast.LENGTH_SHORT).show();
                }else{
                    if(!signatureView.isBitmapEmpty()){
                        bitMap = signatureView.getSignatureBitmap();
                        path = saveImage(bitMap);
                        createFile(productor, path);
                    }else{
                        Toast.makeText(A7.this, "Por favor ingrese una firma para continuar", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


    }

    private String saveImage(Bitmap myBitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes);

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try{

            //File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() +".png");
            String signatureDirectory = storageDir.getAbsolutePath();
            File root = new File(signatureDirectory, "Signatures");

            System.out.println(".....................Ruta de las firmas-...........\n"+root.getAbsolutePath());

            if(!root.exists()){
                Toast.makeText(A7.this, (root.mkdir() ? "Directorio Creado" : "Directorio de firmas no creado"), Toast.LENGTH_SHORT).show();
            }else{
                System.out.println("Directorio de firmas ya existe");
                Log.d("TAG", "Directiorio de firmas ya existe");

            }
            String rootPath = root.getAbsolutePath();
            String nameFile = "Signature_"+Calendar.getInstance().getTimeInMillis() +".png";

            File f = new File(root, nameFile);

            if(f.createNewFile()){
                System.out.println("Archivo firma creado");
            }else{
                System.out.println("Archivo firma no se creado");
            }

            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(A7.this,
                    new String[]{f.getPath()},
                    new String[]{"image/png"}, null);
            fo.close();
            Log.d("TAG", "File saved :: --->"+f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch(IOException e ){
            e.printStackTrace();
        }
        return "";
    }

    public void createFile(String porductor, String signaturePath){
        try {
            arrayA7 = new ArrayList<>();
            arrayA7.addAll(arrayA6);

            arrayA7.add(titulo);

            arrayA7.add(porductor);
            arrayA7.add(latitud);
            arrayA7.add(longitud);
            arrayA7.add(signaturePath);
            //comprobar Array

            for (int i=0; i<arrayA7.size(); i++){
                System.out.println("---------------------------------------------------------------");
                System.out.println("respuesta: "+i+" : "+arrayA7.get(i));
            }

            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                Log.i("Mensaje", "No se tiene permiso para leer.");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
                //+ "/food.txt";

            } else {
                Log.i("Mensaje", "Se tiene permiso para leer y escribir!");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss");
                Date now = new Date();

                //String timeStamp = new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date());
                String fileName = "Formulario" + formatter.format(now) + ".txt";

                File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                String rootDirectory = storageDir.getAbsolutePath();

                File root = new File(rootDirectory, "Reportes");

                System.out.println("Ruta de los reportes: "+root.getAbsoluteFile());


                if (!root.exists()) {
                    Toast.makeText(A7.this, (root.mkdir() ? "Directorio creado" : "Directorio no creado"), Toast.LENGTH_SHORT).show();

                }else{
                    //Toast.makeText(Signature.this, "Directorio existe", Toast.LENGTH_SHORT).show();
                    System.out.println("Directorio Existe");
                }
                ruta = root.getAbsolutePath();
                File gpxfile = new File(root, fileName);
                FileWriter writer = new FileWriter(gpxfile);

                //Ciclo para escribir las respuestas en el archivo
                for (int i = 0; i < arrayA7.size(); i++) {
                    String respuesta = arrayA7.get(i)+"\n";
                    writer.append(respuesta);
                }
                //writer.append(signaturePath+"\n");

                writer.flush();
                writer.close();
                Toast.makeText(A7.this, "Se ha guardado el archivo de manera local", Toast.LENGTH_LONG).show();
                System.out.println("Ruta del archivo "+gpxfile.getAbsolutePath());

                Intent intent = new Intent(A7.this, List.class);
                startActivity(intent);
                finish();

            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

}