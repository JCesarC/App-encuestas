package com.develop.appprotov1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.develop.appprotov1.Resizer.ImageResizer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Preview extends AppCompatActivity {

    final int SOLICITUD_PERMISO = 0;
    final int SOLICITUD_IMAGEN = 1;

    Button btnFormularios;
    Button btnCapturar1;
    int PICK_IMAGE = 100;
    Uri imagenUri;
    GridView gvImagenes;
    //public int cont;
    Uri imageUri;

    public static ArrayList<String> arrayImagenes = new ArrayList<String>();
    public static ArrayList<File> arrayFileList = new ArrayList<>();
    final String AUTORIDAD = "com.develop.appprotov1.prov";


    List<Uri> listaImagenes = new ArrayList<>();
    GridViewAdapter baseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        gvImagenes = findViewById(R.id.gvImagenes);
        btnCapturar1 = findViewById(R.id.btnCapturar1);
        btnFormularios = findViewById(R.id.btnFormularios);

        arrayImagenes = new ArrayList<>();
        arrayFileList = new ArrayList<>();
        listaImagenes = new ArrayList<>();


        arrayFileList = MainActivity.arrayFileList;
        //arrayImagenes = MainActivity.arrayImagenes;
        //listaImagenes = (List<Uri>) getIntent().getSerializableExtra("arrayCapturas");
        arrayImagenes = MainActivity.arrayImagenes;

        for(int i= 0; i<arrayImagenes.size(); i++){
            File f = new File(arrayImagenes.get(i));
            imageUri = Uri.fromFile(f);
            listaImagenes.add(imageUri);
        }

        /**
         for(int i = 0; i<arrayImagenes.size(); i++){
         File F = new File(arrayImagenes.get(i));
         System.out.println(""+arrayImagenes.get(i));
         imagenUri = Uri.fromFile(F);

         listaImagenes.add(imagenUri);
         }

         **/

        baseAdapter = new GridViewAdapter(Preview.this, listaImagenes);
        gvImagenes.setAdapter(baseAdapter);

        btnFormularios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(arrayImagenes.size()>=1){
                    Intent intent = new Intent(v.getContext(), A1.class);
                    intent.putExtra("arrayImagenes", arrayImagenes);
                    //put extra para el arreglo de imagenes
                    startActivity(intent);

                }else{
                    Toast.makeText(Preview.this, "No se ha capturado ninguna imagen", Toast.LENGTH_SHORT).show();
                    //System.out.println("no es mayor");
                }

            }
        });
        btnCapturar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayImagenes.size()<10){
                    capturarImagen();
                }else{
                    Toast.makeText(Preview.this, "Se ha alcanzado el limite de imagenes", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void capturarImagen() {
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
                arrayImagenes.add("" + archivo.getAbsolutePath());

                imageUri = FileProvider.getUriForFile(Preview.this, AUTORIDAD, archivo);

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

            File F = new File(arrayImagenes.get(arrayImagenes.size()-1));
            arrayFileList.add(F);
            System.out.println("Agregada nueva imagen: "+arrayImagenes.get(arrayImagenes.size()-1));
            imagenUri = Uri.fromFile(F);

            listaImagenes.add(imagenUri);

            baseAdapter = new GridViewAdapter(Preview.this, listaImagenes);
            gvImagenes.setAdapter(baseAdapter);
            /**
             imagenVista.setImage(ImageSource.uri(imageUri));
             btnSiguiente.setVisibility(View.VISIBLE);
             textContador.setText(String.valueOf(cont));
             **/
        }
    }

    public void deleteImage(File files) {
        for(int i= arrayImagenes.size()-1; i>=0; i--){
            if(files.exists()){
                if(files.delete()){
                    Log.e("-->", "file Deleted :" + files.getAbsolutePath());
                    arrayImagenes.remove(i);
                    callBroadCast();

                }else{
                    Log.e("-->", "file not Deleted :" + files.getAbsolutePath());
                }
            }
        }
    }
    private File getBitmapFile(Bitmap reduceBitmap) {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nameFile = "Image_"+timeStamp+".png";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = new File(storageDir, nameFile);


        //solo para saber la ruta
        System.out.println("Ruta ruta ruta ----------: "+file.getAbsolutePath());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        reduceBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        byte[] bitmapdata = bos.toByteArray();

        try {
            if(file.createNewFile()){
                System.out.println("Archivo creado");

            }else{
                System.out.println("archivo no creado ");
            }
            //file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }
    public void callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(this, new String[]{Environment.DIRECTORY_PICTURES}, null, new MediaScannerConnection.OnScanCompletedListener() {
                /*
                 *   (non-Javadoc)
                 * @see android.media.MediaScannerConnection.OnScanCompletedListener#onScanCompleted(java.lang.String, android.net.Uri)
                 */
                public void onScanCompleted(String path, Uri uri) {
                    Log.e("ExternalStorage", "Scanned " + path + ":");
                    Log.e("ExternalStorage", "-> uri=" + uri);
                }
            });
        } else {
            Log.e("-->", " < 14");
            //context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
            //Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }


    /**
    public void updateArray(String [] array, int i){
        array [i]  = "";
        //arrayImagenes = array;
        //listaImagenes.remove(i);
        //arrayImagenes = new String[10];
        cont = 0;
        System.out.println(" ******Se supone que debe estar vacio: ******"+ Arrays.toString(arrayImagenes));
        for(int c = 0; c<array.length;c++){
            if (!array[c].equals("")){
                arrayImagenes[cont] = array[c];
                cont++;
            }
        }
        //baseAdapter = new GridViewAdapter(Preview.this, listaImagenes);
        //gvImagenes.setAdapter(baseAdapter);7
        System.out.println("******************* NUEVO: *******************"+ Arrays.toString(arrayImagenes));
    }

    **/

}