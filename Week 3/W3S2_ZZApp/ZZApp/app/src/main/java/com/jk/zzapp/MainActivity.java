package com.jk.zzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    String userEmail = "Unknown";

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
                        this.tvOutput.setText("Login Successful");

                        this.goToHome();
                    }else{
                        Log.d(TAG, "Incorrect email/password. Please try again!");
                        this.tvOutput.setText("Login Unsuccessful");
                    }

                    break;
                }
                case R.id.btn_sign_up:{
                    Log.d(TAG, "Sign Up Button Clicked");
                    this.goToSignUpScreen();
                    break;
                }
            }
        }
    }

    private Boolean verifyUser(){
        Boolean validData = true;

        if (this.editEmail.getText().toString().isEmpty()){
            this.editEmail.setError("Email cannot be empty");
            validData = false;
            return validData;
        }

        if (this.editPassword.getText().toString().isEmpty()){
            this.editPassword.setError("Password cannot be empty");
            validData = false;
            return validData;
        }

        String email = this.editEmail.getText().toString();
//        String password = this.editPassword.getText().toString();
        int password = Integer.parseInt(this.editPassword.getText().toString());

        if (email.equals("test") && password == 1234){
            this.userEmail = email;
            return true;
        }

        return false;
    }

    private void goToHome(){
        //create Intent object
        //Explicit Intent type
        Intent homeIntent = new Intent(this, HomeActivity.class);

        //set Extra data
        homeIntent.putExtra("EXTRA_USER_EMAIL", this.userEmail);

        //start a new activity using the defined Intent object
        startActivity(homeIntent);
    }

    private void goToSignUpScreen(){
        Intent signupIntent = new Intent(this, SignUpActivity.class);
        startActivity(signupIntent);
    }
}










