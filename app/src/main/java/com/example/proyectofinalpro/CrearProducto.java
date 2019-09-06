package com.example.proyectofinalpro;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class CrearProducto extends AppCompatActivity {
    /* Declaracion de variables locales a la activy */
    EditText edtTitulo, edtDescripcion, edtPrecio;
    Button btnCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);
        obtenerReferencias(); //obtengo la referencia a las views
        setearListeners(); //seteo todos los listeners
    }

    private String getServerResponse(String json){
        //Creo el HttpPost con la url a la que se le van a mandar los datos
        HttpPost post = new HttpPost("http://10.152.2.42:52680/api/IngresarProducto");
        try{
            //StringEntity is the raw data that you send in the request.
            StringEntity entity = new StringEntity(json);
            post.setEntity(entity);
            //Declaro los Headers
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            HttpClient client = new DefaultHttpClient();
            BasicResponseHandler handler = new BasicResponseHandler();
            String response=client.execute(post, handler);
            return response;
        } catch (UnsupportedEncodingException e) {
            Log.d ("Error", e.toString());
        } catch (ClientProtocolException e) {
            Log.d ("Error", e.toString());
        } catch (IOException e) {
            Log.d ("Error", e.toString());
        }
        return null;
    }//Cierro la funcion getServerResponse

    private String formatDataAsJSON() {
        String strTitulo = edtTitulo.getText().toString();
        String strDescripcion = edtDescripcion.getText().toString();
        String strPrecio = edtPrecio.getText().toString();
        Integer intPrecio = Integer.parseInt(strPrecio);

        if (strDescripcion.isEmpty() && strTitulo.isEmpty() && intPrecio <= 0) {
            Toast.makeText(getApplicationContext(), "LLene los datos porfavor", Toast.LENGTH_SHORT).show();
        } else {
            try {
                int idvendedor = 1;
                String foto = "Foto";
                int IDAnio = 1;
                int IDMateria = 1;
                // 1. Creo el JSONObject
                JSONObject Mandar = new JSONObject();
                // 2. Lleno el JSONObject
                Mandar.put("IDVendedor", idvendedor);
                Mandar.put("Titulo", strTitulo);
                Mandar.put("Descripcion", strDescripcion);
                Mandar.put("IDAnio", IDAnio);
                Mandar.put("IDMateria", IDMateria);
                Mandar.put("Foto", foto);
                Mandar.put("Precio", intPrecio);
                // 3. Convierto el JSONObject en un String y lo retorno
                return Mandar.toString();
            } catch (JSONException e) {
                Log.d("Error", "Error");
                // Do something to recover ... or kill the app.
                return null;
            }
        }
        return null;
    }//Cierro la funcion LlenarElJSON

    private void obtenerReferencias() { //obtencion de referencias (al layout)
        edtTitulo = (EditText) findViewById(R.id.edtTitulo);
        edtDescripcion = (EditText) findViewById(R.id.edtDescripcion);
        edtPrecio = (EditText) findViewById(R.id.edtPrecio);
        btnCrear = (Button) findViewById(R.id.btnCrear);
    }

    private void setearListeners() {  //setear las acciones(listener)
        btnCrear.setOnClickListener(btn_Click);
    }

    View.OnClickListener btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Guardo el JSON en un String
            final String json = formatDataAsJSON();
            new AsyncTask<Void, Void, String>(){
                @Override
                protected String doInBackground(Void... params){
                    Log.d("Ver", json);
                    //LLamo a la funcion getServerResponse y le mando el json para que este sea mandado al link en cuestion
                    return getServerResponse(json);
                }

                protected void onPostExecute(String results){
                    super.onPostExecute(results);
                }
            }.execute();
            Toast.makeText(getApplicationContext(),"Se agrego el producto correctamente",Toast.LENGTH_SHORT).show();
        }
    };

}
