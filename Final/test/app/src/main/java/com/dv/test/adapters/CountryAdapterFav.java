package com.dv.test.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dv.test.databinding.RvFavBinding;
import com.dv.test.databinding.RvHomeBinding;
import com.dv.test.models.CountryList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CountryAdapterFav extends RecyclerView.Adapter<CountryAdapterFav.CountryViewHolder> {

        private final String TAG = "CountryAdapterFav: ";
        private final Context context;
        private final ArrayList<CountryList> list;
        private final OnCountryListClickListener countryListClickListener;

        public CountryAdapterFav(Context context, ArrayList<CountryList> list, OnCountryListClickListener countryListClickListener) {
            this.context = context;
            this.list = list;
            this.countryListClickListener = countryListClickListener;
        }

        @Override
        public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new CountryViewHolder(RvFavBinding.inflate(LayoutInflater.from(context), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CountryAdapterFav.CountryViewHolder holder, int position) {

            final CountryList current = list.get(position);
            holder.bind(context, current, countryListClickListener);
            Log.d(TAG, "onBindViewHolder: " + list);
        }

        @Override
        public int getItemCount() {
            return this.list.size();
        }

        public static class CountryViewHolder extends RecyclerView.ViewHolder{

            RvFavBinding binding;

            public CountryViewHolder(@NonNull RvFavBinding b) {
                super(b.getRoot());
                this.binding = b;
            }

            public void bind(Context context, final CountryList currentList, final OnCountryListClickListener clickListener){
                binding.rvFavCountryName.setText(currentList.getName());
                Picasso.get().load("https://www.countryflags.io/" + currentList.getCode() + "/flat/64.png").into(binding.rvFavCountryFlag);
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
