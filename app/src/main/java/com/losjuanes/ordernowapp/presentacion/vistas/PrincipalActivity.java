package com.losjuanes.ordernowapp.presentacion.vistas;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.losjuanes.ordernowapp.R;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{
    int numOrden;

    RelativeLayout rl_conocenos, rl_menu, rl_orden,
                    rl_necesitasAlgo, rl_sucursales, rl_quejasYsugerencias;

    ControladorMuestraMenu controladorMenu;

    Activity principalActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        controladorMenu = new ImplementacionControladorMuestraMenu();

        //Obtenemos el numero de orden que envia la actividad NumPersonasActivity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        numOrden = bundle.getInt("numOrden");

        rl_conocenos = (RelativeLayout) findViewById(R.id.rl_conocenos);
        rl_menu = (RelativeLayout) findViewById(R.id.rl_menu);
        rl_orden = (RelativeLayout) findViewById(R.id.rl_orden);
        rl_necesitasAlgo = (RelativeLayout) findViewById(R.id.rl_necesitasAlgo);
        rl_sucursales = (RelativeLayout) findViewById(R.id.rl_sucursales);
        rl_quejasYsugerencias = (RelativeLayout) findViewById(R.id.rl_quejasYsugerencias);

        rl_conocenos.setOnClickListener(this);
        rl_menu.setOnClickListener(this);
        rl_orden.setOnClickListener(this);
        rl_necesitasAlgo.setOnClickListener(this);
        rl_sucursales.setOnClickListener(this);
        rl_quejasYsugerencias.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_conocenos:
                Toast.makeText(principalActivity, "1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_menu:
                Toast.makeText(principalActivity, "2", Toast.LENGTH_SHORT).show();
                controladorMenu.iniciaActivity(principalActivity, MenuActivity.class);
                break;
            case R.id.rl_orden:
                Toast.makeText(principalActivity, "3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_necesitasAlgo:
                Toast.makeText(principalActivity, "4", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_sucursales:
                Toast.makeText(principalActivity, "5", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_quejasYsugerencias:
                Toast.makeText(principalActivity, "6", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
