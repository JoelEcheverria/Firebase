package com.joel.firebaseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class persona extends AppCompatActivity {

    private EditText etCedula,etNombre,etProvincia;
    private RadioButton rbHombre,rbMujer;
    private Spinner spinnerp;
    private TextView tvCorreo;
    private String id;
    private DatabaseReference BD;
    private Button actualizar ,salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona);

        etCedula = (EditText)findViewById(R.id.etCedula);
        etNombre = (EditText)findViewById(R.id.etNombre );
        etProvincia = (EditText)findViewById(R.id.etProvincia);
        rbHombre = (RadioButton)findViewById(R.id.rbHombre);
        rbMujer = (RadioButton)findViewById(R.id.rbMujer);
        spinnerp = (Spinner)findViewById(R.id.spinnerp);
        tvCorreo = (TextView)findViewById(R.id.tvCorreo);
        String correo = getIntent().getStringExtra("correo");
        BD = FirebaseDatabase.getInstance().getReference("Clases");


        id = getIntent().getStringExtra("clave");
        tvCorreo.setText(correo);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,paises);
        spinnerp.setAdapter(adapter);
        actualizar = (Button)findViewById(R.id.btnActualizars);
        salir = (Button)findViewById(R.id.btnSalir);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cedula = etCedula.getText().toString();
                String nombre = etNombre.getText().toString();
                String provincia = etProvincia.getText().toString();
                String sexo = sexo();

                clases persona = new clases(cedula,nombre,provincia,sexo,pais());
                BD.child("persona").child(id).setValue(persona);
                Toast.makeText(persona.this,"Datos Guardados",Toast.LENGTH_LONG).show();
            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
    String paises[]={"Seleccione un País","Ecuador","Colombia","Venezuela"};

    public String sexo(){
        if(rbHombre.isChecked()){
            return "hombre";
        }else{
            return "mujer";
        }
    }

    public String pais(){
        int selecPais = spinnerp.getSelectedItemPosition();
        String p="";
        switch (selecPais){
            case 1:
                p= "Ecuador";
                break;
            case 2:
                p= "Colombia";
                break;
            case 3:
                p= "Venezuela";
                break;
            default: p= "";
                break;
        }
        return p;
    }




}