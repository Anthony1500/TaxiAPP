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

        botonatras = (Button) findViewById(R.id.btn_registroatras);
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
                progressDialog = new ProgressDialog(registro_usuario.this, R.style.MyAlertDialogStyle);
                progressDialog.setCancelable(false);//Método del Progress Dialog para que no se pueda cancelar
                if (caja1.isEmpty() || caja2.isEmpty() || caja3.isEmpty() || caja4.isEmpty() || caja5.isEmpty()) {
                    Toast.makeText(registro_usuario.this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(caja4).matches()) {
                    Toast.makeText(registro_usuario.this, "El correo no es válido.", Toast.LENGTH_SHORT).show();
                } else if (caja2.length() < 6) {
                    Toast.makeText(registro_usuario.this, "La contraseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show();
                } else if (!caja2.equals(caja3)) {
                    Toast.makeText(registro_usuario.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                } else if (caja5.length() < 10) {
                    Toast.makeText(registro_usuario.this, "El teléfono debe tener al menos 10 dígitos.", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setMessage("Registrando usuario...");
                    progressDialog.show();
                    registrase();


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
        usuario.setText("");
        contraseña.setText("");
        contraseña2.setText("");
        correos.setText("");
        telefono.setText("");

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
