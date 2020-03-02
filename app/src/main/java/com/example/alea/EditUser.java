package com.example.alea;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AlertDialog;
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
    private Button buttonUpdate;
    private Button buttonDelete;

    String server_url,nama,passe;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        server_url = "https://aldry.000webhostapp.com/showuser.php";
        pd = new ProgressDialog(this);
        nama="aldry";
        getEmployee(nama);
    }

    private void getEmployee(final String nama) {
        final RequestQueue requestQueue = Volley.newRequestQueue(EditUser.this);

        pd.setCancelable(false);
        pd.setMessage("Harap Menunggu...");
        showDialog();



        StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response.toString());
                        hideDialog();
                        try {
                            JSONObject json = jsonParser.getJSONFromUrl(server_url, null);
                            editTextName.setText(jsonPost.getString("user_name"));
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
                param.put("user_name", nama);
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
