package com.utn.myaplicationutn;

import java.util.ArrayList;
import java.util.List;

public class Autor {
    public int Id;
    public String Nombres;
    public String Apellidos;
    public String IsoPais;
    public int Edad;
    public List<Libro> libros;

    // Constructor original
    public Autor(int id, String nombres, String apellidos, String isoPais, int edad, List<Libro> libros) {
        Id = id;
        Nombres = nombres;
        Apellidos = apellidos;
        IsoPais = isoPais;
        Edad = edad;
        this.libros = libros;
    }

    // Constructor sin la lista de libros (agregado para el uso en la clase Autores)
    public Autor(int id, String nombres, String apellidos, String isoPais, int edad) {
        Id = id;
        Nombres = nombres;
        Apellidos = apellidos;
        IsoPais = isoPais;
        Edad = edad;
        this.libros = new ArrayList<>();  // Inicializa la lista vacía
    }
}
