package com.example.taxiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class recuperar_contrasenia extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    Button  botonverificar;
    EditText cajacorreo;
    RequestQueue rq;//Definimos variables a utilizar
    JsonRequest jrq;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recuperar_contrasenia);
        cajacorreo=(EditText) findViewById(R.id.text_correo);
        rq = Volley.newRequestQueue(recuperar_contrasenia.this);
        botonverificar=(Button) findViewById(R.id.btn_correo);
        botonverificar.setOnClickListener(new View.OnClickListener() {//Método para darle función al botón

            @Override
            public void onClick(View v) {
                String caja1 = cajacorreo.getText().toString();
                if(!caja1.isEmpty() )
                {
                    progressDialog = new ProgressDialog(recuperar_contrasenia.this, R.style.MyAlertDialogStyle);

                    progressDialog.setMessage("Por favor espera...");
                    progressDialog.setCancelable(false);//Método del Progress Dialog
                    progressDialog.show();
                    comprovar();
                }
                else{
                    cajacorreo.setError("Favor de escribir algo");
                }
            }
        });
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.dismiss();
        Toast.makeText(this,"Error de conexión." ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        Toast.makeText(this,"Exito." ,Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    private void comprovar(){

        String urls="http://apptaxi.lovestoblog.com/comprobar.php?correo="+cajacorreo.getText().toString();
        jrq= new JsonObjectRequest(Request.Method.GET,urls,null,this,this);
        rq.add(jrq);//Envió y recepción de datos
    }
}
