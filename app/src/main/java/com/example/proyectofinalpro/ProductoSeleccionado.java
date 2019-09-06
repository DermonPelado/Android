package com.example.proyectofinalpro;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductoSeleccionado extends AppCompatActivity {

    /* Declaracion de variables locales a la activy */
    TextView txvTituloProducto;
    TextView txvDescripcion;
    TextView txvPrecio;
    TextView txvVendedor;
    ImageView imgFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_seleccionado);

        obtenerReferencias(); //obtengo la referencia a las views


        /*obtencion de parametros del intent*/

        Intent ActivityAnterior = getIntent(); //recibe el intent del anterioractivity
        Bundle datos = ActivityAnterior.getExtras(); //devuelve bundle de la activity anterior
        //
        int idProducto = datos.getInt(MainActivity.PARAM_ProductoElegido);//recibo el objeto y lo asigno a otro objeto
        //

        Producto ProductoElegido = null;

        TareaAsincronicaProducto tareaAsinc = new TareaAsincronicaProducto(); //Creo una TareaAsincronicaProducto

        try { //probar ya que puede haber error
            ProductoElegido = tareaAsinc.execute(idProducto).get(); //La ejecuto y mando como parametro el iddelproductoseleccionado y me devuelve el producto

            txvTituloProducto.setText(ProductoElegido.getTitulo()); //asigno al txvtitulo el titulo del objeto
            txvDescripcion.setText(ProductoElegido.getDescripcion());

            String NombreVendedor = "Por: ".concat(ProductoElegido.getVendedor());
            txvVendedor.setText(NombreVendedor);

            String Precio = "$".concat(ProductoElegido.getPrecio());
            txvPrecio.setText(Precio);

            TareaAsincronicaDescargarFoto DescargarFoto = new TareaAsincronicaDescargarFoto(); //Creo TareaAsincronicaDescargarFoto
            DescargarFoto.setFoto(imgFoto);//Pongo la imagen del android de atributo para luego se le asigne la nueva foto
            DescargarFoto.execute(ProductoElegido.getFoto()); //ejecuto la tarea y le mando como parametro la urldefoto


        } catch (Exception e) {
            Toast msg = Toast.makeText(getApplicationContext(), "Hubo un error. Intente de nuevo", Toast.LENGTH_SHORT);
            msg.show();
        }


    }


    public void obtenerReferencias() {  //obtencion de referencias (al layout)

        txvTituloProducto = (TextView) findViewById(R.id.txvTituloProducto);
        txvDescripcion = (TextView) findViewById(R.id.txvDescripcion);
        txvPrecio = (TextView) findViewById(R.id.txvPrecio);
        txvVendedor = (TextView) findViewById(R.id.txvVendedor);
        imgFoto = (ImageView) findViewById(R.id.imgFoto);

    } //fin obtener referencias



}