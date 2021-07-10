package com.develop.appprotov1;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    Context context;
    List<Uri> listaImagenes;
    LayoutInflater layoutInflater;
    String [] newArray;


    public GridViewAdapter(Context context, List<Uri> listaImagenes){
        this.context = context;
        this.listaImagenes = listaImagenes;

    }

    @Override
    public int getCount() {
        return listaImagenes.size();
    }

    @Override
    public Object getItem(int i) {
        return listaImagenes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_gv_imagenes,null);

        }
        ImageView ivImagen = view.findViewById(R.id.ivImagen);
        ImageButton ibtnEliminar = view.findViewById(R.id.btnEliminar);


        ivImagen.setImageURI(listaImagenes.get(i));

        ibtnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //newArray = Preview.arrayImagenes;
                Preview prev = new Preview();
                try{

                    deleteImage(Preview.arrayFileList.get(i));
                    Preview.arrayFileList.remove(i);
                    System.out.println("Se borró la imagen "+i+" de la vista previa");
                }catch(Exception e){
                    e.printStackTrace();
                }
                if(listaImagenes.size()<1){

                }



                listaImagenes.remove(i);
                Preview.arrayImagenes.remove(i);

                //prev.updateArray(newArray, i);
                System.out.println("Se ha actualizado el arreglo");
                notifyDataSetChanged();


                Toast.makeText(context, "Se ha borrado una imagen ", Toast.LENGTH_SHORT ).show();

            }
        });

        return view;
    }

    public void deleteImage(File file) {

        if (file.exists()) {
            if (file.delete()) {
                Log.e("-->", "file Deleted :" + file.getAbsolutePath());
                callBroadCast();
            } else {
                Log.e("-->", "file not Deleted :" + file.getAbsolutePath());
            }
        }
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
}
