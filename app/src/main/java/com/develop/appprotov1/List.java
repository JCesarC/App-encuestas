package com.develop.appprotov1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.FileUtils;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.MultipartBody;

public class List extends AppCompatActivity {



    public static ArrayList<String> arrayArchivos = new ArrayList<String>();
    public static ArrayList<String> nameFiles = new ArrayList<String>();
    public static ArrayList<String> nameFiles1 = new ArrayList<String>();

    public static java.util.List<File> listFiles = new ArrayList<>();
    public static int iDs = 0;
    private FloatingActionButton btnNuevo;
    private static ListView listView;
    private String rutaReportes = "/storage/emulated/0/Android/data/com.develop.appprotov1/files/Documents/Reportes/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        arrayArchivos = new ArrayList<String>();
        listFiles = new ArrayList<>();
        nameFiles = new ArrayList<String>();


        listView = findViewById(R.id.listadoView);
        btnNuevo = findViewById(R.id.btnNuevo);
        leerArchivoRuta();

        CustomFilesList customFiles = new CustomFilesList(this, nameFiles);
        listView.setAdapter(customFiles);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Se borrara el arvhico seleccionado: " + position);
                Toast.makeText(view.getContext(), "Se borrara el archivo presionado: " + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("MIAPP", "Estás online");

            Log.d("MIAPP", " Estado actual: " + networkInfo.getState());

            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // Estas conectado a un Wi-Fi

                Log.d("MIAPP", " Nombre red Wi-Fi: " + networkInfo.getExtraInfo());
            }

        } else {
            Log.d("MIAPP", "Estás offline");
        }
    }

    private void leerArchivoRuta(){
        try {
/**
 *             File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
 *             String rootDirectory = storageDir.getAbsolutePath();
 **/

            File root = new File(rutaReportes);

            String [] temporal = root.list();

            for(int i = 0;i<temporal.length;i++){
                arrayArchivos.add(temporal[i]);
            }
            System.out.println("Arreglo de archivos: "+arrayArchivos);
            Collections.sort(arrayArchivos);

            for(int i = 0; i<arrayArchivos.size();i++){
                File rf = new File (arrayArchivos.get(i));
                File nf = new File(rutaReportes, rf.getName());
                String nameF = rf.getName();
                String name1F = nameF.replaceAll(".txt","");

                nameFiles.add(name1F);
                listFiles.add(nf);
            }

            for(int i = 0; i<nameFiles.size();i++){

                System.out.println("Arreglo de nombre de los archivos");
                System.out.println("Arreglo: "+i+ "....-----------"+nameFiles.get(i));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Principal.class);
        startActivityForResult(intent, 0);
        finish();
    }

}