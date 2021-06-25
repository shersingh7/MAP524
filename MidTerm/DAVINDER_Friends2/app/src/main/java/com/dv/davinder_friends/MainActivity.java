package com.dv.davinder_friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//import com.dv.davinder_friends.databinding.ActivityMainBinding;
import com.dv.davinder_friends.databinding.ActivityMainBinding;
import com.dv.davinder_friends.models.Friend;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import static java.lang.Integer.getInteger;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = this.getClass().getCanonicalName();

    private EditText eName;
    private EditText eEmail;
    private EditText eNumber;
    private EditText eAddress;

    ActivityMainBinding binding;

    Friend newFriend;


    ArrayList<Friend> list = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addbtn = findViewById(R.id.addFriend);
        addbtn.setOnClickListener(this);

        this.eName = findViewById(R.id.editName);
        this.eEmail = findViewById(R.id.editEmail);
        this.eNumber = findViewById(R.id.editNumber);
        this.eAddress = findViewById(R.id.editAddress);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_friends, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.menu_list:{
                this.showFriends();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if(v!=null)
        {
            if (v.getId() == R.id.addFriend) {
                if (checkEmpty()) {
                    this.newFriend = new Friend();

                    this.newFriend.setName(this.eName.getText().toString());

                    if(this.eEmail.getText().toString().isEmpty()){
                        this.newFriend.setEmail("Not Provided");
                    }else this.newFriend.setEmail(this.eEmail.getText().toString());

                    this.newFriend.setPhone(Integer.valueOf(this.eNumber.getText().toString()));
                    this.newFriend.setAddress(this.eAddress.getText().toString());

                    this.list.add(newFriend);

                    Log.d(TAG, "addFriend: " + newFriend);

                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Friend");
                    alert.setMessage("You have successfull added your Friend \nDo you want to add another one?");

                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //reset
                            resetHome();
                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //show list
                            showFriends();
                        }
                    });

                    alert.show();
                    resetHome();
                } else {
                    Log.d(TAG, "Empty");
                    //No input
                }
            }
        }

    }

    private void showFriends() {
        Intent friendListIntent = new Intent(this, FriendsActivity.class);
        friendListIntent.putExtra("EXTRA_FRIEND_OBJ", this.list);
        startActivity(friendListIntent);
    }

    private void resetHome() {
        this.eName.setText("");
        this.eAddress.setText("");
        this.eEmail.setText("");
        this.eNumber.setText("");
    }

    private Boolean checkEmpty(){

        boolean input = true;

        if(this.eName.getText().toString().isEmpty()){
            this.eName.setError("Name can't be empty");
            input = false;
        }

        if(this.eNumber.getText().toString().isEmpty()){
            this.eNumber.setError("Phone Number can't be empty");
            input = false;
        }else {
            if(!correctNumber()){
                this.eNumber.setError("Phone Number should be 10-12 digits!!!");
                this.eNumber.setText("");
                input = false;
            }

        }

        if(this.eAddress.getText().toString().isEmpty()){
            this.eAddress.setError("Address can't be empty");
            input = false;
        }

        return input;
    }

    private Boolean correctNumber(){
        boolean ok = false;

        int digits = String.valueOf(this.eNumber.getText().toString()).length();
        Log.d(TAG, "correctNumber: NUMBER OF DIGITS " + digits );
        if(digits >= 10 && digits <= 12) ok = true;

        return ok;
    }

}