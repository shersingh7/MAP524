package com.jk.zzapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jk.zzapp.DetailActivity;
import com.jk.zzapp.OnScoreItemClickListener;
import com.jk.zzapp.databinding.RvItemScoreBinding;
import com.jk.zzapp.models.GameScore;

import java.util.ArrayList;

/**
 * ZZApp Created by jkp.
 */
public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private final String TAG = "ScoreAdapter";
    private final Context context;
    private final ArrayList<GameScore> gameScoreArrayList;
    private final OnScoreItemClickListener clickListener;

    public ScoreAdapter(Context context, ArrayList<GameScore> gameScoreArrayList, OnScoreItemClickListener clickListener) {
        this.context = context;
        this.gameScoreArrayList = gameScoreArrayList;
        this.clickListener = clickListener;
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScoreViewHolder(RvItemScoreBinding.inflate(LayoutInflater.from(context)));
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        final GameScore currentGameScore = this.gameScoreArrayList.get(position);
        holder.bind(context, currentGameScore, clickListener);
    }

    @Override
    public int getItemCount() {
        return this.gameScoreArrayList.size();
    }

    public static class ScoreViewHolder extends RecyclerView.ViewHolder{
        RvItemScoreBinding binding;

        public ScoreViewHolder(RvItemScoreBinding b){
            super(b.getRoot());
            this.binding = b;
        }

        public void bind(Context context, final GameScore currentScore, OnScoreItemClickListener clickListener){
            binding.tvAttempts.setText("# of attempts : " + currentScore.getNumberOfAttempts());
            binding.tvPoints.setText("Points : " + currentScore.getPoints());

            if (currentScore.isResult()){
                binding.imgResult.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.checkbox_on_background));
            }else{
                binding.imgResult.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.ic_delete));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("ScoreViewHolder", "onClick: Selected Game : " + currentScore.toString());
                    clickListener.onScoreItemClicked(currentScore);

                    //opening a new activity from within adapter is not a good practice
//                    Intent detailIntent = new Intent(context, DetailActivity.class);
//                    detailIntent.putExtra("EXTRA_CURRENT_GAME", currentScore);
//                    context.startActivity(detailIntent);
                }
            });
        }
    }
}
