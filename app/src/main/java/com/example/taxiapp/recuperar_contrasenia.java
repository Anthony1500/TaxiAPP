package com.example.taxiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class recuperar_contrasenia extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    Button  botonverificar,botonatras;
    EditText cajacorreo;
    TextView textocorrecto,textoincorrecto;
    RequestQueue rq;//Definimos variables a utilizar
    JsonRequest jrq;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recuperar_contrasenia);
        textocorrecto=(TextView) findViewById(R.id.text_correcto);
        textoincorrecto=(TextView) findViewById(R.id.text_error);
        cajacorreo=(EditText) findViewById(R.id.text_correo);
        rq = Volley.newRequestQueue(recuperar_contrasenia.this);
        botonverificar=(Button) findViewById(R.id.btn_correo);
        botonatras=(Button) findViewById(R.id.atras);
        botonverificar.setOnClickListener(new View.OnClickListener() {//Método para darle función al botón

            @Override
            public void onClick(View v) {
                String caja1 = cajacorreo.getText().toString();

                    if (!caja1.isEmpty()) {
                        if (!validarEmail(caja1)){
                            cajacorreo.setError("Correo no válido");
                        }else {
                            progressDialog = new ProgressDialog(recuperar_contrasenia.this, R.style.MyAlertDialogStyle);
                            progressDialog.setMessage("Por favor espera...");
                            progressDialog.setCancelable(false);//Método del Progress Dialog
                            progressDialog.show();

                            comprovar();
                        }
                    } else {
                        cajacorreo.setError("Favor de escribir algo");
                    }

            }
        });

        botonatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(recuperar_contrasenia.this, login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Envió hacia otro Activity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(recuperar_contrasenia.this,"Volvió al inicio de sesión." ,Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.dismiss();
        Toast.makeText(this,"Error" ,Toast.LENGTH_SHORT).show();
        textoincorrecto.setText("El correo ingresado no está registrado.");
        textocorrecto.setText("");
    }

    @Override
    public void onResponse(JSONObject response) {

        Toast.makeText(this,"Exito." ,Toast.LENGTH_SHORT).show();
        textocorrecto.setText("Éxito");
        textoincorrecto.setText("");
        progressDialog.dismiss();
    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    private void comprovar(){

        String urls="https://apps.indoamerica.edu.ec/catastros/apptaxi/comprobar.php?correo="+cajacorreo.getText().toString();
        jrq= new JsonObjectRequest(Request.Method.GET,urls,null,this,this);
        rq.add(jrq);//Envió y recepción de datos
    }

}
