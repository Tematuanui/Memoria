package fr.utt.if26.memoria;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
MediaPlayer bip;
MediaPlayer miiSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bip = MediaPlayer.create(getApplicationContext(), R.raw.bip);
        miiSong = MediaPlayer.create(getApplicationContext(),R.raw.miisong);

        miiSong.start();
        miiSong.setLooping(true);

        final Button bt_jouer = (Button)findViewById(R.id.main_bt_jouer);
        final Button bt_options = (Button)findViewById((R.id.main_bt_options));
        final Button bt_scores = (Button)findViewById(R.id.main_bt_scores);
        final Button bt_quitter = (Button)findViewById(R.id.main_bt_quitter);

        final Intent itt_jouer = new Intent(MainActivity.this,JouerActivity.class);
        final Intent itt_options = new Intent(MainActivity.this,OptionsActivity.class);
        final Intent itt_scores = new Intent(MainActivity.this,ScoresActivity.class);

        bt_jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bip.start();
                startActivity(itt_jouer);
            }
        });

        bt_scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bip.start();
                startActivity(itt_scores);
            }
        });

        bt_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bip.start();
                startActivity(itt_options);
            }
        });

        bt_quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
    }


}
