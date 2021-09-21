package com.example.autentificacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String token;
 TextView resultTextView;
    EditText email;
    EditText contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.editText8);
        contraseña =findViewById(R.id.editText9);
        resultTextView = findViewById(R.id.resultTextView3);
        Button btn = (Button) findViewById(R.id.button);
        Button btn1 = (Button) findViewById(R.id.button2);
        Button btn2 = (Button) findViewById(R.id.button9);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Main3Activity.class);
                intent.putExtra("token", token);
                startActivityForResult(intent, 0);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Main2Activity.class);
                intent.putExtra("token", token);
                startActivityForResult(intent, 0);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Main4Activity.class);
                intent.putExtra("token", token);
                startActivityForResult(intent, 0);
            }
        });
    }
    public void postData(View view) {
        String Email = email.getText().toString();
        String Pass = contraseña.getText().toString();
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("email",""+Email+"");
            object.put("password",""+Pass+"");
            object.put("remember_me",true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        String url = getResources().getString(R.string.urlpost);
        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(com.android.volley.Request.Method.POST, url,
                object,
                new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response)
                            {
                                try {
                                    // Obteniendo el token para poder hacer peticiones

                                    token=response.getString("access_token");
                                    resultTextView.setText("Response : Logeo exitoso!!!, ahora puedes hacer peticiones");
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
                ;
        requestQueue.add(jsonObjectRequest);
    }
    public void getLogout(View view){

        RequestQueue requestQueue =
                Volley.newRequestQueue(getApplicationContext());
        try {
            String url = getResources().getString(R.string.urlgetLogout);

            JSONObject object = new JSONObject();
            JsonObjectRequest jsonObjectRequest = new
                    JsonObjectRequest(com.android.volley.Request.Method.GET, url,
                            null, new
                            com.android.volley.Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    resultTextView.setText("Response : " +
                                            response.toString());
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
                        public Map getHeaders()throws AuthFailureError {
                            HashMap headers=new HashMap();

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
