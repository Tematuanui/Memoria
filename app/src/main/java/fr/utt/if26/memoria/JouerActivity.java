package fr.utt.if26.memoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class JouerActivity extends AppCompatActivity {

    private GridView gameTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jouer);

        initTable();
    }

    public void initTable(){

        int tableWidth = 4;
        int tableHeight = 4;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;

        this.gameTable = findViewById(R.id.gameTable);
        gameTable.setAdapter(new ImageAdapterGridView(this, tableWidth, tableHeight));
        gameTable.setColumnWidth(screenWidth/tableWidth);

        gameTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                Toast.makeText(getBaseContext(), "Grid Item " + (position + 1) + " Selected", Toast.LENGTH_LONG).show();
            }
        });
    }



    class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;

        private int tableWidth;
        private int tableHeight;

        public ImageAdapterGridView(Context c, int tableWidth0, int tableHeight0) {
            this.mContext = c;
            this.tableWidth = tableWidth0;
            this.tableHeight = tableHeight0;
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
            } else {
                mImageView = (ImageView) convertView;
            }
            mImageView.setImageResource(R.drawable.doscarte);
            return mImageView;
        }
    }
}
