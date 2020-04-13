package com.example.alea;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
import org.json.JSONArray;
import java.util.HashMap;
import java.util.Map;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

public class MasterBarang extends AppCompatActivity {
    ProgressDialog pd;
    String server_url,data;
    Spinner spinnerKategori;
    String JSON_ARRAY = "data";
    private ArrayList<String> kategori;
    private JSONArray result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_barang);
        pd = new ProgressDialog(this);
        spinnerKategori = (Spinner) findViewById(R.id.Spinner);
        server_url = "https://aldry.000webhostapp.com/showkategori.php";
        kategori = new ArrayList<String>();
        AmbilData();
    }

    private void AmbilData() {
        final RequestQueue requestQueue = Volley.newRequestQueue(MasterBarang.this);

        pd.setCancelable(false);
        pd.setMessage("Harap Menunggu...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response.toString());
                        hideDialog();
                        JSONObject j = null;

                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray("data");
                            getKategori(result);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MasterBarang.this, "Error JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", error.getMessage());
                Toast.makeText(MasterBarang.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }


    private void getKategori(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                kategori.add(json.getString("kategori"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerKategori.setAdapter(new ArrayAdapter<String>(MasterBarang.this, android.R.layout.simple_list_item_1, kategori));
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
