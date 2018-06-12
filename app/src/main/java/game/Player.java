package game;

import framework.Graphics;

public class Player
{
    public int xPos;
    public int yPos;
    public int xVel;
    public int yVel;

    public int score = 0;
    public boolean alive = true;

    final int SPEED = 3;
    public final int TOP_BORDER = 275;
    public final int BOTTOM_BORDER = 50;
    final int LEFT_BORDER = 0;
    final int RIGHT_BORDER = 455;

    public Player()
    {
        xPos = 228;
        yPos = 220;

        xVel = 0;
        yVel = SPEED;
    }

    public void Update()
    {
        if(alive)
        {
            xPos += xVel;
            yPos += yVel;


            if(yPos >= TOP_BORDER - 25)
            {
                yVel = SPEED * -1;
                score++;

                //if(Settings.soundEnabled)
                //    Assets.bump.play(1);
            }

            if(yPos <= BOTTOM_BORDER)
            {
                yVel = SPEED;
                score++;

                //if(Settings.soundEnabled)
                //    Assets.bump.play(1);
            }

            if(xPos > RIGHT_BORDER)
            {
                xPos = RIGHT_BORDER;
            }

            if(xPos < LEFT_BORDER)
            {
                xPos = LEFT_BORDER;
            }
        }
    }

    public void Draw(Graphics g)
    {
        if(alive)
            g.drawPixmap(Assets.player, xPos, yPos);
    }
}
