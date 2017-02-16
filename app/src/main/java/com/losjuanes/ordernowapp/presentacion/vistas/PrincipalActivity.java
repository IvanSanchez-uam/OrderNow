package com.losjuanes.ordernowapp.presentacion.vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.losjuanes.ordernowapp.R;

public class PrincipalActivity extends AppCompatActivity {
    TextView tvNumOrden;
    int numOrden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        tvNumOrden = (TextView) findViewById(R.id.tvNumOrden);

        //Obtenemos los datos que envia la actividad NumPersonasActivity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        numOrden = bundle.getInt("numOrden");
        tvNumOrden.setText("Orden: " + numOrden);
    }
}
