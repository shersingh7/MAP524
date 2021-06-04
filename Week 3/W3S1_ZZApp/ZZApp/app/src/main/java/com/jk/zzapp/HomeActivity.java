package com.jk.zzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    TextView tvEmail;
    String userEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.tvEmail = findViewById(R.id.tv_email);

        Intent currentIntent = this.getIntent();
        if (currentIntent != null){
            this.userEmail = currentIntent.getStringExtra("EXTRA_USER_EMAIL");
            if (this.userEmail != null && !this.userEmail.equals("Unknown")){
                this.tvEmail.setText(this.userEmail);
            }else{
                this.tvEmail.setText("Nothing Received");
            }
        }

    }
}