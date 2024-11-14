package com.utn.myaplicationutn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class Autores {

    private SqlLiteAdmin sqlAdmin;
    private SQLiteDatabase db;

    public Autores(Context ctx, String nombreBdd, int version) {
        sqlAdmin = new SqlLiteAdmin(ctx, nombreBdd, null, version);
        db = sqlAdmin.getWritableDatabase();
    }

    public Autor Create(int id, String nombres, String apellidos, String isoPais, int edad) {
        ContentValues datos = new ContentValues();
        datos.put("id", id);
        datos.put("nombres", nombres);
        datos.put("apellidos", apellidos);
        datos.put("isoPais", isoPais);
        datos.put("edad", edad);

        long r = db.insert("autores", null, datos);  // Cambié "id, nombres, apellidos..." por null en el insert.

        if (r == -1)
            return null;
        else {
            // Crear un nuevo autor con una lista vacía de libros
            return new Autor(id, nombres, apellidos, isoPais, edad, new ArrayList<Libro>());
        }
    }

    public Autor Update(int id, String nombres, String apellidos, String isoPais, int edad) {
        ContentValues datos = new ContentValues();
        datos.put("id", id);
        datos.put("nombres", nombres);
        datos.put("apellidos", apellidos);
        datos.put("isoPais", isoPais);
        datos.put("edad", edad);

        int r = db.update("autores", datos, "id=" + id, null);
        if (r == 0)
            return null;
        else {
            // Crear un nuevo autor con una lista vacía de libros
            return new Autor(id, nombres, apellidos, isoPais, edad, new ArrayList<Libro>());
        }
    }

    public Autor Read_ById(int id) {
        Autor registro = null;
        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM autores WHERE id=" + id, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            registro = new Autor(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), new ArrayList<Libro>());
        }
        cursor.close();
        return registro;
    }

    public Autor[] Read_All() {
        Autor registro;
        Autor[] datos;
        Cursor cursor;
        int i = 0;

        cursor = db.rawQuery("SELECT * FROM autores ORDER BY apellidos, nombres", null);
        datos = new Autor[cursor.getCount()];

        while (cursor.moveToNext()) {
            registro = new Autor(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), new ArrayList<Libro>());
            datos[i++] = registro;
        }
        cursor.close();
        return datos;
    }

    public String Delete(int id) {
        int r = db.delete("autores", "id=" + id, null);
        return "Autor Eliminado";
    }
}
