package fr.utt.if26.memoria;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

public class SoundBox {
    private static SoundBox instance = null;

    private Context context;
    private boolean soundsOn;
    private MediaPlayer music;
    private MediaPlayer bip;
    private MediaPlayer cardFlip;
    
    private SoundBox(Context ctx) {
        this.context = ctx;

        this.music = MediaPlayer.create(this.context,R.raw.miisong);

        this.bip = MediaPlayer.create(this.context, R.raw.bip);
        this.cardFlip = MediaPlayer.create(this.context, R.raw.cardflip);

        this.soundsOn = true;
    }
    
    public static SoundBox getInstance(Context ctx) {
        if( SoundBox.instance == null )
            SoundBox.instance = new SoundBox(ctx);
        else
            SoundBox.instance.setContext(ctx);
    
        return SoundBox.instance;
    }

    public void setContext(Context ctx) {
        this.context = ctx;
    }

    public void toggleSounds(){
        this.toggleSounds(!this.soundsOn);
    }
    public void toggleSounds(boolean flag){
        this.soundsOn = flag;
    }

    public void bip() {
        if( ! this.soundsOn ) return;

        this.bip.start();
    }

    public void cardFlip() {
        if( ! this.soundsOn ) return;

        this.cardFlip.start();
    }


    public void startMusic() {
        this.music.start();
        this.music.setLooping(true);
    }

    public void stopMusic() {
        this.music.pause();
    }
}
