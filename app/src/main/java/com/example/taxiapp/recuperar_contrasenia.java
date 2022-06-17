package com.example.taxiapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class recuperar_contrasenia extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    Button  botonverificar,botonatras;
    EditText cajacorreo;
    TextView textocorrecto,textoincorrecto;
    String correo,usuario,contrasenia,id_usuario;
    RequestQueue rq;//Definimos variables a utilizar
    JsonRequest jrq;

    Session session;

    ProgressDialog progressDialog;
    String url;
    Handler handler = new Handler();
    public static int esperar = 5000;
    //********************************************************************
    String correoelectronico="corpcosechapp@gmail.com";
    String contraseña="ieijrakhyikvfpjn";
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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
        //********************************************************************
        JSONArray jsonArray = response.optJSONArray("probar");
        JSONObject jsonObject= null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
            id_usuario =jsonObject.optString("id_usuario");//Obtención del id
            url = "https://apps.indoamerica.edu.ec/catastros/apptaxi/selectusuarios.php?id_usuario=" +id_usuario;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //********************************************************************
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {

                        jsonObject = response.getJSONObject(i);
                        correo= jsonObject.getString("correo");
                        usuario = jsonObject.getString("usuario");
                        contrasenia = jsonObject.getString("contrasenia");




                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de Conexión", Toast.LENGTH_SHORT).show();
            }
        }
        );

        rq = Volley.newRequestQueue(this);
        rq.add(jsonArrayRequest);


       enviar_email();


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
    public void enviar_email() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.googlemail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        try {
            session = javax.mail.Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correoelectronico,contraseña);
                }
            });

            Toast.makeText(this,session.toString(),Toast.LENGTH_SHORT).show();

            if (session != null) {
                setProgressDialog();
                javax.mail.Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(correoelectronico));
                message.setSubject("Consulta de Datos");
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(cajacorreo.getText().toString()));
                message.setContent("<center><h2>Credenciales de Acceso</h2></center><br>" + "<center><img  src=\"https://apps.indoamerica.edu.ec/catastros/cosecha/img/cosecha.png\"></center>" + "<center>" +usuario.toUpperCase() + "<br>" + "</center>" + "<br>" + "Email : "
                        + correo + "<br>" + "Contraseña:" + contrasenia + "<center><p>Recuerda no compartir esta información. </p></center>", "text/html; charset=utf-8");

                Transport.send(message);
                setProgressDialog();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(recuperar_contrasenia.this, login.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Se ha enviado las credenciales al correo.",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"Revisa tu correo.",Toast.LENGTH_SHORT).show();


            }
        }, 3000);
    }
    public void setProgressDialog() {

        int llPadding = 20;
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setPadding(llPadding, llPadding, llPadding, llPadding);
        ll.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        llParam.gravity = Gravity.CENTER;
        ll.setLayoutParams(llParam);

        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(false);

        progressBar.setPadding(0, 0, llPadding, 0);
        progressBar.setLayoutParams(llParam);

        progressBar.setDrawingCacheBackgroundColor(Color.rgb(248,99,0));


        llParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        llParam.gravity = Gravity.CENTER;
        TextView tvText = new TextView(this);
        tvText.setText("Enviando correo...");
        tvText.setTextColor(Color.parseColor("#000000"));
        tvText.setTextSize(20);
        tvText.setLayoutParams(llParam);

        ll.addView(progressBar);
        ll.addView(tvText);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setView(ll);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        Window window = dialog.getWindow();

        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);

            handler.postDelayed(new Runnable() {
                public void run() {
                    dialog.dismiss();


                }
            }, 5000);


        }

    }

}
