package com.dosdam.proxecto.race;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageButton;


public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen); //en el main cargamos el layout con la imagen del coche (splashscreen)

        RaceEngine.context = this;

        /*Creamos un nuevo hilo para el juego y sus recursos*/
        new Handler().postDelayed(new Thread()
        {
            @Override
            public void run()
            {
                Intent mainMenu = new Intent(MainActivity.this, RaceMainMenu.class);
                MainActivity.this.startActivity(mainMenu);
                MainActivity.this.finish();
                overridePendingTransition(R.layout.fadein,R.layout.fadeout);
            }
        }, RaceEngine.GAME_THREAD_DELAY);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
