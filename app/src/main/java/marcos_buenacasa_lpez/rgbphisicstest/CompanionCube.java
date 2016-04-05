package marcos_buenacasa_lpez.rgbphisicstest;

import android.graphics.drawable.Drawable;

/**
 * Created by Marcos on 29/03/2016.
 */
public class CompanionCube extends Collidable {

    private boolean falling = false;
    private int orientation;

    public CompanionCube(int x, int y, double w, double h, int id_color, double velx, double vely, Drawable d,int orientation){
        super(x,y,w,h,id_color,velx,vely,d);
        this.orientation = orientation;
    }
}
