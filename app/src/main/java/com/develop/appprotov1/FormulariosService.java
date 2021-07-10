package com.develop.appprotov1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;

import static android.support.v4.content.ContextCompat.getSystemService;

public class FormulariosService extends AppCompatActivity {

    public void sendPost(ArrayList<String> respuestas) throws Exception{



        ArrayList<String> preguntas = new ArrayList<String>(Arrays.asList("pre1","pre2","pre3","pre4","pre5","pre6","pre7","pre8","pre9","pre10"));

        URL url = new URL("https://denscode.com:8443/puramielv10/cuestionario");
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");

        /* Payload support */
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes("{\n");
        for(int i = 0; i<10; i++){
            out.writeBytes("\""+preguntas.get(i)+"\""+":"+"\""+respuestas.get(i)+"\""+",\n");
            System.out.println("pregunta y respuesta------------------------"+ preguntas.get(i)+"----------------"+respuestas.get(i));
        }
        /**
         out.writeBytes("  \"pre1\":\"Si\",\n");
         out.writeBytes("  \"pre2\":\"No\",\n");
         out.writeBytes("  \"pre3\":\"Si\",\n");
         out.writeBytes("  \"pre4\":\"No\",\n");
         out.writeBytes("  \"pre5\":\"No\",\n");
         out.writeBytes("  \"pre6\":\"Si\",\n");
         out.writeBytes("  \"pre7\":\"Si\",\n");
         out.writeBytes("  \"pre8\":\"No\",\n");
         out.writeBytes("  \"pre9\":\"No\",\n");
         out.writeBytes("  \"pre10\":\"Si\"\n");
         **/

        out.writeBytes("}");
        out.flush();
        out.close();

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        System.out.println("Response status: " + status);
        System.out.println(content.toString());
    }




}
