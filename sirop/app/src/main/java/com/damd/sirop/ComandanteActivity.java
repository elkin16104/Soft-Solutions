package com.damd.sirop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.damd.sirop.model.Comandante;
import com.damd.sirop.model.Tratador;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ComandanteActivity extends AppCompatActivity {

//    private ActionBar actionBar;

    private final List<Comandante> listaOperaciones = new ArrayList<>();
    ArrayAdapter<Comandante> arrayAdapterComandante;

    TextView division, batallon, caso, bienes, fecha, enemigo, total;
    ListView operacionesView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Comandante datoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comandante);

        //Establecer el titulo
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Comandante: Visualizar Operaciones");

        division = findViewById(R.id.viewDivision);
        batallon = findViewById(R.id.viewBatallon);
        caso = findViewById(R.id.viewCaso);
        bienes = findViewById(R.id.viewBienes);
        fecha = findViewById(R.id.viewFecha);
        enemigo = findViewById(R.id.viewEnemigo);
        total = findViewById(R.id.viewTotal);
        operacionesView = findViewById(R.id.viewOperacionesComandante);

        inicializarFirebase();
        listarDatosEnView();

        operacionesView.setOnItemClickListener((parent, view, position, id) -> {
            datoSeleccionado = (Comandante) parent.getItemAtPosition(position);
            division.setText(datoSeleccionado.getDivision());
            batallon.setText(datoSeleccionado.getBatallon());
            caso.setText(datoSeleccionado.getCaso());
            bienes.setText(datoSeleccionado.getBienes());
            fecha.setText(datoSeleccionado.getFecha());
            enemigo.setText(datoSeleccionado.getEnemigo());
            total.setText(datoSeleccionado.getTotal());
        });
    }

    private void listarDatosEnView() {
        databaseReference.child("Tratador").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaOperaciones.clear();
                for (DataSnapshot snapShotOject : snapshot.getChildren()) {
                    Comandante comandante = snapShotOject.getValue(Comandante.class);
                    listaOperaciones.add(comandante);
                    arrayAdapterComandante = new ArrayAdapter<>(ComandanteActivity.this, android.R.layout.simple_list_item_1, listaOperaciones);
                    operacionesView.setAdapter(arrayAdapterComandante);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
//        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
        Toast.makeText(this, "Conexion Base de datos", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_tratador, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
////        conversionString();
//        String divisionP = division.getText().toString();
//        String batallonP = batallon.getText().toString();
//        String casoP = caso.getText().toString();
//        String bienesP = bienes.getText().toString();
//        String fechaP = fecha.getText().toString();
//        String enemigoP = enemigo.getText().toString();
//        String totalP = total.getText().toString();
//
//        switch (item.getItemId()) {
//            case R.id.icono_guardar:
//                if (divisionP.equals("") || batallonP.equals("") || casoP.equals("") || bienesP.equals("") || fechaP.equals("") || enemigoP.equals("") || totalP.equals("")) {
//                    validacion();
//                } else {
//                    Tratador tratador = new Tratador();
//                    tratador.setUid(UUID.randomUUID().toString());
//                    tratador.setDivision(divisionP);
//                    tratador.setBatallon(batallonP);
//                    tratador.setCaso(casoP);
//                    tratador.setBienes(bienesP);
//                    tratador.setFecha(fechaP);
//                    tratador.setEnemigo(enemigoP);
//                    tratador.setTotal(totalP);
//
//                    //Guarda como Clase Tratador y los hijos
//                    databaseReference.child("Tratador").child(tratador.getUid()).setValue(tratador);
//                    Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show();
//                    limpiarCajas();
//                }
//                break;
//            case R.id.icono_actualizar:
//                Tratador datos = new Tratador();
//                datos.setUid(datoSeleccionado.getUid());
//                datos.setDivision(divisionP.toString().trim());
//                datos.setBatallon(batallonP.toString().trim());
//                datos.setCaso(casoP.toString().trim());
//                datos.setBienes(bienesP.toString().trim());
//                datos.setFecha(fechaP.toString().trim());
//                datos.setEnemigo(enemigoP.toString().trim());
//                datos.setTotal(totalP.toString().trim());
//
//                databaseReference.child("Tratador").child(datos.getUid()).setValue(datos);
//
//                Toast.makeText(this, "Actualizado", Toast.LENGTH_SHORT).show();
//                limpiarCajas();
//                break;
//            case R.id.icono_borrar:
//                Tratador tratador = new Tratador();
//                tratador.setUid(datoSeleccionado.getUid());
//                databaseReference.child("Tratador").child(tratador.getUid()).removeValue();
//
//                Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
//                limpiarCajas();
//                break;
//            default:
//                break;
//        }
//        return true;
//    }

    private void limpiarCajas() {
        division.setText("");
        batallon.setText("");
        caso.setText("");
        bienes.setText("");
        fecha.setText("");
        enemigo.setText("");
        total.setText("");
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

//    private void validacion() {
////        conversionString();
//        String divisionP = division.getText().toString();
//        String batallonP = batallon.getText().toString();
//        String casoP = caso.getText().toString();
//        String bienesP = bienes.getText().toString();
//        String fechaP = fecha.getText().toString();
//        String enemigoP = enemigo.getText().toString();
//        String totalP = total.getText().toString();
//
//        Drawable customErrorDrawable = getResources().getDrawable(R.drawable.ic_error_vacio);
//        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
//
//        if (divisionP.equals("")) {
//            division.setError("Ingrese una división", customErrorDrawable);
//        } else if (batallonP.equals("")) {
//            batallon.setError("Ingrese un batallón", customErrorDrawable);
//        } else if (casoP.equals("")) {
//            caso.setError("Ingrese un caso", customErrorDrawable);
//        } else if (bienesP.equals("")) {
//            caso.setError("Ingrese un bien", customErrorDrawable);
//        } else if (fechaP.equals("")) {
//            caso.setError("Ingrese una fecha", customErrorDrawable);
//        } else if (enemigoP.equals("")) {
//            caso.setError("Ingrese un enemigo", customErrorDrawable);
//        } else if (totalP.equals("")) {
//            caso.setError("Ingrese un total", customErrorDrawable);
//        } else {
//            Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show();
//        }
//    }
}
