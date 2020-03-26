package com.example.alea;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class EditUser extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextPass;
    private Button buttonUpdate;
    private Button buttonTutup;
    CheckBox ChkPassword;

    String server_url, name;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        buttonUpdate = (Button) findViewById(R.id.btnSave);
        buttonTutup = (Button) findViewById(R.id.btnTutup);
        ChkPassword = (CheckBox) findViewById(R.id.checkBox1);
        pd = new ProgressDialog(this);
        editTextPass.setText(getIntent().getStringExtra("name"));
        editTextName.setText(getIntent().getStringExtra("pass"));
        server_url = "https://aldry.000webhostapp.com/edituser.php";

        ChkPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

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


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = editTextName.getText().toString().trim();
                String passwordbaru = editTextPass.getText().toString().trim();
                String name = (getIntent().getStringExtra("name"));

                if (!passwordbaru.isEmpty() && !password.isEmpty() && !name.isEmpty()) {
                    simpanData(name,password,passwordbaru);
                } else if (password.isEmpty()) {
                    editTextName.setError("Password lama tidak boleh kosong");
                    editTextName.requestFocus();
                } else if (passwordbaru.isEmpty()) {
                    editTextPass.setError("Password baru tidak boleh kosong");
                    editTextPass.requestFocus();
                } else if (name.isEmpty()) {
                    editTextPass.setError("Namanya baru tidak boleh kosong");
                    editTextPass.requestFocus();
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
    private void simpanData(final String name,final String password,final String passwordbaru) {
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
                            if (hasil.equalsIgnoreCase("true")) {
                                Toast.makeText(EditUser.this,"heiii", Toast.LENGTH_SHORT).show();
                                requestQueue.stop();
                                editTextPass.getText().clear();
                                editTextName.getText().clear();
                            } else {
                                Toast.makeText(EditUser.this, hasil, Toast.LENGTH_SHORT).show();
                                requestQueue.stop();
                                editTextPass.getText().clear();
                                editTextName.getText().clear();
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
                param.put("password", password);
                param.put("passwordbaru", passwordbaru);
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