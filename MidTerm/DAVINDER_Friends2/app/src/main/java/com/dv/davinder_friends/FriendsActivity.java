package com.dv.davinder_friends;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dv.davinder_friends.adapters.FriendsAdapter;
import com.dv.davinder_friends.databinding.ActivityFriendsBinding;
import com.dv.davinder_friends.models.Friend;

import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity implements OnFriendsItemClickListener{

    ActivityFriendsBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    private FriendsAdapter adapter;

    private ArrayList<Friend> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityFriendsBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_friends);//
        setContentView(this.binding.getRoot());

        this.list = new ArrayList<>();

        ArrayList<Friend> extra = this.getIntent().getParcelableArrayListExtra("EXTRA_FRIEND_OBJ");


        this.list = extra;
        if(extra != null) Log.d(TAG, "onCreate: " + extra);

        this.adapter = new FriendsAdapter(this, this.list, this);
        this.binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFriendsItemClicked(Friend f) {
        Intent detailIntent = new Intent(this, MapsActivity.class);
        //detailIntent.putExtra("EXTRA_CURRENT_FRIEND", f);
        startActivity(detailIntent);
    }
}
