package com.jorgeandresdiaz.recomendadoractividades;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jorgeandresdiaz.recomendadoractividades.model.Actividad;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EstadisticasActivity extends AppCompatActivity {

    private List<Actividad> listaActividades;

    @BindView(R.id.tv_contenido_actividad_que_gusta)
    TextView tvContenidoActividadQueGusta;

    @BindView(R.id.tv_contenido_actividad_que_no_gusta)
    TextView tvContenidoActividadQueNoGusta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        ButterKnife.bind(this);

        inicializarPropiedades();
        inicializarVista();
    }

    private void inicializarVista() {
        inicializarMejorActividad();
        inicializarPeorActividad();
    }


    private void inicializarMejorActividad() {
        Actividad mejorActividad = obtenerMejorActividad();
        tvContenidoActividadQueGusta.setText(mejorActividad.getNombre());
    }


    private void inicializarPeorActividad() {
        Actividad peorActividad = obtenerPeorActividad();
        tvContenidoActividadQueNoGusta.setText(peorActividad.getNombre());
    }

    private void inicializarPropiedades() {
        listaActividades = (List<Actividad>) getIntent().getSerializableExtra("LISTA_ACTIVIDADES");
    }

    private Actividad obtenerMejorActividad() {
        int mejorPuntacion = Integer.MIN_VALUE;
        Actividad mejorActividad = null;
        for (Actividad actividad : listaActividades) {
            if (actividad.getMeGusta() > mejorPuntacion) {
                mejorActividad = actividad;
                mejorPuntacion = mejorActividad.getMeGusta();
            }
        }

        return mejorActividad;
    }

    private Actividad obtenerPeorActividad() {
        int menorPuntacion = Integer.MIN_VALUE;
        Actividad peorActividad = null;
        for (Actividad actividad : listaActividades) {
            if (actividad.getNoMeGusta() > menorPuntacion) {
                peorActividad = actividad;
                menorPuntacion = peorActividad.getNoMeGusta();
            }
        }

        return peorActividad;
    }

    @OnClick(R.id.btn_aceptar)
    void clickBotonAceptar() {
        Intent intent = new Intent();
        intent.putExtra("MENSAJE", "ESTE ES UN MENSAJE");
        setResult(123, intent);

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
