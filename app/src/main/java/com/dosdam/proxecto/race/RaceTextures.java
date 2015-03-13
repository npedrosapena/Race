package com.dosdam.proxecto.race;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by nelson on 6/03/15.
 */
public class RaceTextures
{
    private int textures[]=new int[1];


    public RaceTextures(GL10 gl10)
    {
        gl10.glGenTextures(1,textures,0);

    }

    public int[] loadTexture(GL10 gl, int texture, Context context,int textureNumber)
    {
        try
        {
            InputStream imageStream=context.getResources().openRawResource(texture);
            Bitmap bitmap=null;

            try
            {
                bitmap= BitmapFactory.decodeStream(imageStream);

            }catch(Exception ex)
            {

            }

            imageStream.close();
            imageStream=null;

            gl.glBindTexture(GL10.GL_TEXTURE_2D,textures[textureNumber-1]);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);


        }catch(Exception ex)
        {

        }finally
        {

        }
    }
}
