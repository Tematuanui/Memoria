package fr.utt.if26.memoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JouerActivity extends AppCompatActivity {

    private GridView gameTable;
    private Carte[] deck;
    private Carte visibleCard;
    private boolean gameIsPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jouer);

        this.initTable();
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

    public void initTable(){

        int tableWidth = 5;
        int tableHeight = 5;

        this.deck = this.initDeck(tableWidth * tableHeight);
        this.visibleCard = null;
        this.gameIsPaused = false;

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
                returnCard((ImageView)v, position);
            }
        });
    }

    public void returnCard(ImageView iv, int position) {
        final Carte c = this.deck[position];
        
        if( c.isLocked() || c == this.visibleCard || this.gameIsPaused )
            return;

        c.returnCard();

        if( c.isVisible() ) {
            if( this.visibleCard != null ) {
                if( !c.isSameAs(this.visibleCard) ) {

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
                    this.visibleCard.lock();
                    c.lock();
                    this.visibleCard = null;
                }
            } else {
                this.visibleCard = c;
            }
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
