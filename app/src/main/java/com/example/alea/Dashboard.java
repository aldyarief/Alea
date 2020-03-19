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

public class Dashboard extends AppCompatActivity {
    private TextView txuser;
    private ImageView imageuser;
    private RelativeLayout layoutuser;
    String name,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        txuser = (TextView) findViewById(R.id.txUser);
        imageuser = (ImageView) findViewById(R.id.imageuser);
        layoutuser = (RelativeLayout) findViewById(R.id.layoutuser);
        name = (getIntent().getStringExtra("name"));
        pass = (getIntent().getStringExtra("pass"));

        txuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicit = new Intent(Dashboard.this, user.class);
                startActivity(explicit);
                KirimData();
            }
        });

        imageuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicit = new Intent(Dashboard.this, user.class);
                startActivity(explicit);
                KirimData();
            }
        });

        layoutuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicit = new Intent(Dashboard.this, user.class);
                startActivity(explicit);
                KirimData();
            }
        });
    }

    private void KirimData() {
        Intent intent = new Intent(Dashboard.this, user.class);
        intent.putExtra("name", name.trim());
        intent.putExtra("pass", pass.trim());
        startActivity(intent);
    }
}