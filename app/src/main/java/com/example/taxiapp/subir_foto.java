package com.example.taxiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Map;

public class subir_foto extends AppCompatActivity {
    String info;
    ImageView imagen;
    TextView nombre;
    RequestQueue rq;
    Button cargar_imagen,botonatras;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private String UPLOAD_URL ="https://apps.indoamerica.edu.ec/catastros/apptaxi/subirfoto.php";
    private String KEY_IMAGEN = "imagen_usuario";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subir_foto);
        Bundle datr =getIntent().getExtras();
        info = datr.getString("datos");
        String url="https://apps.indoamerica.edu.ec/catastros/apptaxi/select.php?correo="+info;
        imagen = (ImageView) findViewById(R.id.usuarioimagen);
        cargar_imagen= (Button) findViewById(R.id.btn_cargarimagen);
        nombre=(TextView) findViewById(R.id.text_nombreusuario);
        botonatras = (Button) findViewById(R.id.btn_perfilatras);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        nombre.setText(jsonObject.getString("usuario"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        }
        }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error  Conexión", Toast.LENGTH_SHORT).show();
        }
        }
        );
        rq= Volley.newRequestQueue(this);
        rq.add(jsonArrayRequest);
        cargar_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            cargarfoto();
        }
        });
        botonatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(subir_foto.this, MainActivityTaxiMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Envió hacia otro Activity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Bundle enviar= new Bundle();
                enviar.putString("datos",  info);
                intent.putExtras(enviar);
                startActivity(intent);
                Toast.makeText(subir_foto.this, "Volvió al Menú.", Toast.LENGTH_SHORT).show();
    }
    });
    }
private void cargarfoto(){
    Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    intent.setType("image/");
    startActivityForResult(intent.createChooser(intent,"Seleccione para completar la acción"),10);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if(resultCode==RESULT_OK){
            Uri path=data.getData();
            imagen.setImageURI(path);
    }
    }
    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void uploadImage(){
        //Mostrar el diálogo de progreso
        final ProgressDialog loading = ProgressDialog.show(this,"Subiendo...","Espere por favor...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();
                        //Mostrando el mensaje de la respuesta
                        Toast.makeText(subir_foto.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(subir_foto.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Convertir bits a cadena
                String imagen = getStringImagen(bitmap);

               // https://www.androfast.com/2017/04/como-subir-una-imagen-desde-android-una.html

                //Creación de parámetros
                Map<String,String> params = new Hashtable<String, String>();

                //Agregando de parámetros
                params.put(KEY_IMAGEN, imagen);


                //Parámetros de retorno
                return params;
            }
        };

        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);
    }
    }
