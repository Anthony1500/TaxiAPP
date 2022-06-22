package com.example.taxiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class login extends AppCompatActivity {
    Button  menu;
    Button  boton_recuperar,boton_registro;
    EditText u_correo ,u_con;
    String s_correo,s_con;
    String url="https://apps.indoamerica.edu.ec/catastros/apptaxi/usuarios.php";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        u_correo = findViewById(R.id.email);
        u_con = findViewById(R.id.contraseña);
        boton_recuperar=(Button)findViewById(R.id.btn_recuperarcontrasena);
        boton_registro=(Button)findViewById(R.id.btn_registro);
        boton_recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), recuperar_contrasenia.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Envió hacia otro Activity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                startActivityForResult(intent, 0);

            }
        });

        boton_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), registro_usuario.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Envió hacia otro Activity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                startActivityForResult(intent, 0);
            }
        });

    }
    public void login(View view){
if(u_correo.getText().toString().equals("")){
    Toast.makeText(this,"ingrese correo",Toast.LENGTH_SHORT).show();
}else if(u_con.getText().toString().equals("")){
    Toast.makeText(this,"ingrese contraseña",Toast.LENGTH_SHORT).show();

}else {
    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Espere");
    progressDialog.show();
    s_correo=u_correo.getText().toString().trim();
    s_con=u_con.getText().toString().trim();
    StringRequest request= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            progressDialog.dismiss();
            if (response.equalsIgnoreCase("ingreso correcto")) {
                u_correo.setText("");
                u_con.setText("");
                Bundle enviar= new Bundle();
                enviar.putString("datos",  s_correo);
                Intent intent = new Intent(login.this, Login_Splash_Screen.class);
                intent.putExtras(enviar);
                startActivity(intent);


            } else {
                Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            progressDialog.dismiss();
            Toast.makeText(login.this,error.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    }){
        @Nullable
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {

            Map<String,String> params = new HashMap<>();
            params.put("email",s_correo);
            params.put("password",s_con);
            return params;
        }
    };
    RequestQueue requestQueue = Volley.newRequestQueue(login.this);
    requestQueue.add(request);
        }
    }



}
