package fr.utt.if26.memoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboad);

        Intent intent = getIntent();

        String currentScore = intent.getStringExtra("score");
        String currentTime = intent.getStringExtra("time");

        //
        // Adding score and time to memory ( won't be saved if not in top 5 )
        //
        Memory m = Memory.getInstance( getApplicationContext() );
        if( currentScore != null  && currentTime != null) {
            this.addScoreAndTimeToDB(Integer.parseInt(currentScore), Integer.parseInt(currentTime));
        } else {
            // It means we come from the main menu, we should take off the "play again" button
            final Button bt_playAgain = (Button)findViewById(R.id.button_replay);
            bt_playAgain.setVisibility(View.GONE);
        }

        this.populateLeaderBoard();

        //
        // Buttons callbacks
        //
        final Button bt_retour = (Button)findViewById(R.id.button_retour);
        bt_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(JouerActivity.BACK_TO_MENU_RESULT);
                finish();
            }
        });
        final Button bt_playAgain = (Button)findViewById(R.id.button_replay);
        bt_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(JouerActivity.PLAY_AGAIN_RESULT);
                finish();
            }
        });
    }

    /** 
     * Transforms a time in seconds to a nice mm:ss string
     *
     * @param seconds The time in seconds
     *
     * @return The time formated as mm:ss
     */
    private String formatTime(int seconds) {
        String s = String.valueOf(seconds%60);
        if( s.length()==1 ) s = "0"+s;

        String m = String.valueOf((seconds-seconds%60)/60);
        if( m.length()==1 ) m = "0"+m;

        return m+":"+s;
    }



    private void populateLeaderBoard() {
        class GetScores extends AsyncTask<Void, Void, List<ScoreEntity>> {
            @Override
            protected List<ScoreEntity> doInBackground(Void... voids) {
                List<ScoreEntity> allScores = LeaderboardDBClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .leaderboardDao()
                        .getScoresForNumCards(Option.getInstance(getApplicationContext()).getNbCartes());
                return allScores;
            }
            @Override
            protected void onPostExecute(List<ScoreEntity> scores) {
                super.onPostExecute(scores);
                LinearLayout scoresList = (LinearLayout)findViewById(R.id.scoresTable);
                for( ScoreEntity se : scores ) {
                    TextView tv = new TextView(getApplicationContext());
                    tv.setText(String.valueOf(se.getScore()));
                    tv.setTextColor(Color.parseColor("#eeb912"));
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setPadding(20,20,20,20);

                    scoresList.addView(tv);
                }
            }
        }
        new GetScores().execute();

        class GetTimes extends AsyncTask<Void, Void, List<TimeEntity>> {
            @Override
            protected List<TimeEntity> doInBackground(Void... voids) {
                List<TimeEntity> allTimes = LeaderboardDBClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .leaderboardDao()
                        .getTimesForNumCards(Option.getInstance(getApplicationContext()).getNbCartes());
                return allTimes;
            }
            @Override
            protected void onPostExecute(List<TimeEntity> times) {
                super.onPostExecute(times);
                LinearLayout timesList = (LinearLayout)findViewById(R.id.timeTable);
                for( TimeEntity te : times ) {
                    TextView tv = new TextView(getApplicationContext());
                    tv.setText( formatTime(te.getTime()) );
                    tv.setTextColor(Color.parseColor("#eeb912"));
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setPadding(20,20,20,20);
                    timesList.addView(tv);
                }
            }
        }
        new GetTimes().execute();
    }

    private void addScoreAndTimeToDB(final int score, final int time) {

        class AddScores extends AsyncTask<Void, Void, List<ScoreEntity>> {
            @Override
            protected List<ScoreEntity> doInBackground(Void... voids) {
                ScoreEntity se = new ScoreEntity();;
                se.setScore(score);
                se.setCardsCount(Option.getInstance(getApplicationContext()).getNbCartes());
                LeaderboardDBClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .leaderboardDao()
                        .insert(se);
                return null;
            }
            @Override
            protected void onPostExecute(List<ScoreEntity> scores) {
                super.onPostExecute(scores);
            }
        }
        new AddScores().execute();

        class AddTimes extends AsyncTask<Void, Void, List<TimeEntity>> {
            @Override
            protected List<TimeEntity> doInBackground(Void... voids) {
                TimeEntity se = new TimeEntity();;
                se.setTime(time);
                se.setCardsCount(Option.getInstance(getApplicationContext()).getNbCartes());
                LeaderboardDBClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .leaderboardDao()
                        .insert(se);
                return null;
            }
            @Override
            protected void onPostExecute(List<TimeEntity> times) {
                super.onPostExecute(times);
            }
        }
        new AddTimes().execute();
    }
}
