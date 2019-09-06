package com.example.proyectofinalpro;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

    public class TareaAsincronicaProducto extends AsyncTask<Integer, Void, Producto> { //1-parametro, 2parametro, 3loqueretorna


        @Override
        protected Producto doInBackground(Integer... idProducto) { //mientras se ejecuta - recibe parametro
            Producto ProductoElegido = null;
            try {
                String strIP = "10.152.2.6";//ip
                String StrPuerto = ":52680";//puerto
                String strURL = "http://" + strIP + StrPuerto + "/api/Producto/GetByID/" + Integer.toString(idProducto[0]); //ip de dermon + puerto + api + loquebusco*/

                URL laRuta = new URL(strURL); //creó la ruta a la cual me quiero conectar con la dirección creada

                HttpURLConnection miConexion = (HttpURLConnection) laRuta.openConnection(); //me conecto a esa ruta
                Log.d("AccesoAPI", "Me conecto");
                if (miConexion.getResponseCode() == 200) { //si la conexion devuelve un codigo 200 me pude conectar correctamente
                    Log.d("AccesoAPI", "Conexion OK");
                    InputStream cuerpoRespuesta = miConexion.getInputStream(); //agarras el texto de la pag
                    InputStreamReader lectorRespuesta = new InputStreamReader(cuerpoRespuesta, "UTF-8"); //se lo asignas a un reader
                    ProductoElegido = procesarJsonLeido(lectorRespuesta, idProducto[0]); //mandas reader a funcion que procesará el json
                } else { //no me pude conectar correctamente(puede ser wifi)
                    Log.d("AccesoAPI", "ERROR en la conexion");
                }
                miConexion.disconnect();
            } catch (Exception e) {
                Log.d("AccesoAPI", "ERROR: " + e.getMessage());
            }

            return ProductoElegido; //envio producto como parametro
        }

        /*Funciones */

        private Producto procesarJsonLeido(InputStreamReader streamLeido, Integer idProducto) {

            JsonReader jsonLeido = new JsonReader(streamLeido); //creo un lector de json

            Producto ProductoElegido = null;
            String Titulo = "";
            String NombreVendedor = "";
            String ApellidoVendedor = "";
            String Descripcion = "";
            String Foto = "";
            String Precio = "";
            String Anio = "";
            String Materia = "";

            try {
                jsonLeido.beginArray(); //recorro el array de productos
                while (jsonLeido.hasNext()) { //mientras haya elementos en el array(ej: producto 1, producto 2)
                    jsonLeido.beginObject(); //cada elemento del array es un objeto --> empiezo cada uno
                    while (jsonLeido.hasNext()) { //mientras cada elemento del array tenga dentro otros elementos
                        String nombreElementoActual = jsonLeido.nextName(); //asigno el nombre del atributo en el que estoy a la variable

                        switch (nombreElementoActual) {
                            case "Titulo":
                                Titulo = jsonLeido.nextString();//se asigna titulo
                                break;
                           case "Nombre":
                                NombreVendedor = jsonLeido.nextString();//se asigna titulo
                                break;
                            case "Apellido":
                                ApellidoVendedor = jsonLeido.nextString();//se asigna titulo
                                break;
                            case "Precio":
                                Precio = jsonLeido.nextString();//se asigna titulo
                                break;
                            case "Descripcion":
                                Descripcion = jsonLeido.nextString(); //se asigna descripcion
                                break;
                            case "IDAnio":
                                Anio = jsonLeido.nextString();  //se asigna idAnio
                                break;
                            case "IDMateria":
                                Materia = jsonLeido.nextString(); //se asigna idMateria
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
                    String NombreCompletoVendedor = NombreVendedor.concat(" "+ApellidoVendedor);
                    ProductoElegido = new Producto(idProducto, NombreCompletoVendedor, Titulo, Descripcion, Precio, Anio, Materia, Foto); //creo el producto

                }
                jsonLeido.endArray(); //termina de recorrer el array


            } catch (Exception e) {
                Log.d("LecturaJSON", "ERROR, " + e.getMessage()); //no pudo leer el json
            }
        return ProductoElegido; //envio producto
        }
    }
