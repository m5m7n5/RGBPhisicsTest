package marcos_buenacasa_lpez.rgbphisicstest;

import android.graphics.drawable.Drawable;

/**
 * Created by Marcos on 21/03/2016.
 */
public class Player extends Collidable{
    private boolean jumping = false;
    private boolean moving = false;
    private int orientation;
    public Player(int x, int y, int w, int h, int id_color, int velx, int vely, Drawable d,int orientation){
        super(x,y,w,h,id_color,velx,vely,d);
        this.orientation = orientation;
    }

    public void update(int dt,int gx,int gy,int orientation){
        this.orientation = orientation;
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
        if(!moving){
            switch(orientation) {
                case 1:
                    this.setVel(getVelx(), 5);
                    break;
                case 2:
                    this.setVel(-5, getVely());
                    break;
                case 3:
                    this.setVel(getVelx(), -5);
                    break;
                case 4:
                    this.setVel(5, getVely());
                    break;
                default:
                    break;


            }
            moving = true;
        }
    }

    public void moveLeft(){
        if(!moving){
            switch(orientation) {
                case 1:
                    this.setVel(getVelx(), -5);
                    break;
                case 2:
                    this.setVel(5, getVely());
                    break;
                case 3:
                    this.setVel(getVelx(), 5);
                    break;
                case 4:
                    this.setVel(-5, getVely());
                    break;
                default:
                    break;


            }
            moving = true;
        }
    }

    public void stopMoving(){
        if(moving){
            moving = false;
            switch(orientation){
                case 1:
                case 3:
                    this.setVel(getVelx(),0);
                    break;
                case 2:
                case 4:
                    this.setVel(0,getVely());
                    break;
                default:
                    break;
            }
        }
    }

    public void jumpRight(){
        if(!jumping){
            jumping = true;
            switch(orientation){
                case 1:
                    this.setVel(-6,3);
                    break;
                case 2:
                    this.setVel(-3,-6);
                    break;
                case 3:
                    this.setVel(6,-3);
                    break;
                case 4:
                    this.setVel(3,6);
                    break;
                default:
                    break;
            }
        }
    }

    public void jumpLeft(){
        if(!jumping){
            jumping = true;

            switch(orientation){
                case 1:
                    this.setVel(-6,-3);
                    break;
                case 2:
                    this.setVel(3,-6);
                    break;
                case 3:
                    this.setVel(6,3);
                    break;
                case 4:
                    this.setVel(-3,6);
                    break;
                default:
                    break;
            }
        }
    }

    public void jumpUp(){
        if(!jumping){
            jumping = true;
            switch(orientation){
                case 1:
                    this.setVel(-6,0);
                    break;
                case 2:
                    this.setVel(0,-6);
                    break;
                case 3:
                    this.setVel(6,0);
                    break;
                case 4:
                    this.setVel(0,6);
                    break;
                default:
                    break;
            }

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
