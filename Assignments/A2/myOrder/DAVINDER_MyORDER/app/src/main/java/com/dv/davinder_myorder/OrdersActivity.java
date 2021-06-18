package com.dv.davinder_myorder;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dv.davinder_myorder.adapters.OrderAdapter;
import com.dv.davinder_myorder.databinding.ActivityOrdersBinding;
import com.dv.davinder_myorder.models.OrderList;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity{

    ActivityOrdersBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    private OrderAdapter adapter;

    private ArrayList<OrderList> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityOrdersBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.list = new ArrayList<>();

        ArrayList<OrderList> extra = this.getIntent().getParcelableArrayListExtra("EXTRA_COFFEE_ORDER_OBJ");

        this.list = extra;
        if(extra != null) Log.d(TAG, "onCreate: " + extra);

        this.adapter = new OrderAdapter(this, this.list);
        this.binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.binding.recyclerView.setAdapter(adapter);
    }


}
