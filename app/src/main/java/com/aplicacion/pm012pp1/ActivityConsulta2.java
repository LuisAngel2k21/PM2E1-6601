package com.aplicacion.pm012pp1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aplicacion.pm012pp1.Procesos.Paises;
import com.aplicacion.pm012pp1.Procesos.SQLiteConexion;


public class ActivityConsulta2 extends AppCompatActivity {

    SQLiteConexion conexion;
    EditText id, nombres, edad;
    Button btnconsulta, btneliminar, btnactualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta2);

        init();

        btnconsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuscarEmpleado();
            }
        });
    }

    private void BuscarEmpleado()
    {
        try
        {
            SQLiteDatabase db = conexion.getWritableDatabase();
            /* Parametros de BUSQUEDA de la sentencia SELECT*/
            String [] params = {id.getText().toString()};

            /* Campos a retornar  de la sentencia SELECT*/
            String [] fields = { Paises.nombre,
                    Paises.codigo};

            String WhereCondition = Paises.id + "=?";

            Cursor cdata = db.query(Paises.tablaPais,
                    fields,
                    WhereCondition,params,null,null,null);

            cdata.moveToFirst();

            if(cdata.getCount() > 0 )
            {
                nombres.setText(cdata.getString(0));
                edad.setText(cdata.getString(1));


                Toast.makeText(getApplicationContext(),"Consultado con exito !!",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"No hay datos !!",Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),"ha ocurrido un inconveniente !!",Toast.LENGTH_LONG).show();
        }


    }

    private void Eliminar()
    {


    }

    private void init()
    {
        conexion = new SQLiteConexion(this, Paises.NameDataBase, null, 1);

        btnconsulta = (Button) findViewById(R.id.btnbuscar2);
        btneliminar = (Button) findViewById(R.id.btneliminar);
        btnactualizar = (Button) findViewById(R.id.btnactualizar);

        id = (EditText) findViewById(R.id.txtcid);
        nombres = (EditText) findViewById(R.id.txtcnombres2);
        edad = (EditText) findViewById(R.id.txtcedad2);

    }
}