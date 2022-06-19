package com.aplicacion.pm012pp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aplicacion.pm012pp1.Procesos.Paises;
import com.aplicacion.pm012pp1.Procesos.SQLiteConexion;


public class ActivityPais extends AppCompatActivity {

    Button btnagregar3,btnregresar;
    EditText txtnombresp, txtcodigopais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pais);

        /* Casteo de elementos de la interfaz grafica */
        init();




        btnagregar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarEmpleado();
            }
        });


        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityCrear.class);

                startActivity(intent);
            }
        });




    }

    private void init()
    {
        txtnombresp = (EditText) findViewById(R.id.txtnombrep);
        txtcodigopais = (EditText) findViewById(R.id.txtcodigopais);
        btnagregar3 = (Button) findViewById(R.id.btnagregar3);
        btnregresar = (Button) findViewById(R.id.btnregresar);

    }


    private void AgregarEmpleado()
    {
        /* Conexion e Inserccion a la base de datos */
        SQLiteConexion conexion = new SQLiteConexion(this, Paises.NameDataBase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Paises.nombre, txtnombresp.getText().toString());
        valores.put(Paises.codigo, txtcodigopais.getText().toString());



        Long resultado = db.insert(Paises.tablaPais, Paises.id, valores);

        Toast.makeText(getApplicationContext(), "Registro Ingresado", Toast.LENGTH_LONG).show();

        db.close();

        ClearScreen();
    }

    private void ClearScreen()
    {
        txtnombresp.setText("");
        txtcodigopais.setText("");


    }
}