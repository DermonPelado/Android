package com.example.proyectofinalpro;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TareaAsincronicaTodos extends AsyncTask<Void, Void, Void> {

    @Override


    protected void onPreExecute() { //antes de ejecutar el asyntask
        MainActivity.lstProductos.setVisibility(View.GONE); //desaparezco el mensaje
    }

    @Override
    protected Void doInBackground(Void... voids) { //mientras se ejecuta

        try {
            String strIP = "10.152.2.42";//ip
            String StrPuerto = ":52680";//puerto
            String strURL = "http://" + strIP + StrPuerto + "/api/Productos"; //ip de dermon + puerto + api + loquebusco*/

            URL laRuta = new URL(strURL); //creó la ruta a la cual me quiero conectar con la dirección creada

            HttpURLConnection miConexion = (HttpURLConnection) laRuta.openConnection(); //me conecto a esa ruta
            Log.d("AccesoAPI", "Me conecto");
            if (miConexion.getResponseCode() == 200) { //si la conexion devuelve un codigo 200 me pude conectar correctamente
                Log.d("AccesoAPI", "Conexion OK");
                InputStream cuerpoRespuesta = miConexion.getInputStream(); //agarras el texto de la pag
                InputStreamReader lectorRespuesta = new InputStreamReader(cuerpoRespuesta, "UTF-8"); //se lo asignas a un reader
                procesarJsonLeido(lectorRespuesta); //mandas reader a funcion que procesará el json
            } else { //no me pude conectar correctamente(puede ser wifi)
                Log.d("AccesoAPI", "ERROR en la conexion");
            }
            miConexion.disconnect();
        } catch (Exception e) {
            Log.d("AccesoAPI", "ERROR: " + e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) { //despues de ejecutar
        super.onPostExecute(aVoid);
        if (MainActivity.listaObjetosTraidos.size() > 0) { //si la lista tiene algo
            MainActivity.lstProductos.setAdapter(MainActivity.adaptadordeobjetos); //se pone los productos en la listview con el adapter
            MainActivity.lstProductos.setVisibility(View.VISIBLE); //El list view se pone visible
            MainActivity.txvMensajes.setVisibility(View.GONE); //el mensaje de cargando o error se borra
        } else {
            MainActivity.txvMensajes.setText("No hay productos disponible. Intente nuevamente"); //se muestra mensj de error
        }
    }


        /*Funciones */

        private void procesarJsonLeido (InputStreamReader streamLeido){
            JsonReader jsonLeido = new JsonReader(streamLeido); //creo un lector de json

            int idProducto = -1;
            String Titulo = "";
            String Foto = "";
            String Precio = "";

            try {
                jsonLeido.beginArray(); //recorro el array de productos
                while (jsonLeido.hasNext()) { //mientras haya elementos en el array(ej: producto 1, producto 2)
                    jsonLeido.beginObject(); //cada elemento del array es un objeto --> empiezo cada uno
                    while (jsonLeido.hasNext()) { //mientras cada elemento del array tenga dentro otros elementos
                        String nombreElementoActual = jsonLeido.nextName(); //asigno el nombre del atributo en el que estoy a la variable

                        switch(nombreElementoActual){
                            case "IDProducto":
                                idProducto = jsonLeido.nextInt(); //se asigna idproducto
                                break;
                            case "Titulo":
                                Titulo = jsonLeido.nextString();//se asigna titulo
                                break;
                            case "Precio":
                                Precio = jsonLeido.nextString();//se asigna titulo
                                break;
                            case "Foto":
                                Foto = jsonLeido.nextString(); //se asigna idFoto
                                break;
                            default:
                                jsonLeido.skipValue(); //avanza al siguiente elemento
                                break;
                        }

                    }
                    jsonLeido.endObject();
                    MainActivity.listaObjetosTraidos.add(new Producto(idProducto, Titulo, Precio, Foto));


                }
                jsonLeido.endArray(); //termina de recorrer el array


            } catch (Exception e) {
                Log.d("LecturaJSON", "ERROR, " + e.getMessage()); //no pudo leer el json
            }
        }

}
