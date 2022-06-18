package com.example.taxiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.regex.Pattern;

public class registro_usuario extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    Button botonatras,botonregistro;
    EditText usuario,contraseña,contraseña2,correos,telefono;
    ProgressDialog progressDialog;
    RequestQueue rq, rq2;//Definimos variables a utilizar
    JsonRequest jrq;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_usuario);
        botonatras = (Button) findViewById(R.id.rbtn_registroatras);
        botonregistro = (Button) findViewById(R.id.btn_registrarse);
        usuario = (EditText) findViewById(R.id.usuario_registro);
        contraseña = (EditText) findViewById(R.id.contrasenia1);
        contraseña2 = (EditText) findViewById(R.id.contrasenia2);
        correos = (EditText) findViewById(R.id.correo_registro);
        telefono = (EditText) findViewById(R.id.telefono);
        rq = Volley.newRequestQueue(registro_usuario.this);
        botonatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registro_usuario.this, login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Envió hacia otro Activity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(registro_usuario.this, "Volvió al inicio de sesión.", Toast.LENGTH_SHORT).show();
            }
        });
        botonregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String caja1 = usuario.getText().toString();
                String caja2 = contraseña.getText().toString();
                String caja3 = contraseña2.getText().toString();
                String caja4 = correos.getText().toString();
                String caja5 = telefono.getText().toString();
                if (!caja1.isEmpty()) {
                    if (!validarEmail(caja4) ) {
                        correos.setError("Correo no válido");
                    } else {
                        progressDialog = new ProgressDialog(registro_usuario.this, R.style.MyAlertDialogStyle);
                        progressDialog.setMessage("Por favor espera...");
                        progressDialog.setCancelable(false);//Método del Progress Dialog
                        progressDialog.show();

                        registrase();
                    }
                } else {
                    usuario.setError("Favor de escribir algo");
                    contraseña.setError("Favor de escribir algo");
                    contraseña2.setError("Favor de escribir algo");
                    correos.setError("Favor de escribir algo");
                    telefono.setError("Favor de escribir algo");
                }
            }
        });

    }
    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.dismiss();
        Toast.makeText(registro_usuario.this,"Error al registrase",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialog.dismiss();
        Toast.makeText(registro_usuario.this,"Exito al registrase",Toast.LENGTH_SHORT).show();

    }



    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    private void registrase(){

        String url="https://apps.indoamerica.edu.ec/catastros/apptaxi/agregarusuario.php?usuario="+usuario.getText().toString()+"&contrasenia="+contraseña.getText().toString()+"&correo="+correos.getText().toString()+"&celular="+telefono.getText();
        jrq= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);//Envió y recepción de datos
    }
}
