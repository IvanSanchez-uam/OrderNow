package com.losjuanes.ordernowapp.presentacion.vistas;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.losjuanes.ordernowapp.R;
import com.losjuanes.ordernowapp.presentacion.controladores.Controlador;
import com.losjuanes.ordernowapp.presentacion.controladores.ImplementacionControlador;

public class SettingsActivity extends AppCompatActivity {

    private EditText etNumMesa;
    private Button btnGuardarNumMesa;
    private Controlador controlador;
    private Activity settingsActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etNumMesa = (EditText) findViewById(R.id.etNumMesa);
        btnGuardarNumMesa = (Button) findViewById(R.id.btnGuardarNumMesa);
        controlador = new ImplementacionControlador();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("PreferenciasMesa", MODE_PRIVATE);
        int verificaNumMesa = pref.getInt("num_mesa", 0);


        Log.i("numMesa", "antes de verifica");
        if(verificaNumMesa != 0){
            controlador.iniciaActivity(SettingsActivity.this, Bienvenida.class);
            finish();
        }


        btnGuardarNumMesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numMesa = "";
                int numeroMesa = 0;
                boolean mesaAsignada = false;

                numMesa = etNumMesa.getText().toString();
                if (numMesa.compareTo("") != 0){
                    numeroMesa = Integer.parseInt(numMesa);
                    mesaAsignada = controlador.asigna_numeroMesa(numeroMesa, settingsActivity);

                    if(mesaAsignada){
                        finish();
                    }else{
                        Toast.makeText(SettingsActivity.this, getResources().getString(R.string.msj_numMesaNoAsignado), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
