package com.utn.myaplicationutn;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class SqlLiteAdmin extends SQLiteOpenHelper {

    public SqlLiteAdmin(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Creación de la tabla autores
        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS autores (" +
                        "id INTEGER NOT NULL PRIMARY KEY, " +
                        "nombres TEXT(50), " +
                        "apellidos TEXT(50), " +
                        "isoPais TEXT(5), " +
                        "edad INTEGER)"
        );

        // Creación de la tabla libros con clave foránea
        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS libros (" +
                        "id INTEGER NOT NULL PRIMARY KEY, " +
                        "titulo TEXT(100), " +
                        "idAutor INTEGER, " +
                        "anioPublicacion INTEGER, " +
                        "FOREIGN KEY (idAutor) REFERENCES autores(id))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Elimina las tablas si existen y luego crea las nuevas versiones
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS libros");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS autores");
        onCreate(sqLiteDatabase);
    }
}
