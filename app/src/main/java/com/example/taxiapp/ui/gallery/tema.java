package com.example.taxiapp.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taxiapp.R;

public class tema extends AppCompatActivity {
    Button atras;
    protected void onCreate(Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tema);
        atras = (Button) findViewById(R.id.btn_perfilatras);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tema.this, GalleryFragment.class);


                startActivity(intent);
            }
        });
    }
}