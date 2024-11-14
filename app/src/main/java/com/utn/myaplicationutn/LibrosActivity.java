package com.utn.myaplicationutn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibrosActivity extends AppCompatActivity {

    public EditText etTitulo, etAnioPublicacion;
    public Spinner spinnerAutores;
    public Button btnGuardarLibro, btnRegresar;
    public Libros libros;
    public Autores autores;
    public int libroId = -1; // Variable para saber si estamos editando un libro o creando uno nuevo

    // Lista para almacenar los IDs de los autores
    private List<Integer> autorIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros);

        // Inicialización de las vistas
        etTitulo = findViewById(R.id.etTitulo);
        etAnioPublicacion = findViewById(R.id.etAnioPublicacion);
        spinnerAutores = findViewById(R.id.spinnerAutores);
        btnGuardarLibro = findViewById(R.id.btnGuardarLibro);
        btnRegresar = findViewById(R.id.btnRegresar2);

        // Inicializar las clases para acceder a la base de datos
        libros = new Libros(this, "bliblioteca2", 1);
        autores = new Autores(this, "bliblioteca2", 1);

        // Cargar la lista de autores en el Spinner
        loadAutores();

        // Si es una edición de libro, obtener el ID del libro y cargar los datos
        if (getIntent().hasExtra("libroId")) {
            libroId = getIntent().getIntExtra("libroId", -1);
            loadLibroData(libroId);
        }

        // Configurar el botón para guardar o actualizar el libro
        btnGuardarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = etTitulo.getText().toString();
                String anioPublicacionStr = etAnioPublicacion.getText().toString();

                // Verificar que el título y año de publicación no estén vacíos
                if (titulo.isEmpty() || anioPublicacionStr.isEmpty()) {
                    Toast.makeText(LibrosActivity.this, "Por favor ingrese todos los datos", Toast.LENGTH_SHORT).show();
                    return;
                }

                int anioPublicacion = Integer.parseInt(anioPublicacionStr);

                // Obtener el ID del autor seleccionado usando el índice del Spinner
                int idAutor = autorIds.get(spinnerAutores.getSelectedItemPosition());

                if (libroId == -1) {
                    // Crear un nuevo libro si no tenemos ID
                    Libro libro = libros.Create(titulo, idAutor, anioPublicacion);
                    if (libro != null) {
                        Toast.makeText(LibrosActivity.this, "Libro guardado correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibrosActivity.this, "Error al guardar el libro", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Actualizar un libro existente
                    Libro libro = libros.Update(libroId, titulo, idAutor, anioPublicacion);
                    if (libro != null) {
                        Toast.makeText(LibrosActivity.this, "Libro actualizado correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibrosActivity.this, "Error al actualizar el libro", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Configurar el botón de regreso
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LibrosActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    // Método para cargar la lista de autores en el Spinner
    public void loadAutores() {
        // Obtener la lista de autores desde la base de datos
        List<Autor> listaAutores = Arrays.asList(autores.Read_All());

        // Crear una lista de nombres para el Spinner
        List<String> autorNombres = new ArrayList<>();
        for (Autor autor : listaAutores) {
            autorNombres.add(autor.Nombres + " " + autor.Apellidos);
            autorIds.add(autor.Id);  // Almacenar los IDs de los autores
        }

        // Crear el adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, autorNombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Configurar el Spinner
        spinnerAutores.setAdapter(adapter);
    }

    // Método para cargar los datos del libro en los campos cuando se edita
    public void loadLibroData(int libroId) {
        // Cargar los datos del libro a editar
        Libro libro = libros.Read_ById(libroId);
        if (libro != null) {
            etTitulo.setText(libro.Titulo);
            etAnioPublicacion.setText(String.valueOf(libro.AnioPublicacion));
            // Aquí puedes establecer el autor de forma predeterminada si es necesario
            // Por ejemplo, buscar el autor en la lista de autores y configurarlo
        } else {
            Toast.makeText(this, "Libro no encontrado", Toast.LENGTH_SHORT).show();
        }
    }
}
