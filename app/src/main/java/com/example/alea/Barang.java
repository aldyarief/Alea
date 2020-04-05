package com.example.alea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Barang extends AppCompatActivity {
    private TextView txkategori;
    private ImageView imagekategori;
    private RelativeLayout layoutkategori;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);
        txkategori = (TextView) findViewById(R.id.txedituser);
        imagekategori = (ImageView) findViewById(R.id.imagekategori);
        layoutkategori = (RelativeLayout) findViewById(R.id.layoutkategori);

        txkategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicit = new Intent(Barang.this, KategoriBarang.class);
                startActivity(explicit);
            }
        });

        imagekategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicit = new Intent(Barang.this, KategoriBarang.class);
                startActivity(explicit);
            }
        });

        layoutkategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicit = new Intent(Barang.this, KategoriBarang.class);
                startActivity(explicit);
            }
        });

    }
}
