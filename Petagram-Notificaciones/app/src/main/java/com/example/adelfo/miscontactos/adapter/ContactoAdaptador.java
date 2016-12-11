package com.example.adelfo.miscontactos.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adelfo.miscontactos.ActivityDetalleContacto;
import com.example.adelfo.miscontactos.pojo.Contacto;
import com.example.adelfo.miscontactos.R;
import com.example.adelfo.miscontactos.restAPI.ConstantesRestApi;
import com.example.adelfo.miscontactos.restAPI.EndpointsApi;
import com.example.adelfo.miscontactos.restAPI.Model.LikesResponse;
import com.example.adelfo.miscontactos.restAPI.adapter.RestApiAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.adelfo.miscontactos.ConfigurarCuenta.context;

/**
 * Created by Adelfo on 18/11/2016.
 */

public class ContactoAdaptador extends RecyclerView.Adapter<ContactoAdaptador.ContactoViewHolder>{

    ArrayList<Contacto> contactos;
    Activity activity;

    public ContactoAdaptador(ArrayList<Contacto> contactos, Activity activity){
        this.contactos = contactos;
        this.activity = activity;
    }

    @Override
    public ContactoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carview_grid_contacto, parent, false);
        return new ContactoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ContactoViewHolder contactoViewHolder, int position) {
        final Contacto contacto = contactos.get(position);

        Picasso.with(activity).
                load(contacto.getUrlFoto()).
                placeholder(R.drawable.dog_footprint_filled_50).
                into(contactoViewHolder.imgFoto);

        contactoViewHolder.tvLikes.setText(String.valueOf(contacto.getLikes()));

        contactoViewHolder.imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Diste Like a " + contacto.getNombreCompleto()+" "+contacto.getId(), Toast.LENGTH_SHORT).show();
                int likes = contacto.getLikes();
                contacto.setLikes(likes+1);
                notifyDataSetChanged();

                //ToqueAnimal
                Log.d("TOQUE_ANIMAL", "true");

                //String ANIMAL_EMISOR ="perritocheno";
                //String ANIMAL_RECEPTOR = "gato";
                String id_usuario_instagram, nombre_usuario_instagram;
                String id_foto =ConstantesRestApi.id_foto_example;

                try {
                    SharedPreferences miPrefereciaCompartida = context.getSharedPreferences("UsuariosPetagram", Context.MODE_PRIVATE);
                    id_usuario_instagram = miPrefereciaCompartida.getString("usuario_id", "4179481767");
                    nombre_usuario_instagram = miPrefereciaCompartida.getString("usuario_nombre", "perritocheno");
                }catch (Exception err){
                    id_usuario_instagram = ConstantesRestApi.user_self;
                    nombre_usuario_instagram = ConstantesRestApi.user_self_name;
                }

                //1.    -KYewnnc0ZAaa5X2zsih
                //2.    -KYeul9bJizBG523aEGQ
                final LikesResponse likesResponse = new LikesResponse("-KYgNhf7lWxcUHLDCyti", "123", nombre_usuario_instagram);
                RestApiAdapter restApiAdapter =  new RestApiAdapter();
                EndpointsApi endpoints = restApiAdapter.establecerConexionRestAPI();
                //Call<LikesResponse> likesResponseCall = endpoints.toqueAnimal(likesResponse.getId(), ANIMAL_EMISOR, id_foto);
                Call<LikesResponse> likesResponseCall = endpoints.toqueAnimal(likesResponse.getId(), id_usuario_instagram, nombre_usuario_instagram, id_foto);

                likesResponseCall.enqueue(new Callback<LikesResponse>() {
                    @Override
                    public void onResponse(Call<LikesResponse> call, Response<LikesResponse> response) {
                        LikesResponse usuarioResponse1 = response.body();
                        Log.d("ID_FIREBASE", usuarioResponse1.getId());
                        Log.d("TOKEN_FIREBASE", usuarioResponse1.getToken());
                        Log.d("ANIMAL_FIREBASE", usuarioResponse1.getAnimal());
                    }

                    @Override
                    public void onFailure(Call<LikesResponse> call, Throwable t) {

                    }
                });

                /*Intent intent = new Intent(activity, ActivityDetalleContacto.class);
                intent.putExtra("url", contacto.getUrlFoto());
                intent.putExtra("like", contacto.getLikes());
                activity.startActivity(intent);*/
            }
        });
/*
        mascotaViewHolder.btnLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Diste Like a " + mascota.getNombre(), Toast.LENGTH_SHORT).show();
                int liked = mascota.getLikes();
                liked = liked +1;
                mascota.setLikes(liked);
                notifyDataSetChanged();
            }

        });*/
    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

    public static class ContactoViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgFoto;
        private TextView tvLikes;

        public ContactoViewHolder(View itemView) {
            super(itemView);

            imgFoto = (ImageView) itemView.findViewById(R.id.imgFoto);
            tvLikes = (TextView) itemView.findViewById(R.id.tvLikes);
        }
    }
}
