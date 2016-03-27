package marcos_buenacasa_lpez.rgbphisicstest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Motor m;
    private CountDownTimer bucle;
    private levelView mainview;
    private int gametime;
    private int viewHeight;
    private int viewWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final levelView mainview = new levelView(this);
        setContentView(mainview);
        /*
        Cogiendo la anchura y altura del screen.
        */
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        viewHeight = size.y;
        viewWidth = size.x;
        /*
        Matriz del nivel
         */
        int[][] matrix = {//15x26
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                {12, 0, 13, 13, 13, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                {12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 13, 13, 13, 0, 0, 0, 0, 0, 0, 14, 0, 0, 12},
                {12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 0, 0, 12},
                {12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 0, 0, 0, 12},
                {12, 0, 0, 0, 0, 0, 14, 13, 13, 13, 13, 0, 0, 0, 0, 0, 0, 0, 12, 12, 0, 0, 0, 0, 0, 12},
                {12, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                {12, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                {12, 0, 0, 14, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 13, 0, 0, 0, 12},
                {12, 0, 0, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 13, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                {12, 0, 0, 0, 0, 0, 13, 0, 0, 0, 0, 0, 0, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                {12, 0, 0, 0, 0, 0, 0, 0, 13, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                {12, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12}
        };
        /*
        Creo el motor e inicializo el nivel
         */
        int picsize;
        if(26.0/15.0>viewWidth/viewHeight){
            picsize = viewWidth/26;
        }else{
            picsize = viewHeight/15;
        }
        ArrayList<Drawable> pics = new ArrayList<Drawable>();
        loadResources(pics);
        m = new Motor(10, 0, matrix, picsize,pics);
        m.iniLevel(viewWidth,viewHeight);

        mainview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                v.invalidate();
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(viewHeight/2<y){
                        if(viewWidth/2<x){
                            //Abajo izquierda
                            m.playerAction(1);
                        }else{
                            //Abajo derecha
                            m.playerAction(2);
                        }
                    }else{
                        if((viewWidth)/3<x && x<=(2*viewWidth)/3){
                            //Arraiba centro
                            m.playerAction(3);
                        }else if((2*viewWidth)/3<x){
                            //Arriba derecha
                            m.playerAction(4);
                        }else if(0<x && x<=(viewWidth/3)){
                            //Arriba izquierda
                            m.playerAction(5);
                        }
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP) {
                    m.playerAction(0);
                    return true;
                }
                return false;
            }
        });






        bucle = new CountDownTimer((long) Double.POSITIVE_INFINITY, 32) {
            public void onTick(long millisUntilFinished) {

                m.phisics(32);
                m.logic();
                mainview.invalidate();
                gametime += 32;

            }
            public void onFinish() {

                mainview.invalidate();
            }

        }.start();
    }

    private void loadResources(ArrayList<Drawable> pics) {
        pics.add(getResources().getDrawable(R.drawable.red_player));
        pics.add(getResources().getDrawable(R.drawable.green_player));
        pics.add(getResources().getDrawable(R.drawable.blue_player));
        pics.add(getResources().getDrawable(R.drawable.yellow_player));
        pics.add(getResources().getDrawable(R.drawable.button));
        pics.add(getResources().getDrawable(R.drawable.spikes));
        pics.add(getResources().getDrawable(R.drawable.door));
        pics.add(getResources().getDrawable(R.drawable.red_companion_cube));
        pics.add(getResources().getDrawable(R.drawable.green_companion_cube));
        pics.add(getResources().getDrawable(R.drawable.blue_companion_cube));
        pics.add(getResources().getDrawable(R.drawable.yellow_companion_cube));
        pics.add(getResources().getDrawable(R.drawable.ground_block1));
        pics.add(getResources().getDrawable(R.drawable.ground_block2));
        pics.add(getResources().getDrawable(R.drawable.cloud_block));
        pics.add(getResources().getDrawable(R.drawable.dirt_block));
    }

    class levelView extends View {
        public levelView (Context context) {
            super(context);
        }
        @Override
        protected void onDraw(Canvas canvas) {
            m.draw(canvas);
        }
    }
}
