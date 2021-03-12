package com.joel.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ventas extends AppCompatActivity {

    private EditText etCodigo;
    private TextView tvNombreProductoc,vtStockc,vtPrecioVentac,tvTotalPagarc,vtPCost;
    private DatabaseReference BD;
    private EditText etCantidad;
    private Button btnTotalPagarc,btnVenderc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);

        etCodigo = (EditText)findViewById(R.id.etCodigoc);

        tvNombreProductoc = (TextView)findViewById(R.id.tvNombreProductoc);
        vtStockc = (TextView)findViewById(R.id.vtStockc);
        vtPrecioVentac = (TextView)findViewById(R.id.vtPrecioVentac);
        tvTotalPagarc = (TextView)findViewById(R.id.tvTotalPagarc);
        vtPCost=(TextView)findViewById(R.id.vtPCost);
        etCantidad = (EditText)findViewById(R.id.etCantidadc);
        btnVenderc = (Button)findViewById(R.id.btnVenderc);
        btnTotalPagarc = (Button)findViewById(R.id.btnTotalPagarc);

        BD= FirebaseDatabase.getInstance().getReference("Clases");


    }


    public void buscarv(View view){
        String codigo = etCodigo.getText().toString();

        DatabaseReference actualiz =BD.child("producto").child(codigo);
        actualiz.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvNombreProductoc.setText(snapshot.child("producto").getValue().toString());
                vtStockc.setText((snapshot.child("stock").getValue().toString()));
                vtPrecioVentac.setText((snapshot.child("venta").getValue().toString()));
                vtPCost.setText(snapshot.child("costo").getValue().toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ventas.this,"Se produjo un error "+error,Toast.LENGTH_LONG).show();
            }
        });
    }



    public void vender(View view){

        String cant = etCantidad.getText().toString();
        String product =  tvNombreProductoc.getText().toString();
        String cost =  vtPCost.getText().toString();
        String v= vtPrecioVentac.getText().toString();
        String s = vtStockc.getText().toString();

                int cantidad = Integer.parseInt(cant);
                float venta = Float.parseFloat(v);
                int stock = Integer.parseInt(s);

                float totalPagar= cantidad*venta;
                int totalStock = stock-cantidad;

                if (totalStock<=0){
                    Toast.makeText(ventas.this,"No hay Stock suficiente ",Toast.LENGTH_LONG).show();
                }else{
                    actualizar(String.valueOf(totalStock),product,cost,v);
                    tvTotalPagarc.setText(String.valueOf(totalPagar));

                }




    }
    public void totalPagar(String totalpagar){
        tvTotalPagarc.setText(String.valueOf(totalpagar));
    }

    public void actualizar(String stock,String productos, String costo, String venta ){
        String codigo = etCodigo.getText().toString();

        cProducto producto = new cProducto(codigo, productos, stock, costo, venta);
        BD.child("producto").child(codigo).setValue(producto);
        Toast.makeText(this,"Actualizado",Toast.LENGTH_LONG).show();

    }


}