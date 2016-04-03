package marcos_buenacasa_lpez.rgbphisicstest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * Created by Marcos on 15/03/2016.
 */
public class Collidable {
    private double x;
    private double y;
    private double width;
    private double height;
    private int id_color;
    private double velx;
    private double vely;
    private Drawable picture;
    private int maxvel;
    private int minvel;

    public Collidable(int x, int y, double w, double h, int id_color, double velx, double vely, Drawable d){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.id_color = id_color;
        this.velx = velx;
        this.vely = vely;
        this.picture = d;
        maxvel = 40;
        minvel = -40;
    }

    public void update(int dt,int gx){
        x = x + velx*(dt/1000.0);
        y = y + vely*(dt/1000.0);
        velx = velx + gx*(dt/1000.0);
        if(velx>maxvel){
            velx = maxvel;
        }else if(velx<minvel){
            velx=minvel;
        }
        if(vely>maxvel){
            vely = maxvel;
        }else if(vely<minvel){
            vely = minvel;
        }

    }

    public void draw(int picsize,Canvas c){
        picture.setBounds((int)(y*picsize),(int)(x*picsize),(int)((y+1)*picsize),(int)((x+1)*picsize));
        picture.draw(c);
    }

    public void setVel(double velx,double vely){
        this.velx = velx;
        this.vely = vely;
    }

    public double getx(){
        return x;
    }

    public double gety(){
        return y;
    }

    public double getVelx(){
        return velx;
    }

    public double getVely(){
        return vely;
    }

    public void setPos(double x,double y){
        this.x=x;
        this.y=y;
    }

    public double[] rotateHV(double pos[]){
        double[] newpos = {(-1)*(pos[1])+25,pos[0]};
        return newpos;
    }

    public double[] rotateVH(double pos[]){
        double[] newpos = {(-1)*(pos[1])+14,pos[0]};
        return newpos;
    }

    public Drawable getPicture(){
        return picture;
    }
}
