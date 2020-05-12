package com.example.alea;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import org.json.JSONArray;
import java.util.HashMap;
import java.util.Map;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.net.Uri;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import android.provider.MediaStore;

public class MasterBarang extends AppCompatActivity implements Spinner.OnItemSelectedListener {
    ProgressDialog pd;
    String server_url,data,id,nama,harga,katid,userid;
    Spinner spinnerKategori;
    EditText Ednama,Edharga;
    String JSON_ARRAY = "data";
    Button btnsave,btntutup,buttonChoose;
    TextView DataID;
    private TextView textID;
    private ArrayList<String> kategori;
    private JSONArray result;
    ImageView imageView;
    EditText txt_name;
    Bitmap bitmap, decoded;
    int success;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_barang);
        pd = new ProgressDialog(this);
        spinnerKategori = (Spinner) findViewById(R.id.Spinner);
        textID = (TextView) findViewById(R.id.textID);
        btnsave = (Button) findViewById(R.id.btnSave);
        btntutup = (Button) findViewById(R.id.btnTutup);
        Ednama = (EditText) findViewById(R.id.ednambar);
        Edharga = (EditText) findViewById(R.id.edharbar);
        DataID = (TextView) findViewById(R.id.textID);
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        imageView = (ImageView) findViewById(R.id.imageView);
        userid = (getIntent().getStringExtra("userid"));
        server_url = "https://aldry.000webhostapp.com/showkategori.php";
        kategori = new ArrayList<String>();
        AmbilKategori();
        spinnerKategori.setOnItemSelectedListener(this);

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String nama = Ednama.getText().toString().trim();
            String harga = Edharga.getText().toString().trim();
            String katid = DataID.getText().toString().trim();

            if (!nama.isEmpty() && !harga.isEmpty() ) {
                simpanData(nama,harga,katid,userid);
            } else if (nama.isEmpty()) {
                Ednama.setError("nama barang tidak boleh kosong");
                Ednama.requestFocus();
            } else if (harga.isEmpty()) {
                Edharga.setError("harga barang tidak boleh kosong");
                Edharga.requestFocus();
            }
            }

        });

        btntutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicit = new Intent(MasterBarang.this, Barang.class);
                startActivity(explicit);
            }

        });
    }

    private void AmbilKategori() {
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
        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, kategori);
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerKategori.setAdapter(dataAdapter);
    }

    private String getKategoriID(int position){
        String KategoriID="";
        try {
            JSONObject json = result.getJSONObject(position);
            KategoriID = json.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return KategoriID;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Setting the values to textviews for a selected item
        textID.setText(getKategoriID(position));
    }


    public void onNothingSelected(AdapterView<?> parent) {
        textID.setText("");
    }

    private void simpanData(final String nama,final String harga,final String katid,final String userid) {
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

                        try {
                            JSONObject jObject = new JSONObject(response);
                            String pesan = jObject.getString("pesan");
                            String hasil = jObject.getString("result");
                            if (hasil.equalsIgnoreCase("true")) {
                                Toast.makeText(MasterBarang.this,pesan, Toast.LENGTH_SHORT).show();
                                requestQueue.stop();
                                Ednama.getText().clear();
                                Edharga.getText().clear();

                            } else {
                                Toast.makeText(MasterBarang.this, pesan, Toast.LENGTH_SHORT).show();
                                requestQueue.stop();
                                Ednama.getText().clear();
                                Edharga.getText().clear();
                            }
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
                param.put("nama", nama);
                param.put("harga", harga);
                param.put("katid", katid);
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void showDialog() {
        if (!pd.isShowing())
            pd.show();
    }


    private void hideDialog() {
        if (pd.isShowing())
            pd.dismiss();
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                setToImageView(getResizedBitmap(bitmap, 512));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void kosong() {
        imageView.setImageResource(0);
        txt_name.setText(null);
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        imageView.setImageBitmap(decoded);
    }

    // fungsi resize image
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

}
