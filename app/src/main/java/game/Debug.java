package game;

import android.graphics.Color;

import framework.Graphics;

/**
 * Created by Alex on 2016-04-19.
 */
public class Debug
{

    // USed to test buttons
    public static void DrawSquare(Graphics g, int x, int y, int width, int height)
    {
        g.drawLine(x, y, x + width, y, Color.WHITE);
        g.drawLine(x + width, y, x + width, y + height, Color.WHITE);
        g.drawLine(x + width, y + height, x, y + height, Color.WHITE);
        g.drawLine(x, y + height, x, y, Color.WHITE);


    }

}
