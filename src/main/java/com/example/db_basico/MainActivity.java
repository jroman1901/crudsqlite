package com.example.db_basico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText txtcodigo, txtdescrpcion, txtcantidad, txtprecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtcodigo = (EditText) findViewById(R.id.et1);
        txtdescrpcion = (EditText) findViewById(R.id.et2);
        txtcantidad = (EditText) findViewById(R.id.et3);
        txtprecio = (EditText) findViewById(R.id.et4);

    }
    public void Guardar(View v)
    {
     AdminSQLiteOpenHelper  admin = new AdminSQLiteOpenHelper(this,"inventario",null, 1);
     SQLiteDatabase db = admin.getWritableDatabase();
     String  codigo = txtcodigo.getText().toString();
     String descripcion = txtdescrpcion.getText().toString();
     Integer cantidad = Integer.parseInt(txtcantidad.getText().toString());
     Float precio = Float.parseFloat(txtprecio.getText().toString());
        ContentValues contenedor = new ContentValues();
        contenedor.put("codigo", codigo);
        contenedor.put("descripcion ", descripcion);
        contenedor.put("cantidad",cantidad);
        contenedor.put("precio",precio);
    db.insert("articulos",null,contenedor);
        Toast.makeText(this,"Datos guardados con exito!!", Toast.LENGTH_SHORT).show();

        limpiar();

    }
    public void limpiar()
    {
     txtcodigo.setText("");
     txtdescrpcion.setText("");
     txtcantidad.setText("");
     txtprecio.setText("");
    }

    public void Consultar(View v)
    {
        AdminSQLiteOpenHelper  admin = new AdminSQLiteOpenHelper(this,"inventario",null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String codigo = txtcodigo.getText().toString();
        Cursor fila = db.rawQuery("select codigo, descripcion,cantidad, precio from articulos  where codigo ='"+codigo+"'",null);
       if(fila.moveToFirst()) {
            txtcodigo.setText(fila.getString(0));
           txtdescrpcion.setText(fila.getString(1));
           txtcantidad.setText(fila.getString(2));
           txtprecio.setText(fila.getString(3));
       }
       else
       {
           Toast.makeText(this,"no existen coincidencias con la base de datos", Toast.LENGTH_SHORT).show();
            limpiar();
       }


    }

    public void Eliminar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "inventario", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod= txtcodigo.getText().toString();
        int cant = bd.delete("articulos", "codigo=" + cod, null);
        bd.close();
         limpiar();
        if (cant == 1)
            Toast.makeText(this, "Se borró el artículo con dicho código",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe un artículo con dicho código",
                    Toast.LENGTH_SHORT).show();
    }


}