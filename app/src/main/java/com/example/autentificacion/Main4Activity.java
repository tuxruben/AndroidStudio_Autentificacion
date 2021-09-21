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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main4Activity extends AppCompatActivity {
    private String token ;
    EditText txtName;
    EditText txtEmail;
    EditText Passowrd;
    EditText txtPassworConfirmation;
    private TextView resultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        token = getIntent().getStringExtra("token");
        Button btn = (Button) findViewById(R.id.button5);
        resultTextView = findViewById(R.id.resultTextView2);
        txtName=findViewById(R.id.editText);
        txtEmail =findViewById(R.id.editText2);
        Passowrd =findViewById(R.id.editText5);

        txtPassworConfirmation  = findViewById(R.id.editText11);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               postUsuario();
            }
        });
    }
    public void postUsuario() {
        String nombre = txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String password = Passowrd.getText().toString();
        String password_confirmation = txtPassworConfirmation.getText().toString();

        RequestQueue requestQueue =
                Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("name",""+nombre+"");
            object.put("email",""+email+"");
            object.put("password",""+password+"");
            object.put("password_confirmation",""+password_confirmation+"");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        String url =
                getResources().getString(R.string.urlpostSignup);
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
                        headers.put("Authorization","Bearer "+token);
                        return headers;
                    }
                }
                ;
        requestQueue.add(jsonObjectRequest);
    }
}
