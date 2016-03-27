package marcos_buenacasa_lpez.rgbphisicstest;

import android.graphics.drawable.Drawable;

/**
 * Created by Marcos on 21/03/2016.
 */
public class Player extends Collidable{
    private boolean jumping = false;
    private boolean moving = false;
    public Player(int x, int y, int w, int h, int id_color, int velx, int vely, Drawable d){
        super(x,y,w,h,id_color,velx,vely,d);
    }

    public void update(int dt,int gx,int gy,int orientation){
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
            super.update(dt, gx, gy);
        /*
        }*/
    }

    public void moveRight(){
        if(!moving && !jumping){
            moving = true;
            this.setVel(getVelx(),5);
        }
    }

    public void moveLeft(){
        if(!moving && !jumping){
            moving = true;
            this.setVel(getVelx(),-5);
        }
    }

    public void stopMoving(){
        if(moving){
            moving = false;
            this.setVel(0,0);
        }
    }

    public void jumpRight(){
        if(!jumping){
            jumping = true;
            this.setVel(-5,3);
        }
    }

    public void jumpLeft(){
        if(!jumping){
            jumping = true;
            this.setVel(-5,-3);
        }
    }

    public void jumpUp(){
        if(!jumping){
            jumping = true;
            this.setVel(-5,0);
        }
    }
    public void stopJumping(){
        jumping = false;
    }

    public boolean isJumping() {
        return jumping;
    }
}
