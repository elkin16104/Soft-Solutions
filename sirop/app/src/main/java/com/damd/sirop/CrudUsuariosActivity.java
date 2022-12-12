package com.damd.sirop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.damd.sirop.databinding.ActivityCrudUsuariosBinding;
import com.damd.sirop.databinding.ActivityLoginAdminBinding;
import com.damd.sirop.model.Persona;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrudUsuariosActivity extends AppCompatActivity {

    private final List<Persona> listaUsuarios = new ArrayList<>();
    ArrayAdapter<Persona> arrayAdapterPersona;

    EditText nombre, apellido, correo, contrasena;
    ListView usuariosView;

    FirebaseFirestore mFirestore;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Persona datoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_usuarios);
        //Establecer el titulo
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("CRUD Usuarios");

        nombre = findViewById(R.id.txtNombre);
        apellido = findViewById(R.id.txtApellido);
        correo = findViewById(R.id.txtCorreo);
        contrasena = findViewById(R.id.txtContrasena);
        usuariosView = findViewById(R.id.txtViewUsuarios);

        inicializarFirebase();
        listarDatosEnView();

        usuariosView.setOnItemClickListener((parent, view, position, id) -> {
            datoSeleccionado = (Persona) parent.getItemAtPosition(position);
            nombre.setText(datoSeleccionado.getNombre());
            apellido.setText(datoSeleccionado.getApellido());
            correo.setText(datoSeleccionado.getCorreo());
            contrasena.setText(datoSeleccionado.getContrasena());
        });
    }

    private void listarDatosEnView() {
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaUsuarios.clear();
                for (DataSnapshot snapShotOject : snapshot.getChildren()) {
                    Persona persona = snapShotOject.getValue(Persona.class);
                    listaUsuarios.add(persona);
                    arrayAdapterPersona = new ArrayAdapter<>(CrudUsuariosActivity.this, android.R.layout.simple_list_item_1, listaUsuarios);
                    usuariosView.setAdapter(arrayAdapterPersona);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        mFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
//        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
        Toast.makeText(this, "Conexion Base de datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tratador, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        conversionString();
        String nombreP = nombre.getText().toString();
        String apellidoP = apellido.getText().toString();
        String correoP = correo.getText().toString();
        String contrasenaP = contrasena.getText().toString();

        switch (item.getItemId()) {
            case R.id.icono_guardar:
                if (nombreP.equals("") || apellidoP.equals("") || correoP.equals("") || contrasenaP.equals("")) {
                    validacion();
                } else {
                    Persona persona = new Persona();
                    persona.setUid(UUID.randomUUID().toString());
                    persona.setNombre(nombreP);
                    persona.setApellido(apellidoP);
                    persona.setCorreo(correoP);
                    persona.setContrasena(contrasenaP);

                    //Guarda como Clase Persona y los hijos
                    databaseReference.child("Persona").child(persona.getUid()).setValue(persona);
                    Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show();
                    limpiarCajas();
                }
                break;
            case R.id.icono_actualizar:
                Persona datos = new Persona();
                datos.setUid(datoSeleccionado.getUid());
                datos.setNombre(nombreP.toString().trim());
                datos.setApellido(apellidoP.toString().trim());
                datos.setCorreo(correoP.toString().trim());
                datos.setContrasena(contrasenaP.toString().trim());
                databaseReference.child("Persona").child(datos.getUid()).setValue(datos);

                Toast.makeText(this, "Actualizado", Toast.LENGTH_SHORT).show();
                limpiarCajas();
                break;
            case R.id.icono_borrar:
                Persona persona = new Persona();
                persona.setUid(datoSeleccionado.getUid());
                databaseReference.child("Persona").child(persona.getUid()).removeValue();

                Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
                limpiarCajas();
                break;
            default:
                break;
        }
        return true;
    }

    private void limpiarCajas() {
        nombre.setText("");
        apellido.setText("");
        correo.setText("");
        contrasena.setText("");
    }

//    private void conversionString{
//        String divisionO = division.getText().toString();
//        String batallonP = batallon.getText().toString();
//        String casoP = caso.getText().toString();
//        String bienesP = bienes.getText().toString();
//        String fechaP = fecha.getText().toString();
//        String enemigoP = enemigo.getText().toString();
//        String totalP = total.getText().toString();
//    }

    private void validacion() {
//        conversionString();
        String nombreP = nombre.getText().toString();
        String apellidoP = apellido.getText().toString();
        String correoP = correo.getText().toString();
        String contrasenaP = contrasena.getText().toString();

        Drawable customErrorDrawable = getResources().getDrawable(R.drawable.ic_error_vacio);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());

        if (nombreP.equals("")) {
            nombre.setError("Ingrese una división", customErrorDrawable);
        } else if (apellidoP.equals("")) {
            apellido.setError("Ingrese un batallón", customErrorDrawable);
        } else if (correoP.equals("")) {
            correo.setError("Ingrese un caso", customErrorDrawable);
        } else if (contrasenaP.equals("")) {
            contrasena.setError("Ingrese un bien", customErrorDrawable);
        } else {
            Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show();
        }
    }
}