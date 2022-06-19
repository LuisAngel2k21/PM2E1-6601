package com.aplicacion.pm012pp1.Procesos;

public class Paises
{
    /*Nombre de la Base de datos */
    public static final String NameDataBase = "BDEXAMENPM-02";
    /* Creacion de tablas de la BB */
    public static final String tablaPais = "pais";

    /* Campos de la Tabla Empleados */
    public static final String id = "id";
    public static final String nombre = "nombre";
    public static final String codigo = "codigo";


    /* Sentencias SQL para crear tabla */
    public static final String CreateTablepais = "CREATE TABLE pais " +
                                                    "( id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                                    " nombre TEXT, codigo INTEGER)";

    public static final String DropTablepais = "DROP TABLE IF EXISTS pais";



}
