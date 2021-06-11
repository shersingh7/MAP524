package com.jk.zzapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jk.zzapp.databinding.ActivityHomeBinding;
import com.jk.zzapp.models.GameScore;
import com.jk.zzapp.models.User;

import java.util.ArrayList;
import java.util.Random;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = this.getClass().getCanonicalName();
    ActivityHomeBinding binding;

    TextView tvEmail;
    String userEmail = "";
    User loggedInUser;

    private int guessedNumber = 0;
    private int correctNumber = 0;
    private int attempts = 5;
    private int points = 50;

    ArrayList<GameScore> scoreArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        this.tvEmail = findViewById(R.id.tv_email);
        this.tvEmail = this.binding.tvEmail;
        this.binding.btnCheckAnswer.setOnClickListener(this);

        this.generateRandomNumber();

        Intent currentIntent = this.getIntent();
        if (currentIntent != null){
            this.userEmail = currentIntent.getStringExtra("EXTRA_USER_EMAIL");
            if (this.userEmail != null && !this.userEmail.equals("Unknown")){
                this.tvEmail.setText(this.userEmail);
            }else{
                this.tvEmail.setText("Nothing Received");
            }

            this.loggedInUser = (User) currentIntent.getSerializableExtra("EXTRA_USER_OBJ");
            if (this.loggedInUser != null){
                this.tvEmail.setText(this.loggedInUser.getName());
                Log.d(TAG, "onCreate: LoggedInUser " + this.loggedInUser.toString());
            }else {
                this.tvEmail.setText("Name Not Received");
            }
        }

    }

    @Override
    public void onClick(View v) {
        if(v != null){
            switch (v.getId()){
                case R.id.btn_check_answer:{

                    if (!this.binding.editAnswer.getText().toString().isEmpty()){
                        this.guessedNumber = Integer.parseInt(this.binding.editAnswer.getText().toString());
                        //check for correct answer
                        this.checkResult();
                    }else{
                        this.binding.editAnswer.setError("Please enter your guess !");
                    }
                    break;
                }
            }
        }
    }

    private void generateRandomNumber(){
//        Random rnd = new Random();
//        this.correctNumber = rnd.nextInt(25);

        this.correctNumber = (int) (1 + Math.random() * 25);
        Log.d(TAG, "generateRandomNumber: CorrectNumber " + correctNumber);
    }

    private void checkResult(){
        this.attempts--;
        this.binding.tvAttempts.setText("You have " + this.attempts + " attempts left.");

        if (this.guessedNumber == this.correctNumber){
            //save score
            this.saveScore(true);
            //display result - Won
            this.declareResult(true);
        }else{
            this.points -= 10;

            if (this.attempts == 0){
                //user lost game - GAME OVER
                this.saveScore(false);
                this.declareResult(false);
            }

            if (this.guessedNumber > this.correctNumber){
                this.binding.tvAttempts.setText( this.binding.tvAttempts.getText() + "\nThe number you have guessed is too HIGH.");
            }else{
                this.binding.tvAttempts.setText( this.binding.tvAttempts.getText() + "\nThe number you have guessed is too LOW.");
            }
        }
    }

    private void saveScore(boolean result){
        GameScore currentGame = new GameScore();
        currentGame.setCorrectNumber(this.correctNumber);
        currentGame.setUserEmail(this.userEmail);
        currentGame.setNumberOfAttempts(5 - this.attempts);
        currentGame.setResult(result);
        currentGame.setPoints(this.points);

        this.scoreArrayList.add(currentGame);
        Log.d(TAG, "saveScore: currentGame " + currentGame.toString());
        Log.d(TAG, "saveScore: ScoreArrayList : " + scoreArrayList.toString());
    }

    private void declareResult(boolean result){
        String resultMessage = result ? "Great! You won the game." : "You lost game. Better luck next time.";

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Result");
        alertBuilder.setMessage(resultMessage + "\n Do you want to play again?");
        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //reset game
                resetGame();
            }
        });
        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //show scoreboard
                showScoreBoard();
            }
        });

        alertBuilder.show();
    }

    private void resetGame(){
        this.generateRandomNumber();
        this.attempts = 5;
        this.points = 50;
        this.guessedNumber = 0;

        this.binding.editAnswer.setText("");
        this.binding.editAnswer.setHint("Answer");
        this.binding.tvAttempts.setText("");
    }

    private void showScoreBoard(){
        Intent scoreBoardIntent = new Intent(this, ScoreBoardActivity.class);
        startActivity(scoreBoardIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_show_scoreboard:{
                this.showScoreBoard();
                break;
            }
            case R.id.action_sign_out:{
                this.finish();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}