package com.example.java_email;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Declaración de EditText
    private EditText editTextEmail;
    private EditText editTextEncabezado;
    private EditText editTextMensaje;

    //Botón de enviar
    private Button BotonEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializando las vistas
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextEncabezado = (EditText) findViewById(R.id.editTextEncabezado);
        editTextMensaje = (EditText) findViewById(R.id.editTextMensaje);

        BotonEnviar = (Button) findViewById(R.id.BotonEnviar);

        //Agregar clicklistener
        BotonEnviar.setOnClickListener(this);

    }

    private void sendEmail() {
        //Obtener contenido para correo electrónico
        String email = editTextEmail.getText().toString().trim();
        String encabezado = editTextEncabezado.getText().toString().trim();
        String mensaje = editTextMensaje.getText().toString().trim();

        //Crear objeto SendMail
        SendMail sm = new SendMail(this, email, encabezado, mensaje);

        //Ejecutando sendmail para enviar correo electrónico
        sm.execute();
    }

    @Override
    public void onClick(View v) {
        sendEmail();
    }
}