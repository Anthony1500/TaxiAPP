package com.example.taxiapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class usuario {

    private Bitmap usuariofoto;
    private String dato;


    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
        try {
            byte[] bytecode= Base64.decode(dato,Base64.DEFAULT);
            this.usuariofoto= BitmapFactory.decodeByteArray(bytecode,0,bytecode.length);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Bitmap getUsuariofoto() {
        return usuariofoto;
    }

    public void setUsuariofoto(Bitmap usuariofoto) {
        this.usuariofoto = usuariofoto;
    }
}
