package com.jk.firedemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.jk.firedemo.databinding.ActivityMainBinding;
import com.jk.firedemo.models.Friend;
import com.jk.firedemo.viewmodels.FriendViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;
    private FriendViewModel friendViewModel;
    private Friend matchedFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnInsert.setOnClickListener(this);
        binding.btnDelete.setOnClickListener(this);
        binding.btnSearch.setOnClickListener(this);
        binding.btnUpdate.setOnClickListener(this);

        this.friendViewModel = FriendViewModel.getInstance(this.getApplication());
        this.friendViewModel.allFriends.observe(this, new Observer<List<Friend>>() {
            @Override
            public void onChanged(List<Friend> friends) {
                if (friends != null){
                    for (Friend frnd : friends){
                        Log.e(TAG, "onChanged: Friend : " + frnd.toString() );
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if ( v!= null){
            switch (v.getId()){
                case R.id.btn_insert: {
                    Intent insertIntent = new Intent(this, InsertActivity.class);
                    startActivity(insertIntent);
                    break;
                }
                case R.id.btn_search:{
                    this.searchFriend();
                    break;
                }
                case R.id.btn_delete:{
                    if (this.matchedFriend != null){
                        this.friendViewModel.removeFriend(this.matchedFriend.getId());
                        this.toggleEditControls(false);
                        this.matchedFriend = null;
                    }
                    break;
                }
                case R.id.btn_update:{
                    if (matchedFriend != null){
                        this.matchedFriend.setName(this.binding.editUpdateName.getText().toString());
                        this.matchedFriend.setPhoneNumber(this.binding.editUpdatePhone.getText().toString());

                        this.friendViewModel.updateFriend(this.matchedFriend);
                        this.toggleEditControls(false);
                        this.matchedFriend = null;
                    }
                    break;
                }
            }
        }
    }

    private void toggleEditControls(Boolean state){
        binding.editUpdatePhone.setEnabled(state);
        binding.editUpdateName.setEnabled(state);
        binding.btnDelete.setEnabled(state);
        binding.btnUpdate.setEnabled(state);
    }

    private void searchFriend(){
        String nameToSearch = this.binding.editName.getText().toString();

        if (nameToSearch.isEmpty()){
            this.binding.editName.setError("Please provide name to search a friend");
            return;
        }

        this.friendViewModel.searchFriendByName(nameToSearch);

        this.friendViewModel.getFriendRepository().foundStatus.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String status) {
                if (status.equals("SUCCESS")){
                    binding.progressBar.setVisibility(View.GONE);

                    friendViewModel.getFriendRepository().friendFromDB.observe(MainActivity.this, new Observer<Friend>() {
                        @Override
                        public void onChanged(Friend friend) {
                            matchedFriend = friend;

                            binding.editUpdateName.setText(matchedFriend.getName());
                            binding.editUpdatePhone.setText(matchedFriend.getPhoneNumber());

                            toggleEditControls(true);
                        }
                    });
                }else if (status.equals("SEARCHING")){
                    binding.progressBar.setVisibility(View.VISIBLE);
                }else if (status.equals("FAILURE")){
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "No friends with given name found", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}