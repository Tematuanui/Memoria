package fr.utt.if26.memoria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JouerActivity extends AppCompatActivity {

    public static final int CONTINUE_REQUEST = 0;

    public static final int BACK_TO_MENU_RESULT = 0;
    public static final int PLAY_AGAIN_RESULT = 1;

    private GridView gameTable;
    private Carte[] deck;
    private Carte visibleCard;
    private boolean gameIsPaused;
    private Chronometer chrono;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jouer);

        this.score = 0;
        this.chrono = findViewById(R.id.timeChrono);
        this.visibleCard = null;
        this.gameIsPaused = false;

        this.initTable();
    }

    public void initTable() {

        int nbCartes = Option.getInstance(getApplicationContext()).getNbCartes();

        int tableWidth = 3;
        int tableHeight = 4;

        for( int i=2 ; i<1000 ; i++ ) {
            if( nbCartes%i == 0 && ( nbCartes/i == i || nbCartes/i == i-1 || nbCartes/i == i+1 )) {
                tableWidth = nbCartes/i;
                tableHeight = i;
            }
        }

        // set score at 0
        this.changeScore(-this.score);
        // reset chrono
        this.chrono.setBase(SystemClock.elapsedRealtime());

        this.chrono.start();

        this.deck = this.initDeck(tableWidth * tableHeight);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;

        this.gameTable = findViewById(R.id.gameTable);
        gameTable.setAdapter(new ImageAdapterGridView(this, tableWidth, tableHeight, deck));
        gameTable.setColumnWidth(screenWidth/tableWidth);

        gameTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                returnCard(position);
            }
        });
    }

    public Carte[] initDeck(int nbCartes) {
        Carte[] c = new Carte[nbCartes];

        int i=0;

        for( ; i<nbCartes/2 ; i++ ) {
            c[i] = new Carte();
        }

        for( ; i<nbCartes ; i++ ) {
            c[i] = new Carte(c[ i - nbCartes/2 ].getImage());
        }

        List<Carte> l = Arrays.asList(c);
        Collections.shuffle(l);
        c = l.toArray(new Carte[0]);

        return c;
    }

    public void changeScore(int delta) {
        TextView scoreView = findViewById(R.id.scoreValue);

        this.score += delta;

        if( this.score < 0 ) this.score = 0;

        scoreView.setText(this.score + "");
    }

    public void returnCard(int position) {
        final Carte c = this.deck[position];
        
        if( c.isLocked() || c == this.visibleCard || this.gameIsPaused )
            return;

        SoundBox.getInstance(getApplicationContext()).cardFlip();

        c.returnCard();

        if( c.isVisible() ) {
            // Just to make sure that the card we just returned is visible
            
            if( this.visibleCard != null ) {
                // there is another returned card, we need to test if it's a match

                if( !c.isSameAs(this.visibleCard) ) {
                    // Not the same card, we wait and return them again
                    this.changeScore(-10);

                    this.gameIsPaused = true;

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            visibleCard.returnCard();
                            c.returnCard();
                            visibleCard = null;
                            gameIsPaused = false;
                        }
                    }, 500);

                } else {
                    // Yay, same cards, we keep them visible and lock them
                    
                    this.visibleCard.lock();
                    c.lock();
                    this.visibleCard = null;

                    this.changeScore(100);

                    int count=0;
                    for( Carte card : this.deck )
                        if( !card.isLocked() )
                            count++;

                    if( count <= 1 )
                        this.endOfGame();
                }
            } else {
                this.visibleCard = c;
            }
        }
    }


    public void endOfGame() {
        int time = (int) ((SystemClock.elapsedRealtime() - this.chrono.getBase()) / 1000);

        Intent itt_leaderboard = new Intent(this, LeaderboardActivity.class);
        itt_leaderboard.putExtra("time", String.valueOf(time));
        itt_leaderboard.putExtra("score", String.valueOf(this.score));
        startActivityForResult(itt_leaderboard, 0);

        // finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != JouerActivity.CONTINUE_REQUEST) return;

        switch( resultCode ) {
            case JouerActivity.PLAY_AGAIN_RESULT:
                this.initTable();
                break;
            case JouerActivity.BACK_TO_MENU_RESULT:
                finish();
                break;
        }
    }

    class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;

        private int tableWidth;
        private int tableHeight;
        private Carte[] deck;

        public ImageAdapterGridView(Context c, int tableWidth0, int tableHeight0, Carte[] deck0) {
            this.mContext = c;
            this.tableWidth = tableWidth0;
            this.tableHeight = tableHeight0;
            this.deck = deck0;
        }

        public int getCount() {
            return this.tableWidth * this.tableHeight;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView mImageView;

            int parentHeight = parent.getMeasuredHeight();
            int parentWidth = parent.getMeasuredWidth();

            if( parentWidth == 0 || parentWidth == 0 ) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenHeight = displayMetrics.heightPixels;
                int screenWidth = displayMetrics.widthPixels;

                BitmapFactory.Options dimensions = new BitmapFactory.Options();
                dimensions.inJustDecodeBounds = true;
                Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.doscarte, dimensions);
                int imageHeight = dimensions.outHeight;
                int imageWidth =  dimensions.outWidth;

                parentWidth = screenWidth;
                parentHeight = parentWidth*imageHeight/imageWidth;
            }

            if (convertView == null) {
                mImageView = new ImageView(mContext);
                mImageView.setLayoutParams(new GridView.LayoutParams(parentWidth/this.tableWidth, parentHeight/this.tableHeight));
                mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                mImageView.setPadding(16, 16, 16, 16);
                if( !this.deck[position].hasView() )
                    this.deck[position].setView(mImageView);
            } else {
                mImageView = (ImageView) convertView;
            }
            mImageView.setImageResource(R.drawable.doscarte);
            return mImageView;
        }
    }
}
