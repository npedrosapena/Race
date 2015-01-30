package com.dosdam.proxecto.race;

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
