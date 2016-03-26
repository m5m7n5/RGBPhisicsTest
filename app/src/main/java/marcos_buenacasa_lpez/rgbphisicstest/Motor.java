package marcos_buenacasa_lpez.rgbphisicstest;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.CountDownTimer;

import java.util.ArrayList;

/**
 * Created by Marcos on 19/03/2016.
 */
public class Motor {

    private int gravityx;
    private int gravityy;
    private Canvas canvas;
    private CountDownTimer c;
    private int [][] matrix;
    private Collidable [][] collidableList;
    private int picsize;
    private Player player;
    private ArrayList<Drawable> drawable;

    public Motor(int gx,int gy, int [][] mat,int picsize,ArrayList<Drawable> drawable){
        gravityx = gx;
        gravityy = gy;
        matrix = mat;
        this.picsize = picsize;
        this.drawable = drawable;
    }

    public void iniLevel(){
        player = new Player(23,12,picsize,picsize,1,0,0,drawable.get(0));
    }

    public void phisics(int dt){

    }

    public void logic(){

    }

    public void draw(Canvas c){

    }

    public void playerAction(int action){
        switch(action){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            default:
                break;

        }
    }
}
