package com.example.alea;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class KategoriBarang extends AppCompatActivity {
    String userid,name,server_url;
    Button btnsave,btntutup;
    EditText Eduser;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_barang);
        userid = (getIntent().getStringExtra("userid"));
        Eduser = (EditText) findViewById(R.id.editTextName);
        btnsave = (Button) findViewById(R.id.btnSave);
        btntutup = (Button) findViewById(R.id.btnTutup);
        server_url = "https://aldry.000webhostapp.com/insertkategori.php";
        pd = new ProgressDialog(this);


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Eduser.getText().toString().trim();

                if (!name.isEmpty() ) {
                    simpanData(name, userid);
                }
            }
        });

        btntutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicit = new Intent(KategoriBarang.this, Barang.class);
                startActivity(explicit);
            }

        });
    }

    private void simpanData(final String name,final String userid) {
        final RequestQueue requestQueue = Volley.newRequestQueue(KategoriBarang.this);

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
                                Toast.makeText(KategoriBarang.this,pesan, Toast.LENGTH_SHORT).show();
                                requestQueue.stop();
                                Eduser.getText().clear();

                            } else {
                                Toast.makeText(KategoriBarang.this, pesan, Toast.LENGTH_SHORT).show();
                                requestQueue.stop();
                                Eduser.getText().clear();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(KategoriBarang.this, "Error JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", error.getMessage());
                Toast.makeText(KategoriBarang.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("name", name);
                param.put("userid", userid);
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
