package com.app.mymap;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundMinigame {
    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX = 3;

    private static SoundPool soundPool;
    private static int hitOrangeSound;
    private static int hitPinkSound;
    private static int hitBlackSound;

    public SoundMinigame(Context context){
        //sound pool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC,0);
        }

        hitOrangeSound = soundPool.load(context, R.raw.orange,1);
        hitPinkSound = soundPool.load(context, R.raw.pink,1);
        hitBlackSound = soundPool.load(context, R.raw.black,1);
    }
    public void playHitOrangeSound(){
        soundPool.play(hitOrangeSound,1.0f,1.0f,1,0,1.0f);
    }
    public void playHitPinkSound(){
        soundPool.play(hitPinkSound,1.0f,1.0f,1,0,1.0f);
    }
    public void playHitBlackSound(){
        soundPool.play(hitBlackSound,1.0f,1.0f,1,0,1.0f);
    }
}
