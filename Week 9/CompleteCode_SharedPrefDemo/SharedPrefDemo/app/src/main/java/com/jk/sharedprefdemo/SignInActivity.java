package com.jk.sharedprefdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.jk.sharedprefdemo.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivitySignInBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.binding.btnSignIn.setOnClickListener(this);

        //obtain the instance of shared prefs
        this.prefs = getApplicationContext().getSharedPreferences(getPackageName(), MODE_PRIVATE);

        if (this.prefs.contains("USER_EMAIL")){
            this.binding.editEmail.setText(this.prefs.getString("USER_EMAIL", ""));
        }else{
            Log.e(TAG, "onCreate: USER_EMAIL isn't available in Prefs");
        }

        if (this.prefs.contains("USER_PASSWORD")){
            this.binding.editPassword.setText(this.prefs.getString("USER_PASSWORD", ""));
        }else{
            Log.e(TAG, "onCreate: USER_PASSWORD isn't available in Prefs");
        }
    }

    @Override
    public void onClick(View v) {
        if (v != null){
            switch (v.getId()){
                case R.id.btn_sign_in:{
                    Log.d(TAG, "Sign In Button Clicked");

                    if (this.verifyUser()){
                        Log.d(TAG, "Login Successful");

                        //If remember me switch is on, save the user email and password in the shared preferences
                        //Otherwise, remove email and password from shared preferences if exists
                        if (this.binding.swtRemember.isChecked()){
                            //save prefs
                            this.prefs.edit().putString("USER_EMAIL", this.binding.editEmail.getText().toString()).apply();
                            this.prefs.edit().putString("USER_PASSWORD", this.binding.editPassword.getText().toString()).apply();
                        }else{
                            //do not save prefs
                            //if prefs are already saved, remove them
                            if (this.prefs.contains("USER_EMAIL")){
                                this.prefs.edit().remove("USER_EMAIL").apply();
                            }
                            if (this.prefs.contains("USER_PASSWORD")){
                                this.prefs.edit().remove("USER_PASSWORD").apply();
                            }
                        }

                        this.goToHome();
                    }else{
                        Log.e(TAG, "Incorrect email/password. Please try again!");
                    }

                    break;
                }
            }
        }
    }

    private Boolean verifyUser(){
        String email = this.binding.editEmail.getText().toString();
        String password = this.binding.editPassword.getText().toString();
        Boolean validUser = true;

        if (email.isEmpty()){
            this.binding.editEmail.setError("Email cannot be empty");
            validUser = false;
        }

        if (password.isEmpty()){
            this.binding.editPassword.setError("Password cannot be empty");
            validUser = false;
        }

        if (!email.equals("test") || !password.equals("1234")){
            return false;
        }

        return validUser;
    }

    private void goToHome(){
        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity(homeIntent);

        //remove the SignInActivity from the activity stack
        this.finishAffinity();
    }
}