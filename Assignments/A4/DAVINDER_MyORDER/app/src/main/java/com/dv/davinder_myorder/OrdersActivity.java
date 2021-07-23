package com.dv.davinder_myorder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dv.davinder_myorder.adapters.OnOrderListClickListener;
import com.dv.davinder_myorder.adapters.OrderAdapter;
import com.dv.davinder_myorder.database.Coffee;
import com.dv.davinder_myorder.databinding.ActivityOrdersBinding;
import com.dv.davinder_myorder.models.OrderList;
import com.dv.davinder_myorder.viewModels.CoffeeViewModel;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class OrdersActivity extends AppCompatActivity implements OnOrderListClickListener {

    ActivityOrdersBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    private OrderAdapter adapter;

    private ArrayList<OrderList> list;

    private CoffeeViewModel coffeeViewModel;
    private OrderList orderList;

    private ItemTouchHelper touchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityOrdersBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.touchHelper = new ItemTouchHelper(simpleCallback);
        this.touchHelper.attachToRecyclerView(this.binding.recyclerView);

        this.list = new ArrayList<>();

        this.adapter = new OrderAdapter(this, this.list, this);

        this.binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.binding.recyclerView.setAdapter(adapter);

        this.coffeeViewModel = new ViewModelProvider(this).get(CoffeeViewModel.class);

        this.coffeeViewModel.getAllCoffees().observe(this, new Observer<List<Coffee>>() {
            @Override
            public void onChanged(List<Coffee> coffees) {
                if(!coffees.isEmpty()){
                    Log.d(TAG, "onChanged: order received: " + coffees.toString());

                    list.clear();

                    for(Coffee temp: coffees){
                        orderList = new OrderList();
                        orderList.setCoffeeID(temp.getCoffeeID());
                        orderList.setCoffeeType(temp.getCoffeeType());
                        orderList.setCoffeeSize(temp.getCoffeeSize());
                        orderList.setCoffeeQty(temp.getCoffeeQty());
                        
                        list.add(orderList);

                    }
                    adapter.notifyDataSetChanged();
                }else {
                    Log.d(TAG, "onChanged: empty order");
                }
            }
        });

    }


    @Override
    public void onOrderListClicked(OrderList orderList) {
        Log.d(TAG, "onOrderListClicked: update order");

        Coffee selectedCoffee = new Coffee();
        selectedCoffee.setCoffeeQty(orderList.getCoffeeQty());
        selectedCoffee.setCoffeeSize(orderList.getCoffeeSize());
        selectedCoffee.setCoffeeType(orderList.getCoffeeType());
        selectedCoffee.setCoffeeID(orderList.getCoffeeID());

        AlertDialog.Builder update = new AlertDialog.Builder(this);
        update.setTitle("Update");
        update.setMessage("Enter the updated quantity");

        final EditText input = new EditText(this);
        input.setText(selectedCoffee.getCoffeeQty().toString());
        Log.d(TAG, "onOrderListClicked: input is " + input);
        update.setView(input);

        update.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                selectedCoffee.setCoffeeQty(parseInt(input.getText().toString()));
                coffeeViewModel.updateCoffee(selectedCoffee);
                adapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), selectedCoffee.getCoffeeQty() + " has been updated", Toast.LENGTH_LONG).show();
            }
        });

        update.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        update.show();
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if(direction == ItemTouchHelper.LEFT){
                deleteCoffee(viewHolder.getAdapterPosition());
            }
        }


    };

    private void deleteCoffee(int position) {
        OrderList selectedCoffee = list.get(position);

        AlertDialog.Builder delete = new AlertDialog.Builder(this);
        delete.setTitle("Delete");
        delete.setMessage("Confirm?");

        delete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Coffee toBeDeleted = new Coffee();
                toBeDeleted.setCoffeeQty(selectedCoffee.getCoffeeQty());
                toBeDeleted.setCoffeeSize(selectedCoffee.getCoffeeSize());
                toBeDeleted.setCoffeeType(selectedCoffee.getCoffeeType());
                toBeDeleted.setCoffeeID(selectedCoffee.getCoffeeID());

                coffeeViewModel.deleteCoffee(toBeDeleted);

                adapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), selectedCoffee.getCoffeeType() + " has been deleted", Toast.LENGTH_LONG).show();
            }
        });

        delete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        delete.show();


    }
}
