package fr.utt.if26.memoria;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scores")
public class ScoreEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "score")
    private int score;

    @NonNull
    @ColumnInfo(name = "cardsCount")
    private int cardsCount;

    public int getId()         { return this.id;         }
    public int getScore()      { return this.score;      }
    public int getCardsCount() { return this.cardsCount; }

    public void setId(int id0)                 { this.id = id0;                 }
    public void setScore(int score0)           { this.score = score0;           }
    public void setCardsCount(int cardsCount0) { this.cardsCount = cardsCount0; }
}
