package com.develop.appprotov1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class A5 extends AppCompatActivity {
    
    Button btnBackA5, btnSiguienteA5;
    EditText res61, res62, res63, res64, res65, res66;
    private String titulo = "A5";
    private ArrayList<String> arrayA4 = new ArrayList<>();
    private ArrayList<String> arrayA5 = new ArrayList<>();
    private ArrayList<EditText> editsA5 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a5);

        arrayA4 = (ArrayList<String>) getIntent().getSerializableExtra("arrayA4");

        btnBackA5 = findViewById(R.id.btnBackA5);
        btnSiguienteA5 = findViewById(R.id.btnSiguienteA5);
        res61 = findViewById(R.id.res61);
        res62 = findViewById(R.id.res62);
        res63 = findViewById(R.id.res63);
        res64 = findViewById(R.id.res64);
        res65 = findViewById(R.id.res65);
        res66 = findViewById(R.id.res66);
        editsA5.add(res61);
        editsA5.add(res62);
        editsA5.add(res63);
        editsA5.add(res64);
        editsA5.add(res65);
        editsA5.add(res66);


        btnBackA5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSiguienteA5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguiente();
            }
        });
    }

    private void siguiente(){
        arrayA5 = new ArrayList<>();
        arrayA5.addAll(arrayA4);
        //comprobar Array
        arrayA5.add(titulo);
        for (int i=0; i<arrayA5.size(); i++){
            System.out.println("---------------------------------------------------------------");
            System.out.println("respuesta: "+i+" : "+arrayA5.get(i));
        }

        for(int i=0; i<editsA5.size(); i++){
            String respuesta = editsA5.get(i).getText().toString();
            respuesta = validateString(respuesta);
            arrayA5.add(respuesta);
        }

        Intent intent = new Intent(this, A6.class);
        intent.putExtra("arrayA5", arrayA5);
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