package com.damd.sirop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_Sirop);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        //Establecer el titulo
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Bienvenido a SIROP");
    }

    public void ingresar(View view) {
        Intent ingresar = new Intent(IndexActivity.this, LoginAdminActivity.class);
        startActivity(ingresar);
    }

    public void registrar(View view) {
        Intent registro = new Intent(IndexActivity.this, RegistrarActivity.class);
        startActivity(registro);
    }
}