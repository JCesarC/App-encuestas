package com.develop.appprotov1.Date;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.develop.appprotov1.A1;
import com.develop.appprotov1.List;
import com.develop.appprotov1.R;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

public class GridViewApiario extends ArrayAdapter {

    //private Integer[] imageId;
    private ImageButton imgEliminar;
    private static Activity context;
    private ArrayList<Integer> arrayApiarios;
    private static String idRespuesta;
    private String requestCode;
    private int resultCode;

    private ArrayList<File> arrayImagenes = new ArrayList<>();
    private static ArrayList<String> arrayRespuestas = new ArrayList<>();
    private static ArrayList<String> arrayPaths = new ArrayList<>();
    private static ArrayList<String> arrayPathsC = new ArrayList<>();
    private String rutaImagenes = "/storage/emulated/0/Android/data/com.develop.appprotov1/files/Pictures/";

    public GridViewApiario(Activity context, ArrayList<Integer> arrayApiarios) {
        super(context, R.layout.row_item, arrayApiarios);
        this.context = context;
        this.arrayApiarios = arrayApiarios;

        //this.imageId = imageId;
    }
    @Override
    public int getCount(){
        return arrayApiarios.size();
    }
    @Override
    public Object getItem(int i) {
        return arrayApiarios.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView == null){
            row = inflater.inflate(R.layout.row_apiario, null, true);
        }else{
            imgEliminar = (ImageButton) row.findViewById(R.id.imgEliminar);
            TextView textViewTitulo = (TextView) row.findViewById(R.id.textViewTitulo);
            TextView textViewNombre = (TextView) row.findViewById(R.id.nombreApiarioText);
            TextView textViewNumero = (TextView) row.findViewById(R.id.numeroApiarioText);
            TextView textViewRendimiento = (TextView) row.findViewById(R.id.rendimientoApiarioText);

            String titulo = "Apiario N°"+(position+1);
            textViewTitulo.setText(titulo);
            String nombre = ""+A1.nombresApiarios.get(position);
            textViewNombre.setText(nombre);
            String numeroA = ""+ A1.numApiarios.get(position);
            textViewNumero.setText(numeroA);
            String rend = ""+A1.rendApiarios.get(position);
            textViewRendimiento.setText(rend);

            imgEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    A1.nombresApiarios.remove(position);
                    A1.numApiarios.remove(position);
                    A1.rendApiarios.remove(position);

                    A1.apiariosCont.remove(position);

                    notifyDataSetChanged();
                }
            });

        }
        return row;
    }
}