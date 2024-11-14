package com.utn.myaplicationutn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Libros {

    private SqlLiteAdmin sqlAdmin;
    private SQLiteDatabase db;

    public Libros(Context ctx, String nombreBdd, int version) {
        sqlAdmin = new SqlLiteAdmin(ctx, nombreBdd, null, version);
        db = sqlAdmin.getWritableDatabase();
    }

    // Crear un nuevo libro
    public Libro Create(String titulo, int idAutor, int anioPublicacion) {
        ContentValues datos = new ContentValues();
        datos.put("titulo", titulo);
        datos.put("idAutor", idAutor);
        datos.put("anioPublicacion", anioPublicacion);

        long r = db.insert("libros", null, datos);

        if (r == -1)
            return null;
        else {
            // Crear un nuevo libro con el id asignado automáticamente por la base de datos
            Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            cursor.close();
            return new Libro(id, titulo, idAutor, anioPublicacion);
        }
    }

    // Actualizar los detalles de un libro
    public Libro Update(int id, String titulo, int idAutor, int anioPublicacion) {
        ContentValues datos = new ContentValues();
        datos.put("titulo", titulo);
        datos.put("idAutor", idAutor);
        datos.put("anioPublicacion", anioPublicacion);

        int r = db.update("libros", datos, "id=" + id, null);
        if (r == 0)
            return null;
        else {
            return new Libro(id, titulo, idAutor, anioPublicacion);
        }
    }

    // Leer un libro por su ID
    public Libro Read_ById(int id) {
        Libro registro = null;
        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM libros WHERE id=" + id, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            registro = new Libro(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
        }
        cursor.close();
        return registro;
    }
    // Método para obtener los libros de un autor por su ID
    public List<Libro> Read_ByAutorId(int autorId) {
        List<Libro> libros = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Realizamos la consulta SQL para obtener los libros con el autorId proporcionado
            cursor = db.rawQuery("SELECT * FROM libros WHERE idAutor = ?", new String[]{String.valueOf(autorId)});

            // Recorremos el cursor si hay resultados
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndexId = cursor.getColumnIndex("id");
                int columnIndexTitulo = cursor.getColumnIndex("titulo");
                int columnIndexAnioPublicacion = cursor.getColumnIndex("anioPublicacion");

                // Verificamos si los índices son válidos
                if (columnIndexId != -1 && columnIndexTitulo != -1 && columnIndexAnioPublicacion != -1) {
                    do {
                        // Extraemos los datos de las columnas usando los índices
                        int id = cursor.getInt(columnIndexId);
                        String titulo = cursor.getString(columnIndexTitulo);
                        int anioPublicacion = cursor.getInt(columnIndexAnioPublicacion);

                        // Creamos el objeto Libro y lo agregamos a la lista
                        Libro libro = new Libro(id, titulo, autorId, anioPublicacion);
                        libros.add(libro);
                    } while (cursor.moveToNext());
                } else {
                    // Si alguna columna no existe, puedes agregar un log o manejo de error
                    Log.e("DB_ERROR", "Columnas no encontradas en la consulta.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Si ocurre un error, lo imprimimos en los logs
        } finally {
            if (cursor != null) {
                cursor.close(); // Cerramos el cursor después de usarlo
            }
        }

        return libros; // Devolvemos la lista de libros encontrados
    }

    // Leer todos los libros
    public Libro[] Read_All() {
        Libro registro;
        Libro[] datos;
        Cursor cursor;
        int i = 0;

        cursor = db.rawQuery("SELECT * FROM libros ORDER BY titulo", null);
        datos = new Libro[cursor.getCount()];

        while (cursor.moveToNext()) {
            registro = new Libro(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
            datos[i++] = registro;
        }
        cursor.close();
        return datos;
    }

    // Eliminar un libro por su ID
    public String Delete(int id) {
        int r = db.delete("libros", "id=" + id, null);
        return r > 0 ? "Libro Eliminado" : "Error al eliminar libro";
    }
}
