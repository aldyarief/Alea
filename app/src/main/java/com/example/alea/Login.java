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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class Login extends AppCompatActivity {
    Button button,button2;
    CheckBox password;
    private EditText textname,textpass;
    String server_url;
    ProgressDialog pd;
    private Button imb_popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button = (Button) findViewById(R.id.btnLogin);
        button2 = (Button) findViewById(R.id.btnTutup);
        textname = (EditText) findViewById(R.id.textname);
        textpass = (EditText) findViewById(R.id.textpass);
        password = (CheckBox) findViewById(R.id.checkBox1);
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
                    textname.setError("username tidak boleh kosong");
                    textname.requestFocus();
                } else if (pass.isEmpty()) {
                    textpass.setError("password tidak boleh kosong");
                    textpass.requestFocus();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        password.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (!isChecked) {
                    textpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    textpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
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
                                KirimData();
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

    private void KirimData() {
        Intent intent = new Intent(Login.this, Dashboard.class);
        intent.putExtra("name", textname.getText().toString().trim());
        intent.putExtra("pass", textpass.getText().toString().trim());
        startActivity(intent);
    }
}

