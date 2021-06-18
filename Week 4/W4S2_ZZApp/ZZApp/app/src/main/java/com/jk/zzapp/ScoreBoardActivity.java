package com.jk.zzapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jk.zzapp.adapters.ScoreAdapter;
import com.jk.zzapp.databinding.ActivityScoreBoardBinding;
import com.jk.zzapp.models.GameScore;

import java.util.ArrayList;

public class ScoreBoardActivity extends AppCompatActivity implements OnScoreItemClickListener{
    private final String TAG = this.getClass().getCanonicalName();
    private ArrayList<GameScore> scoreArrayList;
    private ActivityScoreBoardBinding binding;
    private ScoreAdapter scoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_score_board);
        this.binding = ActivityScoreBoardBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.scoreArrayList = new ArrayList<>();

        ArrayList<GameScore> extraScores = this.getIntent().getParcelableArrayListExtra("EXTRA_SCORE_LIST");
        if (extraScores != null){
            this.scoreArrayList = extraScores;
            Log.d(TAG, "onCreate: Score list received from HomeActivity " + this.scoreArrayList.toString());
        }else{
            Log.e(TAG, "onCreate: No data received from HomeActivity");
        }

        this.scoreAdapter = new ScoreAdapter(this, this.scoreArrayList, this);
        this.binding.rvScoreList.setLayoutManager(new LinearLayoutManager(this));
        this.binding.rvScoreList.addItemDecoration(new DividerItemDecoration(this.getApplicationContext(), DividerItemDecoration.VERTICAL));
        this.binding.rvScoreList.setAdapter(scoreAdapter);
    }

    @Override
    public void onScoreItemClicked(GameScore gameScore) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("EXTRA_CURRENT_GAME", gameScore);
        startActivity(detailIntent);
    }
}