package com.example.autentificacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main3Activity extends AppCompatActivity {
    EditText txtId;
    private Button getApiBtn,
            postApiBtn,postLibroBtn,getLibrosBtn;
    private TextView resultTextView;
    Tabla tabla;
    private String token ;
    private static final String TAG =
            Main3Activity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        token = getIntent().getStringExtra("token");
        txtId=findViewById(R.id.editText10);
        tabla = new Tabla(this, (TableLayout)findViewById(R.id.tabla));
        resultTextView = findViewById(R.id.resultTextView);
        getApiBtn = findViewById(R.id.getApiBtn);

        getLibrosBtn = findViewById(R.id.getLibrosBtn);
        postLibroBtn = findViewById(R.id.postLibroBtn);
        postLibroBtn.setOnClickListener(new
                                                View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        getLibro();
                                                    }
                                                });
        getLibrosBtn.setOnClickListener(new
                                                View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        getLibros();
                                                    }
                                                });
        //Click Listner for get user
        getApiBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //getData();
                getUser();
            }
        });
    }
    // Haciendo un logeo para poder hacer peticiones

    // Post Libro

    public void getLibro(){
        String id = txtId.getText().toString();
        RequestQueue requestQueue =
                Volley.newRequestQueue(getApplicationContext());
        try {
            String uri = String.format("http://192.168.1.71:8888/laracrud/public/api/auth/libro/"+id+"");

            JSONObject object = new JSONObject();
            JsonObjectRequest jsonObjectRequest = new
                    JsonObjectRequest(com.android.volley.Request.Method.GET, uri,
                            null, new
                            com.android.volley.Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    JsonParser parser = new JsonParser();
                                    Gson gson = new GsonBuilder().create();
                                    JsonArray jarray = (JsonArray) parser.parse(response.toString()).getAsJsonObject().get("data");
                                    data[] cuerpo = gson.fromJson(jarray, data[].class);
                                    tabla.agregarCabecera(R.array.cabecera_tabla);
                                    for (data Data : cuerpo) {
                                        ArrayList<String> elementos = new ArrayList<String>();
                                        elementos.add(Data.getId());
                                        elementos.add(Data.getNombre());
                                        elementos.add(Data.getResumen());
                                        elementos.add(Data.getNpagina());
                                        elementos.add(Data.getEdicion());
                                        elementos.add(Data.getAutor());
                                        elementos.add(Data.getPrecio());

                                        tabla.agregarFilaTabla(elementos);
                                    }
                                    Toast.makeText(getApplicationContext(),
                                            "Petición exitosa !" , Toast.LENGTH_LONG).show();
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            resultTextView.setText("Asegurate de estar logeado, Tipo Error : "+error.toString());
                            Toast.makeText(getApplicationContext(),
                                    "Error"+error.toString(), Toast.LENGTH_LONG).show();
                        }
                    })
                    { //Agregando headers a las peticiones
                        @Override
                        public Map getHeaders()throws AuthFailureError{
                            HashMap headers=new HashMap();
                            headers.put("Content-Type","application/json");
                            headers.put("Authorization","Bearer "+token.toString());
                            return headers;
                        }
                    }
                    ;
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get Request For JSONObject
    public void getUser(){
        RequestQueue requestQueue =
                Volley.newRequestQueue(getApplicationContext());
        try {
            String url =
                    getResources().getString(R.string.urlget);
            JSONObject object = new JSONObject();
            JsonObjectRequest jsonObjectRequest = new
                    JsonObjectRequest(com.android.volley.Request.Method.GET, url,
                            null, new
                            com.android.volley.Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    tabla.agregarCabecera(R.array.cabecera_tabla1);
                                    // Obtain Array
                                    Gson gson = new Gson();
                                    usuarios[] Usuarios   = gson.fromJson("["+response.toString()+"]",
                                            usuarios[].class);

                                    for (usuarios usuarios1 : Usuarios) {
                                        ArrayList<String> elementos = new ArrayList<String>();
                                        elementos.add(usuarios1.getId());
                                        elementos.add(usuarios1.getNombre());
                                        elementos.add(usuarios1.getEmail());
                                        elementos.add(usuarios1.getEmail_verified_at());
                                        elementos.add(usuarios1.getCreated_at());
                                        elementos.add(usuarios1.getUpdated_at());


                                        tabla.agregarFilaTabla(elementos);


                                    }


                                    // for each element of array


                                    Toast.makeText(getApplicationContext(), "I am OK !" + response.toString(), Toast.LENGTH_LONG).show();
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            resultTextView.setText("Asegurate de estar logeado, Tipo Error : "+error.toString());
                            Toast.makeText(getApplicationContext(),
                                    "Error"+error.toString(), Toast.LENGTH_LONG).show();
                        }
                    })
                    { //Agregando headers a las peticiones
                        @Override
                        public Map getHeaders()throws AuthFailureError{
                            HashMap headers=new HashMap();
                            headers.put("Content-Type","application/ json");
                                    headers.put("Authorization","Bearer "+token.toString());
                            return headers;
                        }
                    }
                    ;
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Get Request For get libros
    public void getLibros(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        try {
            String url = getResources().getString(R.string.urlgetlibro);
            JSONObject object = new JSONObject();
            JsonObjectRequest jsonObjectRequest = new
                    JsonObjectRequest(com.android.volley.Request.Method.GET, url,
                            null, new
                            com.android.volley.Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    JsonParser parser = new JsonParser();
                                    Gson gson = new GsonBuilder().create();
                                    JsonArray jarray = (JsonArray) parser.parse(response.toString()).getAsJsonObject().get("data");
                                    data[] cuerpo = gson.fromJson(jarray, data[].class);
                                    tabla.agregarCabecera(R.array.cabecera_tabla);
                                    for (data Data : cuerpo) {
                                        ArrayList<String> elementos = new ArrayList<String>();
                                        elementos.add(Data.getId());
                                        elementos.add(Data.getNombre());
                                        elementos.add(Data.getResumen());
                                        elementos.add(Data.getNpagina());
                                        elementos.add(Data.getEdicion());
                                        elementos.add(Data.getAutor());
                                        elementos.add(Data.getPrecio());

                                        tabla.agregarFilaTabla(elementos);
                                    }
                                    Toast.makeText(getApplicationContext(),
                                            "Petición exitosa !" , Toast.LENGTH_LONG).show();
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            resultTextView.setText("Asegurate de estar logeado, Tipo Error : "+error.toString());
                            Toast.makeText(getApplicationContext(),
                                    "Error: "+error.toString(), Toast.LENGTH_LONG).show();
                        }
                    })
                    { //Agregando headers a las peticiones
                        @Override
                        public Map getHeaders()throws AuthFailureError{
                            HashMap headers=new HashMap();
                            headers.put("Content-Type","application/json");
                                    //agregamos el token que previamente se obtuvo del login, de lo contrario no se hara la petición.
                                            headers.put("Authorization","Bearer "+token.toString());
                            return headers;
                        }
                    }
                    ;
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}