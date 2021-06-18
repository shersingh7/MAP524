
package com.dv.davinder_myorder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dv.davinder_myorder.databinding.RvItemsOrdersBinding;
import com.dv.davinder_myorder.models.OrderList;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    private final String TAG = "OrderAdapter: ";
    private final Context context;
    private final ArrayList<OrderList> list;

    public OrderAdapter(Context context, ArrayList<OrderList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new OrderViewHolder(RvItemsOrdersBinding.inflate(LayoutInflater.from(context)));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        final OrderList current = this.list.get(position);
        holder.bind(context, current);
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

        public void bind(Context context, final OrderList currentList){
            binding.rvCoffee.setText(currentList.getCoffeeType());
            binding.rvSize.setText(currentList.getCoffeeSize());
            binding.rvQty.setText(currentList.getCoffeeQty().toString());
        }
    }


}
