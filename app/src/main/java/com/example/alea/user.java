package com.example.alea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class user extends AppCompatActivity {
    private TextView txedituser,txtambahuser;
    private ImageView imagetambahuser,imageedituser;
    private RelativeLayout layouttambahuser,layoutedituser;
    String name,pass,userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        txtambahuser = (TextView) findViewById(R.id.txtambahuser);
        imagetambahuser = (ImageView) findViewById(R.id.imagetambahuser);
        layouttambahuser = (RelativeLayout) findViewById(R.id.layouttambahuser);
        txedituser = (TextView) findViewById(R.id.txedituser);
        imageedituser = (ImageView) findViewById(R.id.imageedituser);
        layoutedituser = (RelativeLayout) findViewById(R.id.layoutedituser);
        name = (getIntent().getStringExtra("name"));
        pass = (getIntent().getStringExtra("pass"));
        userid = (getIntent().getStringExtra("userid"));

        txtambahuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kirim();
            }
        });

        imagetambahuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kirim();
            }
        });

        layouttambahuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kirim();
            }
        });

        txedituser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KirimData();
            }
        });

        imageedituser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KirimData();
            }
        });

        layoutedituser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KirimData();
            }
        });

    }

    private void KirimData() {
        Intent intent = new Intent(user.this, EditUser.class);
        intent.putExtra("name", name.trim());
        intent.putExtra("pass", pass.trim());
        startActivity(intent);
    }

    private void Kirim() {
        Intent intent = new Intent(user.this, MasterUser.class);
        intent.putExtra("userid", userid.trim());
        startActivity(intent);
    }
}
