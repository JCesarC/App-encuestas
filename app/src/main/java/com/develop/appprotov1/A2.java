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

public class A2 extends AppCompatActivity {
    Button btnAtrasA2, btnSiguienteA2;
    RadioGroup rGroup21;
    EditText resOtro21, res22, res23, res24, res25, res26, res27, res28;
    private ArrayList<EditText> editsA2 = new ArrayList<>();
    private String res21;
    private String tituloA2 = "A2";

    public static ArrayList<String> arrayA1 = new ArrayList<>();
    public static ArrayList<String> arrayA2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2);

        rGroup21 = findViewById(R.id.rGroup21);
        resOtro21 = findViewById(R.id.resOtro21);
        res21="";
        res22 = findViewById(R.id.res22);
        res23 = findViewById(R.id.res23);
        res24 = findViewById(R.id.res24);
        res25 = findViewById(R.id.res25);
        res26 = findViewById(R.id.res26);
        res27 = findViewById(R.id.res27);
        res28 = findViewById(R.id.res28);

        btnAtrasA2 = findViewById(R.id.btnBackA2);
        btnSiguienteA2 = findViewById(R.id.btnSiguienteA2);

        btnAtrasA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSiguienteA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(A2.this, A3.class);
                siguiente();
                //startActivity(intent);
            }
        });

        arrayA1 = (ArrayList<String>) getIntent().getSerializableExtra("arrayA1");
        editsA2.add(res22);
        editsA2.add(res23);
        editsA2.add(res24);
        editsA2.add(res25);
        editsA2.add(res26);
        editsA2.add(res27);
        editsA2.add(res28);

        rGroup21.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = rGroup21.findViewById(checkedId);
                RadioButton radioB = (RadioButton) rGroup21.findViewById(checkedId);
                int index = rGroup21.indexOfChild(radioButton);
                switch (index){
                    case 0:
                        res21 = radioB.getText()+"";
                        System.out.println("-------------------------------------------");
                        System.out.println("Se presionó un radio Buton "+res21);
                        resOtro21.setClickable(false);
                        resOtro21.setFocusable(false);
                        break;
                    case 1:
                        res21 = radioB.getText()+"";
                        System.out.println("-------------------------------------------");
                        System.out.println("Se presionó un radio Buton "+res21);
                        resOtro21.setClickable(false);
                        resOtro21.setFocusable(false);
                        break;
                    case 2:
                        res21 = radioB.getText()+"";
                        System.out.println("-------------------------------------------");
                        System.out.println("Se presionó un radio Buton "+res21);
                        resOtro21.setClickable(false);
                        resOtro21.setFocusable(false);
                        break;

                    case 3:
                        String buttonR = radioB.getText()+"";
                        System.out.println("-------------------------------------------");
                        System.out.println("Se presionó un radio Buton "+buttonR);

                        resOtro21.setClickable(true);
                        resOtro21.setFocusable(true);
                        resOtro21.setFocusableInTouchMode(true);
                        resOtro21.setEnabled(true);
                        res21 = null;

                        break;

                }
            }
        });


    }

    private void siguiente(){
        arrayA2 = new ArrayList<>();
        arrayA2.addAll(arrayA1);
        arrayA2.add(tituloA2);
        if(res21!=null){
            if(res21.equals("")){
                res21 = "-";
            }
        }else{
            res21 = resOtro21.getText().toString();
        }
        arrayA2.add(res21);
        for(int i = 0; i<editsA2.size(); i++){
            String respuesta = editsA2.get(i).getText().toString();
            if(respuesta.equals("")){
                respuesta = "-";
            }
            arrayA2.add(respuesta);

        }

        for (int i=0; i<arrayA2.size(); i++){
            System.out.println("---------------------------------------------------------------");
            System.out.println("respuesta: "+i+" : "+arrayA2.get(i));
        }

        Intent intent = new Intent(this, A3.class);
        intent.putExtra("arrayA2", arrayA2);
        startActivity(intent);

    }
    @Override
    public void onBackPressed() {
        finish();
    }
}