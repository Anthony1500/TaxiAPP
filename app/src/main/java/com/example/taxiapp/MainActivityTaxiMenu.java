package com.example.taxiapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.taxiapp.databinding.ActivityMainTaxiMenuBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.collect.Maps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MainActivityTaxiMenu extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainTaxiMenuBinding binding;
    TextView datousu,datos1;
    String info;
    ImageView imagen;
    TextView idusuario;
    RequestQueue rq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idusuario= (TextView) findViewById(R.id.idusu);
        datousu= (TextView) findViewById(R.id.datousu);
        Bundle datr =getIntent().getExtras();
        info = datr.getString("datos");
        String url="https://apps.indoamerica.edu.ec/catastros/apptaxi/select.php?correo="+info;
       // datos.setText(info);


        binding = ActivityMainTaxiMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMainActivityTaxiMenu.toolbar);

        binding.appBarMainActivityTaxiMenu.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_maps,R.id.nav_ayuda,
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_informacion)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_activity_taxi_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View headerView = navigationView.getHeaderView(0);
        imagen = (ImageView) headerView.findViewById(R.id.fotousuario);
        TextView navnombre = (TextView) headerView.findViewById(R.id.nombre);
        TextView navcorreo = (TextView) headerView.findViewById(R.id.correo);
        TextView navusu = (TextView) headerView.findViewById(R.id.idusu);


        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        navnombre.setText(jsonObject.getString("usuario"));
                        navcorreo.setText(Html.fromHtml(jsonObject.getString("correo")));
                        navusu.setText(Html.fromHtml(jsonObject.getString("id_usuario")));

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error  Conexión", Toast.LENGTH_SHORT).show();
            }
        }
        );
        rq= Volley.newRequestQueue(this);
        rq.add(jsonArrayRequest);
//*************************************************


imagen.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivityTaxiMenu.this, subir_foto.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Envió hacia otro Activity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle enviar= new Bundle();
        enviar.putString("datos",  info);
        intent.putExtras(enviar);
        startActivity(intent);
        startActivityForResult(intent, 0);
    }
});


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_taxi_menu, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_activity_taxi_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}