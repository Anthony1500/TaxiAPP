package com.example.taxiapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Login_Splash_Screen extends AppCompatActivity {
    private final int DURACION_SPLASH =4000;// tiempo de espera
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {// metodo para pasar de una pantalla a otra
                Intent intent = new Intent(Login_Splash_Screen.this,MainActivityTaxiMenu.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);
    }


}


