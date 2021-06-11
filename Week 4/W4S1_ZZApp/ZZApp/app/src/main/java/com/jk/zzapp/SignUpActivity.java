package com.jk.zzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.jk.zzapp.databinding.ActivitySignUpBinding;
import com.jk.zzapp.models.User;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
//    EditText editName;
    ActivitySignUpBinding binding;
    User newUser;
    private final String TAG = this.getClass().getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_up);

        this.binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        this.newUser = new User();
//        this.editName = findViewById(R.id.edit_name);

        this.binding.btnCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v != null){
            switch (v.getId()){
                case R.id.btn_create_account:{
                    //validate data
                    if (this.validateDate()){
                        //create object for User class
                        this.createNewUser();

                        //send the user info to home screen
                        this.goToHome();
                    }
                    break;
                }
            }
        }
    }

    private void createNewUser(){
        this.newUser.setName(this.binding.editName.getText().toString());
        this.newUser.setEmail(this.binding.editEmail.getText().toString());
        this.newUser.setPhoneNumber(this.binding.editPhoneNumber.getText().toString());
        this.newUser.setPassword(this.binding.editPassword.getText().toString());

        RadioButton selectedGenderID = findViewById(this.binding.rdgGender.getCheckedRadioButtonId());
        this.newUser.setGender(selectedGenderID.getText().toString());

        Log.d(TAG, "createNewUser: NewUser " + this.newUser.toString());
    }

    private boolean validateDate(){
        boolean validUser = true;

        if (!this.binding.editPassword.getText().toString().equals(this.binding.editConfirmPassword.getText().toString())){
            this.binding.editPassword.setError("Both passwords must be same");
            this.binding.editConfirmPassword.setError("Both passwords must be same");
            validUser = false;
        }

        //Complete remainign validations

        return validUser;
    }

    private void goToHome(){
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.putExtra("EXTRA_USER_EMAIL", this.newUser.getEmail());
        homeIntent.putExtra("EXTRA_USER_OBJ", this.newUser);
        startActivity(homeIntent);
        this.finishAffinity();
    }
}