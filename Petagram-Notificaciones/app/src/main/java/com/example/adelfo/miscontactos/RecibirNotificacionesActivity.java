package com.example.adelfo.miscontactos;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.adelfo.miscontactos.restAPI.EndpointsApi;
import com.example.adelfo.miscontactos.restAPI.Model.UsuarioResponse2;
import com.example.adelfo.miscontactos.restAPI.adapter.RestApiAdapter;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.adelfo.miscontactos.ConfigurarCuenta.context;

public class RecibirNotificacionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibir_notificaciones);
    }

}
