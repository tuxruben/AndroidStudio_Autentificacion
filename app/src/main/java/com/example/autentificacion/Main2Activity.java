package com.example.autentificacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {
    EditText txtNombre;
    EditText txtResumen;
    EditText txtNpagina;
    EditText txtEdicion;
    EditText txtAutor;
    EditText txtPrecio;
    EditText txtId;
    private String token ;
    private TextView resultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        token =getIntent().getStringExtra("token");
        resultTextView = findViewById(R.id.resultTextView2);
        txtNombre=findViewById(R.id.editText);
        txtResumen =findViewById(R.id.editText2);
        txtNpagina =findViewById(R.id.editText3);

        txtEdicion  = findViewById(R.id.editText4);
        txtAutor  = findViewById(R.id.editText5);
        txtPrecio  = findViewById(R.id.editText6);
        txtId  = findViewById(R.id.editText7);
    }
    public void putLibro(View view) {
        String id = txtId.getText().toString();
        String nombre = txtNombre.getText().toString();
        String resumen = txtResumen.getText().toString();
        String npagina = txtNpagina.getText().toString();
        String edicion = txtEdicion.getText().toString();
        String autor = txtAutor.getText().toString();
        String precio = txtPrecio.getText().toString();
        RequestQueue requestQueue =
                Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("nombre",""+nombre+"");
            object.put("resumen",""+resumen+"");
            object.put("npagina",""+npagina+"");
            object.put("edicion",""+edicion+"");
            object.put("autor",""+autor+"");
            object.put("precio",""+precio+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        String uri = String.format("http://192.168.1.71:8888/laracrud/public/api/auth/libro/"+id+"");
        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.PUT, uri,
                        object,
                        new
                                com.android.volley.Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response)
                                    {
                                        try {
                                            //Respuesta despues de mandar los datos

                                            resultTextView.setText(response.getString("data"));

                                            Toast.makeText(getApplicationContext(),
                                                    response.getString("message"), Toast.LENGTH_LONG).show();
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }, new
                        com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                resultTextView.setText("Asegurate de estar logeado, Tipo Error : "+error.toString());
                            }
                        })
                { //Agregando headers a las peticiones
                    @Override
                    public Map getHeaders()throws AuthFailureError {
                        HashMap headers=new HashMap();
                        headers.put("Content-Type","application/json");
                        headers.put("Authorization","Bearer "+token.toString());
                        return headers;
                    }
                }
                ;
        requestQueue.add(jsonObjectRequest);
    }
    public void postLibro(View view) {
        String nombre = txtNombre.getText().toString();
        String resumen = txtResumen.getText().toString();
        String npagina = txtNpagina.getText().toString();
        String edicion = txtEdicion.getText().toString();
        String autor = txtAutor.getText().toString();
        String precio = txtPrecio.getText().toString();
        RequestQueue requestQueue =
                Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("nombre",""+nombre+"");
            object.put("resumen",""+resumen+"");
            object.put("npagina",""+npagina+"");
            object.put("edicion",""+edicion+"");
            object.put("autor",""+autor+"");
            object.put("precio",""+precio+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        String url =
                getResources().getString(R.string.urlpostlibro);
        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(com.android.volley.Request.Method.POST, url,
                        object,
                        new
                                com.android.volley.Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response)
                                    {
                                        try {
                                            //Respuesta despues de mandar los datos

                                            resultTextView.setText(response.getString("data"));

                                            Toast.makeText(getApplicationContext(),
                                                    response.getString("message"), Toast.LENGTH_LONG).show();
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }, new
                        com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                resultTextView.setText("Asegurate de estar logeado, Tipo Error : "+error.toString());
                            }
                        })
                { //Agregando headers a las peticiones
                    @Override
                    public Map getHeaders()throws AuthFailureError {
                        HashMap headers=new HashMap();
                        headers.put("Content-Type","application/json");
                        headers.put("Authorization","Bearer "+token.toString());
                        return headers;
                    }
                }
                ;
        requestQueue.add(jsonObjectRequest);
    }
    public void deleteLibro(View view) {
        String id = txtId.getText().toString();

        RequestQueue requestQueue =
                Volley.newRequestQueue(getApplicationContext());

        // Enter the correct url for your api service site
    String url =
            getResources().getString(R.string.urlpostlibro)+"/"+id;
        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.DELETE, url,
                        null,
                        new
                                com.android.volley.Response.Listener<JSONObject>() {
                                    @Override

                                    public void onResponse(JSONObject response)
                                    {
                                        try {
                                            //Respuesta despues de mandar los datos

                                            resultTextView.setText(response.getString("data"));

                                            Toast.makeText(getApplicationContext(),
                                                    response.getString("message"), Toast.LENGTH_LONG).show();
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }, new
                        com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                resultTextView.setText("Asegurate de estar logeado, Tipo Error : " + error.toString());
                            }
                        }) { //Agregando headers a las peticiones
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Content-Type", "application/json");
                        headers.put("Authorization", "Bearer " + token.toString());
                        return headers;
                    }
                };

        requestQueue.add(jsonObjectRequest);


    }


    }
