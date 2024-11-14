package com.utn.myaplicationutn;

import android.content.Intent;
import android.media.AudioRecord;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //Crear variables para interactuar en el tiempo de ejecución
TextView label1, label2, lblResultado, lblTitulo;
EditText txtId, txtNombre, txtApellido, txtIsoPais, txtEdad;

Autores lsAutores;
Button cmd_Crear;


    Integer n;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Instanciar las variables de Views
        txtId =findViewById(R.id.txtId);
        txtNombre =findViewById(R.id.txtNombre);
        txtApellido =findViewById(R.id.txtApellido);
        txtIsoPais =findViewById(R.id.txtIsoPais);
        txtEdad= findViewById(R.id.txtEdad);
        lblTitulo=findViewById(R.id.lblTitulo);

        lsAutores = new Autores(this,"bliblioteca2",1);

        Bundle extra = getIntent().getExtras();
        lblTitulo.setText("BIENVENIDO "+extra.getString("usuario"));

        // operar con los atributos de los views



    }
    public void cmdCrear_onClick(View v)
    {
        Autor autor =lsAutores.Create(
          Integer.parseInt(txtId.getText().toString() ),
                txtNombre.getText().toString(),
                txtApellido.getText().toString(),
                txtIsoPais.getText().toString(),
                Integer.parseInt(txtEdad.getText().toString())
          );

        if (autor != null){
            Toast.makeText(this,"Autor Creado Correctamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Autor Creado Incorrectamente", Toast.LENGTH_SHORT).show();
        }

    }


    public void btnRegresar_onClick(View v)
    {
        finish();

    }

    public void cmdUpdate_onClick (View v)
    {
        Autor autor =lsAutores.Update(
                Integer.parseInt(txtId.getText().toString() ),
                txtNombre.getText().toString(),
                txtApellido.getText().toString(),
                txtIsoPais.getText().toString(),
                Integer.parseInt(txtEdad.getText().toString())
        );
        if (autor != null){
            Toast.makeText(this,"Autor Actualizado Correctamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Autor Actualizado Incorrectamente", Toast.LENGTH_SHORT).show();
        }
    }

    public void cmd_Leer (View v)
    {
    Autor autor = lsAutores.Read_ById(
            Integer.parseInt(txtId.getText().toString())
    );
        if (autor != null){
           txtNombre.setText(autor.Nombres);
            txtApellido.setText(autor.Apellidos);
            txtIsoPais.setText(autor.IsoPais);
            txtEdad.setText(""+autor.Edad);

            // Crear e insertar el fragment con los libros del autor
            Fragment_Libros fragment = Fragment_Libros.newInstance(autor.Id);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }else{
            Toast.makeText(this,"Autor No Encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    public void cmd_Delete (View v)
    {
        Autor autor = lsAutores.Read_ById(
                Integer.parseInt(txtId.getText().toString())
        );

        if (autor != null){
            lsAutores.Delete(autor.Id);
            txtId.setText("");
            txtNombre.setText("");
            txtApellido.setText("");
            txtIsoPais.setText("");
            txtEdad.setText(" ");
            Toast.makeText(this,"Autor Borrado Correctamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Autor No Existente", Toast.LENGTH_SHORT).show();
        }




    }

    public void btn_libros_onClick(View v) {
        Intent i = new Intent(this, LibrosActivity.class);
        startActivity(i);
    }
}