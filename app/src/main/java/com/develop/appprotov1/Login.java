package com.develop.appprotov1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity {
    EditText usernameText;
    EditText passwordText;
    Button loginButton;
    ProgressBar loading;

    private String username;
    private String password;
    private Activity context = this;
    public static String credencial = null;
    private Boolean acceso = null;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor myEditor;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(context);

        usernameText = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        loading = findViewById(R.id.loading);

        loginPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
        myEditor = loginPreferences.edit();

        credencial = loginPreferences.getString("CREDENCIAL", null);
        if(credencial!= null){
            System.out.println("Se encontró una credencial: \n"+credencial);
        }else{
            System.out.println("No Se encontró una credencial: null");
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameText.getText().toString().equals("") || passwordText.getText().toString().equals("")){
                    Toast.makeText(Login.this, "Por favor ingrese un nombre de usuario y una contraseña para acceder", Toast.LENGTH_LONG).show();
                }else{
                    acceder();

                }


            }
        });
    }

    private void acceder(){
        username = usernameText.getText().toString();
        password = passwordText.getText().toString();
        progresssBar();
        //loading.setVisibility(View.VISIBLE);
        Callback callback = new Callback(){


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){

                    int code = response.code();

                    if(code == 200){
                        if(response.headers().get("Authorization") != null){
                            credencial = response.headers().get("Authorization");
                            System.out.println("--------------------------------------------------------------");
                            //System.out.println(""+credencial);

                            myEditor.putString("CREDENCIAL", credencial);
                            myEditor.commit();


                            Thread thread = new Thread(){
                                public void run(){
                                    runOnUiThread(new Runnable() {

                                        public void run() {
                                            progressDialog.dismiss();
                                            Toast.makeText(Login.this, "Acceso correcto", Toast.LENGTH_LONG).show();

                                            Intent intent = new Intent(context, List.class);
                                            startActivity(intent);

                                        }
                                    });
                                }
                            };
                            thread.start();
                            acceso = true;
                        }else{
                            Thread thread = new Thread(){
                                public void run(){
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            progressDialog.dismiss();
                                            Toast.makeText(Login.this, "Acceso Incorrecto", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            };
                            thread.start();

                            System.out.println("------------------------------Acceso Incorrecto--------------------");
                            acceso = false;
                        }

                    }else{
                        //loading.setVisibility(View.GONE);
                        Thread thread = new Thread(){
                            public void run(){
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        progressDialog.dismiss();
                                        Toast.makeText(Login.this, "Algo salió mal, comprueba tu conexión a internet", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        };

                        System.out.println("------------------------------Algo salio mal--------------------");
                        System.out.println(""+response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                //loading.setVisibility(View.GONE);
                Thread thread = new Thread(){
                    public void run(){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(Login.this, "Algo salió mal, comprueba tu conexión a internet", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                };
                thread.start();

                System.out.println("---------------------------Algo salió mal con el servidor_----------------------");
                e.printStackTrace();
            }

        };


        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"username\":\""+username+"\",\r\n    \"password\":\""+password+"\"\r\n}");

        Request request = new Request.Builder().url("https://denscode.com:8443/puramielv10/signin").method("POST", body).addHeader("Content-type", "text/plain").build();

        Call call = client.newCall(request);
        call.enqueue(callback);


    }
    public void progresssBar(){

        //progressDialog.setTitle("Eliminar encuesta");
        //progressDialog.
        progressDialog.setMessage("Conectando...");
        progressDialog.setIndeterminate(false);

        //progressDialog.setMax(100);
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        progressDialog.show();

        //new Thread(new Threads(progressDialog, context)).start();
        //context.runOnUiThread(new threads(progressDialog, context));

    }

}