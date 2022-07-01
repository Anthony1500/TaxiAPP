package com.example.taxiapp.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.taxiapp.MainActivityTaxiMenu;
import com.example.taxiapp.R;
import com.example.taxiapp.subir_foto;


public class datos extends AppCompatActivity {
        Button atras;
protected void onCreate(Bundle savedInstanceState
                       ) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.datosusua);
        atras = (Button) findViewById(R.id.btn_perfilatras);
        atras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(datos.this, GalleryFragment.class);


                        startActivity(intent);
                }
        });
        }
        }