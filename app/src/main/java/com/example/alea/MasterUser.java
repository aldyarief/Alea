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
import android.widget.CheckBox;
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
import android.widget.PopupMenu;
import android.annotation.SuppressLint;

public class MasterUser extends AppCompatActivity {
    Button btntambah,btntutup;
    EditText Eduser,EdPass;
    String server_url,name;
    ProgressDialog pd;
    String hakuser,hakbarang,hakbeli,hakjual,hakstok,haklaporan,userid;
    CheckBox user,barang,beli,jual,koreksi,laporan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_user);
        Eduser = (EditText) findViewById(R.id.EdUserName);
        EdPass = (EditText) findViewById(R.id.EdPassword);
        btntambah = (Button) findViewById(R.id.buttonAdd);
        btntutup = (Button) findViewById(R.id.buttonClose);
        user=(CheckBox)findViewById(R.id.checkbox_user);
        barang=(CheckBox)findViewById(R.id.checkbox_barang);
        beli=(CheckBox)findViewById(R.id.checkbox_beli);
        jual=(CheckBox)findViewById(R.id.checkbox_jual);
        koreksi=(CheckBox)findViewById(R.id.checkbox_stok);
        laporan=(CheckBox)findViewById(R.id.checkbox_laporan);
        userid = (getIntent().getStringExtra("userid"));
        server_url = "https://aldry.000webhostapp.com/insertuser.php";
        pd = new ProgressDialog(this);


        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Eduser.getText().toString().trim();
                String pass = EdPass.getText().toString().trim();

                if(user.isChecked()){
                    hakuser="true";
                } else {
                    hakuser="false";
                }

                if(barang.isChecked()){
                    hakbarang ="true";
                } else {
                    hakbarang="false";
                }

                if(beli.isChecked()){
                    hakbeli="true";
                } else {
                    hakbeli="false";
                }

                if(jual.isChecked()){
                    hakjual ="true";
                } else {
                    hakjual="false";
                }

                if(koreksi.isChecked()){
                    hakstok="true";
                } else {
                    hakstok="false";
                }

                if(laporan.isChecked()){
                    haklaporan ="true";
                } else {
                    haklaporan ="false";
                }

                if (!name.isEmpty() && !pass.isEmpty() ) {
                    simpanData(name,pass,hakuser,hakbarang,hakbeli,hakjual,hakstok,haklaporan,userid);
                } else if (name.isEmpty()) {
                    Eduser.setError("username tidak boleh kosong");
                    Eduser.requestFocus();
                } else if (pass.isEmpty()) {
                    EdPass.setError("password tidak boleh kosong");
                    EdPass.requestFocus();
                }
            }
        });


        btntutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicit = new Intent(MasterUser.this, user.class);
                startActivity(explicit);
            }

        });

    }
    private void simpanData(final String name,final String pass,final String hakuser,final String hakbarang,
                            final String hakbeli,final String hakjual,final String hakkoreksi,
                            final String haklaporan,final String userid) {
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
                                Eduser.getText().clear();
                                EdPass.getText().clear();

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
                param.put("hakuser", hakuser);
                param.put("hakbarang", hakbarang);
                param.put("hakbeli", hakbeli);
                param.put("hakjual", hakjual);
                param.put("hakkoreksi", hakkoreksi);
                param.put("haklaporan", haklaporan);
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
