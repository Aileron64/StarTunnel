package game;

import android.graphics.Color;
import android.util.Log;

import java.util.Random;

import framework.Graphics;

/**
 * Created by Alex on 2016-04-19.
 */
public class Explosion
{
    int xOrigin;
    int yOrigin;

    final int NUM = 100;

    float[] x = new float[NUM];
    float[] y = new float[NUM];

    float[] xVel = new float[NUM];
    float[] yVel = new float[NUM];

    public float timer = 1;
    float[] lifeTime = new float[NUM];
    String color;

    Random r = new Random();

    public Explosion(int _x, int _y, String _color)
    {
        for(int i = 0; i < NUM; i++)
        {
            x[i] = _x;
            y[i] = _y;

            //
            //

            //Circle effect
            xVel[i] = (float)Math.sin((6.28 / 10) * i + i) * (0.2f * (i / 10));
            yVel[i] = (float)Math.cos((6.28 / 10) * i + i) * (0.2f * (i / 10));

            //xVel[i] = (float)Math.sin(r.nextDouble() * 6.28) * 2;
            //yVel[i] = (float)Math.cos(r.nextDouble() * 6.28) * 2;



            lifeTime[i] = r.nextFloat();
        }

        if(Settings.soundEnabled)
            Assets.bump.play(1);

        color = _color;
    }

    public void Update(float time)
    {
        for(int i = 0; i < NUM; i++)
        {
            x[i] += xVel[i];
            y[i] += yVel[i];
            lifeTime[i] -= time;
        }

        timer -= time;
    }

    public void Draw(Graphics g)
    {
        for(int i = 0; i < NUM; i++)
        {
            if(lifeTime[i] > 0)
                g.drawPixel((int)x[i], (int)y[i], Color.parseColor(color));
        }
    }


}
