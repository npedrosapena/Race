package com.dosdam.proxecto.race;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by nelson on 4/02/15.
 */
public class RaceBackground
{
    private FloatBuffer vertexBuffer; //bufferes para manejar los arrays con las coordenadas
    private FloatBuffer textureBuffer;
    private ByteBuffer indexBuffer;

    private int textures[]=new int[1];

    private float vertices[]={
          // X    Y    Z
            0.0f,0.0f,0.0f, //esquina 1
            1.0f,0.0f,0.0f, //esquina 2
            1.0f,1.0f,0.0f, //esquina 3
            0.0f,1.0f,0.0f //esquina 4
    }; //idices de una esquina en un cuadrado X Y Z

    private float texture[]={
            0.0f,0.0f,
            1.0f,0f,
            1f,1.0f,
            0f,1f
    }; //indices en los que nuestra textura se encuadra con el cuadrado creado con los vertices[] solo tiene X e Y

    private byte indices[]={
            0,1,2, //triangulo 1
            0,2,3 //triangulo 2
    }; //define un triangulo que duplicado crea un cuadrado



    public RaceBackground()
    {
        //asociamos a cada buffer su array
        ByteBuffer byteBuf=ByteBuffer.allocateDirect(vertices.length * 4); //se multiplica por 4 porque usamos bytes y para que se equiparen con Integers...1 integer = 1 byte * 4
        byteBuf.order(ByteOrder.nativeOrder());

        vertexBuffer=byteBuf.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);


        byteBuf=ByteBuffer.allocateDirect(texture.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        textureBuffer=byteBuf.asFloatBuffer();
        textureBuffer.put(texture);
        textureBuffer.position(0);


        indexBuffer=ByteBuffer.allocateDirect(indices.length);
        indexBuffer.put(indices);
        indexBuffer.position(0);
    };


    public void loadTexture(GL10 gl,int texture, Context context)
    {
        InputStream imagestream= context.getResources().openRawResource(texture); //puntero a la imagen
        Bitmap bitmap= null;

        try
        {
            bitmap=BitmapFactory.decodeStream(imagestream); //carga imagen en un bitmap

        }catch(Exception ex)
        {
            Log.e("Error: ",ex.getMessage().toString());
        }finally
        {
            //limpiamos siempre la memoria para no tener basura y ralentice el sistema o tengamos
            //problemas de espacio en memoria

            try
            {
                imagestream.close();
                imagestream=null;
            }catch(Exception ex)
            {
                Log.e("Error limpiar mem. img",ex.getMessage().toString());
            }

            gl.glGenTextures(1,textures,0); //genera un puntero a las texturas (numero de nombres para la textura que tiene que generar para este caso es 1, array con los valores de la textura, numero de ultima posicion del array de la que necesitamos los elementos)
            gl.glBindTexture(GL10.GL_TEXTURE_2D,textures[0]); //transforma la textura en un objeto opengl
            gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST); //indicamos como tiene que manejar las texturas opengl
            gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT); //repite la imagen de fondo hasta el infinito en caso de necesitar scroll
            gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D,0,bitmap,0); //asocia la textura cargada con el primer elemento del array de texturas
            bitmap.recycle();



        }
    }


    public void draw(GL10 gl)
    {
        gl.glBindTexture(GL10.GL_TEXTURE_2D,textures[0]);

        //ignora vertices que no sean visibles para el jugador (3D) para un gráfico en 2D
        //ignora vertices que no sean visibles para el jugador (3D) para un gráfico en 2D
        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);

        //carga los vertices y texturas y los prepara para ser procesados
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT,0,vertexBuffer);
        gl.glTexCoordPointer(2,GL10.GL_FLOAT,0,textureBuffer);

        //dibuja en pantalla el grafico
        gl.glDrawElements(GL10.GL_TRIANGLES,indices.length,GL10.GL_UNSIGNED_BYTE,indexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);

    }
}