package com.develop.appprotov1.io;

import com.develop.appprotov1.CustomFilesList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;

import okhttp3.Request;
import okhttp3.RequestBody;

import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiagnosticVetApiAdapter {
    private static DiagnosticVetApiService API_SERVICE;

    public static DiagnosticVetApiService getApiService() {

        // Creamos un interceptor y le indicamos el log level a usar
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Asociamos el interceptor a las peticiones
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        String baseUrl = "https://denscode.com:8443/puramielv10/";

        if (API_SERVICE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) // <-- usamos el log level
                    .build();
            API_SERVICE = retrofit.create(DiagnosticVetApiService.class);
        }

        return API_SERVICE;
    }


    public static void Api(ArrayList<String> filePaths, String id, String credencial) throws IOException {
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("----------No Creado correctamente------"+"\n"+e.getMessage());
                System.out.println("-----------------------------");
                //CustomFilesList.alertDialog("Algo salió mal", "Ha ocurrido un error en la carga al servidor, intenta de nuevo por favor.");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("------------- Creado correctamente------");
                //ventana para indicar que se ha realizado correctamente la carga?
            }

        };

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");

         MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);

         for(int i = 0; i<filePaths.size(); i++){
             body.addFormDataPart("file", filePaths.get(i),
                     RequestBody.create(MediaType.parse("application/octet-stream"),
                             new File(filePaths.get(i))));
             System.out.println("---------------------- Imagen añadida: "+filePaths.get(i));
         }

         /**
         body.addFormDataPart("file","/storage/emulated/0/Android/data/com.develop.appprotov1/files/Pictures/reducedFile1.png",
         RequestBody.create(MediaType.parse("application/octet-stream"),
         new File("/storage/emulated/0/Android/data/com.develop.appprotov1/files/Pictures/reducedFile1.png")));
        /**

         body.addFormDataPart("file","/storage/emulated/0/Android/data/com.develop.appprotov1/files/Pictures/imagenEjemplo2.png",
         RequestBody.create(MediaType.parse("application/octet-stream"),
         new File("/storage/emulated/0/Android/data/com.develop.appprotov1/files/Pictures/imagenEjemplo2.png")));
         **/



        RequestBody rquestBody = body.build();
        Request request = new Request.Builder()
                .url("https://denscode.com:8443/puramielv10/cuestionario/img/"+id)
                .method("POST", rquestBody)
                .addHeader("Authorization", credencial)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
