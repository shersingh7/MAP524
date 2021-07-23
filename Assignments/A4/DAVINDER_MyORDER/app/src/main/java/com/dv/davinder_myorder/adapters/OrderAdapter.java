
package com.dv.davinder_myorder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dv.davinder_myorder.database.Coffee;
import com.dv.davinder_myorder.databinding.RvItemsOrdersBinding;
import com.dv.davinder_myorder.models.OrderList;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    private final String TAG = "OrderAdapter: ";
    private final Context context;
    private final ArrayList<OrderList> list;
    private final OnOrderListClickListener orderListClickListener;

    public OrderAdapter(Context context, ArrayList<OrderList> list, OnOrderListClickListener orderListClickListener) {
        this.context = context;
        this.list = list;
        this.orderListClickListener = orderListClickListener;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new OrderViewHolder(RvItemsOrdersBinding.inflate(LayoutInflater.from(context)));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        final OrderList current = this.list.get(position);
        holder.bind(context, current, orderListClickListener);
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder{

        RvItemsOrdersBinding binding;

        public OrderViewHolder(RvItemsOrdersBinding b) {
            super(b.getRoot());
            this.binding = b;
        }

        public void bind(Context context, final OrderList currentList, final OnOrderListClickListener clickListener){
            binding.rvCoffee.setText(currentList.getCoffeeType());
            binding.rvSize.setText(currentList.getCoffeeSize());
            binding.rvQty.setText(currentList.getCoffeeQty().toString());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onOrderListClicked(currentList);
                }
            });
        }
    }


}
