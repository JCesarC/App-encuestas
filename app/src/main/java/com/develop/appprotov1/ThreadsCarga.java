package com.develop.appprotov1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;

public class ThreadsCarga implements Runnable{
    private ProgressDialog progressDialog;
    private Handler handler;
    private Activity context;
    public ThreadsCarga(ProgressDialog progressDialog, Activity context){
        this.progressDialog = progressDialog;
        this.handler = new Handler();
        this.context = context;
    }

    @Override
    public void run() {
        try{
            while(progressDialog.getProgress() <= progressDialog.getMax()-1){
                Thread.sleep(10);

                progressDialog.incrementProgressBy(1);

                //System.out.println("Progreso:   "+progressDialog.getProgress());


            }
            progressDialog.dismiss();
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    //Toast.makeText(context, "encuesta eliminada", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(context);
                    alertDialogBuilder1.setTitle("Carga terminada");
                    alertDialogBuilder1.setMessage("Se ha subido al servidor correctamente una encuesta").setCancelable(false)
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create().show();
                }
            });

            //System.out.println("ya termino");
        }catch (InterruptedException er){
            er.printStackTrace();

        }
    }
}
