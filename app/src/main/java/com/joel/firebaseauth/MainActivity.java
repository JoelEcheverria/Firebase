package com.joel.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsu, etPass;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference clases;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth= FirebaseAuth.getInstance();
        etUsu=(EditText)findViewById(R.id.etUsu);
        etPass=(EditText)findViewById(R.id.etPass);

        btnRegistrar=(Button)findViewById(R.id.btnIngresar);
        progressDialog=new ProgressDialog(this);
        clases = FirebaseDatabase.getInstance().getReference("Clases");

        btnRegistrar.setOnClickListener(this);
    }



    private void RegistrarUsuario(){
        String email= etUsu.getText().toString().trim();
        String password= etPass.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Ingrese el email",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Ingrese la contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando Registro...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Se ha registrado exitosamente "+email,Toast.LENGTH_LONG).show();
                            progressDialog.cancel();
                            //crear la clase persona
                            String id = clases.push().getKey();
                            clases persona = new clases(id,email);
                            clases.child("persona").child(id).setValue(persona);

                            Intent i = new Intent(getApplication(), persona.class);
                            i.putExtra("correo",email);
                            i.putExtra("clave",id);
                            startActivity(i);
                            Toast.makeText(MainActivity.this,"Bievenido",Toast.LENGTH_LONG).show();


                        }else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(MainActivity.this,"Este usuario ya existe",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(MainActivity.this,"No se pudo registrar el usuario",Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        RegistrarUsuario();
    }
}