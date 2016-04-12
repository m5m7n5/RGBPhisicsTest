package marcos_buenacasa_lpez.rgbphisicstest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by Marcos on 21/03/2016.
 */
public class Player extends Collidable{

    private boolean jumping = false;
    private boolean moving = false;
    private int orientation;
    private int jumpheight = 8;
    private ArrayList<Drawable> drawPics;
    private ArrayList<Bitmap> drawBPics;

    public Player(int x, int y, int w, int h, int id_color, int velx, int vely,int orientation,int picsize, Drawable d){
        super(x,y,w,h,id_color,velx,vely,null);
        this.orientation = orientation;
        drawPics = new ArrayList<Drawable>();
        drawBPics = new ArrayList<Bitmap>();
        Bitmap aux = Bitmap.createBitmap(picsize, picsize, Bitmap.Config.ARGB_8888);
        Canvas wD/*workingDrawing*/ = new Canvas(aux);
        d.setBounds(0,0,picsize,picsize);
        d.draw(wD);
        drawBPics.add(aux);

        aux = Bitmap.createBitmap(picsize, picsize, Bitmap.Config.ARGB_8888);
        wD = new Canvas(aux);
        wD.save();
        wD.rotate(270,picsize/2,picsize/2);
        d.draw(wD);
        wD.restore();
        drawBPics.add(aux);

        aux = Bitmap.createBitmap(picsize, picsize, Bitmap.Config.ARGB_8888);
        wD = new Canvas(aux);
        wD.save();
        wD.rotate(180,picsize/2,picsize/2);
        d.draw(wD);
        wD.restore();
        drawBPics.add(aux);

        aux = Bitmap.createBitmap(picsize, picsize, Bitmap.Config.ARGB_8888);
        wD = new Canvas(aux);
        wD.save();
        wD.rotate(90,picsize/2,picsize/2);
        d.draw(wD);
        wD.restore();
        drawBPics.add(aux);
    }

    public void changeOrientation(int orientation){
        double[] newcoord = {0,0};
        double[] oldcoord = {this.getx(), this.gety()};
        if((this.orientation-orientation)%2==0){
            if(this.orientation%2 == 0){
                newcoord = rotateHV(rotateVH(oldcoord));
            }else{
                newcoord = rotateVH(rotateHV(oldcoord));
            }
        }else{
            switch(this.orientation){
                case 1:
                    if(orientation == 4){
                        newcoord = rotateHV(oldcoord);
                    }else{
                        newcoord = rotateHV(rotateVH(rotateHV(oldcoord)));
                    }
                    break;
                case 2:
                    if(orientation == 3){
                        newcoord = rotateVH(rotateHV(rotateVH(oldcoord)));
                    }else{
                        newcoord = rotateVH(oldcoord);
                    }
                    break;
                case 3:
                    if(orientation == 4){
                        newcoord = rotateHV(rotateVH(rotateHV(oldcoord)));
                    }else{
                        newcoord = rotateHV(oldcoord);
                    }
                    break;
                case 4:
                    if(orientation == 3){
                        newcoord = rotateVH(oldcoord);
                    }else{
                        newcoord = rotateVH(rotateHV(rotateVH(oldcoord)));
                    }
                    break;
                default:
                    break;
            }
        }
        this.setPos(newcoord[0],newcoord[1]);
        this.orientation = orientation;
    }

    public void update(int dt,int g)    {
        /*
        if(!jumping){
            double x = this.getx() + this.getVelx()*(1.0/dt);
            double y = this.gety() + this.getVely()*(1.0/dt);
            double velx = this.getVelx() + 0*(1.0/dt);
            double vely = this.getVely() + gy*(1.0/dt);
            if(vely>20){
                vely = 20;
            }else if(vely<-20){
                vely = -20;
            }
            setPos(x,y);
            setVel(velx,vely);
        }else {
           */
            super.update(dt, g);
        /*
        }*/
    }

    public void draw(int picsize,Canvas c){
        double[] oldcoord = {this.getx(), this.gety()};
        double[] newcoord = oldcoord;
        switch(orientation){
            case 1:
                c.drawBitmap(drawBPics.get(0),(int)(newcoord[1]*picsize),(int)(newcoord[0]*picsize),null);
                break;
            case 2:
                newcoord = rotateVH(oldcoord);
                c.drawBitmap(drawBPics.get(1),(int)(newcoord[1]*picsize),(int)(newcoord[0]*picsize),null);
                break;
            case 3:
                newcoord = rotateVH(rotateHV(oldcoord));
                c.drawBitmap(drawBPics.get(2),(int)(newcoord[1]*picsize),(int)(newcoord[0]*picsize),null);
                break;
            case 4:
                newcoord = rotateVH(rotateHV(rotateVH(oldcoord)));
                c.drawBitmap(drawBPics.get(3),(int)(newcoord[1]*picsize),(int)(newcoord[0]*picsize),null);
                break;
            default:
                break;
        }

    }

    public void moveRight(){
        if(!moving){
            this.setVel(getVelx(), 5);
            moving = true;
        }
    }

    public void moveLeft(){
        if(!moving){
            this.setVel(getVelx(), -5);
            moving = true;
        }
    }

    public void stopMoving(){
        if(moving){
            moving = false;
            this.setVel(getVelx(),0);
        }
    }

    public void jumpRight(){
        if(!jumping){
            jumping = true;
            this.setVel((-1)*jumpheight,3);
        }
    }

    public void jumpLeft(){
        if(!jumping){
            jumping = true;
            this.setVel((-1)*jumpheight,-3);
        }
    }

    public void jumpUp(){
        if(!jumping){
            jumping = true;
            this.setVel((-1)*jumpheight,0);
        }
    }

    public void stopJumping(){
        jumping = false;
    }

    public boolean isJumping() {
        return jumping;
    }

    public boolean isMoving() {
        return moving;
    }

    public void startJumping() {
        jumping = true;
    }

}