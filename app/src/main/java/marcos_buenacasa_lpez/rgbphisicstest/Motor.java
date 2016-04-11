package marcos_buenacasa_lpez.rgbphisicstest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;

import java.util.ArrayList;

/**
 * Created by Marcos on 19/03/2016.
 */
public class Motor {

    /*
    PRECAUCIÓN
    X es el eje VERTICAL
    Y es el eje HORIZONTAL

      ---------> Y
    |
    |
    |
    |
    v
    X
    */

    private Canvas ground;
    private CountDownTimer c;
    private int [][] horizontalmatrix;
    private int [][] verticalmatrix;
    private int [][] dualhorizontalmatrix;
    private int [][] dualverticalmatrix;
    private Collidable [][] collidableList;
    private ArrayList<CompanionCube> companion;
    private int picsize;
    private Player player;
    private ArrayList<Drawable> drawable;
    private int groundBlockConstant = 11;
    private Bitmap groundB;
    private int id_color;
    private int orientation;
    private int gravityabsolutevalue;


    public Motor(int g, int [][] mat,int picsize,ArrayList<Drawable> drawable,int id_color,int orientation){
        horizontalmatrix = mat;
        verticalmatrix = new int[26][15];
        dualhorizontalmatrix = new int[15][26];
        dualverticalmatrix = new int[26][15];
        this.picsize = picsize;
        this.drawable = drawable;
        collidableList = new Collidable[15][26];
        this.id_color = id_color;
        this.orientation = orientation;
        gravityabsolutevalue = g;
        companion = new ArrayList<CompanionCube>();

        for(int i=0;i<horizontalmatrix.length;i++){
            for(int j=0;j<horizontalmatrix[i].length;j++){
                dualhorizontalmatrix[horizontalmatrix.length-(i+1)][horizontalmatrix[i].length-(j+1)] = horizontalmatrix[i][j];
                verticalmatrix[horizontalmatrix[i].length-(j+1)][i]=horizontalmatrix[i][j];
                dualverticalmatrix[j][horizontalmatrix.length-(i+1)] = horizontalmatrix[i][j];
            }
        }

    }

    public void iniLevel(int h,int w){
        ground = new Canvas();
        for(int i=0;i<horizontalmatrix.length;i++){
            for(int j=0;j<horizontalmatrix[0].length;j++){
                switch(horizontalmatrix[i][j]){
                    //Los 4 primeros casos son los jugadores, respectivamente.
                    //Los casos intermedios son los objetos
                    //Los últimos casos son los bloques
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        collidableList[i][j] = new Player(i,j,1,1,horizontalmatrix[i][j],0,0,drawable.get(horizontalmatrix[i][j]-1),this.orientation);
                        if(id_color == horizontalmatrix[i][j]) {
                            player = (Player) collidableList[i][j];
                        }
                        break;
                    //Botones
                    case 5:
                        collidableList[i][j] = new Collidable(i,j,1,0.5,0,0,0,drawable.get(horizontalmatrix[i][j]-1));
                        break;
                    //Pinchos
                    case 6:
                        collidableList[i][j] = new Collidable(i,j,1,1,0,0,0,drawable.get(horizontalmatrix[i][j]-1));
                        break;
                    //Puerta
                    case 7:
                        collidableList[i][j] = new Collidable(i,j,1,1,0,0,0,drawable.get(horizontalmatrix[i][j]-1));
                        break;
                    //Companion cube
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                        collidableList[i][j] = new CompanionCube(i,j,1,1,horizontalmatrix[i][j]-7,0,0,drawable.get(horizontalmatrix[i][j]-1),orientation);
                        if(horizontalmatrix[i][j]-7==id_color){
                            companion.add((CompanionCube) collidableList[i][j]);
                        }
                        break;
                    default:
                        if(horizontalmatrix[i][j]!=0){
                            collidableList[i][j] = new Collidable(i,j,1,1,0,0,0,drawable.get(horizontalmatrix[i][j]-1));
                        }
                        break;
                }

            }
        }
        ground.drawARGB(255, 225, 225, 255);
        for(int i=0;i<horizontalmatrix.length;i++) {
            for (int j = 0; j < horizontalmatrix[0].length; j++) {
                if(collidableList[i][j]!=null) {
                    collidableList[i][j].draw(picsize,ground);
                }
            }
        }
    }

