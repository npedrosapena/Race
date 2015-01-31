package com.dosdam.proxecto.race;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
    /* metodo para salir del juego */

    public boolean onExit(View v)
    {
        try
        {
         return true;
        }catch(Exception ex)
        {
            Log.e("error saliendo del juego",ex.getMessage().toString());//si se produce un error que nos informe por consola
            return false;
        }
    }
}
