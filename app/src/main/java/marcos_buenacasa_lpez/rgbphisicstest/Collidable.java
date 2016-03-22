package marcos_buenacasa_lpez.rgbphisicstest;

import android.graphics.drawable.Drawable;

/**
 * Created by Marcos on 15/03/2016.
 */
public class Collidable {
    private int x;
    private int y;
    private int width;
    private int height;
    private int id_color;
    private int velx;
    private int vely;
    private Drawable picture;

    public Collidable(int x, int y, int w, int h, int id_color, int velx, int vely, Drawable d){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.id_color = id_color;
        this.velx = velx;
        this.vely = vely;
        this.picture = d;
    }

    public void update(int dt,int gx,int gy){
        x = x + velx*dt;
        y = y + vely*dt;
        velx = velx + gx*dt;
        vely = vely + gy*dt;
    }
}
