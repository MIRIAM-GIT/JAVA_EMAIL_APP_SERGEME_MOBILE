package com.example.java_email;

import android.app.ProgressDialog;

import android.content.Context;
import android.os.AsyncTask;

import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail extends AsyncTask<Void,Void,Void>{

    //Declaracion de Variables
    private Context context;
    private Session session;

    //Información para enviar correo electrónico
    private String email;
    private String encabezado;
    private String mensaje;

    //Progressdialog para mostrar al enviar correo electrónico
    private ProgressDialog progressDialog;

    //Clase Constructor
    public SendMail(Context context, String email, String encabezado, String mensaje){
        //Inicializando variables
        this.context = context;
        this.email = email;
        this.encabezado = encabezado;
        this.mensaje = mensaje;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Mostrando diálogo de progreso al enviar correo electrónico
        progressDialog = ProgressDialog.show(context,"Enviando mensaje","Espere ...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Descartar el cuadro de diálogo de progreso
        progressDialog.dismiss();
        //Mostrando un mensaje de éxito
        Toast.makeText(context,"Mensaje Enviado",Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... params) {

        //Creando propiedades
        Properties props = new Properties();

        // Configurando propiedades para gmail
        // Si no se utiliza gmail se tiene que cambiar los valores
        //colocamos el nombre del servidor de correo electronico
        props.put("mail.smtp.host", "smtp.gmail.com");
        //Socket para recivir respuesta del servidor de gmail, y el puerto del Socket
        props.put("mail.smtp.socketFactory.port", "465");
        //indico que nuestro Socket va a ser ssl para que se envie de manera segura la informacion
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //indico lo que voy a autentificar para el Socket
        props.put("mail.smtp.auth", "true");
        //Este es para el puerto de Gmail,o cualquier otro servidor de correo
        props.put("mail.smtp.port", "465");


        //Creando una nueva sesion
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Autenticar la contraseña
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
                    }
                });

        try {
            //Creando el objeto MimeMessage
            MimeMessage mm = new MimeMessage(session);

            //Configuración de la dirección del remitente
            mm.setFrom(new InternetAddress(Config.EMAIL));
            //Agregar receptor
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Añadiendo asunto
            mm.setSubject(encabezado);
            //Añadiendo mensaje
            mm.setText(mensaje);

            //enviar el mensaje de correo electronico
            Transport.send(mm);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}