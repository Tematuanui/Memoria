package fr.utt.if26.memoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        if( currentScore != null )
            m.addNewScore(Integer.parseInt(currentScore));
        if( currentTime != null )
            m.addNewTime(Integer.parseInt(currentTime));

        if( currentScore == null && currentTime == null ) {
            // It means we come from the main menu, we should take off the "play again" button
            final Button bt_playAgain = (Button)findViewById(R.id.button_replay);
            bt_playAgain.setVisibility(View.GONE);
        }


        //
        // Displaying best scores...
        //
        int[] bs = m.getBestScores();
        LinearLayout scoresList = (LinearLayout)findViewById(R.id.scoresTable);
        for( int score : bs ) {
            TextView tv = new TextView(this.getApplicationContext());
            tv.setText(String.valueOf(score));
            tv.setTextColor(Color.parseColor("#eeb912"));
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setPadding(20,20,20,20);

            scoresList.addView(tv);
        }

        //
        // Displaying best times...
        //
        int[] bt = m.getBestTimes();
        LinearLayout timesList = (LinearLayout)findViewById(R.id.timeTable);
        for( int time : bt ) {
            TextView tv = new TextView(this.getApplicationContext());
            tv.setText( this.formatTime(time) );
            tv.setTextColor(Color.parseColor("#eeb912"));
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setPadding(20,20,20,20);

            timesList.addView(tv);
        }


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
}
