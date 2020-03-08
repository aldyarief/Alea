package com.example.alea;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MasterUser extends AppCompatActivity {
    Button btntambah,btnview;
    EditText Eduser,EdPass;
    String server_url;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_user);
        Eduser = (EditText) findViewById(R.id.EdUserName);
        EdPass = (EditText) findViewById(R.id.EdPassword);
        btntambah = (Button) findViewById(R.id.buttonAdd);
        btnview = (Button) findViewById(R.id.buttonView);
        server_url = "https://aldry.000webhostapp.com/insertuser.php";
        pd = new ProgressDialog(this);

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Eduser.getText().toString().trim();
                String pass = EdPass.getText().toString().trim();

                if (!name.isEmpty() && !pass.isEmpty() ) {
                    simpanData(name,pass);
                } else if (name.isEmpty()) {
                    Eduser.setError("username tidak boleh kosong");
                    Eduser.requestFocus();
                } else if (pass.isEmpty()) {
                    EdPass.setError("password tidak boleh kosong");
                    EdPass.requestFocus();
                }
            }
        });
    }
    private void simpanData(final String name,final String pass) {
        final RequestQueue requestQueue = Volley.newRequestQueue(MasterUser.this);

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
                                Toast.makeText(MasterUser.this,pesan, Toast.LENGTH_SHORT).show();
                                requestQueue.stop();
                            } else {
                                Toast.makeText(MasterUser.this, pesan, Toast.LENGTH_SHORT).show();
                                requestQueue.stop();
                                Eduser.getText().clear();
                                EdPass.getText().clear();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MasterUser.this, "Error JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", error.getMessage());
                Toast.makeText(MasterUser.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
