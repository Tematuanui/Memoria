package fr.utt.if26.memoria;

import android.content.Context;

import androidx.room.Room;

public class LeaderboardDBClient {

    private Context mCtx;
    private static LeaderboardDBClient mInstance;
    
    //our app database object
    private LeaderboardDB appDatabase;

    private LeaderboardDBClient(Context mCtx) {
        this.mCtx = mCtx;
        
        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, LeaderboardDB.class, "MyToDos").build();
    }

    public static synchronized LeaderboardDBClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new LeaderboardDBClient(mCtx);
        }
        return mInstance;
    }

    public LeaderboardDB getAppDatabase() {
        return appDatabase;
    }
}
