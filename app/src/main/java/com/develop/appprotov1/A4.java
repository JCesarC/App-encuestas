package com.develop.appprotov1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class A4 extends AppCompatActivity {
    Button btnBackA4, btnSiguienteA4;
    EditText res41, res51, res52;
    ArrayList<EditText> editsA4 = new ArrayList<>();
    ArrayList<String> arrayA3 = new ArrayList<>();
    ArrayList<String> arrayA4 = new ArrayList<>();
    private String titulo = "A4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a4);

        arrayA3 = (ArrayList<String>) getIntent().getSerializableExtra("arrayA3");

        btnBackA4 = findViewById(R.id.btnBackA4);
        btnSiguienteA4 = findViewById(R.id.btnSiguienteA4);
        res41 = findViewById(R.id.res41);
        res51 = findViewById(R.id.res51);
        res52 = findViewById(R.id.res52);
        editsA4.add(res41);
        editsA4.add(res51);
        editsA4.add(res52);

        btnBackA4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSiguienteA4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguiente();
            }
        });
    }

    private void siguiente(){
        arrayA4 = new ArrayList<>();
        arrayA4.addAll(arrayA3);
        //comprobar Array
        arrayA4.add(titulo);
        for (int i=0; i<arrayA4.size(); i++){
            System.out.println("---------------------------------------------------------------");
            System.out.println("respuesta: "+i+" : "+arrayA4.get(i));
        }

        for(int i=0;i<editsA4.size();i++){
            String respuesta = editsA4.get(i).getText().toString();
            respuesta = validateString(respuesta);
            arrayA4.add(respuesta);
        }
        Intent intent = new Intent(this, A5.class);
        intent.putExtra("arrayA4", arrayA4);
        startActivity(intent);
    }
    private String validateString(String value){
        if(value.equals("")){
            value = "-";
        }
        return value;
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}