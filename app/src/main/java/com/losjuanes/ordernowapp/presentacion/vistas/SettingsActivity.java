package com.losjuanes.ordernowapp.presentacion.vistas;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.losjuanes.ordernowapp.R;
import com.losjuanes.ordernowapp.presentacion.controladores.ControladorAsignarMesa;
import com.losjuanes.ordernowapp.presentacion.controladores.ImplementacionControladorAsignarMesa;

import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {

    private EditText etNumMesa;
    private Button btnGuardarNumMesa;
    private ControladorAsignarMesa controlador;
    private Activity settingsActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etNumMesa = (EditText) findViewById(R.id.etNumMesa);
        btnGuardarNumMesa = (Button) findViewById(R.id.btnGuardarNumMesa);
        controlador = new ImplementacionControladorAsignarMesa();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("PreferenciasMesa", MODE_PRIVATE);

        int verificaNumMesa = pref.getInt("num_mesa", 0);

        if(verificaNumMesa != 0){
            controlador.iniciaActivity(SettingsActivity.this, Bienvenida.class);
            finish();
        }


        btnGuardarNumMesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numMesa = "";
                AsignarMesa asignarMesa = new AsignarMesa();

                numMesa = etNumMesa.getText().toString();
                asignarMesa.execute(new String[]{numMesa});

                /*AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        String numMesa = "";
                        int numeroMesa = 0;
                        int mesaAsignada; // Codigo de respuesta del servidor (200 la mesa fue agregada,
                                          //                                   404 la mesa ya existe).

                        numMesa = etNumMesa.getText().toString();
                        if (numMesa.compareTo("") != 0){
                            numeroMesa = Integer.parseInt(numMesa);
                            try {
                                mesaAsignada = controlador.asignar_numeroMesa(numeroMesa, settingsActivity);
                                Log.d("MesaAsignada",mesaAsignada + "");
                                if(mesaAsignada == 200){
                                    finish();
                                }else if(mesaAsignada == 404){
                                    msjRespuestaApiREST = getResources().getString(R.string.msj_numMesaNoAsignado);
                                    Log.d("Respuesta","NumMesa ya existe");
                                }else {
                                    msjRespuestaApiREST = getResources().getString(R.string.msj_falloConexion);
                                    Log.d("Respuesta", "Falló la conexión");
                                }
                            } catch (IOException e) {
                                msjRespuestaApiREST = getResources().getString(R.string.msj_falloConexion);
                                Log.d("Respuesta","Excepción de conexión con el Api REST");
                            }
                        }
                    }
                });
                if(msjRespuestaApiREST.compareTo("") != 0) {
                    Toast.makeText(settingsActivity, msjRespuestaApiREST, Toast.LENGTH_SHORT).show();
                    msjRespuestaApiREST = "";
                }*/
            }
        });
    }

    private class AsignarMesa extends AsyncTask<String,Void,String>{

        @Override
        protected void onPostExecute(String msjRespuestaApiREST) {
            if (msjRespuestaApiREST.compareTo("") != 0)
                Toast.makeText(settingsActivity, msjRespuestaApiREST, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            String numMesa = "";
            int numeroMesa = 0;
            int mesaAsignada; // Codigo de respuesta del servidor (200 la mesa fue agregada,
            //                                   404 la mesa ya existe).
            String msjRespuestaApiREST = "";

            if (params.length > 0){
                numMesa = params[0];
            }

            if (numMesa.compareTo("") != 0){
                numeroMesa = Integer.parseInt(numMesa);
                try {
                    mesaAsignada = controlador.asignar_numeroMesa(numeroMesa, settingsActivity);
                    Log.d("MesaAsignada",mesaAsignada + "");
                    if(mesaAsignada == 200){
                        finish();
                    }else if(mesaAsignada == 404){
                        msjRespuestaApiREST = getResources().getString(R.string.msj_numMesaNoAsignado);
                        Log.d("Respuesta","NumMesa ya existe");
                    }
                } catch (IOException e) {
                    msjRespuestaApiREST = getResources().getString(R.string.msj_falloConexion);
                    Log.d("Respuesta","Excepción de conexión con el Api REST");
                }
            }else{
                msjRespuestaApiREST = getResources().getString(R.string.msj_ingresarNumMesa);
            }

            return msjRespuestaApiREST;
        }
    }




}
