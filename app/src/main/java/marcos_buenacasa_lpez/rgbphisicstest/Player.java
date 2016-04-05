package marcos_buenacasa_lpez.rgbphisicstest;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * Created by Marcos on 21/03/2016.
 */
public class Player extends Collidable{

    private boolean jumping = false;
    private boolean moving = false;
    private int orientation;
    private int jumpheight = 8;

    public Player(int x, int y, int w, int h, int id_color, int velx, int vely, Drawable d,int orientation){
        super(x,y,w,h,id_color,velx,vely,d);
        this.orientation = orientation;
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

    public void update(int dt,int g,int orientation){
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
        Drawable picture = this.getPicture();
        double[] oldcoord = {this.getx(), this.gety()};
        double[] newcoord = oldcoord;
        switch(orientation){
            case 2:
                newcoord = rotateVH(oldcoord);
                break;
            case 3:
                newcoord = rotateVH(rotateHV(oldcoord));
                break;
            case 4:
                newcoord = rotateVH(rotateHV(rotateVH(oldcoord)));
                break;
            default:
                break;
        }
        picture.setBounds((int)(newcoord[1]*picsize),(int)(newcoord[0]*picsize),(int)((newcoord[1]+1)*picsize),(int)((newcoord[0]+1)*picsize));
        picture.draw(c);
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
}
