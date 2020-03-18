package com.example.alea;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;


public class EditUser extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextPass;
    private EditText editTextCoba;
    private Button buttonUpdate;
    private Button buttonTutup;
    CheckBox password;

    String server_url, name,result,server_edit;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        buttonUpdate = (Button) findViewById(R.id.btnSave);
        buttonTutup = (Button) findViewById(R.id.btnTutup);
        password = (CheckBox) findViewById(R.id.checkBox1);
        pd = new ProgressDialog(this);

        password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (!isChecked) {
                    editTextPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editTextName.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    editTextPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editTextName.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });

        buttonTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicit = new Intent(EditUser.this, user.class);
                startActivity(explicit);
            }

        });
    }
    private void AmbilData(final String name) {
        final RequestQueue requestQueue = Volley.newRequestQueue(EditUser.this);

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
                            String hasil = jObject.getString("result");
                            String pass = jObject.getString("pass");
                            if (hasil.equalsIgnoreCase("true")) {
                                editTextPass.setText(pass);
                                requestQueue.stop();
                            } else {
                                Toast.makeText(EditUser.this, "Data Kosong / Data tidak ada", Toast.LENGTH_SHORT).show();
                                requestQueue.stop();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EditUser.this, "Error JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", error.getMessage());
                Toast.makeText(EditUser.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("name", name);
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }


    private void EditData(final String status) {
        final RequestQueue requestQueue = Volley.newRequestQueue(EditUser.this);

        pd.setCancelable(false);
        pd.setMessage("Harap Menunggu...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_edit,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response.toString());
                        hideDialog();
                        try {
                            JSONObject jObject = new JSONObject(response);
                            String hasilnya = jObject.getString("result");

                                Toast.makeText(EditUser.this, hasilnya, Toast.LENGTH_SHORT).show();
                                requestQueue.stop();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EditUser.this, "Error JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", error.getMessage());
                Toast.makeText(EditUser.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("result", status);
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