package com.example.alea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {
    private TextView txuser,txbarang;
    private ImageView imageuser,imagebarang;
    private RelativeLayout layoutuser,layoutbarang;
    String name,pass,userid,user,barang,beli,jual,koreksi,laporan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        txuser = (TextView) findViewById(R.id.txUser);
        imageuser = (ImageView) findViewById(R.id.imageuser);
        layoutuser = (RelativeLayout) findViewById(R.id.layoutuser);
        txbarang = (TextView) findViewById(R.id.txbarang);
        imagebarang = (ImageView) findViewById(R.id.imagebarang);
        layoutbarang = (RelativeLayout) findViewById(R.id.layoutbarang);
        name = (getIntent().getStringExtra("name"));
        pass = (getIntent().getStringExtra("pass"));
        userid = (getIntent().getStringExtra("userid"));
        user = (getIntent().getStringExtra("user"));
        barang = (getIntent().getStringExtra("barang"));
        beli = (getIntent().getStringExtra("beli"));
        jual = (getIntent().getStringExtra("jual"));
        koreksi = (getIntent().getStringExtra("koreksi"));
        laporan = (getIntent().getStringExtra("laporan"));

        txuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.equalsIgnoreCase("1")) {
                    KirimData();
                }else{
                    Toast.makeText(Dashboard.this, "Anda Tidak Memiliki Akses", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.equalsIgnoreCase("1")) {
                    KirimData();
                }else{
                    Toast.makeText(Dashboard.this, "Anda Tidak Memiliki Akses", Toast.LENGTH_SHORT).show();
                }
            }
        });

        layoutuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.equalsIgnoreCase("1")) {
                    KirimData();
                }else{
                    Toast.makeText(Dashboard.this, "Anda Tidak Memiliki Akses", Toast.LENGTH_SHORT).show();
                }
            }
        });


        txbarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (barang.equalsIgnoreCase("1")) {
                    Kirim();

                }else{
                    Toast.makeText(Dashboard.this, "Anda Tidak Memiliki Akses", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imagebarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (barang.equalsIgnoreCase("1")) {
                    Kirim();
                }else{
                    Toast.makeText(Dashboard.this, "Anda Tidak Memiliki Akses", Toast.LENGTH_SHORT).show();
                }
            }
        });

        layoutbarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (barang.equalsIgnoreCase("1")) {
                    Kirim();
                }else{
                    Toast.makeText(Dashboard.this, "Anda Tidak Memiliki Akses", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void KirimData() {
        Intent intent = new Intent(Dashboard.this, user.class);
        intent.putExtra("name", name.trim());
        intent.putExtra("pass", pass.trim());
        intent.putExtra("userid", userid.trim());
        startActivity(intent);
    }

    private void Kirim() {
        Intent intent = new Intent(Dashboard.this, Barang.class);
        intent.putExtra("userid", userid.trim());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda Yakin Keluar?");

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            finish();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}