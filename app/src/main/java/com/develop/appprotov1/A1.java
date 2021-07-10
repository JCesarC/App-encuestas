package com.develop.appprotov1;

import android.app.DatePickerDialog;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.develop.appprotov1.Date.DatePickerFragment;
import com.develop.appprotov1.Date.GridViewApiario;
import com.develop.appprotov1.io.FormDialogFragment;
import com.develop.appprotov1.io.FormDialogListener;

import java.util.ArrayList;
import java.util.Calendar;




public class A1 extends AppCompatActivity implements FormDialogListener {

    Button btnAdd, btnSiguienteA1;
    LinearLayout layoutApiarios;
    EditText res11, res12, res13, res14, res15, res16, res17, res18, res19;
    private static ArrayList<EditText> edits = new ArrayList<>();
    private String titulo = "A1";
    private String Apiarios  = "APIARIOS";
    private int yearSelect, monthSelect, daySelect;
    private int yearCurrent, monthCurrent, dayCurrent;
    public static ArrayList<String> nombresApiarios = new ArrayList<>();
    public static ArrayList<String> numApiarios = new ArrayList<>();
    public static ArrayList<String> rendApiarios = new ArrayList<>();
    public static ArrayList<Integer> apiariosCont = new ArrayList<>();
    public static ArrayList<String> arrayA1 = new ArrayList<>();
    public static ListView listView;
    private int apiCont = 0;
    private static String[] arrayTitulos = {
            "Nombre del apiario: ",
            "N° de colmenas: ",
            "Rendimiento/colmena: "
    };

    private ArrayList<TextView> arrayTViews = new ArrayList<>();
    private ArrayList<String > arrayImagenes = new ArrayList<>();

    GridViewApiario gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1);
        
        res11 = findViewById(R.id.res11);
        res12 = findViewById(R.id.res12);
        res13 = findViewById(R.id.res13);
        res14 = findViewById(R.id.res14);
        res15 = findViewById(R.id.res15);
        res16 = findViewById(R.id.res16);
        res17 = findViewById(R.id.res17);
        res18 = findViewById(R.id.res18);
        res19 = findViewById(R.id.res19);
        edits.add(res11);
        edits.add(res12);
        edits.add(res13);
        edits.add(res14);
        edits.add(res15);
        edits.add(res16);
        //edits.add(res17);
        //edits.add(res18);
        edits.add(res19);
        arrayImagenes = (ArrayList<String>) getIntent().getSerializableExtra("arrayImagenes");

        layoutApiarios = findViewById(R.id.layoutApiarios);
        listView = findViewById(R.id.listadoViewApiarios);
        btnSiguienteA1 = findViewById(R.id.btnSiguienteA1);

        final Calendar calendario = Calendar.getInstance();
        yearCurrent = calendario.get(Calendar.YEAR);
        monthCurrent = calendario.get(Calendar.MONTH);
        dayCurrent = calendario.get(Calendar.DAY_OF_MONTH);
        btnAdd = findViewById(R.id.btnAdd);

        refreshEditText(yearCurrent, monthCurrent, dayCurrent);

        gridView = new GridViewApiario(this, apiariosCont);
        listView.setAdapter(gridView);


        res11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialogoFecha = new DatePickerDialog(A1.this, listenerDatePicker, yearCurrent, monthCurrent, dayCurrent);
                dialogoFecha.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormDialogFragment form = FormDialogFragment.newInstance("", 1, "");
                form.show(getSupportFragmentManager(), FormDialogFragment.TAG);
            }
        });
        btnSiguienteA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(A1.this, A2.class);
                //startActivity(intent);
                siguiente();
            }
        });
    }



    private DatePickerDialog.OnDateSetListener listenerDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yearSelect = year;
            monthSelect = month;
            daySelect = dayOfMonth;

            refreshEditText(yearSelect, monthSelect, daySelect);
        }
    };

    private void refreshEditText(int year, int month, int day){
        month+=1;
        String m = "";
        if(month<10){
            m = "0"+month;
        }else{
            m = month+"";
        }
        String fecha = year+"-"+(m)+"-"+day;
        System.out.println(fecha+"-..........................");
        res11.setText(fecha);

    }

    @Override
    public void add(String nombre, int numero, String rendimiento) {
        System.out.println("------------------------------------------");
        System.out.println("nombre: "+nombre);
        System.out.println("numero: "+numero);
        System.out.println("rendimiento: "+rendimiento);
        System.out.println("------------------------------------------");

        addApiario(nombre, numero, rendimiento);
    }

    private void addApiario(String nombre, int numero, String rendimiento) {
        nombresApiarios.add(nombre);
        numApiarios.add(""+numero);
        rendApiarios.add(rendimiento);

        apiariosCont.add(0);

       // GridViewApiario gridView = new GridViewApiario(this, apiariosCont);
        listView.setAdapter(gridView);
    }
    private void siguiente(){
        if(apiariosCont.size()>=1){

            arrayA1 = new ArrayList<>();

            if(arrayImagenes.size()<10){
                int arr = arrayImagenes.size();
                System.out.println("cantidad de imagnes o linea de imagenes: "+arr);
                int c = 10-arr;
                for(int i= 0; i<c; i++){
                    String resp = "-";
                    arrayImagenes.add(resp);
                }
            }

            arrayA1.addAll(arrayImagenes);
            arrayA1.add(titulo);
            //comprobar Array
            for (int i=0; i<arrayA1.size(); i++){
                System.out.println("---------------------------------------------------------------");
                System.out.println("array 1 : "+i+" : "+arrayA1.get(i));
            }

            for(int i= 0; i<edits.size(); i++){
                String respuesta = edits.get(i).getText().toString();
                if(respuesta.equals("")){
                    respuesta = "-";
                }
                System.out.println("---------------------------------------------------");
                System.out.println(respuesta);
                arrayA1.add(respuesta);
            }
            //Apiarios
            arrayA1.add(""+apiariosCont.size());
            System.out.println(""+apiariosCont.size());
            for(int i =0; i<apiariosCont.size(); i++){
                arrayA1.add(nombresApiarios.get(i));
                arrayA1.add(numApiarios.get(i));
                arrayA1.add(rendApiarios.get(i));
            }

            Intent intent = new Intent(this, A2.class);
            intent.putExtra("arrayA1", arrayA1);
            startActivity(intent);
        }else{
            Toast.makeText(A1.this, "Ingrese un apiario como minimo", Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public void onBackPressed() {
        finish();
    }
}