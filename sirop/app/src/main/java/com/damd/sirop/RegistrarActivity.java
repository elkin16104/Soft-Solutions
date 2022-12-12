package com.damd.sirop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.damd.sirop.databinding.ActivityRegistrarBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity {

    private TextView seleccionSP;
    private Spinner listaPerfiles;
//    private TextView name;
//    private TextView lastName;
//    private TextView pass;
//    private TextView email;
//    private Button boton;

    FirebaseFirestore mFirestore;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;
    private ActivityRegistrarBinding binding;

    //
//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference databaseReference;

    String email = "", password = "", perfil = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Establecer el titulo
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Registro de usuario");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
//        inicializarFirebase();

        // Initialize Firebase Auth
        mFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Por favor espere");
        progressDialog.setMessage("Creando tu cuenta...");
        progressDialog.setCanceledOnTouchOutside(false);

        //Listener Botones
        binding.btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrarActivity.this, LoginAdminActivity.class));
            }
        });

        binding.btnRegistroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

//        name = findViewById(R.id.txtNombreRegistro);
//        lastName = findViewById(R.id.txtApellidoRegistro);
//        pass = findViewById(R.id.txtContrasenaRegistro);
//        email = findViewById(R.id.txtEmailRegistro);

        seleccionSP = findViewById(R.id.txtPerfilSelec);
        listaPerfiles = findViewById(R.id.SPSelPerfilRegistro);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void validateData() {
        email = binding.txtEmailRegistro.getText().toString().trim();
        password = binding.txtContrasenaRegistro.getText().toString().trim();
        perfil = listaPerfiles.getSelectedItem().toString();

        if (perfil.equals("Tratador")) {
            seleccionSP.setText("Tratador");
        } else if (perfil.equals("Comandante")) {
            seleccionSP.setText("Comandante");
        } else {
            seleccionSP.setText("Seleccione un perfil correcto");
        }

//        String opcionPerfil = binding.txtSeleccionPerfil.getText().toString().trim();
//        String name = binding.txtNombreRegistro.getText().toString().trim();
//        String lastName = binding.txtApellidoRegistro.getText().toString().trim();

//        if (name.equals("")) {
//            binding.txtNombreRegistro.setError("Ingrese solo letras");
//        } else if (lastName.equals("")) {
//            binding.txtApellidoRegistro.setError("Ingrese solo letras");
//        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.txtEmailRegistro.setError("Formato de correo inválido");
        } else if (TextUtils.isEmpty(password)) {
            binding.txtContrasenaRegistro.setError("Contrasena inválida");
        } else if (password.length() < 7) {
            binding.txtContrasenaRegistro.setError("La contrasena debe tener al menos 7 caracteres");
        } else if (seleccionSP.getText().toString().equals("Seleccione un perfil correcto")) {
            binding.txtPerfilSelec.setError("Elija un perfil correcto");
        } else {
            firebaseSingUp(email, password, perfil);
        }
    }

    private void firebaseSingUp(String email, String password, String perfil) {
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    progressDialog.dismiss();
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    String correoFire = firebaseUser.getEmail();
                    Toast.makeText(RegistrarActivity.this, "Cuenta Creada\n" + correoFire, Toast.LENGTH_SHORT).show();

                    //
//                    String id = firebaseAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
//                    map.put("id", id);
                    map.put("Correo", email);
                    map.put("Contraseña", password);
                    map.put("Tipo de perfil", perfil);

                    mFirestore.collection("Usuarios").document("Operaciones").set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(RegistrarActivity.this, "Usuario guardado", Toast.LENGTH_SHORT).show();
                            finish();
                            if (perfil.equals("Tratador")) {
                                startActivity(new Intent(RegistrarActivity.this, AdminConsoleActivity.class));
                            } else if (perfil.equals("Comandante")) {
                                startActivity(new Intent(RegistrarActivity.this, ComandanteActivity.class));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegistrarActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                        }
                    });
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegistrarActivity.this, "Error al crear cuenta" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
//
//                    progressDialog.dismiss();
//                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//                    String correoFire = firebaseUser.getEmail();
//                    Toast.makeText(RegistrarActivity.this, "Cuenta Creada\n" + correoFire, Toast.LENGTH_SHORT).show();
//
//                    Intent ingComandante = new Intent(RegistrarActivity.this, ComandanteActivity.class);
//                    startActivity(ingComandante);


//                        if (seleccionSP.getText().toString().equals("Tratador")) {
//                            Intent ingTratador = new Intent(RegistrarActivity.this, AdminAgregarActivity.class);
//                            startActivity(ingTratador);
//                        } else {
//                            Intent ingComandante = new Intent(RegistrarActivity.this, ComandanteActivity.class);
//                            startActivity(ingComandante);
//                        }
//
//                    finish();
//

//}}
//    private void inicializarFirebase() {
//        FirebaseApp.initializeApp(this);
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference();
//        Toast.makeText(this, "Base de datos inicializada", Toast.LENGTH_SHORT).show();
//    }
