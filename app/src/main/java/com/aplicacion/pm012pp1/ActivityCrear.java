package com.aplicacion.pm012pp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.aplicacion.pm012pp1.Procesos.Empleados;
import com.aplicacion.pm012pp1.Procesos.SQLiteConexion;
import com.aplicacion.pm012pp1.Procesos.Transacciones;
import com.aplicacion.pm012pp1.Procesos.Paises;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityCrear extends AppCompatActivity {

    Button btnagregar,btnagregar2,btnTomarFoto;
    EditText txtnombres, txtedad,txtcomentario;
    SQLiteConexion conexion;
    Spinner comboEmpleado;
    EditText nombres, edad;


    ArrayList<Empleados> listaempleados;
    ArrayList<String> ArregloEmpleados;

    static final int PETICION_ACCESO_CAM = 100;
    static final int TAKE_PIC_REQUEST = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        /* Casteo de elementos de la interfaz grafica */
        init();

        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarEmpleado();
            }
        });

     }

     private void init()
     {
         txtnombres = (EditText) findViewById(R.id.txtnombres);
         txtedad = (EditText) findViewById(R.id.txtedad);
         txtcomentario = (EditText)findViewById(R.id.txtcomentario);
         comboEmpleado =(Spinner) findViewById(R.id.comboPersona);
         btnagregar = (Button) findViewById(R.id.btnagregar);
         btnagregar2 = (Button) findViewById(R.id.btnagregar2);




         conexion = new SQLiteConexion(this, Paises.NameDataBase, null, 1);

         ObtenerListaEmpleados();

         ArrayAdapter<CharSequence> adp = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ArregloEmpleados);
         comboEmpleado.setAdapter(adp);

         comboEmpleado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
             {


             }

             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {

             }
         });
     }


    private void ObtenerListaEmpleados()
    {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Empleados pais = null;
        listaempleados = new ArrayList<Empleados>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Paises.tablaPais, null);

        while(cursor.moveToNext())
        {
            pais = new Empleados();
            pais.setId(cursor.getInt(0));
            pais.setNombre(cursor.getString(1));
            pais.setCodigo(cursor.getInt(2));
            listaempleados.add(pais);
        }

        cursor.close();
        fllList();

    }

    private void fllList()
    {
        ArregloEmpleados = new ArrayList<String>();

        for(int i = 0; i<listaempleados.size(); i ++)
        {
            ArregloEmpleados.add(listaempleados.get(i).getId()  + " + "
                    + listaempleados.get(i).getNombre());
        }



        btnagregar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityPais.class);

                startActivity(intent);
            }
        });



    }




    private void AgregarEmpleado()
    {



        /* Conexion e Inserccion a la base de datos */
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombres, txtnombres.getText().toString());
        valores.put(Transacciones.edad, txtedad.getText().toString());
        valores.put(Transacciones.comentario, txtcomentario.getText().toString());


        Long resultado = db.insert(Transacciones.tablaEmpleados, Transacciones.id, valores);

        Toast.makeText(getApplicationContext(), "Registro Ingresado", Toast.LENGTH_LONG).show();

        db.close();

        ClearScreen();

    }

    private void ClearScreen()
    {
        txtnombres.setText("");
        txtedad.setText("");
        txtcomentario.setText("");

    }



}
