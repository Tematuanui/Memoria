package fr.utt.if26.memoria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OptionsActivity extends AppCompatActivity {
    MediaPlayer bip;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        bip = MediaPlayer.create(getApplicationContext(),R.raw.bip);


        final Button bt_theme = (Button)findViewById(R.id.options_bt_theme);
        final Button bt_audio = (Button)findViewById(R.id.options_bt_audio);
        final Button bt_retour = (Button)findViewById(R.id.options_bt_retour);


        bt_theme.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                bip.start();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setTitle("CHOIX DE THEME");

                alertDialogBuilder
                    .setMessage("Choisissez votre theme")
                    .setCancelable(false)

                    .setPositiveButton("NORMAL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            bip.start();
                        }
                    })

                .setPositiveButton("DARK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        bip.start();

                    }
                })


                .setNeutralButton("RETOUR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        bip.start();
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
                bip.start();
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
                bip.start();
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
                bip.start();
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
    }
}

