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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        txtambahuser = (TextView) findViewById(R.id.txtambahuser);
        imagetambahuser = (ImageView) findViewById(R.id.imagetambahuser);
        layouttambahuser = (RelativeLayout) findViewById(R.id.layouttambahuser);

        txtambahuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicit = new Intent(user.this, MasterUser.class);
                startActivity(explicit);
            }
        });

        imagetambahuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicit = new Intent(user.this, MasterUser.class);
                startActivity(explicit);
            }
        });

        layouttambahuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicit = new Intent(user.this, MasterUser.class);
                startActivity(explicit);
            }
        });


    }
}
