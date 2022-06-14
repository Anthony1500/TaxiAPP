package com.example.taxiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class login extends AppCompatActivity {
    Button  menu;
    Button  boton_recuperar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        menu=(Button)findViewById(R.id.bt_login);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivityTaxiMenu.class);
                startActivity(i);
            }
        });
        boton_recuperar= (Button) findViewById(R.id.btn_recuperarcontrasena);
        boton_recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), recuperar_contrasenia.class);
              //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Envió hacia otro Activity
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                //startActivityForResult(intent, 0);
            }
        });
    }

}
