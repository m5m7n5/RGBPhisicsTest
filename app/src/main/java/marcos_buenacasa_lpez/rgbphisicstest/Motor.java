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
    private int gravityx;
    private int gravityy;
    private Canvas ground;
    private CountDownTimer c;
    private int [][] matrix;
    private Collidable [][] collidableList;
    private int picsize;
    private Player player;
    private ArrayList<Drawable> drawable;
    private int groundBlockConstant = 11;
    private Bitmap groundB;
    private int id_color;
    private int orientation;
    private int gravityabsolutevalue;

    public Motor(int g, int [][] mat,int picsize,ArrayList<Drawable> drawable,int id_color,int orientation){
        matrix = mat;
        this.picsize = picsize;
        this.drawable = drawable;
        collidableList = new Collidable[15][26];
        this.id_color = id_color;
        this.orientation = orientation;
        gravityabsolutevalue = g;
        switch(orientation){
            /*
            1 down grav
            2 right grav
            3 top grav
            4 left grav
             */
            case 1:
                gravityx = g;
                break;
            case 2:
                gravityy = g;
                break;
            case 3:
                gravityx = (-1)*g;
                break;
            case 4:
                gravityy = (-1)*g;
                break;
            default:
                break;
        }
    }

    public void iniLevel(int h,int w){
        ground = new Canvas();
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                switch(matrix[i][j]){
                    //Los 4 primeros casos son los jugadores, respectivamente.
                    //Los casos intermedios son los objetos
                    //Los últimos casos son los bloques
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        collidableList[i][j] = new Player(i,j,1,1,matrix[i][j],0,0,drawable.get(matrix[i][j]-1),this.orientation);
                        if(id_color == matrix[i][j]) {
                            player = (Player) collidableList[i][j];
                        }
                        break;
                    //Botones
                    case 5:
                        collidableList[i][j] = new Collidable(i,j,1,0.5,0,0,0,drawable.get(matrix[i][j]-1));
                        break;
                    //Pinchos
                    case 6:
                        collidableList[i][j] = new Collidable(i,j,1,1,0,0,0,drawable.get(matrix[i][j]-1));
                        break;
                    //Puerta
                    case 7:
                        collidableList[i][j] = new Collidable(i,j,1,1,0,0,0,drawable.get(matrix[i][j]-1));
                        break;
                    //Companion cube
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                        collidableList[i][j] = new Collidable(i,j,1,1,matrix[i][j]-7,0,0,drawable.get(matrix[i][j]-1));
                        break;
                    default:
                        if(matrix[i][j]!=0){
                            collidableList[i][j] = new Collidable(i,j,1,1,0,0,0,drawable.get(matrix[i][j]-1));
                        }
                        break;
                }

            }
        }
        ground.drawARGB(255, 225, 225, 255);
        for(int i=0;i<matrix.length;i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if(collidableList[i][j]!=null) {
                    collidableList[i][j].draw(picsize,ground);
                }
            }
        }
    }

    public void phisics(int dt){
        player.update(dt,gravityx,gravityy,orientation);
        staticCollitions(player);
    }

    public void logic(){

    }

    public void draw(Canvas c){
        c.drawARGB(255, 225, 225, 255);
        for(int i=0;i<matrix.length;i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if(collidableList[i][j]!=null) {
                    collidableList[i][j].draw(picsize, c);
                }
            }
        }
        /*
        c.drawText(Double.toString(player.getx()),50,70,new Paint(Color.BLACK));
        c.drawText(Double.toString(player.gety()),50,90,new Paint(Color.BLACK));
        c.drawText(Double.toString(player.getVelx()),50,110,new Paint(Color.BLACK));
        c.drawText(Double.toString(player.getVely()),50,130,new Paint(Color.BLACK));
        */
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
                this.changeOrientation(orientation+1);
                break;
            default:
                player.stopMoving();
                break;

        }
    }

    private void staticCollitions(Player player){
        //Verdadero if
         /*
        0--1
        |  |
        3--2
         */

        boolean[] vertex = {matrix[(int)(player.getx())][(int)(player.gety())]>groundBlockConstant,
                matrix[(int)(player.getx())][(int)(player.gety()+0.99)]>groundBlockConstant,
                matrix[(int)(player.getx()+0.99)][(int)(player.gety()+0.99)]>groundBlockConstant,
                matrix[(int)(player.getx()+0.99)][(int)(player.gety())]>groundBlockConstant};

        if(player.getVely()>0 && player.getVelx()>0){
            //******************************
            //***rigth-bot velocity arrow***
            //DONE
            if(vertex[1] && vertex[3]){
                player.setPos((int)(player.getx()),(int)(player.gety()));
                player.setVel(0,0);
                player.stopJumping();
            }else if(vertex[1]){
                player.setPos((player.getx()),(int)(player.gety()));
                player.setVel(player.getVelx(),0);
                if(player.isJumping() && orientation == 2){
                    player.setVel(0,0);
                    player.stopJumping();
                }

            }else if(vertex[3]){
                player.setPos((int)(player.getx()),(player.gety()));
                player.setVel(0,player.getVely());
                if(player.isJumping() && orientation == 1){
                    player.setVel(0,0);
                    player.stopJumping();
                }
            }else if(vertex[2]){
                //Caso esquina
                if(((player.getx()+0.99)-(int)(player.getx()+0.99))>((player.gety()+0.99)-(int)(player.gety()+0.99))){
                    player.setPos(player.getx(),(int)player.gety());
                    player.setVel(player.getVelx(),0);
                }else{
                    player.setPos((int)player.getx(),player.gety());
                    player.setVel(0,player.getVely());
                    if(player.isJumping() && !player.isMoving()){
                        player.setVel(0,0);
                        player.stopJumping();
                    }
                }
            }

        }else if(player.getVely()>0 && player.getVelx()<0){
            //******************************
            //***rigth-top velocity arrow***
            //DONE
            if(vertex[0] && vertex[2]){
                player.setPos((int)(player.getx()),(int)(player.gety()));
            }else if(vertex[0]){
                player.setPos((int)(player.getx()+1),(player.gety()));
                player.setVel(0,player.getVely());
            }else if(vertex[2]){
                player.setPos((player.getx()),(int)(player.gety()));
                player.setVel(player.getVelx(),0);
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

        }else if(player.getVely()>0 && player.getVelx()==0){
            //******************************
            //***rigth velocity arrow***
            //DONE
            if(vertex[1]){
                player.setPos((int)(player.getx()),(int)(player.gety()));
            }

        }else if(player.getVely()<0 && player.getVelx()>0){
            //******************************
            //***left-bot velocity arrow***
            //DONE
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
                if(player.isJumping() && orientation == 1){
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

        }else if(player.getVely()<0 && player.getVelx()<0){
            //******************************
            //***left-top velocity arrow***
            if(vertex[1] && vertex[3]){
                player.setPos((int)(player.getx()+1),(int)(player.gety()));
            }else if(vertex[1]){
                player.setPos((int)(player.getx()+1),(player.gety()));
                player.setVel(0,player.getVely());
            }else if(vertex[3]){
                player.setPos((player.getx()),(int)(player.gety()+1));
                player.setVel(player.getVelx(),0);
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

        }else if(player.getVely()<0 && player.getVelx()==0){
            //******************************
            //***left velocity arrow***
            //DONE
            if(vertex[0]){
                player.setPos((int)(player.getx()),(int)(player.gety()+1));
            }

        }else if(player.getVely()==0 && player.getVelx()>0){
            //******************************
            //***bot velocity arrow***
            //DONE
            if(vertex[3]){
                player.setPos((int)(player.getx()),(player.gety()));
                player.setVel(0,0);
                player.stopJumping();
            }else if(vertex[2]){
                player.setPos((int)(player.getx()),(player.gety()));
                player.setVel(0,0);
                player.stopJumping();
            }

        }else if(player.getVely()==0 && player.getVelx()<0){
            //******************************
            //***top velociti arrow***
            //DONE
            if(vertex[0]){
                player.setPos((int)(player.getx())+1,(player.gety()));
                player.setVel(0,0);
            }else if(vertex[1]){
                player.setPos((int)(player.getx())+1,(player.gety()));
                player.setVel(0,0);
            }

        }
    }

    public int getOrientation(){
        return orientation;
    }
    private void changeOrientation(int or){
        if(or > 4 || or < 1){
            or=(or%4);
        }
        orientation = or;
        switch(or){
            /*
            1 down grav
            2 right grav
            3 top grav
            4 left grav
             */
            case 1:
                gravityx = gravityabsolutevalue;
                gravityy = 0;
                break;
            case 2:
                gravityx = 0;
                gravityy = gravityabsolutevalue;
                break;
            case 3:
                gravityx = (-1)*gravityabsolutevalue;
                gravityy = 0;
                break;
            case 4:
                gravityx = 0;
                gravityy = (-1)*gravityabsolutevalue;
                break;
            default:
                break;
        }
    }
}
