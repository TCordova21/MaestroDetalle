package com.utn.myaplicationutn;

public class Libro {
    public int Id;
    public String Titulo;
    public int IdAutor;
    public int AnioPublicacion;

    // Constructor para cuando se crea el libro desde la base de datos
    public Libro(int id, String titulo, int idAutor, int anioPublicacion) {
        this.Id = id;
        this.Titulo = titulo;
        this.IdAutor = idAutor;
        this.AnioPublicacion = anioPublicacion;
    }

    // Constructor sin id, para instanciar el libro antes de insertarlo en la base de datos
    public Libro(String titulo, int idAutor, int anioPublicacion) {
        this.Titulo = titulo;
        this.IdAutor = idAutor;
        this.AnioPublicacion = anioPublicacion;
    }
    public String toString() {
        return Titulo+" - "+AnioPublicacion;  // Devuelve el título del libro
    }

}
