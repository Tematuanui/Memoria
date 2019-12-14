package fr.utt.if26.memoria;

import android.content.Context;

import java.util.ArrayList;

public class Option {

    private static Option instance = null;

    private int nbCartes;
    private Context context;
    
    private Option(Context ctx) {
        this.nbCartes = -1;
        this.context = ctx;
    }
    
    public static Option getInstance(Context ctx) {
        if( Option.instance == null )
            Option.instance = new Option(ctx);
        else
            Option.instance.setContext(ctx);

        return Option.instance;
    }

    public void setContext(Context c) {
        this.context = c;
    }

    public void setNbCartes(int nbCartes0) {
        if( (nbCartes0%2)==1 || nbCartes0 < 0 ) return;

        this.nbCartes = nbCartes0;

        Memory m = Memory.getInstance(this.context);
        m.setNbCartes(nbCartes0);
    }

    public int getNbCartes() {
        if( this.nbCartes < 0 ) {
            Memory m = Memory.getInstance(this.context);
            this.nbCartes = m.getNbCartes();
        }

        return this.nbCartes;
    }

    public int[] getPossibleCardsAmount() {
        ArrayList<Integer> out = new ArrayList<Integer>();

        for( int i=2 ; i<10 ; i++ ) {
            for( int j=0 ; j<2 ; j++ ) {
                if( (i*(i+j))%2 == 0 && !out.contains(i*(i+j)) ) {
                    out.add( new Integer(i*(i+j)) );
                }
            }
        }

        int[] intArray = new int[out.size()];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = out.get(i);
        }

        return intArray;
    }
}