    public void physics(int dt){
        player.update(dt,gravityabsolutevalue);
        staticCollitions(player);
        for(int i=0;i<companion.size();i++){
            companion.get(i).update(dt,gravityabsolutevalue);
            companionCollitions(companion.get(i));
        }
    }

    public void logic(){

    }

    public void draw(Canvas c){
        c.drawARGB(255, 225, 225, 255);
        for(int i=0;i<collidableList.length;i++) {
            for (int j = 0; j < collidableList[0].length; j++) {
                if(collidableList[i][j]!=null) {
                    collidableList[i][j].draw(picsize, c);
                }
            }
        }

        c.drawText(Double.toString(player.getx()),50,70,new Paint(Color.BLACK));
        c.drawText(Double.toString(player.gety()),50,90,new Paint(Color.BLACK));
        c.drawText(Double.toString(player.getVelx()),50,110,new Paint(Color.BLACK));
        c.drawText(Double.toString(player.getVely()),50,130,new Paint(Color.BLACK));
    }

    public void motorAction(int action){
        switch(action){
            case 1:
                player.moveRight();
                break;
            case 2:
                player.moveLeft();
                break;
            case 3:
                player.jumpUp();
                break;
            case 4:
                player.jumpRight();
                break;
            case 5:
                player.jumpLeft();
                break;
            case 6:
                this.changeOrientation(orientation-1);
                break;
            default:
                player.stopMoving();
                break;

        }
    }

    private void staticCollitions(Player player){
         /*
        0--1
        |  |
        3--2
         */
        if(player.getVely()>0 && player.getVelx()>0){
            //******************************
            //***rigth-bot velocity arrow***
            rightBotVel(player);

        }else if(player.getVely()>0 && player.getVelx()<0){
            //******************************
            //***rigth-top velocity arrow***
            rightTopVel(player);

        }else if(player.getVely()>0 && player.getVelx()==0){
            //******************************
            //***rigth velocity arrow***
            rightVel(player);

        }else if(player.getVely()<0 && player.getVelx()>0){
            //******************************
            //***left-bot velocity arrow***
            leftBotVel(player);

        }else if(player.getVely()<0 && player.getVelx()<0){
            //******************************
            //***left-top velocity arrow***
            leftTopVel(player);

        }else if(player.getVely()<0 && player.getVelx()==0){
            //******************************
            //***left velocity arrow***
            leftVel(player);

        }else if(player.getVely()==0 && player.getVelx()>0){
            //******************************
            //***bot velocity arrow***
            botVel(player);

        }else if(player.getVely()==0 && player.getVelx()<0){
            //******************************
            //***top velociti arrow***
            topVel(player);

        }
    }

    private void companionCollitions(CompanionCube c){
        boolean[] vertex = calculateVertex(c);
        if(c.IsFalling()){
            if(vertex[2]||vertex[3]){
                c.setPos((int)(c.getx()),(int)(c.gety()));
                c.setVel(0,0);
                c.stopFalling();
            }
        }else{
            if((!vertex[2])&&(!vertex[3])){
                c.startFalling();
            }else if(vertex[0]){
                //Vibra cuando toca la pared
                c.setPos((int)(c.getx()),(int)(c.gety()+1));
            }else if(vertex[1]){
                //Vibra cuando toca la pared
                c.setPos((int)(c.getx()),(int)(c.gety()));
            }

        }
    }

    public int getOrientation(){
        return orientation;
    }

    public void changeOrientation(int or){
        if(or > 4){
            or=1;
        }else if(or < 1){
            or=4;
        }
        orientation = or;
        player.changeOrientation(or);
        for(int i=0;i<companion.size();i++){
            companion.get(i).changeOrientation(or);
        }
    }

