package fr.utt.if26.memoria;

import android.widget.ImageView;

public class Carte {
    
    public static final int[] ALL_CARDS = new int[]{R.drawable.cartes_carreau1, R.drawable.cartes_carreau2, R.drawable.cartes_carreau3, R.drawable.cartes_carreau4, R.drawable.cartes_carreau5, R.drawable.cartes_carreau6, R.drawable.cartes_carreau7, R.drawable.cartes_carreau8, R.drawable.cartes_carreau9, R.drawable.cartes_carreau10, R.drawable.cartes_carreau11, R.drawable.cartes_carreau12, R.drawable.cartes_carreau13, R.drawable.cartes_coeur1, R.drawable.cartes_coeur2, R.drawable.cartes_coeur3, R.drawable.cartes_coeur4, R.drawable.cartes_coeur5, R.drawable.cartes_coeur6, R.drawable.cartes_coeur7, R.drawable.cartes_coeur8, R.drawable.cartes_coeur9, R.drawable.cartes_coeur10, R.drawable.cartes_coeur11, R.drawable.cartes_coeur12, R.drawable.cartes_coeur13, R.drawable.cartes_pique1, R.drawable.cartes_pique2, R.drawable.cartes_pique3, R.drawable.cartes_pique4, R.drawable.cartes_pique5, R.drawable.cartes_pique6, R.drawable.cartes_pique7, R.drawable.cartes_pique8, R.drawable.cartes_pique9, R.drawable.cartes_pique10, R.drawable.cartes_pique11, R.drawable.cartes_pique12, R.drawable.cartes_pique13, R.drawable.cartes_trefle1, R.drawable.cartes_trefle2, R.drawable.cartes_trefle3, R.drawable.cartes_trefle4, R.drawable.cartes_trefle5, R.drawable.cartes_trefle6, R.drawable.cartes_trefle7, R.drawable.cartes_trefle8, R.drawable.cartes_trefle9, R.drawable.cartes_trefle10, R.drawable.cartes_trefle11, R.drawable.cartes_trefle12, R.drawable.cartes_trefle13, R.drawable.cartes_joker1, R.drawable.cartes_joker3};

    private int image;
    private ImageView view;
    private boolean isReturned;
    private boolean locked;
    

    public Carte() {
        int random = (int)(Math.random() * Carte.ALL_CARDS.length );

        this.image = Carte.ALL_CARDS[random];
        this.isReturned = false;
        this.locked = false;
    }

    public Carte(int image0) {
        this.image = image0;
        this.isReturned = false;
        this.locked = false;
    }

    public void setView(ImageView iv0) {
        this.view  = iv0;
    }

    public boolean hasView() {
        return this.view != null;
    }

    public int getImage() {
        return this.image;
    }

    public boolean isSameAs(Carte other) {
        return this.image == other.getImage();
    }

    public void returnCard() {
        this.isReturned = !this.isReturned;
        if( this.view != null && this.locked == false ) {
            if( this.isReturned ) {
                this.view.setImageResource(this.image);
            } else {
                this.view.setImageResource(R.drawable.doscarte);
            }
        }
    }

    public void lock() {
        this.locked = true;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public boolean isVisible() {
        return this.isReturned;
    }
}
