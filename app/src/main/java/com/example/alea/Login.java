package com.example.alea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyLog;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import android.content.Intent;

public class Login extends AppCompatActivity {
    Button button;
    private EditText textname,textpass;
    String server_url;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button = (Button) findViewById(R.id.btnLogin);
        textname = (EditText) findViewById(R.id.textname);
        textpass = (EditText) findViewById(R.id.textpass);
        server_url = "https://aldry.000webhostapp.com/Login.php";
        pd = new ProgressDialog(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textname.getText().toString().trim();
                String pass = textpass.getText().toString().trim();

                if (!name.isEmpty() && !pass.isEmpty() ) {
                    simpanData(name, pass);
                } else if (name.isEmpty()) {
                    textname.setError("user tidak boleh kosong");
                    textname.requestFocus();
                } else if (pass.isEmpty()) {
                    textpass.setError("pass tidak boleh kosong");
                    textpass.requestFocus();
                }
            }
        });
    }

    private void simpanData(final String name,final String pass) {
        final RequestQueue requestQueue = Volley.newRequestQueue(Login.this);

        pd.setCancelable(false);
        pd.setMessage("Harap Menunggu...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response.toString());
                        hideDialog();

                        try {
                            JSONObject jObject = new JSONObject(response);
                            String pesan = jObject.getString("pesan");
                            String hasil = jObject.getString("result");
                            if (hasil.equalsIgnoreCase("true")) {
                                Intent explicit = new Intent(Login.this, Dashboard.class);
                                startActivity(explicit);
                                requestQueue.stop();
                            } else {
                                Toast.makeText(Login.this, pesan, Toast.LENGTH_SHORT).show();
                                requestQueue.stop();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Login.this, "Error JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", error.getMessage());
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("name", name);
                param.put("pass", pass);
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }


    private void showDialog() {
        if (!pd.isShowing())
            pd.show();
    }


    private void hideDialog() {
        if (pd.isShowing())
            pd.dismiss();
    }
}

