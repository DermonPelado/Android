package com.example.proyectofinalpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    /*Declaracion de parametros*/
    static final String PARAM_ProductoElegido = "com.ProyectoFinal.PARAM_ProductoElegido";

    /* Declaracion de variables locales a la activity */
    static ListView lstProductos; //lista que muestra los objetos
    static TextView txvMensajes; //mensaje dependiendo de si esta cargando o no
    static ArrayList<Producto> listaObjetosTraidos = new ArrayList<Producto>();
    static ArrayAdapter<Producto> adaptadordeobjetos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        obtenerReferencias(); //obtengo la referencia a las views
        setearListeners(); //seteo todos los listeners

        TareaAsincronicaTodos tareaAsinc = new TareaAsincronicaTodos(); //Creo una TareaAsincronicaObjetos
        tareaAsinc.execute(); //La ejecuto


        adaptadordeobjetos = new ArrayAdapter<Producto>(this, android.R.layout.simple_list_item_1, listaObjetosTraidos); //creo adaptador que pone datos de una lista en un listview


    }

    private void obtenerReferencias() { //obtencion de referencias (al layout)
        lstProductos = findViewById(R.id.lstProductos);
        txvMensajes = findViewById(R.id.txvMensajes);
    }

    private void setearListeners() {  //setear las acciones(listener)
        lstProductos.setOnItemClickListener(lstResultadosTraidos_ItemClick);
    }



    /* Defino los listener*/


    ListView.OnItemClickListener lstResultadosTraidos_ItemClick = new AdapterView.OnItemClickListener() { //cuando clickea en un item
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            int idProducto = listaObjetosTraidos.get(position).getIdProducto(); //asigno idproducto

            /*Paso de activity a otra*/
            Intent activity = new Intent(MainActivity.this, ProductoSeleccionado.class); //declaro intent para viajar a la otra activity

            /*envio de parametros*/

            Bundle datos = new Bundle();  //declaro bundle que pasa los datos a la otra activity
            datos.putInt(PARAM_ProductoElegido, idProducto);  //guarda en el bundle el objeto (con parcelable)
            activity.putExtras(datos); //guardo el bundle en el intent

            //
            startActivity(activity);//ejecuto intent para ir a la otra activity


        }
    };




}
