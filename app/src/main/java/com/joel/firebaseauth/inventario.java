package com.joel.firebaseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class inventario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.op, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.opCerrar) {
            finish();
        }
        if (id==R.id.opCompra) {
            Intent i = new Intent(this, compras.class);
            startActivity(i);
        }
        if (id==R.id.opVenta) {
            Intent i = new Intent(this, ventas.class);
            startActivity(i);
        }
        if (id==R.id.opLista) {
            Intent i = new Intent(this, listaProductos.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}