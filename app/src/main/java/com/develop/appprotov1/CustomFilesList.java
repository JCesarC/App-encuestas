package com.develop.appprotov1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.appprotov1.Resizer.ImageResizer;
import com.develop.appprotov1.ThreadFiles.NotLogged;
import com.develop.appprotov1.ThreadFiles.ThreadSuccessError;
import com.develop.appprotov1.io.DiagnosticVetApiAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CustomFilesList extends ArrayAdapter {
    private ProgressDialog progressDialog12;

    //private Integer[] imageId;
    private ImageButton imgCargar;
    private  static Activity context;
    private ArrayList<String> arrayArchivos;
    private static String idRespuesta;
    private String requestCode;
    private int resultCode;

    private ArrayList<File> arrayImagenes = new ArrayList<>();
    private static ArrayList<String> arrayRespuestas = new ArrayList<>();
    private String pathSignature = "";
    private String servicePost = "";
    private static ArrayList<String> arrayPaths = new ArrayList<>();
    private static ArrayList<String> arrayPathsC = new ArrayList<>();
    private String rutaImagenes = "/storage/emulated/0/Android/data/com.develop.appprotov1/files/Pictures/";
    private String[] arrayA1 = {
      "fechaInspeccion",
      "propietario",
      "idOrganico",
      "comunidad",
      "municipio",
      "estado",
        "estatusProductor"
    };
    private String[] arrayA2 = {

            "pre21",
            "pre22",
            "pre23",
            "pre24",
            "pre25",
            "pre26",
            "pre27",
            "pre28"
    };
    private String[] arrayA3 = {
            "pre31",
            "pre32",
            "pre33",
            "pre34",
            "pre35",
            "pre36",
            "pre37",
            "pre38",
    };
    private String[] arrayA4 = {
            "pre41",
            "pre51",
            "pre52"
    };
    private String[] arrayA5 = {
            "pre61",
            "pre62",
            "pre63",
            "pre64",
            "pre65",
            "pre66"
    };
    private String[] arrayA6 = {
            "observaciones",
            "pre81"
    };
    private String[] arrayA7 = {
            "productor",
            "latitud",
            "longitud",
    };
    public CustomFilesList(Activity context, ArrayList <String> arrayArchivos) {
        super(context, R.layout.row_item, arrayArchivos);
        this.context = context;
        this.arrayArchivos = arrayArchivos;
        this.progressDialog12 = new ProgressDialog(context);

        //this.imageId = imageId;
    }

    //progressDialog12 = new ProgressDialog(context);
    @Override
    public int getCount() {
        return arrayArchivos.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayArchivos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null) {
            row = inflater.inflate(R.layout.row_item, null, true);
        }
            imgCargar = (ImageButton) row.findViewById(R.id.imgCargar);

            TextView textViewNames = (TextView) row.findViewById(R.id.textViewNames);
            ImageView imageNames = (ImageView) row.findViewById(R.id.imageNames);
            //ImageButton imageSend = (ImageButton) row.findViewById(R.id.btnSend);
            textViewNames.setText(arrayArchivos.get(position));
            imageNames.setImageResource(R.drawable.docs);
            //imageSend.setImageResource(R.drawable.iconsave);
            row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText(context, "Se borrara este archivito eh ", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Eliminar archivo ");
                alertDialogBuilder.setMessage("¿Deseas eliminar la encuesta: \n"+arrayArchivos.get(position)).setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                readFile(position);
                                //Aun por resolver si se borran las imagens tambien
                                deleteImages(arrayImagenes);
                                System.out.println("Se borrara el archivito: "+position);
                                deleteFile(List.listFiles.get(position), position);

                                notifyDataSetChanged();
                                progresssBar();
                                //alertDialog();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create().show();

                return true;
            }
        });

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Manten presionado un registro para eliminarlo", Toast.LENGTH_SHORT).show();
                }
            });

        imgCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //readFile(position);
                //String credencial = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYW1pYW5yb3NhZG8iLCJBVVRIT1JJVElFU19LRVkiOiJST0xFX1ZFTkRFRE9SIiwiZXhwIjoxNjE0MzA5MDc3fQ.vAoAiBPT0-rHJM0X27j8ZG_CdxjzarQs4xbUgUcXpj5vQumdAFvK4WbxUfe4z3uZRpfyuFOIZ1tn42dhUm2N4w";
                //sendPost(servicePost, credencial);
                //---------------------------------------------------------------------------------------------------------------------

                if(Login.credencial != null){
                    System.out.println("---------------Acceso correcto");
                    String credencial = Login.credencial;
                    //String credencial = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYW1pYW5yb3NhZG8iLCJBVVRIT1JJVElFU19LRVkiOiJSTxFX1ZFTkRFRE9SIiwiZXhwIjoxNjEzMTMwNjQ5fQ.0qkgqetwe5_meJXL41IFE8iHBbJRA_5irpX3V7I69BM_V8EhvY46s6_04ZGJ5CdnFEDfmpVi7MtQDdEu9SqGag";
                    resultCode = 0;


                    okhttp3.Callback callback = new okhttp3.Callback( ){
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                        public void run() {

                            String title = "Algo ha salido mal";
                            String message = "Por favor revise su conexión a internet e inicie sesión";
                            new Thread(new NotLogged(context, title, message)).start();

                        }
                        });

                        }
                        @Override
                        public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                            resultCode = response.code();
                            if(resultCode == 200){
                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.post(new Runnable() {
                                public void run() {

                                    readFile(position);
                                    sendPost(servicePost, credencial);
                                    System.out.println("-------------------El codigo es:"+requestCode);

                                    //Descomentar despues el borrado del archivo
                                    //deleteFile(List.listFiles.get(position), position);


                                    notifyDataSetChanged();

                                }
                                });
                            }else{
                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.post(new Runnable() {
                                public void run() {

                                String title = "Algo ha salido mal";
                                String message = "La sesión ya expiró, porfavor vuelva a iniciar sesión";
                                new Thread(new NotLogged(context, title, message)).start();

                                }
                                });

                             }
                         }
                        };


                         OkHttpClient client = new OkHttpClient().newBuilder().build();
                         Request request = new Request.Builder().url("https://denscode.com:8443/puramielv10/cuestionario?size=100&pages=0")
                         .method("GET", null)
                         .addHeader("Authorization", credencial)
                         .build();

                         okhttp3.Call call = client.newCall(request);
                         call.enqueue(callback);

                     }else{

                         String title = "No hay sesión";
                         String message = "Por favor inicie sesión para enviar archivos";
                         System.out.println("------------------No hay acceso----------------");
                         new Thread(new NotLogged(context, title, message)).start();

                     }