    /*************************************/
    //Métodos de las colisiones estáticas//
    /*************************************/
    private void rightBotVel(Player player){
        boolean[] vertex = calculateVertex(player);
            if (vertex[1] && vertex[3]) {
                player.setPos((int) (player.getx()), (int) (player.gety()));
                player.setVel(0, 0);
                player.stopJumping();
            } else if (vertex[1]) {
                player.setPos((player.getx()), (int) (player.gety()));
                player.setVel(player.getVelx(), 0);

            } else if (vertex[3]) {
                player.setPos((int) (player.getx()), (player.gety()));
                player.setVel(0, player.getVely());
                if (player.isJumping()) {
                    player.setVel(0, 0);
                    player.stopJumping();
                }

            } else if (vertex[2]) {
                //Caso esquina
                if (((player.getx() + 0.99) - (int) (player.getx() + 0.99)) > ((player.gety() + 0.99) - (int) (player.gety() + 0.99))) {
                    player.setPos(player.getx(), (int) player.gety());
                    player.setVel(player.getVelx(), 0);
                } else {
                    player.setPos((int) player.getx(), player.gety());
                    player.setVel(0, player.getVely());
                    if (player.isJumping() && !player.isMoving()) {
                        player.setVel(0, 0);
                        player.stopJumping();
                    }
                }
        }
    }

    private void rightTopVel(Player player){
        boolean[] vertex = calculateVertex(player);
        if(vertex[0] && vertex[2]){
            player.setPos((int)(player.getx()),(int)(player.gety()));
        }else if(vertex[0]){
            player.setPos((int)(player.getx()+1),(player.gety()));
            player.setVel(0,player.getVely());
        }else if(vertex[2]){
            player.setPos((player.getx()),(int)(player.gety()));
        }else if(vertex[1]){
            //Caso esquina
            if((int)(player.getx()+0.99)-(player.getx())>(player.gety()+0.99)-(int)(player.gety()+0.99)){
                player.setPos(player.getx(),(int)player.gety());
                player.setVel(player.getVelx(),0);
            }else{
                player.setPos((int)(player.getx()+0.99),player.gety());
                player.setVel(0,player.getVely());
            }
        }
    }

    private void leftBotVel(Player player){
        boolean[] vertex = calculateVertex(player);
        if(vertex[0] && vertex[2]){
            player.setPos((int)(player.getx()),(int)(player.gety()+1));
            player.setVel(0,0);
            player.stopJumping();
        }else if(vertex[0]){
            player.setPos((player.getx()),(int)(player.gety()+1));
            player.setVel(player.getVelx(),0);

        }else if(vertex[2]){
            player.setPos((int)(player.getx()),(player.gety()));
            player.setVel(0,player.getVely());
            if(player.isJumping()){
                player.setVel(0,0);
                player.stopJumping();
            }
        }else if(vertex[3]){
            //Caso esquina
            if(((player.getx()+0.99)-(int)(player.getx()+0.99))>(int)(player.gety()+0.99)-(player.gety())){
                player.setPos(player.getx(),(int)(player.gety()+0.99));
                player.setVel(player.getVelx(),0);
            }else{
                player.setPos((int)player.getx(),player.gety());
                player.setVel(0,player.getVely());
                if(player.isJumping() && !player.isMoving()) {
                    player.setVel(0,0);
                    player.stopJumping();
                }
            }
        }
    }

    private void leftTopVel(Player player){
        boolean[] vertex = calculateVertex(player);
        if(vertex[1] && vertex[3]){
            player.setPos((int)(player.getx()+1),(int)(player.gety()));
        }else if(vertex[1]){
            player.setPos((int)(player.getx()+1),(player.gety()));
            player.setVel(0,player.getVely());
        }else if(vertex[3]){
            player.setPos((player.getx()),(int)(player.gety()+1));
        }else if(vertex[0]){
            //Caso esquina
            if(((int)(player.getx()+0.99)-player.getx())>(int)(player.gety()+0.99)-player.gety()){
                player.setPos(player.getx(),(int)(player.gety()+0.99));
                player.setVel(player.getVelx(),0);
            }else{
                player.setPos((int)(player.getx()+0.99),(player.gety()));
                player.setVel(0,player.getVely());
            }
        }
    }

