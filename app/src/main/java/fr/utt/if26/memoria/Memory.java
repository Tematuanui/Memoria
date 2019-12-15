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

    /** 
     * Gives best times saved in the memory
     */
    public int[] getBestTimes() {

        SharedPreferences sharedPref = this.context.getSharedPreferences("Leaderboard", MODE_PRIVATE);
        String bestTimes = sharedPref.getString("bestTimes", null);

        if( bestTimes == null ) return null;

        String[] tmp = bestTimes.split(",");

        int[] out = new int[tmp.length];

        int i=0;
        for( String time : tmp ) {
            try {
                out[i] = Integer.parseInt(tmp[i]);
                i++;
            } catch(NumberFormatException e) {
            }
        }

        out = Arrays.copyOf(out, i);
        Arrays.sort(out);

        return out;
    }

    public void addNewTime(int time) {

        int[] bestTimes = this.getBestTimes();

        int highest = -1;
        int highest_position = -1;

        if( bestTimes == null ) {
            bestTimes = new int[1];
            highest = time+1;
            highest_position = 0;
        } else if( bestTimes.length < Memory.MAX_SAVES ) {
            int[] tmp = new int[ bestTimes.length + 1 ];
            for( int i=0 ; i<bestTimes.length ; i++ ) {
                tmp[i] = bestTimes[i];
            }
            highest = time+1;
            highest_position = bestTimes.length;

            bestTimes = tmp;
        } else {
            for( int i=0 ; i<bestTimes.length ; i++ ) {

                if( highest == -1 || highest < bestTimes[i] ) {
                    highest = bestTimes[i];
                    highest_position = i;
                }
            }
        }

        if( time > highest ) return;

        bestTimes[highest_position] = time;

        String str_bestTimes = String.valueOf(bestTimes[0]);

        for( int i=1 ; i<bestTimes.length ; i++ )
            str_bestTimes += "," + bestTimes[i];


        SharedPreferences sharedPref = this.context.getSharedPreferences("Leaderboard", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("bestTimes", str_bestTimes);
        editor.commit();
    }

    public int[] getBestScores() {

        SharedPreferences sharedPref = this.context.getSharedPreferences("Leaderboard", MODE_PRIVATE);
        String bestScores = sharedPref.getString("bestScores", null);

        if( bestScores == null ) return null;

        String[] tmp = bestScores.split(",");

        int[] out = new int[tmp.length];

        int i=0;
        for( String score : tmp ) {
            try {
                out[i] = Integer.parseInt(tmp[i]);
                i++;
            } catch(NumberFormatException e) {
            }
        }

        out = Arrays.copyOf(out, i);

        Arrays.sort(out);

        // reversing the array...
        for( i=0; i<out.length/2; i++ ) {
            int tmp1 = out[i];
            out[i] = out[out.length -i -1];
            out[out.length -i -1] = tmp1;
        }

        return out;
    }

    public void addNewScore(int score) {

        int[] bestScores = this.getBestScores();

        int lowest = -1;
        int lowest_position = -1;

        if( bestScores == null ) {
            bestScores = new int[1];
            lowest = score-1;
            lowest_position = 0;
        } else if( bestScores.length < Memory.MAX_SAVES ) {
            int[] tmp = new int[ bestScores.length + 1 ];
            for( int i=0 ; i<bestScores.length ; i++ ) {
                tmp[i] = bestScores[i];
            }
            lowest = score-1;
            lowest_position = bestScores.length;

            bestScores = tmp;
        } else {
            for( int i=0 ; i<bestScores.length ; i++ ) {

                if( lowest == -1 || lowest > bestScores[i] ) {
                    lowest = bestScores[i];
                    lowest_position = i;
                }
            }
        }

        if( score < lowest ) return;

        bestScores[lowest_position] = score;

        String str_bestScores = String.valueOf(bestScores[0]);

        for( int i=1 ; i<bestScores.length ; i++ )
            str_bestScores += "," + bestScores[i];


        SharedPreferences sharedPref = this.context.getSharedPreferences("Leaderboard", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("bestScores", str_bestScores);
        editor.commit();
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
