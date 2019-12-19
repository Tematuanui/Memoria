package fr.utt.if26.memoria;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ScoreEntity.class, TimeEntity.class}, version = 1, exportSchema = false)
public abstract class LeaderboardDB extends RoomDatabase {

    public abstract LeaderboardDAO leaderboardDao();

    private static volatile LeaderboardDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static LeaderboardDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LeaderboardDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LeaderboardDB.class, "leaderboard")
                        .build();
                }
            }
        }
        return INSTANCE;
    }
}
