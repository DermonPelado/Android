package com.example.proyectofinalpro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TareaAsincronicaDescargarFoto  extends AsyncTask<String, Void, Bitmap> {
    private ImageView urlFoto;

    public void setFoto(ImageView urlFoto) {
        this.urlFoto = urlFoto;
    }

    protected Bitmap doInBackground(String... Ruta) {
        Bitmap imagenFinal = null;

        try {
            URL miRuta = new URL(Ruta[0]);
            Log.d("RutaFoto", miRuta.getPath());

            HttpURLConnection conexionURL = (HttpURLConnection) miRuta.openConnection();
            if (conexionURL.getResponseCode() == 200) {
                InputStream cuerpoDatos = conexionURL.getInputStream();
                BufferedInputStream lectorEntrada = new BufferedInputStream(cuerpoDatos);
                imagenFinal = BitmapFactory.decodeStream(lectorEntrada);
                conexionURL.disconnect();
            }
        } catch (Exception error) {
            Log.d("Excepcion", error.getMessage());
        }

        return imagenFinal;
    }

    protected void onPostExecute(Bitmap imagenFinal) {
        if (imagenFinal != null) {
            urlFoto.setImageBitmap(imagenFinal);
        }
        else {
            urlFoto.setImageResource(android.R.drawable.ic_dialog_alert);
        }
    }

}

