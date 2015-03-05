package com.dosdam.proxecto.race;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;

/**
 * Created by nelson on 31/01/15.
 */
public class RaceGame extends Activity
{
    private RaceGameView gameView;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        gameView = new RaceGameView(this);
        setContentView(gameView);
    }

    /*en caso de que se inicie otra activity (llamada de tlf, salida al menú del tlf...)
    * tenemos que pausar nuestra aplicacion, para eso sobreescribimos los métodos onResume y
    * onPause, son métodos que están implementados ya en la clase Activity */
    @Override
    protected void onResume()
    {
        //reactiva la activity NO EL JUEGO
        super.onResume();;
        gameView.onResume();
    }

    @Override
    protected void onPause()
    {
        //pausa la activity NO EL JUEGO
        super.onPause();
        gameView.onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //recogemos las posiciones del evento, es decir, si pulsamos la izquierda o derecha de la pantalla
        float x=event.getX();
        float y=event.getY();

        //establecemos el area del juego donde queremos controlar los eventos
        //en este caso, será en la zona baja de la pantalla para así controlar
        //el movimiento de la nave con los pulgares

        /*
        DisplayMetrics displaymetrics = new DisplayMetrics();
        Activity mContext=null;
        mContext.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;
        */
        Display d=getWindowManager().getDefaultDisplay(); //RaceEngine.display;


        Point size= new Point();
        d.getSize(size);
        int height=size.y/4;
        //int height=RaceEngine.display.getHeight()/4; //el tamaño de la pantalla y lo dividimos por 4 nos dará el número de pixels
                                                     //a utilizar 100% /4= 25% de la pantalla, o lo que es lo mismo la zona baja ocupada
                                                    //por nuestro sprite
        //int height=screenHeight/4;

        int playableArea= size.y-height; //el total de la pantalla - la zona de eventos de movimiento
        //int playableArea= displaymetrics.heightPixels-height;

        if(y>playableArea)
        {
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    if(x<size.y/2)
                    {
                        RaceEngine.playerFlightAction=RaceEngine.PLAYER_BANK_LEFT_1;
                    }else
                    {
                        RaceEngine.playerFlightAction=RaceEngine.PLAYER_BANK_RIGHT_1;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    RaceEngine.playerFlightAction=RaceEngine.PLAYER_RELEASE;


                    break;
            }
        }

        return false;
    }
}
