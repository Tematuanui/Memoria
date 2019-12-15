package fr.utt.if26.memoria;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    SoundBox soundbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.soundbox = SoundBox.getInstance(getApplicationContext());
        Option opt = Option.getInstance(getApplicationContext());

        this.soundbox.toggleSounds(opt.areSoundsOn());
        this.soundbox.startMusic();
        if( !opt.isMusicOn() ) {
            this.soundbox.stopMusic();
        }


        final Button bt_jouer = (Button)findViewById(R.id.main_bt_jouer);
        final Button bt_options = (Button)findViewById((R.id.main_bt_options));
        final Button bt_scores = (Button)findViewById(R.id.main_bt_scores);
        final Button bt_quitter = (Button)findViewById(R.id.main_bt_quitter);

        bt_jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundbox.bip();
                Intent itt_jouer = new Intent(MainActivity.this,JouerActivity.class);
                startActivity(itt_jouer);
            }
        });

        bt_scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundbox.bip();
                Intent itt_leaderboard = new Intent(MainActivity.this, LeaderboardActivity.class);
                startActivity(itt_leaderboard);
            }
        });

        bt_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundbox.bip();
                Intent itt_options = new Intent(MainActivity.this,OptionsActivity.class);
                startActivity(itt_options);
            }
        });

        bt_quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundbox.stopMusic();
                MainActivity.this.finish();
            }
        });
    }


}
