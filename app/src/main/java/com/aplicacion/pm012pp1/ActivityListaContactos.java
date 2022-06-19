package com.aplicacion.pm012pp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.aplicacion.pm012pp1.Clases.Contactos;
import com.aplicacion.pm012pp1.Procesos.SQLiteConexion;
import com.aplicacion.pm012pp1.Procesos.Transacciones;

import java.util.ArrayList;

public class ActivityListaContactos extends AppCompatActivity {

    /* Variables globales */
    SQLiteConexion conexion;
    ListView lista;
    ArrayList<Contactos> listaContactos;
    ArrayList<String> ArregloContactos;
    EditText alctxtnombre;
    Button alcbtnAtras, btnactualizarContacto, btnEliminar, btnCompartir, btnVerImagen,btnRegresar;
    Intent intent;
    Contactos contacto;


    static final int PETICION_LLAMADA_TELEFONO = 102;


    int previousPosition = 1;
    int count = 1;
    long previousMil = 0;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);

        lista = (ListView) findViewById(R.id.LisViewContactos);
        intent = new Intent(getApplicationContext(), ActivityCrear.class);

        conexion = new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);

        obtenerlistaContactos();
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, ArregloContactos);
        lista.setAdapter(adp);

        alctxtnombre = (EditText) findViewById(R.id.alctxtnombre);

        alctxtnombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                adp.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        alcbtnAtras = (Button) findViewById(R.id.btnAtras);
        btnactualizarContacto = (Button) findViewById(R.id.btnActualizarContacto);
        btnEliminar = (Button) findViewById(R.id.btnaclEliminarContacto);
        btnCompartir = (Button) findViewById(R.id.btnCompartir);
        btnVerImagen = (Button) findViewById(R.id.btnVerImagen);

        alcbtnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityCrear.class);
                startActivity(intent);
            }
        });


        btnactualizarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);
            }
        });

        btnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarContacto();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setTitle("Eliminar Contacto");
                alertDialogBuilder
                        .setMessage("¿Está seguro de eliminar el contacto?")
                        .setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                eliminarContacto();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();


                alertDialog.show();

            }
        });


        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityCrear.class);

                startActivity(intent);
            }
        });

    }

    private void eliminarContacto() {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        int obtenerCodigo = contacto.getCodigo();

        db.delete(Transacciones.tablaEmpleados, Transacciones.id + " = " + obtenerCodigo, null);

        Toast.makeText(getApplicationContext(), "Registro eliminado con exito, Codigo " + obtenerCodigo
                , Toast.LENGTH_LONG).show();
        db.close();

        Intent intent = new Intent(this, ActivityListaContactos.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();


    }

    private void enviarContacto() {
        String contactoEnviado = "El numero de " + contacto.getNombreContacto().toString() +
                " es +" + contacto.getCodigoPais() + contacto.getNumeroContacto();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, contactoEnviado);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }


    private void setContactoSeleccionado() {

        intent.putExtra("codigo", contacto.getCodigo() + "");
        intent.putExtra("nombreContacto", contacto.getNombreContacto());
        intent.putExtra("numeroContacto", contacto.getNumeroContacto() + "");
        intent.putExtra("codigoPais", contacto.getCodigoPais() + "");
        intent.putExtra("notaContacto", contacto.getNota());

    }


    private void obtenerlistaContactos() {

        SQLiteDatabase db = conexion.getReadableDatabase();

        Contactos list_contact = null;
        listaContactos = new ArrayList<Contactos>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Transacciones.tablaEmpleados, null);

        while (cursor.moveToNext()) {
            list_contact = new Contactos();
            list_contact.setCodigo(cursor.getInt(0));
            list_contact.setNombreContacto(cursor.getString(1));
            list_contact.setNumeroContacto(cursor.getInt(2));
            list_contact.setNota(cursor.getString(3));
            list_contact.setCodigoPais(cursor.getString(5));
            listaContactos.add(list_contact);
        }
        cursor.close();
        llenarlista();

    }

    private void llenarlista() {
        ArregloContactos = new ArrayList<String>();

        for (int i = 0; i < listaContactos.size(); i++) {
            ArregloContactos.add(listaContactos.get(i).getNombreContacto() + " | " +
                    listaContactos.get(i).getCodigoPais() +
                    listaContactos.get(i).getNumeroContacto());

        }
    }
}