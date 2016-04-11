package marcos_buenacasa_lpez.rgbphisicstest;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * Created by Marcos on 29/03/2016.
 */
public class CompanionCube extends Collidable {

    private boolean falling = true;
    private int orientation;

    public CompanionCube(int x, int y, double w, double h, int id_color, double velx, double vely, Drawable d,int orientation){
        super(x,y,w,h,id_color,velx,vely,d);
        this.orientation = orientation;
    }

    public void update(int dt,int g){

        if(falling) {
            super.update(dt, g);
        }
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

    public boolean IsFalling(){
        return falling;
    }

    public void stopFalling(){
        falling = false;
    }

    public void startFalling(){
        falling = true;
    }
}