//---------------------------------------------------------------------------------------------------------------------
                //progeso de carga
                /**
                 readFile(position);
                 sendPost(arrayRespuestas);
                 System.out.println("-------------------El codigo es:"+requestCode);

                 //Descomentar despues el borrado del archivo
                 deleteFile(List.listFiles.get(position), position);

                 progressCarga();
                 notifyDataSetChanged();
                **/

            }
        });

        return row;
        }
        private void readFile(int position) {
            try{
                arrayRespuestas = new ArrayList<>();
                arrayPaths = new ArrayList<>();
                arrayImagenes = new ArrayList<>();
                servicePost = "";
                String exd = "";
                FileInputStream fis = new FileInputStream(List.listFiles.get(position));

                System.out.println("Se leyó el archivo "+ List.listFiles.get(position).getAbsolutePath());
                DataInputStream in = new DataInputStream(fis);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                String strLine;
                int contador = 0;
                while ((strLine = br.readLine()) != null) {
                    //Array para obtener el arreglo de imagenes
                    if(contador<10){
                        if(!strLine.equals("-")){
                            File fileImagen = new File(""+strLine);
                            arrayImagenes.add(fileImagen);
                            arrayPaths.add(""+strLine);
                            System.out.println("Se leyó una imagen: "+fileImagen.getAbsolutePath());
                        }
                    }
                    //Array de respuestas
                    if(contador>=10){
                        arrayRespuestas.add(strLine);
                        System.out.println("Respuesta encontrada:" +strLine);
                    }
                    System.out.println(contador+" texto ---------------"+strLine);
                    contador++;
                }

                //final Gson gson = new Gson();
                //" \"pre1\": \""+respuestas.get(0)+"\",\r\n            " +
                //Cuestionario cuest = gson.fromJson()

                for(int i= 0; i<arrayRespuestas.size(); i++){
                    String resp = arrayRespuestas.get(i);
                    if(resp.equals("A1")){
                        String start = "{";
                        int aux = i+1;
                        int k = 0;
                        String auxiliar = "A2";
                        servicePost+=start;
                        for(int j = 0; j <arrayA1.length; j++){
                            servicePost+="\""+arrayA1[j]+"\" :\""+arrayRespuestas.get(aux+j)+"\",";
                            System.out.println(".............................................................");
                            System.out.println(servicePost);
                            k=aux+j;
                        }
                        ArrayList<Apiario> apiario = new ArrayList<>();
                        k+=1;
                        int cantidadApiarios = Integer.parseInt( arrayRespuestas.get(k));

                        for(int l=0; l<cantidadApiarios; l++){
                            String nombre = arrayRespuestas.get(k+1);
                            String colmenas = arrayRespuestas.get(k+2);
                            String rendimiento = arrayRespuestas.get((k+3));
                            System.out.println("----------------------------------------------------------------------------");
                            System.out.println("Apiario: "+nombre+"----"+colmenas+"-----------"+rendimiento);
                            Apiario appi = new Apiario(nombre, colmenas, rendimiento);
                            apiario.add(appi);
                            k+=3;
                        }

                        Gson gsonApiario = new GsonBuilder().setPrettyPrinting().create();
                        String json = gsonApiario.toJson(apiario);
                        System.out.println("----------------------------------------------............................");
                        System.out.println(json);
                        servicePost+="\"apiarios\":"+json+",";
                        System.out.println("-------------------------S E R V I C E ---------------------............................");
                        System.out.println(servicePost);

                    }if(resp.equals("A2")){
                        String auxiliar = "A3";
                        int aux =  i+1;
                        int k = 0;
                        for(int j = 0; j<arrayA2.length;j++){
                            servicePost+="\""+arrayA2[j]+"\": \""+arrayRespuestas.get(aux+j)+"\",";
                            System.out.println(".............................................................");
                            //System.out.println(servicePost);
                            //k=aux+j;
                        }
                    }
                    if(resp.equals("A3")){
                        String auxiliar = "A4";
                        int aux =  i+1;
                        int k = 0;
                        for(int j = 0; j<arrayA3.length;j++){
                            servicePost+="\""+arrayA3[j]+"\": \""+arrayRespuestas.get(aux+j)+"\",";
                            System.out.println(".............................................................");
                            //System.out.println(servicePost);
                            //k=aux+j;
                        }
                    }
                    if(resp.equals("A4")){
                        String auxiliar = "A4";
                        int aux =  i+1;
                        int k = 0;
                        for(int j = 0; j<arrayA4.length;j++){
                            servicePost+="\""+arrayA4[j]+"\": \""+arrayRespuestas.get(aux+j)+"\",";
                            System.out.println(".............................................................");
                            //System.out.println(servicePost);
                            //k=aux+j;
                        }
                    }
                    if(resp.equals("A5")){
                        String auxiliar = "A4";
                        int aux =  i+1;
                        int k = 0;
                        for(int j = 0; j<arrayA5.length;j++){
                            servicePost+="\""+arrayA5[j]+"\": \""+arrayRespuestas.get(aux+j)+"\",";
                            System.out.println(".............................................................");
                            //System.out.println(servicePost);
                            //k=aux+j;
                        }
                    }

                    if(resp.equals("A6")){
                        String auxiliar = "A4";
                        int aux =  i+1;
                        int k = 0;
                        for(int j = 0; j<arrayA6.length;j++){
                            servicePost+="\""+arrayA6[j]+"\": \""+arrayRespuestas.get(aux+j)+"\",";
                            System.out.println(".............................................................");
                            //System.out.println(servicePost);
                            //k=aux+j;
                        }
                    }
                    if(resp.equals("A7")){
                        String auxiliar = "A4";
                        int aux =  i+1;
                        int k = 0;
                        for(int j = 0; j<arrayA7.length;j++){
                            servicePost+="\""+arrayA7[j]+"\": \""+arrayRespuestas.get(aux+j)+"\"";
                            if(j+1<arrayA7.length){
                                servicePost+=",";
                            }else{
                                servicePost+="}";
                            }
                            System.out.println(".............................................................");
                            //System.out.println(servicePost);
                            k=aux+j;
                        }
                        k+=1;
                        pathSignature = arrayRespuestas.get(k);
                        System.out.println(".-.-.-.-.-.-.-.-.-.-.-.-.-..-.-.-.-.F I R M A .-.-.-.-.-.-.-.-.-.");
                        System.out.println(pathSignature);
                    }
                }

                System.out.println(".............................................................");
                System.out.println(servicePost);

                //Ciclo para borrar las imagenes
                /**
                 for(int i= arrayImagenes.size()-1; i>=0;i--){
                 deleteImage(arrayImagenes.get(i));
                 arrayImagenes.remove(i);
                 }

                 **/

                notifyDataSetChanged();
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    public void deleteImages(ArrayList<File> files) {
        for(int i= arrayImagenes.size()-1; i>=0; i--){
            if(files.get(i).exists()){
                if(files.get(i).delete()){
                    Log.e("-->", "file Deleted :" + files.get(i).getAbsolutePath());
                    arrayImagenes.remove(i);
                    callBroadCast();

                }else{
                    Log.e("-->", "file not Deleted :" + files.get(i).getAbsolutePath());
                }
            }
        }
    }
    public void deleteFile(File file, int posicion){
        if(file.exists()){
            if(file.delete()){
                List.nameFiles.remove(posicion);
                //arrayArchivos.remove(posicion);
                System.out.println("Se ha borrado el archivo de la lista ");

                System.out.println("Se ha borrado el archivo de la lista de nombres");
                List.listFiles.remove(posicion);
                System.out.println("Se ha borrado el archivo de la lista de archivos ");

                notifyDataSetChanged();

            }else{
                Log.e("-->", "file not Deleted :" + file.getAbsolutePath());
            }
        }
    }
    public static void alertDialog( String title, String message){

        AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(context);
        alertDialogBuilder1.setTitle(title);
        alertDialogBuilder1.setMessage(message).setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }
    public void callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(context, new String[]{Environment.DIRECTORY_PICTURES}, null, new MediaScannerConnection.OnScanCompletedListener() {
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

    public void progresssBar(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Eliminar encuesta");
        progressDialog.setMessage("Eliminando...");
        progressDialog.setIndeterminate(false);

        progressDialog.setMax(100);
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.show();

        new Thread(new Threads(progressDialog, context)).start();
        //context.runOnUiThread(new threads(progressDialog, context));

    }
    public void progressCarga(){

        progressDialog12.setTitle("Subiendo archivos al servidor");
        progressDialog12.setMessage("Por favor espere...");
        progressDialog12.setIndeterminate(false);

        progressDialog12.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressDialog12.setCancelable(false);
        progressDialog12.show();

    }

    public void sendPost(String service, String credencial){
        progressCarga();
        requestCode = "";
        idRespuesta = "";
        okhttp3.Callback callback = new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {

                        progressDialog12.dismiss();

                        String title = "Algo ha salido mal";
                        String message = "La sesión ya expiró, porfavor vuelva a iniciar sesión";
                        new Thread(new NotLogged(context, title, message)).start();

                    }
                });
                System.out.println("Algo salio maaaal----------------------------");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("Algo salio bieeen----------------------------");
                System.out.println(""+response.body().toString());

                Cuestionario cuestionarioE = new Gson().fromJson(response.body().charStream(), Cuestionario.class);

                System.out.println("---------------------id-------------");
                System.out.println(""+cuestionarioE.getId());

                idRespuesta = String.valueOf(cuestionarioE.getId());
                int resultCode1 = response.code();

                if(resultCode1 == 200){
                   try{
                       System.out.println("---------------Si es el codigo-----------");
                       uploadImages(arrayPaths, idRespuesta, credencial);

                   }catch(Exception e){
                       e.printStackTrace();
                   }
                }else{
                    System.out.println("------------No es el codigo: "+requestCode);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {

                            progressDialog12.dismiss();

                            String title = "Algo ha salido mal";
                            String message = "Por favor vuelva a intentarlo";
                            new Thread(new ThreadSuccessError(context, progressDialog12, title, message)).start();
                        }
                    });
                    //ventana de error
                }
            }
        };
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, service);

        Request request = new Request.Builder()
                .url("https://denscode.com:8443/puramielv10/cuestionario")
                .method("POST", body)
                .addHeader("Authorization", credencial)
                .addHeader("Content-Type", "application/json")

                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

    }

    public void uploadImages(ArrayList<String> filePaths, String id, String credencial) throws IOException {
        prepareImage(filePaths);

        System.out.println("Pasando a la subida de archivos");
        //DiagnosticVetApiAdapter.Api(arrayPathsC, id, credencial);


            Callback callback = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("----------No Creado correctamente------"+"\n"+e.getMessage());
                    System.out.println("-----------------------------");
                    //CustomFilesList.alertDialog("Algo salió mal", "Ha ocurrido un error en la carga al servidor, intenta de nuevo por favor.");
                    e.printStackTrace();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {

                            progressDialog12.dismiss();

                            String title = "Error";
                            String message = "No se ha podido realizar la carga de archivos al servidor";
                            new Thread(new ThreadSuccessError(context, progressDialog12, title, message)).start();

                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println("------------- Creado correctamente 1111111111111111------");
                    //ventana para indicar que se ha realizado correctamente la carga?
                    if(response.code() == 200){
                        System.out.println("------------- Creado correctamente------");
                        System.out.println("------------- Cargando Firma------");
                        uploadFirma(pathSignature, idRespuesta, credencial);
                    }
                }

            };

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            //MediaType mediaType = MediaType.parse("text/plain");

            MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);

            for(int i = 0; i<arrayPathsC.size(); i++){
                body.addFormDataPart("file", arrayPathsC.get(i),
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File(arrayPathsC.get(i))));
                System.out.println("---------------------- Imagen añadida: "+arrayPathsC.get(i));
            }

            RequestBody rquestBody = body.build();
            Request request = new Request.Builder()
                    .url("https://denscode.com:8443/puramielv10/cuestionario/img/"+id)
                    .method("POST", rquestBody)
                    .addHeader("Authorization", credencial)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(callback);

    }
    public void prepareImage(ArrayList<String> filepaths){
        arrayPathsC = new ArrayList<>();
        for(int i= 0; i<filepaths.size();i++){
            //scale
            Bitmap fullSizeBitmap = BitmapFactory.decodeFile(filepaths.get(i));
            Bitmap reduceBitmap = ImageResizer.reduceBitmapSize(fullSizeBitmap, 2073600);
            //guardar el archivo scalado
            File reducedFile = getBitmapFile(reduceBitmap);
            arrayPathsC.add(""+reducedFile.getAbsolutePath());
            System.out.println("------------------------Ruta de la nueva imagen:---------"+reducedFile.getAbsolutePath());
        }

    }

    private File getBitmapFile(Bitmap reduceBitmap) {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nameFile = "reduced_File"+timeStamp+".png";
        File file = new File(rutaImagenes+nameFile);


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
    private void uploadFirma(String signatureP, String idCuestionario, String credencial){
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("-----------------------------------------------");
                System.out.println("-------------------------A L G O  S A L I O  M A L----------------------");
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {

                        progressDialog12.dismiss();

                        String title = "Error";
                        String message = "Ha ocurrido un error con el envio de la firma del formulario";
                        new Thread(new ThreadSuccessError(context, progressDialog12, title, message)).start();

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("-----------------------------------------------");
                System.out.println("-------Salió bien-----");
                if(response.code() == 200){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {

                            progressDialog12.dismiss();
                            //progressDialog12.

                            String title = "Carga completada con exito";
                            String message = "Se han cargado correctamente los archivos al servidor";
                            new Thread(new ThreadSuccessError(context, progressDialog12, title, message)).start();

                        }
                    });
                    System.out.println("Firma cargada correctamente");
                }
            }
        };

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);

        body.addFormDataPart("file", signatureP,
                RequestBody.create(MediaType.parse("application/octet-stream"),
                new File(signatureP)));

        RequestBody requestBody = body.build();
        Request request = new Request.Builder()
                .url("https://denscode.com:8443/puramielv10/cuestionario/firma/"+idCuestionario)
                .method("POST", requestBody)
                .addHeader("Authorization", credencial)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

    }

}
