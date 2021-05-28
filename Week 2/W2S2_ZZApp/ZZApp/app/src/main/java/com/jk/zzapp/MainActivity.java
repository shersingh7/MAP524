package com.jk.zzapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = this.getClass().getCanonicalName();

    //1 - create an object to represent the view
    private Button btnSignIn;
    private Button btnSignUp;
    private EditText editEmail;
    private EditText editPassword;
    private TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2 - find the reference of the view and associate it with object
        this.btnSignIn = findViewById(R.id.btn_sign_in);
        this.btnSignIn.setOnClickListener(this);

        this.btnSignUp = findViewById(R.id.btn_sign_up);
        this.btnSignUp.setOnClickListener(this);

        this.editEmail = findViewById(R.id.edit_email);
        this.editPassword = findViewById(R.id.edit_password);
        this.tvOutput = findViewById(R.id.tv_output);
    }

    @Override
    public void onClick(View v) {
        if (v != null){
            switch (v.getId()){
                case R.id.btn_sign_in:{
                    Log.d(TAG, "Sign In Button Clicked");

                    if (this.verifyUser()){
                        Log.d(TAG, "Login Successful");
                    }else{
                        Log.d(TAG, "Incorrect email/password. Please try again!");
                    }

                    break;
                }
                case R.id.btn_sign_up:{
                    Log.d(TAG, "Sign Up Button Clicked");
                    break;
                }
            }
        }
    }

    private Boolean verifyUser(){
        String email = this.editEmail.getText().toString();
        String password = this.editPassword.getText().toString();

        if (email.equals("test") && password.equals("test")){
            return true;
        }

        return false;
    }
}