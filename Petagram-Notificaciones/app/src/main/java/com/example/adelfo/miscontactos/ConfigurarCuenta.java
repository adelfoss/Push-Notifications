package com.example.adelfo.miscontactos;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Map;
import java.util.Set;
public class ConfigurarCuenta extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    public static Context context;

    SharedPreferences miPrefereciaCompartida0 = null;
    //Usuario por default, si borraran datos de la aplicacion y dejaran de existir sharedpreferences
    public static String usuario_nombre = "perritocheno";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_cuenta);

        context = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null)
            setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_configurar_cuenta));
        getSupportActionBar().setIcon(R.drawable.dog_footprint_filled_50);

    }

    public void guardarUsuarioPetagram(View v){
        EditText etAgregarUsuario = (EditText) findViewById(R.id.etAgregarUsuario);
        String usuario = etAgregarUsuario.getText().toString();

        SharedPreferences miPrefereciaCompartida = getSharedPreferences(getResources().getString(R.string.nombre_base_datos), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = miPrefereciaCompartida.edit();
        editor.putString(getResources().getString(R.string.usuario_nombre), usuario);

        //editor.putString("url_img_perfil_usuario_principal", usuario);
        editor.commit();

        Toast.makeText(ConfigurarCuenta.this, getResources().getString(R.string.usuario_guardado), Toast.LENGTH_SHORT).show();
        etAgregarUsuario.setText("");
        mostrarPreferencia();

        miPrefereciaCompartida0= MainActivity.mainActivityContext.getSharedPreferences(getResources().getString(R.string.nombre_base_datos), Context.MODE_PRIVATE);
        usuario_nombre = miPrefereciaCompartida0.getString(getResources().getString(R.string.usuario_nombre), getResources().getString(R.string.usuario_default));
    }

    public static String getShared () {
        SharedPreferences miPrefereciaCompartida = context.getSharedPreferences("UsuariosPetagram", Context.MODE_PRIVATE);
        String usuario = miPrefereciaCompartida.getString("usuario", ConfigurarCuenta.usuario_nombre);
        return usuario;
    }

    public void mostrarPreferencia(){
        SharedPreferences miPrefereciaCompartida = getSharedPreferences(getResources().getString(R.string.nombre_base_datos), Context.MODE_PRIVATE);
        Map<String, ?> allPrefs = miPrefereciaCompartida.getAll();
        Set<String> set = allPrefs.keySet();
        for(String s : set){}
    }
}
