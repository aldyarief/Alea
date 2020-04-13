package com.example.alea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Barang extends AppCompatActivity {
    private TextView txkategori,txmasbar;
    private ImageView imagekategori,imagemasbar;
    private RelativeLayout layoutkategori,layoutmasbar;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);
        txkategori = (TextView) findViewById(R.id.txkategori);
        imagekategori = (ImageView) findViewById(R.id.imagekategori);
        layoutkategori = (RelativeLayout) findViewById(R.id.layoutkategori);
        txmasbar = (TextView) findViewById(R.id.txmasbar);
        imagemasbar = (ImageView) findViewById(R.id.imagemasbar);
        layoutmasbar = (RelativeLayout) findViewById(R.id.layoutmasbar);
        userid = (getIntent().getStringExtra("userid"));

        txkategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kirim();
            }
        });
        imagekategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kirim();
            }
        });
        layoutkategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kirim();
            }
        });
        txmasbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kiriman();
            }
        });
        imagemasbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kiriman();
            }
        });
        layoutmasbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kiriman();
            }
        });
    }

    private void Kirim() {
        Intent intent = new Intent(Barang.this, KategoriBarang.class);
        intent.putExtra("userid", userid.trim());
        startActivity(intent);
    }


    private void Kiriman() {
        Intent intent = new Intent(Barang.this, MasterBarang.class);
        intent.putExtra("userid", userid.trim());
        startActivity(intent);
    }

}
