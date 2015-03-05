package com.dosdam.proxecto.race;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by nelson on 31/01/15.
 */
public class RaceGameRenderer implements GLSurfaceView.Renderer
{
    private RaceBackground background= new RaceBackground();
    private RaceBackground background2_coche=new RaceBackground();
    private float bgScroll1;
    private float bgScroll2;

    private long loopstart=0;
    private long loopEnd=0;
    private long loopRunTime=0;

    private RaceGoodGuy player1=new RaceGoodGuy();
    private int goodGuyBankFrames=0;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {

        gl.glEnable(GL10.GL_TEXTURE_2D); //activa las capacidades 2d de opengl
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);

        //crea transparencias en las imagenes
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE,GL10.GL_ONE);

        background.loadTexture(gl,RaceEngine.BACKGROUND_LAYER_ONE,RaceEngine.context);
        background2_coche.loadTexture(gl,RaceEngine.BACKGROUN_LAYER_TWO_COCHE,RaceEngine.context);
        player1.loadTexture(gl,RaceEngine.PLAYER_SHIP,RaceEngine.context);

    }

    //este metodo se llama cada vez que la pantalla se redimensiona, cambia la orientación y en la ejecucion inicial
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {

        gl.glViewport(0,0,width,height);
        gl.glMatrixMode(GL10.GL_PROJECTION); //necesitaremos acceder al metodo glOrthof para poder renderizar las imagenes
                                            // para eso, necesitamos establecer como vamos a renderizar las imagenes, en este caso es PROJECTION

        gl.glLoadIdentity(); //estado por defecto del renderizado
        gl.glOrthof(0f,1f,0f,1f,-1f,1f); //establecemos los limites de nuestro plano para renderizar. esto quiere decir
                                         //que toda imagen que salga de esas coordenadas, no será mostrada.
                                        //Las coordenadas representan: izda, dcha, bottom, top, cercania, alejado

    }

    @Override
    public void onDrawFrame(GL10 gl)
    {
        loopstart=System.currentTimeMillis();
        try
        {
            if(loopRunTime<RaceEngine.GAME_THREAD_FPS_SLEEP)
            {
                Thread.sleep(RaceEngine.GAME_THREAD_FPS_SLEEP-loopRunTime);
            }


        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        if(bgScroll1==Float.MAX_VALUE)
        {
            bgScroll1=0f;
        }
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT); //preparamos el buffer para usar OPENGL

        scrollBackground1(gl); //pintado y movimiento
        scrollBackground2(gl);
        movePlayer1(gl);

        //transparencias etc
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
        loopEnd=System.currentTimeMillis();
        loopRunTime=((loopEnd-loopstart));

    }

    //el fondo
    private void scrollBackground1(GL10 gl)
    {
        /*Resetea los valores de escalado y posicionamiento */
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glScalef(1f, 1f, 1f);
        gl.glTranslatef(0f, 0f, 0f);

        //carga el fondo
        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f,bgScroll1,0.0f); //scroll de textura

        //dibuja el fondo y lo mueve
        background.draw(gl);
        gl.glPopMatrix();
        bgScroll1+=RaceEngine.SCROLL_BACKGROUND_1;
        gl.glLoadIdentity();
    }

    //el coche

    private void scrollBackground2(GL10 gl)
    {
        if(bgScroll2==Float.MAX_VALUE)
        {
            bgScroll2=0f;
        }

        /*Resetea los valores de escalado y posicionamiento */
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glScalef(.5f, 1f, 1f);
        gl.glTranslatef(1.5f, 0f, 0f);

        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();
        gl.glTranslatef( 0.0f,bgScroll2, 0.0f);

        background2_coche.draw(gl);
        gl.glPopMatrix();
        bgScroll2 +=  RaceEngine.SCROLL_BACKGROUND_2;
        gl.glLoadIdentity();



        //para controlar la imagen bg2 y dar sensaciones de movimiento ve a la página 110 del libro capitulo 4
    }


    private void movePlayer1(GL10 gl)
    {
        switch (RaceEngine.playerFlightAction)
        {
            case RaceEngine.PLAYER_BANK_LEFT_1:
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glPushMatrix();
                gl.glScalef(.25f,.25f,1f);

                if(goodGuyBankFrames<RaceEngine.PLAYER_FRAMES_BETWEEN_ANI && RaceEngine.playerBankPosx>0)
                {
                    //movimiento de la figura
                    RaceEngine.playerBankPosx -=RaceEngine.PLAYER_BANK_SPEED;
                    gl.glTranslatef(RaceEngine.playerBankPosx,0f,0f);
                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(0.75f,0.0f,0.0f);
                    goodGuyBankFrames+=1;

                }else if(goodGuyBankFrames >=RaceEngine.PLAYER_FRAMES_BETWEEN_ANI && RaceEngine.playerBankPosx>0)
                {
                    RaceEngine.playerBankPosx-=RaceEngine.PLAYER_BANK_SPEED;

                    gl.glTranslatef(RaceEngine.playerBankPosx,0f,0f);
                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(0.0f,0.25f,0.0f);

                }else
                {
                    gl.glTranslatef(RaceEngine.playerBankPosx,0f,0f);
                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(0.0f,0.0f,0.0f);
                }

                //carga del sprite del movimiento
               /* gl.glMatrixMode(GL10.GL_TEXTURE); //le pasamos las texturas (sprite)
                gl.glLoadIdentity(); //cargamos
                gl.glTranslatef(0.75f,0.0f,0.0f);
                goodGuyBankFrames+=1;*/

                player1.draw(gl);
                gl.glPopMatrix();
                gl.glLoadIdentity();


                break;

            case RaceEngine.PLAYER_BANK_RIGHT_1:

                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glPushMatrix();
                gl.glScalef(.25f,.25f,1f);

                if(goodGuyBankFrames<RaceEngine.PLAYER_FRAMES_BETWEEN_ANI && RaceEngine.playerBankPosx<3)
                {
                    //movimiento de la figura
                    RaceEngine.playerBankPosx +=RaceEngine.PLAYER_BANK_SPEED;
                    gl.glTranslatef(RaceEngine.playerBankPosx,0f,0f);
                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(0.25f,0.0f,0.0f);
                    goodGuyBankFrames+=1;

                }else if(goodGuyBankFrames >=RaceEngine.PLAYER_FRAMES_BETWEEN_ANI && RaceEngine.playerBankPosx<3)
                {
                    RaceEngine.playerBankPosx+=RaceEngine.PLAYER_BANK_SPEED;

                    gl.glTranslatef(RaceEngine.playerBankPosx,0f,0f);
                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(0.50f,0.0f,0.0f);

                }else
                {
                    gl.glTranslatef(RaceEngine.playerBankPosx,0f,0f);
                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(0.0f,0.0f,0.0f);
                }

                player1.draw(gl);
                gl.glPopMatrix();
                gl.glLoadIdentity();


                break;
            case RaceEngine.PLAYER_RELEASE:

                //cargamos el personaje y lo ajustamos al tamaño
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glPushMatrix();
                gl.glScalef(.25f, .25f, 1f); //75% de su tamaño original
                gl.glTranslatef(RaceEngine.playerBankPosx, 0f, 0f);//movemos al sprite en el eje X para buscar su dibujo

                //volvemos a la posición original ya que no hacemos nada
                gl.glMatrixMode(GL10.GL_TEXTURE);
                gl.glLoadIdentity();
                gl.glTranslatef(0.0f,0.0f, 0.0f);

                player1.draw(gl);
                gl.glPopMatrix();
                gl.glLoadIdentity();
                goodGuyBankFrames+=1;

                break;
            default:
                //cargamos el personaje y lo ajustamos al tamaño
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glPushMatrix();
                gl.glScalef(.25f, .25f, 1f); //75% de su tamaño original
                gl.glTranslatef(RaceEngine.playerBankPosx, 0f, 0f);//movemos al sprite en el eje X para buscar su dibujo

                //volvemos a la posición original ya que no hacemos nada
                gl.glMatrixMode(GL10.GL_TEXTURE);
                gl.glLoadIdentity();
                gl.glTranslatef(0.0f,0.0f, 0.0f);

                player1.draw(gl);
                gl.glPopMatrix();
                gl.glLoadIdentity();

                break;
        }
    }
}
