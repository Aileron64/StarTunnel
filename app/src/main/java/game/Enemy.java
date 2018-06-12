package game;

import framework.Graphics;

/**
 * Created by Alex on 2016-04-17.
 */
public class Enemy
{

    public int xPos;
    public int yPos;
    public int xVel;


    final int SPEED = 2;
    final int TOP_BORDER = 260;
    final int BOTTOM_BORDER = 40;
    final int LEFT_BORDER = 80;
    final int RIGHT_BORDER = 380;

    public Enemy(boolean direction, int height)
    {
        yPos = height;


        if(direction)
        {
            xPos = 500;
            xVel = -SPEED;
        }
        else
        {
            xPos = -50;
            xVel = SPEED;
        }


    }

    public void Update()
    {
        xPos += xVel;


    }

    public void Draw(Graphics g)
    {
        g.drawPixmap(Assets.enemy, xPos, yPos);
    }
}
