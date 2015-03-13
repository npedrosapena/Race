package com.dosdam.proxecto.race;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Display;
import android.view.View;

/**
 * Created by nelson on 30/01/15.
 */
public class RaceEngine
{
    public static final int GAME_THREAD_DELAY=4000;
    public static final int MENU_BUTTON_ALPHA=0; //se usará mas tarde para quitar el fondo a las imagenes de los botones
    public static final boolean HAPTIC_BUTTON_FEEDBACK=true; //el feedback es una opcion que nos habilita muchos pequeños detalles tal como
                                                             //vibrar al pulsar una tecla,  volver atrás en los dispositivos con boton táctil...

    public static final int SPLASH_SCREEN_MUSIC=R.raw.audiointro; //indicamos cual es el archivo a reproducir en el menu
    public static final int R_VOLUME=100; //nivel audio derecha
    public static final int L_VOLUME=100; //nivel audio izda
    public static final boolean LOOP_BACKGROUND_MUSIC=true; //reproducimos en loop
    public static Context context; //"guarda" el hilo en el que se reproduce el audio, así podemos hacer cosas con ese hilo (context)
    public static Thread musicThread; //hilo para reproducir musica
    //public static final int BACKGROUND_LAYER_ONE=R.drawable.trazadob; //Apuntamos a la imagen de fondo
    public static final int BACKGROUND_LAYER_ONE=R.drawable.backgroundstars; //Apuntamos a la imagen de fondo
    //public static final int BACKGROUN_LAYER_TWO_COCHE=R.drawable.top_coche;
    public static final int BACKGROUN_LAYER_TWO_COCHE=R.drawable.debris;
    public static final int GAME_THREAD_FPS_SLEEP=(1000/60); //pausa para controlar los fps del juego
    public static float SCROLL_BACKGROUND_1=.002f;
    public static float SCROLL_BACKGROUND_2=.004f;

    public static int playerFlightAction=0;
    public static final int PLAYER_SHIP= R.drawable.good_sprite;

    //Posiciones del dibujo del array según accion
    public static final int PLAYER_BANK_LEFT_1=1; //giro izquierda
    public static final int PLAYER_RELEASE=3; //neutral
    public static final int PLAYER_BANK_RIGHT_1=4; //giro derecha

    public static final int PLAYER_FRAMES_BETWEEN_ANI=9; //indica el número de iteracion para refrescar el sprite
    public static final float PLAYER_BANK_SPEED=0.09f;
    public static float playerBankPosx=1.75f;

    //pantalla
    public static Display display;
    //public Display display=getWindowManager().getDefaultDisplay();



    /* metodo para salir del juego */
    public boolean onExit(View v)
    {
        try
        {
            Intent bgMusic= new Intent(context,RaceMusic.class); //context está definido en esta clase
                                                                // porque lo declaramos en la clase RaceMusic, por lo que
                                                                // en bgmusic recogemos el intent creado en RaceMusic
            context.stopService(bgMusic); //paramos la musica
          //METODO DEPRECATED  musicThread.stop(); //paramos el hilo
         return true;
        }catch(Exception ex)
        {
            Log.e("error salir del juego",ex.getMessage().toString());//si se produce un error que nos informe por consola
            return false;
        }
    }
}
