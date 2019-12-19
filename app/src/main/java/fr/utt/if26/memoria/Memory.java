package fr.utt.if26.memoria;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static android.content.Context.MODE_PRIVATE;

class Memory {

    private static final int MAX_SAVES = 5;
    public static final int DEFAULT_CARDS_AMOUNT = 12;

    private static Memory instance;

    private Context context;

    private Memory(Context ctx0) {
        this.context = ctx0;
    }

    public static Memory getInstance(Context ctx0) {
        if( Memory.instance == null )
            Memory.instance = new Memory(ctx0);
        else
            Memory.instance.setContext(ctx0);
        return Memory.instance;
    }

    public void setContext(Context c) {
        this.context = c;
    }

    public void setNbCartes(int nbCartes) {
        SharedPreferences sharedPref = this.context.getSharedPreferences("Options", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("nbCartes", nbCartes);
        editor.commit();
    }

    public int getNbCartes() {
        SharedPreferences sharedPref = this.context.getSharedPreferences("Options", MODE_PRIVATE);

        int nbCartes;

        try {
            nbCartes = sharedPref.getInt("nbCartes", Memory.DEFAULT_CARDS_AMOUNT);
        } catch( ClassCastException e ) {
            nbCartes = Memory.DEFAULT_CARDS_AMOUNT;
        }

        return nbCartes;
    }

    public void toggleSounds(boolean flag) {
        SharedPreferences sharedPref = this.context.getSharedPreferences("Options", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Sounds", flag);
        editor.commit();
    }
    public boolean areSoundsOn() {
        SharedPreferences sharedPref = this.context.getSharedPreferences("Options", MODE_PRIVATE);

        boolean sounds;

        try {
            sounds = sharedPref.getBoolean("Sounds", true);
        } catch( ClassCastException e ) {
            sounds = true;
        }
        return sounds;
    }

    public void toggleMusic(boolean flag) {
        SharedPreferences sharedPref = this.context.getSharedPreferences("Options", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Music", flag);
        editor.commit();
    }
    public boolean isMusicOn() {
        SharedPreferences sharedPref = this.context.getSharedPreferences("Options", MODE_PRIVATE);
        boolean music;
        try {
            music = sharedPref.getBoolean("Music", true);
        } catch( ClassCastException e ) {
            music=true;
        }

        return music;
    }
}
