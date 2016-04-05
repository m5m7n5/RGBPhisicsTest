package marcos_buenacasa_lpez.rgbphisicstest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.util.List;









import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private Motor m;
    private CountDownTimer bucle;
    private levelView mainview;
    private int gametime;
    private int viewHeight;
    private int viewWidth;
    private float prevx,prevy = 0;
    private float curx,cury= 0;


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
                {12, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 12},
                {12, 00, 13, 13, 13, 13, 00, 00, 00, 00, 00, 00, 00, 04, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 12},
                {12, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 13, 13, 13, 13, 00, 00, 00, 00, 00, 00, 14, 00, 00, 12},
                {12, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 14, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 14, 00, 00, 12},
                {12, 00, 00, 00, 00, 00, 00, 00, 00, 00, 14, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 14, 00, 00, 00, 12},
                {12, 00, 00, 00, 00, 00, 14, 13, 13, 13, 13, 00, 00, 00, 00, 00, 00, 00, 12, 12, 00, 00, 00, 00, 00, 12},
                {12, 00, 15, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 12},
                {12, 00, 00, 15, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 12},
                {12, 00, 00, 14, 14, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 03, 00, 00, 00, 13, 13, 00, 00, 00, 12},
                {12, 00, 00, 00, 00, 15, 00, 00, 00, 00, 00, 00, 00, 00, 00, 13, 13, 00, 00, 00, 00, 00, 00, 00, 00, 12},
                {12, 00, 00, 00, 00, 00, 13, 00, 02, 00, 00, 00, 00, 13, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 12},
                {12, 00, 00, 00, 00, 00, 00, 00, 13, 13, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 12},
                {12, 01, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 12, 13, 14, 15, 00, 00, 00, 00, 00, 00, 00, 00, 00, 12},
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
        m = new Motor(25, matrix, picsize,pics,4,1);
        m.iniLevel(viewWidth,viewHeight);

        mainview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                v.invalidate();
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    switch(m.getOrientation()) {
                        case 1:
                            if (viewHeight / 2 < y) {
                                if (viewWidth / 3 > x) {
                                    //Abajo izquierda
                                    m.motorAction(2);
                                } else if ((2 * viewWidth) / 3 < x) {
                                    //Abajo derecha
                                    m.motorAction(1);
                                } else if((viewWidth) / 3 < x && x <= (2 * viewWidth) / 3){
                                    //Ejecutar cambio de rotacion
                                    //m.motorAction(6);
                                }
                            } else {
                                if ((viewWidth) / 3 < x && x <= (2 * viewWidth) / 3) {
                                    //Arraiba centro
                                    m.motorAction(3);
                                } else if ((2 * viewWidth) / 3 < x) {
                                    //Arriba derecha
                                    m.motorAction(4);
                                } else if (x <= (viewWidth / 3)) {
                                    //Arriba izquierda
                                    m.motorAction(5);
                                }
                            }
                            break;
                        case 2:
                            if(viewHeight/3 > y){
                                if(viewWidth/2 > x){
                                    m.motorAction(4);
                                }else{
                                    m.motorAction(1);
                                }
                            }else if(viewHeight/3 < y && (2*viewHeight)/3 > y){
                                if(viewWidth/2 > x){
                                    m.motorAction(3);
                                }else{
                                    //m.motorAction(6);
                                }
                            }else if((2*viewHeight)/3 < y){
                                if(viewWidth/2 > x){
                                    m.motorAction(5);
                                }else{
                                    m.motorAction(2);
                                }
                            }
                            break;
                        case 3:
                            if (viewHeight / 2 < y) {
                                if (viewWidth / 3 > x) {
                                    //Abajo izquierda
                                    m.motorAction(4);
                                } else if ((2 * viewWidth) / 3 < x) {
                                    //Abajo derecha
                                    m.motorAction(5);
                                } else if((viewWidth) / 3 < x && x <= (2 * viewWidth) / 3){
                                    //Ejecutar cambio de rotacion
                                    m.motorAction(3);
                                }
                            } else {
                                if ((viewWidth) / 3 < x && x <= (2 * viewWidth) / 3) {
                                    //Arraiba centro
                                    //m.motorAction(6);
                                } else if ((2 * viewWidth) / 3 < x) {
                                    //Arriba derecha
                                    m.motorAction(2);
                                } else if (x <= (viewWidth / 3)) {
                                    //Arriba izquierda
                                    m.motorAction(1);

                                }
                            }
                            break;
                        case 4:
                            if(viewHeight/3 > y){
                                if(viewWidth/2 < x){
                                    m.motorAction(5);
                                }else{
                                    m.motorAction(2);
                                }
                            }else if(viewHeight/3 < y && (2*viewHeight)/3 > y){
                                if(viewWidth/2 < x){
                                    m.motorAction(3);
                                }else{
                                    //m.motorAction(6);
                                }
                            }else if((2*viewHeight)/3 < y){
                                if(viewWidth/2 < x){
                                    m.motorAction(4);
                                }else{
                                    m.motorAction(1);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP) {
                    m.motorAction(0);
                    return true;
            }
                return false;
            }
        });






        bucle = new CountDownTimer((long) Double.POSITIVE_INFINITY, 20) {
            public void onTick(long millisUntilFinished) {

                m.phisics(20);
                m.logic();
                mainview.invalidate();
                gametime += 20;
                if(curx > 5 && cury > -3 && cury < 3){
                    if(m.getOrientation() != 1) {
                        m.changeOrientation(1);
                    }
                }else if(curx<-5 && cury > -3 && cury < 3){
                    if(m.getOrientation() != 3) {
                        m.changeOrientation(3);
                    }
                }else if(cury > 5 && curx > -3 && curx < 3){
                    if(m.getOrientation() != 2) {
                        m.changeOrientation(2);
                    }
                }else if(cury < -5 && curx > -3 && curx < 3){
                    if(m.getOrientation() != 4) {
                        m.changeOrientation(4);
                    }
                }


            }
            public void onFinish() {

                mainview.invalidate();
            }

        }.start();
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> listSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        checkSensors(listSensors, sensorManager);

    }

    private void checkSensors(List<Sensor> listSensors, SensorManager sensorManager){
        listSensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (!listSensors.isEmpty()){
            Sensor accelerometerSensor = listSensors.get(0);
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int precision){}

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        // We need to synchronize the access because a principal thread
        // can go through here.
        synchronized (this){
            Sensor accelerometer = sensorEvent.sensor;
            if (accelerometer.getType() == Sensor.TYPE_ACCELEROMETER) {
                // list "values" saves the data of the three axis of the accelerometer.
                curx = sensorEvent.values[0];
                cury = sensorEvent.values[1];

                // We check if the code has been executed before. We inicialize the values.
                if (prevx == 0 && prevy == 0) {
                    prevx = curx;
                    prevy = cury;
                }

                prevx = curx;
                prevy = cury;
            }
        }
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
