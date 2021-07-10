package com.develop.appprotov1.ThreadFiles;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.develop.appprotov1.Login;

public class ThreadSuccessError implements Runnable{

    private Handler handler;
    private Activity context;
    private String title;
    private String message;
    private ProgressDialog progressDialog;

    public ThreadSuccessError(Activity context, ProgressDialog progressDialog, String title, String message) {

        this.context = context;
        this.title = title;
        this.message = message;
        this.handler = new Handler();
        this.progressDialog = progressDialog;
    }


    @Override
    public void run() {
        try{
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    progressDialog.dismiss();
                    //Toast.makeText(context, "encuesta eliminada", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(context);
                    alertDialogBuilder1.setTitle(title);
                    alertDialogBuilder1.setMessage(message).setCancelable(false)
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Intent intent = new Intent(context, Login.class);
                                    //context.startActivity(intent);
                                    System.out.println("Se cerró el dialog---ss");
                                    dialog.cancel();
                                }
                            })
                    .create().show();
                }
            });
        }catch (Exception er){
            er.printStackTrace();

        }
    }
}
