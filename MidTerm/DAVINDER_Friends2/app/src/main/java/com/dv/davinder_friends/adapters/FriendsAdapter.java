package com.dv.davinder_friends.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dv.davinder_friends.OnFriendsItemClickListener;
import com.dv.davinder_friends.databinding.RvFriendsListBinding;
import com.dv.davinder_friends.models.Friend;

import java.util.ArrayList;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {

    private final String TAG = "FriendsAdapter: ";
    private final Context context;
    private final ArrayList<Friend> list;
    private final OnFriendsItemClickListener clickListener;

    public FriendsAdapter(Context context, ArrayList<Friend> list, OnFriendsItemClickListener clickListener) {
        this.context = context;
        this.list = list;
        this.clickListener = clickListener;
    }


    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendsViewHolder(RvFriendsListBinding.inflate(LayoutInflater.from(context)));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {

        final Friend current = this.list.get(position);
        holder.bind(context, current, clickListener);

    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }


    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        RvFriendsListBinding binding;

        public FriendsViewHolder(RvFriendsListBinding b) {
            super(b.getRoot());
            this.binding = b;
        }

        public void bind(Context context, final Friend currentList, OnFriendsItemClickListener clickListner) {
            binding.rvName.setText(currentList.getName());
            binding.rvOutputEmail.setText(currentList.getEmail());
            binding.rvOutputNumber.setText(currentList.getPhone().toString());
            binding.rvOutputAddress.setText(currentList.getAddress());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListner.onFriendsItemClicked(currentList);
                }
            });

        }

    }
}
