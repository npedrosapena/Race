package com.dosdam.proxecto.race;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by nelson on 31/01/15.
 */
public class RaceGameView extends GLSurfaceView
{
    private RaceGameRenderer renderer;

    public RaceGameView(Context context)
    {
        super(context);

        renderer= new RaceGameRenderer();
        this.setRenderer(renderer);
    }
}
