package com.dosdam.proxecto.race;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by nelson on 30/01/15.
 */
public class RaceMusic extends Service
{
    public static boolean isRunning=false;
    static MediaPlayer player; //instanciamos un objeto tipo mediaplayer para reproducir audio
    private static Context context;


    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        context=this;
        setMusicOptions(RaceEngine.LOOP_BACKGROUND_MUSIC,RaceEngine.R_VOLUME,RaceEngine.L_VOLUME,RaceEngine.SPLASH_SCREEN_MUSIC); //le pasamos los valores por defecto establecidos en el motor del juego
    }

    //establecemos los valores por defecto para el reproductor de musica
    public void setMusicOptions(boolean isLooped,int rVolume, int lVolume, int soundFile)
    {
        player=MediaPlayer.create(context,soundFile); //le pasamos el "hilo" o conenedor que va a manejar el audio y el puntero al archivo de audio
        player.setLooping(isLooped); //reproduccion en bucle
        player.setVolume(rVolume,lVolume);
    }

    public int onStartCommand(Intent intent, int flags,int startId)
    {
        try
        {
            player.start();
            isRunning = true;

        }catch (Exception ex)
        {
            Log.e("Error iniciando audio: ",ex.getMessage().toString());
            isRunning=false;
            player.stop();
        }
        return 1;
    }

    public void onStart(Intent intent, int startId)
    {

    }

    public void onStop()
    {
        isRunning=false;
    }

    public IBinder onUnBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    public void onPause()
    {

    }

    @Override
    public void onDestroy()
    {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory()
    {
        player.stop();
    }

}
