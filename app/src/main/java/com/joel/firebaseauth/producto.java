package com.joel.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class producto extends AppCompatActivity  {

    private EditText etCodigo,etProducto,etStock,etCosto,etVenta;
    private DatabaseReference BD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        etCodigo = (EditText)findViewById(R.id.etCodigo);
        etProducto = (EditText)findViewById(R.id.etNomProducto);
        etStock = (EditText)findViewById(R.id.etStock);
        etCosto = (EditText)findViewById(R.id.etPCosto);
        etVenta = (EditText)findViewById(R.id.etPVenta);

        BD= FirebaseDatabase.getInstance().getReference("Clases");


    }
    public void salir(View viw){
        finish();
    }


    public void guardar(View view){
        String codigo = etCodigo.getText().toString();
        String productos = etProducto.getText().toString();
        String stock = etStock.getText().toString();
        String costo = etCosto.getText().toString();
        String venta = etVenta.getText().toString();

        cProducto producto = new cProducto(codigo, productos, stock, costo, venta);
        BD.child("producto").child(codigo).setValue(producto);
        Toast.makeText(this,"Guardado",Toast.LENGTH_LONG).show();

    }

    public void actualizar(View view){
        String codigo = etCodigo.getText().toString();
        String productos = etProducto.getText().toString();
        String stock = etStock.getText().toString();
        String costo = etCosto.getText().toString();
        String venta = etVenta.getText().toString();

        cProducto producto = new cProducto(codigo, productos, stock, costo, venta);
        BD.child("producto").child(codigo).setValue(producto);
        Toast.makeText(this,"Actualizado",Toast.LENGTH_LONG).show();

    }


    public void buscar(View view){
        String codigo = etCodigo.getText().toString();

        DatabaseReference actualiz =BD.child("producto").child(codigo);
        actualiz.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                etProducto.setText(snapshot.child("producto").getValue().toString());
                etStock.setText((snapshot.child("stock").getValue().toString()));
                etCosto.setText(snapshot.child("costo").getValue().toString());
                etVenta.setText((snapshot.child("venta").getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(producto.this,"Se produjo un error "+error,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void borrar(View view){
        String codigo = etCodigo.getText().toString();

        Query eliminar = BD.child("producto").child(codigo);

        eliminar.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot obj: snapshot.getChildren()){
                        obj.getRef().removeValue();
                        Toast.makeText(producto.this,"Eliminado ",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(producto.this,"Se produjo un error "+error,Toast.LENGTH_LONG).show();
            }
        });
    }


}
