package fr.utt.if26.memoria;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LeaderboardDAO {

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   void insert(ScoreEntity score);

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   void insert(TimeEntity time);

   @Query("SELECT * from scores WHERE cardsCount = :cardsCount ORDER BY score DESC LIMIT 5")
   List<ScoreEntity> getScoresForNumCards(int cardsCount);

   @Query("SELECT * from times WHERE cardsCount = :cardsCount ORDER BY time ASC LIMIT 5")
   List<TimeEntity> getTimesForNumCards(int cardsCount);
}
