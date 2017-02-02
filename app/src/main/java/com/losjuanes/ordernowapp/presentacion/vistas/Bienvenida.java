package com.losjuanes.ordernowapp.presentacion.vistas;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.losjuanes.ordernowapp.R;

public class Bienvenida extends AppCompatActivity  implements View.OnClickListener{
    private TextView tvNumPersonas;
    private Button btnContinuar;
    private FloatingActionButton btnMas, btnMenos;

    private int numPersonas = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        tvNumPersonas = (TextView) findViewById(R.id.tv_numPersonas);
        btnMas = (FloatingActionButton) findViewById(R.id.btnMas);
        btnMenos = (FloatingActionButton) findViewById(R.id.btnMenos);
        btnContinuar = (Button) findViewById(R.id.btnContinuar);

        btnMas.setOnClickListener(this);
        btnMenos.setOnClickListener(this);
        btnContinuar.setOnClickListener(this);
    }

    /**
     * Método de la interface OnClickListener
     * @param v, vista que acciona el listener
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnMas: //Botón Mas
                numPersonas += 1;
                tvNumPersonas.setText(String.valueOf(numPersonas));
                break;
            case R.id.btnMenos: //Botón Menos

                if(numPersonas > 1) {
                    numPersonas -= 1;
                    tvNumPersonas.setText(String.valueOf(numPersonas));
                }
                break;
            case R.id.btnContinuar: //Botón Continuar
                Toast.makeText(this, "CONTINUAR", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
