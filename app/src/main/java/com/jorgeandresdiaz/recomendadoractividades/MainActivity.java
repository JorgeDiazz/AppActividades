package com.jorgeandresdiaz.recomendadoractividades;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jorgeandresdiaz.recomendadoractividades.model.Actividad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private List<Actividad> listaActividades;
    private Actividad actividadActual;

    @BindView(R.id.tv_nombre_actividad)
    TextView tvNombreActividad;

    @BindView(R.id.iv_imagen_actividad)
    ImageView ivImagenActividad;

    @BindView(R.id.btn_no_me_gusta)
    Button btnNoMeGusta;

    @BindView(R.id.btn_me_gusta)
    Button btnMeGusta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        inicializarVista();
        inicializarActividades();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void inicializarVista() {
        ocultarBotones();
    }

    private void ocultarBotones() {
        btnNoMeGusta.setVisibility(View.INVISIBLE);
        btnMeGusta.setVisibility(View.INVISIBLE);
    }

    private void inicializarActividades() {
        listaActividades = new ArrayList<>();
        listaActividades.add(new Actividad("Dormir", R.drawable.imagen_dormir));
        listaActividades.add(new Actividad("Trotar", R.drawable.imagen_trotar));
        listaActividades.add(new Actividad("Videojuegos", R.drawable.imagen_videojuegos));
    }

    @OnClick(R.id.btn_generar_actividad)
    void clickBotonGenerarActividad() {
        mostrarBotones();
        actividadActual = obtenerMejorActividad();
        mostrarInformacionActividad();
    }

    private Actividad obtenerMejorActividad() {

        // me gustó: 5
        // no me gustó: 4
        // 1

        int maximaCalifacion = Integer.MIN_VALUE;
        Actividad mejorActividad = null;
        for (Actividad actividad : listaActividades) {
            if (actividad.getMeGusta() - actividad.getNoMeGusta() > maximaCalifacion) {
                mejorActividad = actividad;
                maximaCalifacion = mejorActividad.getMeGusta() - mejorActividad.getNoMeGusta();
            }
        }

        return mejorActividad;
    }

    private void mostrarInformacionActividad() {
        tvNombreActividad.setText(actividadActual.getNombre());
        ivImagenActividad.setImageDrawable(getDrawable(actividadActual.getIdImagen()));
    }

    private Actividad obtenerActividadAlAzar() {
        Random random = new Random();

        int numeroActividad = random.nextInt(3); // 0, 1, 2

        return listaActividades.get(numeroActividad);
    }

    private void mostrarBotones() {
        btnNoMeGusta.setVisibility(View.VISIBLE);
        btnMeGusta.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_no_me_gusta)
    void clickBotonNoMeGusta() {
        int noMeGusta = actividadActual.getNoMeGusta();
        actividadActual.setNoMeGusta(noMeGusta + 1);
    }

    @OnClick(R.id.btn_me_gusta)
    void clickBotonMeGusta() {
        int meGusta = actividadActual.getMeGusta();
        actividadActual.setMeGusta(meGusta + 1);
    }

    @OnClick(R.id.btn_ver_estadisticas)
    void clickBotonVerEstadisticas(){
        Intent intent = new Intent(this, EstadisticasActivity.class);
        intent.putExtra("LISTA_ACTIVIDADES", (Serializable) listaActividades);
        startActivityForResult(intent, 123);
    }


    @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == 123){
                String mensaje = data.getStringExtra("MENSAJE");
                Log.i("MENSAJE A ESPERAR >>> ", mensaje);
            }

    }
}
