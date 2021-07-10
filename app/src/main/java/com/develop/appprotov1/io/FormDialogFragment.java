package com.develop.appprotov1.io;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.develop.appprotov1.R;

public class FormDialogFragment extends DialogFragment {
    public static final String TAG = FormDialogFragment.class.getSimpleName();

    private static final String ARG_NOMBRE ="ARG_NOMBRE";
    private static final String ARG_NUMERO ="ARG_NUMERO";
    private static final String ARG_RENDIMIENTO ="ARG_RENDIMIENTO";

    private TextInputLayout textInputLayoutNombre, textInputLayoutNumero, textInputLayoutRendimiento;
    private EditText nombreText;
    private EditText numeroText;
    private EditText rendimientoText;

    private FormDialogListener listener;

    public static FormDialogFragment newInstance(String nombre, int numero, String rendimiento){
        Bundle args = new Bundle();
        args.putString(ARG_NOMBRE, nombre);
        args.putInt(ARG_NUMERO, numero);
        args.putString(ARG_RENDIMIENTO, rendimiento);

        FormDialogFragment frag = new FormDialogFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FormDialogListener){
            listener = (FormDialogListener) context;
        }else{
            throw new IllegalArgumentException("Context no es FormDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View content = LayoutInflater.from(getContext()).inflate(R.layout.fragment_form, null);
        setupContent(content);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

            alertDialog
                .setView(content)
                .setCancelable(true)
                .setNegativeButton("Cancelar", null)
                .setTitle("Añadir nuevo Apiario")
                .setPositiveButton("Añadir", null)
                .create();

            alertDialog.setView(content);
        //aSEGURARSE DE QUE SE MUESTRE EL TECLADO CON EL DIALOGO COMPLETO
        //alertDialog.getWindow().setSoftInputMode(
                //WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return alertDialog.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        Button possitiveButton = ((AlertDialog) getDialog()).getButton(Dialog.BUTTON_POSITIVE);
        possitiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    returnValues();
                    getDialog().dismiss();
                }
            }
        });
    }
    private boolean validate() {
        if (TextUtils.isEmpty(nombreText.getText())) {
            textInputLayoutNombre.setError("Inserte un nombre");
            textInputLayoutNombre.setErrorEnabled(true);
            return false;
        }
        if (TextUtils.isEmpty(numeroText.getText())) {
            textInputLayoutNumero.setError("Inserte un numero");
            textInputLayoutNumero.setErrorEnabled(true);
            return false;
        }
        if (TextUtils.isEmpty(rendimientoText.getText())) {
            textInputLayoutRendimiento.setError("Rellene este campo");
            textInputLayoutRendimiento.setErrorEnabled(true);
            return false;
        }
        return true;
    }

    private void returnValues(){
        listener.add(nombreText.getText().toString(), Integer.parseInt(numeroText.getText().toString()), rendimientoText.getText().toString());
        
    }
    private void setupContent(View content) {
        textInputLayoutNombre = content.findViewById(R.id.textInputLayoutNombre);
        textInputLayoutNumero = content.findViewById(R.id.textInputLayoutNumero);
        textInputLayoutRendimiento = content.findViewById(R.id.textInputLayoutRendimiento);
        
        nombreText = content.findViewById(R.id.nombreText);
        numeroText = content.findViewById(R.id.numeroText);
        rendimientoText = content.findViewById(R.id.rendimientoText);
        
        
        //nombreText.setText(ARG_NOMBRE);

        //nombreText.setSelection(getArguments().getString(ARG_NOMBRE).length());
        
        //numeroText.setText(getArguments().getString(ARG_NUMERO));
        //numeroText.setSelection(getArguments().getString(ARG_NUMERO).length());

        //rendimientoText.setText(getArguments().getString(ARG_RENDIMIENTO));
        //rendimientoText.setSelection(getArguments().getString(ARG_RENDIMIENTO).length());

        numeroText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    returnValues();
                    dismiss();
                    return true;
                }
                return false;
            }
        });


        nombreText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (nombreText.getVisibility() == View.VISIBLE) {
                    textInputLayoutNombre.setError(null);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //nothing here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //nothing here
            }
        });
        numeroText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (nombreText.getVisibility() == View.VISIBLE) {
                    textInputLayoutNumero.setError(null);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //nothing here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //nothing here
            }
        });
        rendimientoText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (nombreText.getVisibility() == View.VISIBLE) {
                    textInputLayoutRendimiento.setError(null);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //nothing here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //nothing here
            }
        });
    }
}
