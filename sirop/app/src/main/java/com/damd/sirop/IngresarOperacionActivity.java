package com.damd.sirop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class IngresarOperacionActivity extends AppCompatActivity {

    private TextView division;
    private TextView batallon;
    private TextView caso;
    private TextView bienes;
    private TextView enemigo;
    private TextView total;
    private EditText fecha;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_operacion);
        //Establecer el titulo
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ingresar Operación");
        inicializarFirebase();

        division = findViewById(R.id.txtDivision);
        batallon = findViewById(R.id.txtBatallon);
        caso = findViewById(R.id.txtCaso);
        bienes = findViewById(R.id.txtBienes);
        enemigo = findViewById(R.id.txtEnemigo);
        total = findViewById(R.id.txtTotal);

        fecha = findViewById(R.id.txtFecha);
//        fecha.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tratador, menu);
        return super.onCreateOptionsMenu(menu);

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        Toast.makeText(this, "Base de datos inicializada", Toast.LENGTH_SHORT).show();
    }

    public void ingresarOperacionTratadorIndex(View view) {
        Intent ingresarOperacTratador = new Intent(IngresarOperacionActivity.this, AdminConsoleActivity.class);
        startActivity(ingresarOperacTratador);
    }

    public void registrarDatos(View view) {
        if (division.getText().toString().isEmpty() || batallon.getText().toString().isEmpty() || caso.getText().toString().isEmpty() ||
                bienes.getText().toString().isEmpty() || enemigo.getText().toString().isEmpty() || total.getText().toString().isEmpty() ||
                fecha.getText().toString().isEmpty()) {

            Toast.makeText(this, "Ingrese todos los datos", Toast.LENGTH_SHORT).show();

        } else {
            Intent regExitoso = new Intent(IngresarOperacionActivity.this, AdminConsoleActivity.class);
            startActivity(regExitoso);
            registroExitoso();
        }
    }

    public void registroExitoso() {
        Toast.makeText(this, "Información Registrada", Toast.LENGTH_SHORT).show();
    }
}