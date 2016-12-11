package com.example.adelfo.miscontactos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adelfo.miscontactos.adapter.PageAdapter;
import com.example.adelfo.miscontactos.fragment.FragmentPerfil;
import com.example.adelfo.miscontactos.fragment.RecyclerViewFragment;
import com.example.adelfo.miscontactos.fragment.RecyclerViewFragmentUsuarioPrincipal;
import com.example.adelfo.miscontactos.restAPI.EndpointsApi;
import com.example.adelfo.miscontactos.restAPI.Model.UsuarioResponse;
import com.example.adelfo.miscontactos.restAPI.Model.UsuarioResponse2;
import com.example.adelfo.miscontactos.restAPI.adapter.RestApiAdapter;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.adelfo.miscontactos.ConfigurarCuenta.context;

public class MainActivity extends AppCompatActivity {

    public static Activity fa;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
public static Context mainActivityContext;
    SharedPreferences miPrefereciaCompartida0 = null;
    public static String usuario_nombre = "perritocheno";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityContext = this;
        fa = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        setUpViewPager();

        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setLogo(R.drawable.dog_footprint_filled_50);


        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }





    }

    @Override
    protected void onStart() {
        super.onStart();
        miPrefereciaCompartida0= MainActivity.mainActivityContext.getSharedPreferences("UsuariosPetagram", Context.MODE_PRIVATE);
        usuario_nombre = miPrefereciaCompartida0.getString("usuario_nombre", "perritocheno");
        setUpViewPager();
    }

    public   ArrayList<Fragment> agregarFragments(){
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(new RecyclerViewFragment());
        fragments.add(new RecyclerViewFragmentUsuarioPrincipal());
        return fragments;
    }

    public void setUpViewPager(){
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), agregarFragments()));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.home_48);
        tabLayout.getTabAt(1).setIcon(R.drawable.year_of_dog_50);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mContacto) {
            Intent intent = new Intent(this, ContactarActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.mAcercaDe){
            Intent intent = new Intent(this, AcercaDeActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.mConfigurarCuenta) {
            Intent intent = new Intent(this, ConfigurarCuenta.class);
            startActivity(intent);
        }

        if(item.getItemId() == R.id.mRecibirNotificaciones) {
            enviarToken();
        }

        return super.onOptionsItemSelected(item);
    }

    public void guardarUsuarioPetagram(View v){
        EditText etAgregarUsuario = (EditText) findViewById(R.id.etAgregarUsuario);
        String usuario = etAgregarUsuario.getText().toString();

        SharedPreferences miPrefereciaCompartida = getSharedPreferences("UsuariosPetagram", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = miPrefereciaCompartida.edit();
        editor.putString(usuario, usuario);
        editor.commit();

        Toast.makeText(MainActivity.this, "Se ha guardado el usuario "+usuario+".", Toast.LENGTH_SHORT).show();
        etAgregarUsuario.setText("");
    }


    public void enviarToken(){
        String token = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        String usuario_id = "", nombre_usuario_instagram = "";

        try{
            SharedPreferences miPrefereciaCompartida = context.getSharedPreferences("UsuariosPetagram", Context.MODE_PRIVATE);
            usuario_id = miPrefereciaCompartida.getString("usuario_id", "4179481767");

            nombre_usuario_instagram = miPrefereciaCompartida.getString("usuario_nombre", "perritocheno");

        }catch (Exception err){
            usuario_id = "4179481767";
            nombre_usuario_instagram = "perritocheno";
        }

        //if(usuario_id == null || usuario_id.isEmpty())


        RestApiAdapter restApiAdapter = new RestApiAdapter();
        EndpointsApi endpoints = restApiAdapter.establecerConexionRestAPI();
        Call<UsuarioResponse2> usuarioResponseCall = endpoints.registrarTokenID(token, usuario_id);


        usuarioResponseCall.enqueue(new Callback<UsuarioResponse2>() {
            @Override
            public void onResponse(Call<UsuarioResponse2> call, Response<UsuarioResponse2> response) {
                UsuarioResponse2 usuarioResponse2 = response.body();
                Log.d("ID FIREBASE: ", usuarioResponse2.getId());
                Log.d("ID DISPOSITIVO: ", usuarioResponse2.getId_dispositivo());
                Log.d("ID USUARIO INSTAGRAM: ", usuarioResponse2.getId_usuario_instagram());

            }

            @Override
            public void onFailure(Call<UsuarioResponse2> call, Throwable t) {

            }
        });
    }

}
