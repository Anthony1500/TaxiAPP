package com.example.taxiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class registro_usuario extends AppCompatActivity {
    Button botonatras;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_usuario);
        botonatras=(Button) findViewById(R.id.rbtn_registroatras);
        botonatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registro_usuario.this, login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Envió hacia otro Activity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(registro_usuario.this,"Volvió al inicio de sesión." ,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
