package com.losjuanes.ordernowapp.presentacion.vistas;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.losjuanes.ordernowapp.R;
import com.losjuanes.ordernowapp.presentacion.controladores.ControladorCuenta;
import com.losjuanes.ordernowapp.presentacion.controladores.ControladorMuestraMenu;
import com.losjuanes.ordernowapp.presentacion.controladores.ImplementacionControladorCuenta;
import com.losjuanes.ordernowapp.presentacion.controladores.ImplementacionControladorMuestraMenu;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout rl_conocenos, rl_menu, rl_orden,
                    rl_necesitasAlgo, rl_sucursales, rl_quejasYsugerencias;

    private ControladorMuestraMenu controladorMenu;
    private ControladorCuenta controladorCuenta;

    private Activity principalActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        controladorMenu = new ImplementacionControladorMuestraMenu();
        controladorCuenta = new ImplementacionControladorCuenta();

        rl_conocenos = (RelativeLayout) findViewById(R.id.rl_conocenos);
        rl_menu = (RelativeLayout) findViewById(R.id.rl_menu);
        rl_orden = (RelativeLayout) findViewById(R.id.rl_cuenta);
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
                break;
            case R.id.rl_menu:
                controladorMenu.iniciaActivity(principalActivity, MenuActivity.class);
                break;
            case R.id.rl_cuenta:
                controladorCuenta.iniciaActivity(principalActivity,CuentaActivity.class);
                break;
            case R.id.rl_necesitasAlgo:
                break;
            case R.id.rl_sucursales:
                break;
            case R.id.rl_quejasYsugerencias:
                break;
        }
    }
}
