package com.develop.appprotov1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final int SOLICITUD_PERMISO = 0;
    final int SOLICITUD_IMAGEN = 1;

    public static int cont = 0;
    public static ArrayList<String> arrayImagenes = new ArrayList<String>();;
    public static ArrayList<File> arrayFileList = new ArrayList<>();
    List<Uri> arrayCapts = new ArrayList<>();

    final String AUTORIDAD = "com.develop.appprotov1.prov";
    Button btnCapture;
    Button btnGirar;
    Button btnSiguiente;
    TextView textContador;
    SubsamplingScaleImageView imagenVista;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        arrayImagenes = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCapture = findViewById(R.id.btnCapturar);
        imagenVista = findViewById(R.id.imagenVista);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnGirar = findViewById(R.id.btnGirar);
        textContador = findViewById(R.id.idContador);



        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, SOLICITUD_PERMISO);
                        System.out.println("Permiso concedido");
                     }
                     if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                         System.out.println("Se han concedido los permisos");
                         try {
                             if(arrayImagenes.size()<10) {
                                //imagenVista.setRotation(imagenVista.getRotation()-90);
                                capturarImagen();

                             }else{
                                 Toast.makeText(MainActivity.this, "Se han alcanzado el limite de "+cont+" Imagenes", Toast.LENGTH_SHORT).show();
                             /**
                                 for(int i = 0; i<cont; i++){
                                       File F = new File(arrayImagenes[i]);
                                       Uri contentUri = Uri.fromFile(F);
                                       Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
                                       Captura(mediaScanIntent);
                                       System.out.println("Se ha guardado la imagen con el nombre "+arrayImagenes[i]);
                                 }
                                 Toast.makeText(MainActivity.this, "Se han guardado las imagenes", Toast.LENGTH_LONG).show();
                             **/

                             }
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                     }
            }
        });
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Preview.class);
                //intent.putExtra("arrayCapturas", (Parcelable) arrayCapts);
                intent.putExtra("arrayIm", arrayImagenes);
                startActivity(intent);
                //startActivityForResult(intent, 0);
                finish();
            }
        });

        btnGirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenVista.setRotation(imagenVista.getRotation()+90);
            }
        });

        }

    

    public void Captura(Intent intent ){
        this.sendBroadcast(intent);

    }

    //Este apartado se va a optimizar
    public void capturarImagen() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName ="imagen"+timeStamp;

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(intent.resolveActivity(getPackageManager())!= null){
            try {
                File archivo = File.createTempFile(
                        imageFileName,
                        ".png",
                        storageDir
                );
                arrayImagenes.add(""+archivo.getAbsolutePath());
                File f = new File(archivo.getAbsolutePath());
                arrayFileList.add(f);
                imageUri = FileProvider.getUriForFile(MainActivity.this, AUTORIDAD, archivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, SOLICITUD_IMAGEN);



            }catch (IOException e ){
                System.out.println("IOException encontrada: \n"+e.getMessage());
            }catch(Exception e){
                System.out.println("Ha ocurrido un error con otra excepcion: "+e.getMessage() +"\n"+e.getCause());
            }
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == SOLICITUD_IMAGEN && resultCode == Activity.RESULT_OK){
            imagenVista.setImage(ImageSource.uri(imageUri));
            arrayCapts.add(imageUri);
            btnGirar.setVisibility(View.VISIBLE);
            btnSiguiente.setVisibility(View.VISIBLE);
            textContador.setText(String.valueOf(arrayImagenes.size()));
        }
    }

}