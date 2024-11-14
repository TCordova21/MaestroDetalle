package com.utn.myaplicationutn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.List;

public class Fragment_Libros extends Fragment {

    public ListView lvLibros;
    public Libros libros;
    public int autorId;

    // Método estático para crear una nueva instancia del fragment con el ID del autor
    public static Fragment_Libros newInstance(int autorId) {
        Fragment_Libros fragment = new Fragment_Libros();
        Bundle args = new Bundle();
        args.putInt("autorId", autorId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla el layout del fragment
        View rootView = inflater.inflate(R.layout.fragment__libros, container, false);
        lvLibros = rootView.findViewById(R.id.lvLibros);
        libros = new Libros(getActivity(), "bliblioteca2", 1);

        // Obtiene el ID del autor desde los argumentos
        if (getArguments() != null) {
            autorId = getArguments().getInt("autorId");
            loadBooksForAutor(autorId);
        }

        return rootView;
    }

    // Método para cargar los libros del autor
    public void loadBooksForAutor(int autorId) {
        List<Libro> listaLibros = libros.Read_ByAutorId(autorId);

        // Usamos un ArrayAdapter para mostrar los libros en el ListView
        ArrayAdapter<Libro> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listaLibros);
        lvLibros.setAdapter(adapter);
    }
}
