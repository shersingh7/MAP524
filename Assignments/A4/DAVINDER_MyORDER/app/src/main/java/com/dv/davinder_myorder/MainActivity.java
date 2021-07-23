package com.dv.davinder_myorder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dv.davinder_myorder.adapters.OrderAdapter;
import com.dv.davinder_myorder.database.Coffee;
import com.dv.davinder_myorder.database.CoffeeDAO;
import com.dv.davinder_myorder.models.OrderList;
import com.dv.davinder_myorder.viewModels.CoffeeViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = this.getClass().getCanonicalName();

    private Button btnAdd;
    private Button btnSub;
    private TextView qtyOutput;
    private RadioGroup coffeeSizeGroup;
    private RadioGroup coffeeTypeGroup;
    private Integer tempCoffeeID = 0;

    int qtyValue = 0; //Quantity of the coffee
    private CoffeeViewModel coffeeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnAdd = findViewById(R.id.qty_add);
        this.btnAdd.setOnClickListener(this);

        this.btnSub = findViewById(R.id.qty_minus);
        this.btnSub.setOnClickListener(this);

        this.qtyOutput = findViewById(R.id.qty);
        this.qtyOutput.setText("0");

        this.coffeeViewModel = new ViewModelProvider(this).get(CoffeeViewModel.class);

        coffeeSizeGroup = (RadioGroup) findViewById(R.id.cSize);
        coffeeTypeGroup = (RadioGroup) findViewById(R.id.cType);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {

        if (v != null) {
            switch (v.getId()) {
                case R.id.qty_add: {
                    ++qtyValue;
                    this.qtyOutput.setText("" + qtyValue);
                    break;
                }

                case R.id.qty_minus: {
                    if (qtyValue > 0) --qtyValue;
                    this.qtyOutput.setText("" + qtyValue);
                    break;
                }
            }
        }
    }

    public void order(View v){

        RadioButton selectedCoffeeSizeID = findViewById(coffeeSizeGroup.getCheckedRadioButtonId());
        RadioButton selectedCoffeeTypeID = findViewById(coffeeTypeGroup.getCheckedRadioButtonId());

        saveOrder(selectedCoffeeSizeID, selectedCoffeeTypeID);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Order");
        alert.setMessage("You have successfull ordered the coffee \nDo you want to order another one?");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //reset order
                resetHome();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //show orders
                showOrder();
            }
        });

        alert.show();
        resetHome();
    }

    private void saveOrder(RadioButton size, RadioButton type) {
//        OrderList current = new OrderList();
//        current.setCoffeeQty(qtyValue);
//        current.setCoffeeSize(size.getText().toString());
//        current.setCoffeeType(type.getText().toString());
//        current.setCoffeeID(tempCoffeeID++);

//
        Coffee newCoffee = new Coffee();
        newCoffee.setCoffeeQty(qtyValue);
        newCoffee.setCoffeeSize(size.getText().toString());
        newCoffee.setCoffeeType(type.getText().toString());
        newCoffee.setCoffeeID(tempCoffeeID++);

        this.coffeeViewModel.insertCoffee(newCoffee);

        Log.d(TAG, "saveOrder: Item inserted: " + newCoffee.toString());


       // this.orderArrayList.add(current);

        //Log.d(TAG, "saveOrder: "+ orderArrayList);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.menu_order:{
                this.showOrder();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showOrder(){
        Intent listOrderIntent = new Intent(this, OrdersActivity.class);
        startActivity(listOrderIntent);
    }

    private void resetHome(){
        this.qtyValue=0;
        this.qtyOutput.setText("" + qtyValue);

        coffeeTypeGroup.clearCheck();
        coffeeSizeGroup.clearCheck();
    }


}