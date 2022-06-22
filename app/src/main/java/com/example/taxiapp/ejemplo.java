package com.example.taxiapp;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ejemplo extends AppCompatActivity {
    TextView datos, datos1;
    String info;

    RequestQueue rq;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ejemplo);
        datos = findViewById(R.id.datae);
        datos1 = findViewById(R.id.ejemp);
        Bundle datr = getIntent().getExtras();
        info = datr.getString("datos");
        datos.setText(info);


    }
}