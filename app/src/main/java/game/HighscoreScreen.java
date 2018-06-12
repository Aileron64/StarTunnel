package game;

import java.util.List;

import framework.Game;
import framework.Graphics;
import framework.Screen;
import framework.Input.TouchEvent;

public class HighscoreScreen extends Screen {
    String lines[] = new String[5];

    public HighscoreScreen(Game game)
    {
        super(game);

        for (int i = 0; i < 5; i++) {
            lines[i] = "" + (i + 1) + ". " + Settings.highscores[i];
        }
    }

    @Override
    public void update(float deltaTime)
    {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++)
        {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP)
            {
                if(inBounds(event, 310, 240, 160, 60))
                {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);

                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime)
    {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.background, 0, 0);

        int y = 50;
        for (int i = 0; i < 5; i++)
        {
            drawText(g, lines[i], 40, y);
            y += 50;
        }

        g.drawPixmap(Assets.back, 310, 240);

        //Debug.DrawSquare(g, 310, 240, 160, 60);
    }

    public void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);
    
            if (character == ' ')
            {
                x += 20;
                continue;
            }
    
            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }
    
            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    private boolean inBounds(TouchEvent event, int x, int y, int width, int height)
    {
        if(event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }

    @Override
    public void pause(){ }

    @Override
    public void resume(){ }

    @Override
    public void dispose(){ }
}
