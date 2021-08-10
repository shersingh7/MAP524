package com.dv.test.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dv.test.databinding.RvHomeBinding;
import com.dv.test.models.CountryList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CountryAdapterMain extends RecyclerView.Adapter<CountryAdapterMain.CountryViewHolder> {

    private final String TAG = "CountryAdapter: ";
    private final Context context;
    private final ArrayList<CountryList> list;
    private final OnCountryListClickListener countryListClickListener;

    public CountryAdapterMain(Context context, ArrayList<CountryList> list, OnCountryListClickListener countryListClickListener) {
        this.context = context;
        this.list = list;
        this.countryListClickListener = countryListClickListener;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CountryViewHolder(RvHomeBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapterMain.CountryViewHolder holder, int position) {

        final CountryList current = list.get(position);
        holder.bind(context, current, countryListClickListener);
        Log.d(TAG, "onBindViewHolder: " + list);
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static class CountryViewHolder extends RecyclerView.ViewHolder{

        RvHomeBinding binding;

        public CountryViewHolder(RvHomeBinding b) {
            super(b.getRoot());
            this.binding = b;
        }

        public void bind(Context context, final CountryList currentList, final OnCountryListClickListener clickListener){
            binding.rvHomeCountryName.setText(currentList.getName());
            binding.rvHomeCountryCapital.setText(currentList.getCapital());
            Picasso.get().load("https://www.countryflags.io/" + currentList.getCode() + "/flat/64.png").into(binding.rvHomeCountryFlag);

            Log.d("bind(countryAdapter)", "bind: " + currentList);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onCountryListClicked(currentList);
                }
            });
        }
    }


}
