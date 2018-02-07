package com.pag.tic_tac_toe;

import android.graphics.drawable.Drawable;

public enum Player {

    PLAYER_ONE("",'X',R.drawable.cross),PLAYER_TWO("",'O', R.drawable.circle);

    Player (String name, char symbol, int iconId){
        this.symbol = symbol;
        this.iconId = iconId;
        this.name = name;
    }

    private final char symbol;
    private final int iconId;
    private String name;

    public int getDrawable(){return iconId;}

    public void setName(String name){this.name = name;}

    public String toString(){return name;}
}
