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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class subir_foto extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    String info;
    ImageView imagen;
    TextView nombre;
    RequestQueue rq;

    JsonRequest jrq;
    String id_usuario;
    Button cargar_imagen,botonatras, botonguardar;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private String UPLOAD_URL ="https://apps.indoamerica.edu.ec/catastros/apptaxi/subirfoto.php";
    private String KEY_IMAGEN = "imagen_usuario";
    private String KEY_ID = "id_usuario";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rq = Volley.newRequestQueue(subir_foto.this);
        setContentView(R.layout.subir_foto);
        Bundle datr =getIntent().getExtras();
        info = datr.getString("datos");

        String urls = "https://apps.indoamerica.edu.ec/catastros/apptaxi/selectfotousuario.php?correo="+info;
        jrq = new JsonObjectRequest(Request.Method.GET, urls, null, this, this);
        rq.add(jrq);//Envi?? y recepci??n de datos
        imagen = (ImageView) findViewById(R.id.usuarioimagen);
        cargar_imagen= (Button) findViewById(R.id.btn_cargarimagen);
        nombre=(TextView) findViewById(R.id.text_nombreusuario);
        botonatras = (Button) findViewById(R.id.btn_perfilatras);
        botonguardar = (Button) findViewById(R.id.btn_guardar);
        botonguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

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
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Envi?? hacia otro Activity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Bundle enviar= new Bundle();
                enviar.putString("datos",  info);
                intent.putExtras(enviar);
                startActivity(intent);
                Toast.makeText(subir_foto.this, "Volvi?? al Men??.", Toast.LENGTH_SHORT).show();
    }
    });
    }
    private void cargarfoto(){
    Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    intent.setType("image/");
    startActivityForResult(intent.createChooser(intent,"Seleccione para completar la acci??n"),PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if(resultCode == RESULT_OK ){
            Uri path=data.getData();
            try {
                //C??mo obtener el mapa de bits de la Galer??a
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                //Configuraci??n del mapa de bits en ImageView
                imagen.setImageBitmap(bitmap);
               // imagen.setImageURI(path);

    }          catch (IOException e) {
               e.printStackTrace();
    }
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
        //Mostrar el di??logo de progreso
        final ProgressDialog loading = ProgressDialog.show(this,"Subiendo...","Espere por favor...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Descartar el di??logo de progreso
                        loading.dismiss();
                        //Mostrando el mensaje de la respuesta
                        Toast.makeText(subir_foto.this, s , Toast.LENGTH_LONG).show();
                }
                },
                new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                //Descartar el di??logo de progreso
                loading.dismiss();
                //Showing toast
                Toast.makeText(subir_foto.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Convertir bits a cadena
                String foto = getStringImagen(bitmap);
                //Obtener el nombre de la imagen
                String id = id_usuario;
                //Creaci??n de par??metros
                Map<String,String> params = new Hashtable<String, String>();
                //Agregando de par??metros
                params.put(KEY_IMAGEN,foto);
                params.put(KEY_ID,id);
                //Par??metros de retorno
                return params;
        }
        };
        //Creaci??n de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        usuario usufoto = new usuario();
        JSONArray jsonArray = response.optJSONArray("probar");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
           // usufoto.setDato(jsonObject.optString("imagen_usuario"));
            if(jsonObject.optString("imagen_usuario")!=null) {
                Toast.makeText(subir_foto.this, "exito", Toast.LENGTH_LONG).show();

                Glide.with(subir_foto.this)
                        .load(jsonObject.optString("imagen_usuario"))
                        .into(imagen);
            }else{
                Toast.makeText(subir_foto.this, "fallido", Toast.LENGTH_LONG).show();
                imagen.setImageResource(R.drawable.sin_imagen);
            }
            id_usuario = jsonObject.optString("id_usuario");//Obtenci??n del id
            nombre.setText(jsonObject.optString("usuario"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        //no funciona decoddificacion en base 64


       
    }
}
