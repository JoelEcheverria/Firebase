package com.joel.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listaProductos extends AppCompatActivity {

    private ListView listView;
    private DatabaseReference BD;
    cProducto producto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        ArrayList<String> lista =new ArrayList<String>();
        listView=(ListView) findViewById(R.id.lista);
        BD=FirebaseDatabase.getInstance().getReference("Clases");
        producto=new cProducto();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.informacion,R.id.iformation,lista);
        BD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot ds: snapshot.getChildren()){
                    producto=ds.getValue(cProducto.class);

                    lista.add("Codigo: "+producto.getCodigo() +"\nProducto: "+producto.getProducto() +"\nStock:  "+producto.getStock()
                            +"\nPrecio Costo:  "+producto.getCosto() +"\nPrecio Venta:  "+producto.getVenta());
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplication(),"Error "+error,Toast.LENGTH_LONG).show();
            }
        });
    }
}