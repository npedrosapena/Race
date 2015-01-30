package com.dosdam.proxecto.race;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by nelson on 30/01/15.
 */

public class RaceMainMenu extends Activity
{

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //en el main cargamos el layout con la imagen del coche (splashscreen)

        final RaceEngine engine = new RaceEngine();

         /*vamos a instancias los botones para poder darles propiedades*/
        ImageButton start= (ImageButton)findViewById(R.id.btnStart);
        ImageButton exit=(ImageButton)findViewById(R.id.btnExit);

        start.getBackground().setAlpha(RaceEngine.MENU_BUTTON_ALPHA); //añadimos canal alpha con el valor 0
        start.setHapticFeedbackEnabled(RaceEngine.HAPTIC_BUTTON_FEEDBACK); //activamos propiedades especiales

        exit.getBackground().setAlpha(RaceEngine.MENU_BUTTON_ALPHA);
        exit.setHapticFeedbackEnabled(RaceEngine.HAPTIC_BUTTON_FEEDBACK);


        /* le añadimos el click a los botones */

        start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /* Empieza el juego*/
            }
        });


        exit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean clean=false;
                clean=engine.onExit(v);

                if(clean)
                {
                    int pid=android.os.Process.myPid(); //recogemos la clave interna de la app
                    android.os.Process.killProcess(pid); //cerramos la aplicacion limpiamente y todos sus hilos
                }
            }
        });





    }
}
