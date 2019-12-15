package fr.utt.if26.memoria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OptionsActivity extends AppCompatActivity {

    private SoundBox soundbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        this.soundbox = SoundBox.getInstance(getApplicationContext());

        final Button bt_theme = (Button)findViewById(R.id.options_bt_theme);
        final Button bt_music = (Button)findViewById(R.id.options_bt_music);
        final Button bt_sounds = (Button)findViewById(R.id.options_bt_sounds);
        final Button bt_retour = (Button)findViewById(R.id.options_bt_retour);


        bt_theme.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                soundbox.bip();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OptionsActivity.this);

                alertDialogBuilder.setTitle("CHOIX DE THEME");

                alertDialogBuilder
                    .setMessage("Choisissez votre theme")
                    .setCancelable(false)

                    .setPositiveButton("NORMAL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            soundbox.bip();
                        }
                    })

                .setPositiveButton("DARK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        soundbox.bip();

                    }
                })


                .setNeutralButton("RETOUR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        soundbox.bip();
                        dialog.cancel();

                    }
                });

                AlertDialog alertDialog_theme = alertDialogBuilder.create();

                alertDialog_theme.show();
            }
        });


        bt_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundbox.bip();
                OptionsActivity.this.finish();
            }
        });



        final Button bt_moreCards = (Button)findViewById(R.id.options_bt_moreCards);
        final Button bt_lessCards = (Button)findViewById(R.id.options_bt_lessCards);

        final TextView nbCardsView = (TextView)findViewById(R.id.options_txt_nbCartes);
        nbCardsView.setText(String.valueOf(Option.getInstance(getApplicationContext()).getNbCartes()));

        bt_moreCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundbox.bip();
                int currentNbCards = Integer.parseInt(nbCardsView.getText().toString());
                int newNbCards = currentNbCards;

                Option opt = Option.getInstance(getApplicationContext());

                int[] possibleAmounts = opt.getPossibleCardsAmount();
                for( int amount : possibleAmounts ) {
                    if( amount > currentNbCards ){ 
                        newNbCards = amount;
                        break;
                    }
                }

                opt.setNbCartes(newNbCards);
                nbCardsView.setText(String.valueOf(newNbCards));
            }
        });

        bt_lessCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundbox.bip();
                int currentNbCards = Integer.parseInt(nbCardsView.getText().toString());
                int newNbCards = currentNbCards;

                Option opt = Option.getInstance(getApplicationContext());

                int[] possibleAmounts = opt.getPossibleCardsAmount();
                for( int amount : possibleAmounts ) {
                    if( amount >= currentNbCards )
                        break;

                    newNbCards = amount;
                }
                
                opt.setNbCartes(newNbCards);
                nbCardsView.setText(String.valueOf(newNbCards));
            }
        });


        //
        // restauring options from saves
        //
        Option opt = Option.getInstance(getApplicationContext());
        this.toggleSounds(bt_sounds, opt.areSoundsOn());
        this.toggleMusic(bt_music, opt.isMusicOn());

        bt_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMusic(bt_music);
                soundbox.bip();
            }
        });
        bt_sounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSounds(bt_sounds);
                soundbox.bip();
            }
        });
    }

    public void toggleSounds(Button bt_sounds) {
        String txt = bt_sounds.getText().toString();
        this.toggleSounds(bt_sounds, txt.toLowerCase().contains("off") );
    }
    public void toggleSounds(Button bt_sounds, boolean flag) {
        String txt = bt_sounds.getText().toString();
        Option opt = Option.getInstance(getApplicationContext());
        opt.toggleSounds(flag);
        if( flag ) {
            soundbox.toggleSounds(true);
            bt_sounds.setText( txt.replace("off", "on").replace("OFF", "ON") );
        } else {
            soundbox.toggleSounds(false);
            bt_sounds.setText( txt.replace("on", "off").replace("ON", "OFF") );
        }
    }

    public void toggleMusic(Button bt_music) {
        String txt = bt_music.getText().toString();
        this.toggleMusic(bt_music, txt.toLowerCase().contains("off") );
    }
    public void toggleMusic(Button bt_music, boolean flag) {
        String txt = bt_music.getText().toString();
        Option opt = Option.getInstance(getApplicationContext());
        opt.toggleMusic(flag);
        if( flag ) {
            soundbox.startMusic();
            bt_music.setText( txt.replace("off", "on").replace("OFF", "ON") );
        } else {
            soundbox.stopMusic();
            bt_music.setText( txt.replace("on", "off").replace("ON", "OFF") );
        }
    }
}

