package com.joel.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//paquetes fecebook


public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener{

    private Button btnRegistrarUsu,btnIngresar;
    private EditText etUsu, etPass;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    //google
    SignInButton signInButton;
    GoogleApiClient googleApiClient;
    private static final String TAG="SignInActivity";
    private static final int RC_SIGN_IN=9001;




    private Spinner spinner;
    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnIngresar= (Button)findViewById(R.id.btnIngresar);
        btnRegistrarUsu= (Button)findViewById(R.id.btnRegistrarUsu);
        etUsu=(EditText)findViewById(R.id.etUsu);
        etPass=(EditText)findViewById(R.id.etPass);
        progressDialog = new ProgressDialog(this);

        btnRegistrarUsu.setOnClickListener(this);
        btnIngresar.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();//instancia de firebase

        spinner = (Spinner)findViewById(R.id.spinner);
        database = FirebaseDatabase.getInstance().getReference("database");

        //Elegir la entrada

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tipoEntrada);
        spinner.setAdapter(adapter);
    }
    String tipoEntrada[] = {"Tipo Ingreso","Persona","Producto","Inventario"};

    public String tipoIngreso(){
        int opcion = spinner.getSelectedItemPosition();
        String op="";
        switch (opcion){
            case 0:
                op="vacio";
                break;
            case 1:
                op= "persona";
                break;
            case 2:
                op="producto";
                break;
            case 3:
                op="inventario";
                break;
        }
        return  op;
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG,"onConnectionFailed:"+connectionResult);
    }





    //autenticacion de correo y contraseña

    public void verificarUsuario(){
        String email= etUsu.getText().toString().trim();
        String password= etPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Ingrese el email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Ingrese la contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Verificando datos");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            if (tipoIngreso() != "vacio"){
                                if (tipoIngreso() == "persona"){
                                    Intent persona = new Intent(getApplication(), persona.class);
                                    startActivity(persona);
                                    Toast.makeText(Login.this,"Bienvenido",Toast.LENGTH_LONG).show();
                                }else if (tipoIngreso() == "producto"){
                                    Intent producto = new Intent(getApplication(), producto.class);
                                    startActivity(producto);
                                    Toast.makeText(Login.this,"Bienvenido",Toast.LENGTH_LONG).show();
                                }else if (tipoIngreso() == "inventario"){
                                    Intent inventario = new Intent(getApplication(), inventario.class);
                                    startActivity(inventario);
                                    Toast.makeText(Login.this,"Bienvenido",Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(Login.this,"Debe elegir una de las opciones de Entrada",Toast.LENGTH_LONG).show();
                                progressDialog.cancel();
                            }

                        }else{
                            Toast.makeText(Login.this,"Usuario o Clave incorrectos",Toast.LENGTH_LONG).show();
                            progressDialog.cancel();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnIngresar:
                verificarUsuario();
                break;
            case R.id.btnRegistrarUsu:
                Intent registrar = new Intent(this,MainActivity.class);
                startActivity(registrar);
                break;

        }

    }



}