package fr.utt.if26.memoria;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "times")
public class TimeEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "time")
    private int time;

    @NonNull
    @ColumnInfo(name = "cardsCount")
    private int cardsCount;

    public int getId()         { return this.id;         }
    public int getTime()       { return this.time;       }
    public int getCardsCount() { return this.cardsCount; }

    public void setId(int id0)                 { this.id = id0;                 }
    public void setTime(int time0)             { this.time = time0;             }
    public void setCardsCount(int cardsCount0) { this.cardsCount = cardsCount0; }
}