    private void leftVel(Player player){
        boolean[] vertex = calculateVertex(player);
        if(vertex[0] && !vertex[3]){
            //Vibra cuando toca la pared
            player.setPos((int)(player.getx()),(int)(player.gety()+1));
        }
    }

    private void rightVel(Player player){
        boolean[] vertex = calculateVertex(player);
        if(vertex[1] && !vertex[2]){
            //Vibra cuando toca la pared
            player.setPos((int)(player.getx()),(int)(player.gety()));
        }
    }

    private void topVel(Player player){
        boolean[] vertex = calculateVertex(player);
        if(vertex[0]){
            player.setPos((int)(player.getx())+1,(player.gety()));
            player.setVel(0,0);
        }else if(vertex[1]){
            player.setPos((int)(player.getx())+1,(player.gety()));
            player.setVel(0,0);
        }
    }

    private void botVel(Player player){
        boolean[] vertex = calculateVertex(player);
        if(vertex[3]){
            player.setPos((int)(player.getx()),(player.gety()));
            player.setVel(0,0);
            player.stopJumping();
        }else if(vertex[2]){
            player.setPos((int)(player.getx()),(player.gety()));
            player.setVel(0,0);
            player.stopJumping();
        }
    }

    private boolean[] calculateVertex(Collidable player){
        boolean[] vertex = new boolean[4];
        switch(orientation){
            case 1:
                vertex[0] = horizontalmatrix[(int) (player.getx())][(int) (player.gety())] > groundBlockConstant;
                vertex[1] = horizontalmatrix[(int) (player.getx())][(int) (player.gety() + 0.99)] > groundBlockConstant;
                vertex[2] = horizontalmatrix[(int) (player.getx() + 0.99)][(int) (player.gety() + 0.99)] > groundBlockConstant;
                vertex[3] = horizontalmatrix[(int) (player.getx() + 0.99)][(int) (player.gety())] > groundBlockConstant;
                break;
            case 2:
                vertex[0] = dualverticalmatrix[(int) (player.getx())][(int) (player.gety())] > groundBlockConstant;
                vertex[1] = dualverticalmatrix[(int) (player.getx())][(int) (player.gety() + 0.99)] > groundBlockConstant;
                vertex[2] = dualverticalmatrix[(int) (player.getx() + 0.99)][(int) (player.gety() + 0.99)] > groundBlockConstant;
                vertex[3] = dualverticalmatrix[(int) (player.getx() + 0.99)][(int) (player.gety())] > groundBlockConstant;
                break;
            case 3:
                vertex[0] = dualhorizontalmatrix[(int) (player.getx())][(int) (player.gety())] > groundBlockConstant;
                vertex[1] = dualhorizontalmatrix[(int) (player.getx())][(int) (player.gety() + 0.99)] > groundBlockConstant;
                vertex[2] = dualhorizontalmatrix[(int) (player.getx() + 0.99)][(int) (player.gety() + 0.99)] > groundBlockConstant;
                vertex[3] = dualhorizontalmatrix[(int) (player.getx() + 0.99)][(int) (player.gety())] > groundBlockConstant;
                break;
            case 4:
                vertex[0] = verticalmatrix[(int) (player.getx())][(int) (player.gety())] > groundBlockConstant;
                vertex[1] = verticalmatrix[(int) (player.getx())][(int) (player.gety() + 0.99)] > groundBlockConstant;
                vertex[2] = verticalmatrix[(int) (player.getx() + 0.99)][(int) (player.gety() + 0.99)] > groundBlockConstant;
                vertex[3] = verticalmatrix[(int) (player.getx() + 0.99)][(int) (player.gety())] > groundBlockConstant;
                break;
            default:
                break;
        }
        return vertex;
    }
}