package com.damd.sirop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.damd.sirop.databinding.ActivityLoginAdminBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginAdminActivity extends AppCompatActivity {

    private ActivityLoginAdminBinding binding;

    private TextView seleccionSP;
    private Spinner listaPerfiles;

//    private TextView name;
//    private TextView pass;
//
//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference databaseReference;

    private String email = "", password = "";
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login_admin);
        binding = ActivityLoginAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Establecer el titulo
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Iniciar Sesion");

//        Barras de progreso
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setMessage("Iniciando sesion...");
        progressDialog.setCanceledOnTouchOutside(false);

//       Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //Listeners acciones de los botones al presionarlos en funciones lamba
        binding.btnRegistrar.setOnClickListener(view -> startActivity(new Intent(LoginAdminActivity.this, RegistrarActivity.class)));
        binding.btnLoginSesion.setOnClickListener(view -> validateData());

//        inicializarFirebase();
//
//        name = findViewById(R.id.txtUsuarioSesion);
//        pass = findViewById(R.id.txtContrasenaSesion);
        seleccionSP = findViewById(R.id.txtPerfilSelec);
        listaPerfiles = findViewById(R.id.SPSelPerfilSesion);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void validateData() {
        email = binding.txtUsuarioSesion.getText().toString().trim();
        password = binding.txtContrasenaSesion.getText().toString().trim();
        String perfil = listaPerfiles.getSelectedItem().toString();

        if (perfil.equals("Tratador")) {
            seleccionSP.setText("Tratador");
        } else if (perfil.equals("Comandante")) {
            seleccionSP.setText("Comandante");
        } else {
            seleccionSP.setText("Seleccione un perfil correcto");
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.txtUsuarioSesion.setError("Formato de correo inválido");
        } else if (TextUtils.isEmpty(password)) {
            binding.txtContrasenaSesion.setError("Contrasena inválida");
        } else if (seleccionSP.getText().toString().equals("Seleccione un perfil correcto")) {
            binding.txtPerfilSelec.setError("Elija un perfil correcto");
        } else {
            firebaseLogin();
        }
    }

    private void firebaseLogin() {
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String email = firebaseUser.getEmail();
                Toast.makeText(LoginAdminActivity.this, "Iniciaste sesion como\n" + email, Toast.LENGTH_SHORT).show();

                if (seleccionSP.getText().toString().equals("Tratador")) {
                    Intent ingTratador = new Intent(LoginAdminActivity.this, AdminConsoleActivity.class);
                    startActivity(ingTratador);
                } else if (seleccionSP.getText().toString().equals("Comandante")) {
                    Intent ingComandante = new Intent(LoginAdminActivity.this, ComandanteActivity.class);
                    startActivity(ingComandante);
                } else {
                    binding.txtPerfilSelec.setError("Elija un perfil correcto");
                }
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(LoginAdminActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//
//    public void registrar(View view) {
//        Intent registrar = new Intent(LoginAdminActivity.this, RegistrarActivity.class);
//        startActivity(registrar);
//    }

//    private void inicializarFirebase() {
//        FirebaseApp.initializeApp(this);
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference();
//        Toast.makeText(this, "Base de datos inicializada", Toast.LENGTH_SHORT).show();
//    }

//    public void perfilesSP(View view) {
//        String perfil = listaPerfiles.getSelectedItem().toString();
//
//        if (perfil.equals("Seleccione...")) {
//            seleccionSP.setText("");
//        } else if (perfil.equals("Tratador")) {
//            seleccionSP.setText("Tratador");
//        } else {
//            seleccionSP.setText("Comandante");
//        }

//        errorValidar();
//        validarDatos();
//    }

    //Validar datos vacios para INICIAR SESION
//    public void validarDatos() {
//        if (name.getText().toString().isEmpty() || pass.getText().toString().isEmpty() || seleccionSP.getText().toString().isEmpty()) {
//            Toast.makeText(this, "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
//        } else if (seleccionSP.getText() == "Tratador") {
//            Intent ingTratador = new Intent(LoginAdminActivity.this, IngresarOperacionActivity.class);
//            startActivity(ingTratador);
//            inicioExitoso();
//        } else if (seleccionSP.getText() == "Comandante") {
//            Intent ingComandante = new Intent(LoginAdminActivity.this, ComandanteActivity.class);
//            startActivity(ingComandante);
//            inicioExitoso();
//        }
//    }

    /*private void errorValidar() {

        if (name.getText().toString().isEmpty()) {
            name.setError("Requerido");
            Toast.makeText(this, "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
        }
        if (pass.getText().toString().isEmpty()) {
            pass.setError("Requerido");
            Toast.makeText(this, "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
        }
        if (seleccionSP.getText().toString().isEmpty()) {
            seleccionSP.setError("Requerido");
            Toast.makeText(this, "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
        }
        if (seleccionSP.getText() == "Tratador") {
            Intent ingTratador = new Intent(LoginAdminActivity.this, AdminConsoleActivity.class);
            startActivity(ingTratador);
            inicioExitoso();
        } else if (seleccionSP.getText() == "Comandante") {
            Intent ingComandante = new Intent(LoginAdminActivity.this, ComandanteActivity.class);
            startActivity(ingComandante);
            inicioExitoso();
        }
    }*/

//    public void inicioExitoso() {
//        Toast.makeText(this, "Inicio de Sesion Exitoso", Toast.LENGTH_SHORT).show();
//    }


}
