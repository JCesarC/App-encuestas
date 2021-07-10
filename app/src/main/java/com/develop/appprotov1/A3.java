package com.develop.appprotov1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class A3 extends AppCompatActivity {
    Button btnAtrasA3, btnSiguienteA3;
    EditText res31, res32, res33, res34, resOtro355, res37, res38;
    String res35 = "", res36 = "";
    RadioGroup rGroup35, rGroup36;
    private String titulo = "A3";

    private ArrayList<EditText> editsA3 = new ArrayList<>();
    private ArrayList<String> arrayA2 = new ArrayList<>();
    private ArrayList<String >arrayA3 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a3);

        btnAtrasA3 = findViewById(R.id.btnBackA3);
        btnSiguienteA3 = findViewById(R.id.btnSiguienteA3);
        res31 = findViewById(R.id.res31);
        res32 = findViewById(R.id.res32);
        res33 = findViewById(R.id.res33);
        res34 = findViewById(R.id.res34);
        resOtro355 = findViewById(R.id.resOtro355);
        res37 = findViewById(R.id.res37);
        res38 = findViewById(R.id.res38);
        rGroup35 = findViewById(R.id.rGroup35);
        rGroup36 = findViewById(R.id.rGroup36);

        arrayA2 = (ArrayList<String>) getIntent().getSerializableExtra("arrayA2");
        editsA3.add(res31);
        editsA3.add(res32);
        editsA3.add(res33);
        editsA3.add(res34);




        btnAtrasA3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSiguienteA3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguiente();
            }
        });
        rGroup35.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = rGroup35.findViewById(checkedId);
                RadioButton radioB = (RadioButton) rGroup35.findViewById(checkedId);
                int index = rGroup35.indexOfChild(radioButton);
                switch (index){
                    case 0:
                        res35 = radioB.getText()+"";
                        System.out.println("-------------------------------------------");
                        System.out.println("Se presionó un radio Buton "+res35);
                        resOtro355.setClickable(false);
                        resOtro355.setFocusable(false);
                        break;
                    case 1:
                        res35 = radioB.getText()+"";
                        System.out.println("-------------------------------------------");
                        System.out.println("Se presionó un radio Buton "+res35);
                        resOtro355.setClickable(false);
                        resOtro355.setFocusable(false);
                        break;
                    case 2:
                        res35 = radioB.getText()+"";
                        System.out.println("-------------------------------------------");
                        System.out.println("Se presionó un radio Buton "+res35);
                        resOtro355.setClickable(false);
                        resOtro355.setFocusable(false);
                        break;

                    case 3:
                        String buttonR = radioB.getText()+"";
                        System.out.println("-------------------------------------------");
                        System.out.println("Se presionó un radio Buton "+buttonR);

                        resOtro355.setClickable(true);
                        resOtro355.setFocusable(true);
                        resOtro355.setFocusableInTouchMode(true);
                        resOtro355.setEnabled(true);
                        res35 = null;

                        break;

                }
            }
        });

        rGroup36.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioB = (RadioButton) rGroup36.findViewById(checkedId);
                res36 = radioB.getText()+"";
                System.out.println("-------------------------------------------");
                System.out.println("Se presionó un radio Buton "+res36);
            }
        });

    }
    private void siguiente(){
        arrayA3 = new ArrayList<>();
        arrayA3.addAll(arrayA2);
        arrayA3.add(titulo);

        for(int i=0;i<editsA3.size(); i++){
            String respuesta = editsA3.get(i).getText().toString();
            respuesta = validateString(respuesta);
            arrayA3.add(respuesta);
        }
        if(res35!= null){
            res35 = validateString(res35);
        }else{
            String respuesta = resOtro355.getText().toString();
            respuesta = validateString(respuesta);
            res35 = respuesta;
        }
        arrayA3.add(res35);
        res36 = validateString(res36);
        arrayA3.add(res36);

        String respuesta1 = res37.getText().toString();
        respuesta1 = validateString(respuesta1);
        arrayA3.add(respuesta1);

        String respuesta2 = res38.getText().toString();
        respuesta2 = validateString(respuesta2);
        arrayA3.add(respuesta2);

        Intent intent = new Intent(this, A4.class);
        intent.putExtra("arrayA3", arrayA3);
        startActivity(intent);
    }
    private String validateString(String validar){
        if(validar.equals("")){
            validar = "-";
            return validar;
        }else{
            return validar;
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}