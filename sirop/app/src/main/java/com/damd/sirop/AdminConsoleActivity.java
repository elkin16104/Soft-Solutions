package com.damd.sirop;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.damd.sirop.databinding.ActivityAdminConsoleBinding;

public class AdminConsoleActivity extends AppCompatActivity {

    private ActivityAdminConsoleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_console);
        binding = ActivityAdminConsoleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Establecer el titulo
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Consola Administrador ");

        binding.btnCrudOperacion.setOnClickListener(view -> startActivity(new Intent(AdminConsoleActivity.this, CrudOperacionesActivity.class)));
        binding.btnCrudUsuarios.setOnClickListener(view -> startActivity(new Intent(AdminConsoleActivity.this, CrudUsuariosActivity.class)));
        binding.btnCrudCerrarSesion.setOnClickListener(view -> startActivity(new Intent(AdminConsoleActivity.this, IndexActivity.class)));
    }

//    public void tratadorInicio(View view) {
//        Intent tratadorInicio = new Intent(AdminConsoleActivity.this, IndexActivity.class);
//        startActivity(tratadorInicio);
//    }

//    public void tratadorCRUDOperacion(View view) {
//        Intent tratadorAgregaOP = new Intent(AdminConsoleActivity.this, CrudOperacionesActivity.class);
//        startActivity(tratadorAgregaOP);
//    }

//    public void tratadorCRUDUsuarios(View view) {
//        Intent tratadorCRUD = new Intent(AdminConsoleActivity.this, IndexActivity.class);
//        startActivity(tratadorCRUD);
//    }
}