package com.app.mymap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.mymap.model.Leaderboard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MiniGame extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference Scoreboard;
    String currentuser = LoginActivity.currentuser ;

    //Frame
    private FrameLayout gameFrame;
    private int frameHigh,frameWide, initialFrameWide;
    private LinearLayout startLayout;

    //Image
    private ImageView box, black ,orange, pink;
    private Drawable imageBoxRight, imageBoxLeft;

    //Size
    private int boxSize;

    //Position
    private float boxX, boxY;
    private float blackX, blackY;
    private float orangeX, orangeY;
    private float pinkX, pinkY;

    //Score
    private TextView scoreLabel, highScoreLabel;
    private int score, highScore, timeCount;
    private SharedPreferences setting;

    //Class
    private Timer timer;
    private Handler handler = new Handler();
    private SoundMinigame soundMinigame;

    //status
    private boolean start_flg = false;
    private boolean action_flg = false;
    private boolean pink_flg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_minigame);
        database = FirebaseDatabase.getInstance();
        Scoreboard = database.getReference("Leaderboard");
        soundMinigame = new SoundMinigame(this);
        gameFrame = findViewById(R.id.gameFrame);
        startLayout = findViewById(R.id.startLayout);
        box = findViewById(R.id.box);
        black = findViewById(R.id.black);
        orange = findViewById(R.id.orange);
        pink = findViewById(R.id.pink);
        scoreLabel = findViewById(R.id.scoreLabel);
        highScoreLabel = findViewById(R.id.highScoreLabel);
        imageBoxLeft = getResources().getDrawable(R.drawable.box_left);
        imageBoxRight = getResources().getDrawable(R.drawable.box_right);
        //High Score
        setting = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        highScore = setting.getInt("HIGH_Score",6);
        highScoreLabel.setText("HIGH_Score : " + highScore);
    }

    public void changePost(){

        //add timeCount
        timeCount += 20;


        //orange
        orangeY += 12;

        float orangeCenterX = orangeX + orange.getWidth() / 2;
        float orangeCenterY = orangeY + orange.getWidth() / 2;

        if (hitCheck(orangeCenterX, orangeCenterY)){
            orangeY = frameHigh + 100;
            score += 10;
            soundMinigame.playHitOrangeSound();
        }
        if (orangeY > frameHigh){
            orangeY = -100;
            orangeX = (float) Math.floor(Math.random()*(frameWide - orange.getWidth()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        //Pink
        if (!pink_flg && timeCount % 10000 == 0){
            pink_flg = true;
            pinkY = -20;
            pinkX = (float) Math.floor(Math.random() * (frameWide -pink.getWidth()));
        }

        if (pink_flg){
            pinkY += 20;

            float pinkCenterX = pinkX + pink.getWidth() / 2;
            float pinkCenterY = pinkY + pink.getHeight() / 2;

            if (hitCheck(pinkCenterX,pinkCenterY)){
                pinkY = frameHigh + 30;
                score += 30;
                // Change FrameWidth

                if (initialFrameWide > frameWide * 110 / 100){
                    frameWide = frameWide * 110 / 100;
                    changeFrameWidth(frameWide);
                }
                soundMinigame.playHitPinkSound();
            }

            if (pinkY > frameHigh) pink_flg = false;
            pink.setX(pinkX);
            pink.setY(pinkY);
        }
        //black
        blackY += 18;
        float blackCenterX = blackX + black.getWidth() / 2;
        float blackCenterY = blackY + black.getHeight() / 2;

        if(hitCheck(blackCenterX,blackCenterY)){
            blackY = frameHigh + 100;
            // Change FrameWidth
            frameWide = frameWide * 80/100;
            changeFrameWidth(frameWide);
            soundMinigame.playHitBlackSound();
            if (frameWide <= boxSize){
                //Game over
                gameOver();
            }
        }
        if (blackY > frameHigh) {
            blackY = -100;
            blackX = (float) Math.floor(Math.random() * (frameWide - black.getWidth()));
        }
        black.setX(blackX);
        black.setY(blackY);

        //Move Box
        if(action_flg){
            //Touching
            boxX += 14;
            box.setImageDrawable(imageBoxRight);
        }else{
            //Releasing
            boxX -=14;
            box.setImageDrawable(imageBoxLeft);
        }

        //Check box position
        if(boxX < 0){
            boxX = 0;
            box.setImageDrawable(imageBoxRight);
        }
        if (frameWide - boxSize < boxX){
            boxX = frameWide - boxSize;
            box.setImageDrawable(imageBoxLeft);
        }

        box.setX(boxX);

        scoreLabel.setText("Score : " + score);
    }

    public boolean hitCheck(float x,float y){
        if (boxX <= x && x <= boxX + boxSize && boxY <= y && y <= frameHigh){
            return true;
        }
        return false;
    }

    public void changeFrameWidth(int frameWide){
        ViewGroup.LayoutParams params = gameFrame.getLayoutParams();
        params.width = frameWide;
        gameFrame.setLayoutParams(params);
    }

    public void gameOver(){
        // stop timer
        timer.cancel();
        timer = null;
        start_flg =false;

        //Before showing start
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        changeFrameWidth(initialFrameWide);

        startLayout.setVisibility(View.VISIBLE);
        box.setVisibility(View.INVISIBLE);
        black.setVisibility(View.INVISIBLE);
        orange.setVisibility(View.INVISIBLE);
        pink.setVisibility(View.INVISIBLE);

        //Update High Score
        if (score > highScore){
            highScore = score;
            highScoreLabel.setText("High Score : " + highScore);

            SharedPreferences.Editor editor = setting.edit();
            editor.putInt("HIGH_SCORE : ",highScore);
            editor.commit();
        }
        final Leaderboard update = new Leaderboard(currentuser,highScore);
        Scoreboard.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Scoreboard.child(update.getUsername()).setValue(update);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (start_flg){
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                action_flg = true;
            }else{
                if (event.getAction() == MotionEvent.ACTION_UP){
                    action_flg = false;
                }
            }
        }
        return true;
    }

    public void startGame(View view) {
        start_flg = true;
        startLayout.setVisibility(View.INVISIBLE);

        if (frameHigh == 0){
            frameHigh = gameFrame.getHeight();
            frameWide = gameFrame.getWidth();
            initialFrameWide = frameWide;

            boxSize = box.getHeight();
            boxX = box.getX();
            boxY = box.getY();
        }

        frameWide = initialFrameWide;

        box.setX(0.0f);
        black.setY(3000.0f);
        orange.setY(3000.0f);
        pink.setY(3000.0f);

        blackY = black.getY();
        orangeY = orange.getY();
        pinkY = pink.getY();

        box.setVisibility(View.VISIBLE);
        black.setVisibility(View.VISIBLE);
        orange.setVisibility(View.VISIBLE);
        pink.setVisibility(View.VISIBLE);

        timeCount = 0;
        score = 0;
        scoreLabel.setText("Score : 0");

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (start_flg){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePost();
                        }
                    });
                }
            }
        },0,20);
    }

    public void quitGame(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            finishAndRemoveTask();
        }else{
            finish();
        }
    }
}
