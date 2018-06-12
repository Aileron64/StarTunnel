package game;

import java.util.List;

import framework.Game;
import framework.Graphics;
import framework.Input.TouchEvent;
import framework.Screen;

public class MainMenuScreen extends Screen
{
    boolean debugMode = true;

    public MainMenuScreen(Game game)
    {
        super(game);               
    }   

    @Override
    public void update(float deltaTime)
    {
        Graphics g = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();       
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP)
            {
                if(inBounds(event, g.getWidth() - 80, g.getHeight() - 80, 55, 55))
                {
                    //Log.i("Debug", "Audio Button Clicked");

                    Settings.soundEnabled = !Settings.soundEnabled;

                    if(Settings.soundEnabled)
                    {
                        Assets.click.play(1);
                        Assets.music.play();
                    }
                    else
                        Assets.music.stop();

                }
                if(inBounds(event, 45, 170, 160, 60) )
                {
                    game.setScreen(new GameScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }

                if(inBounds(event, 45, 240, 160, 50) )
                {
                    game.setScreen(new HighscoreScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }

//                if(inBounds(event, 64, 220 + 84, 192, 42) )
//                {
//                    game.setScreen(new HelpScreen(game));
//                    if(Settings.soundEnabled)
//                        Assets.click.play(1);
//                    return;
//                }
            }
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
    public void present(float deltaTime)
    {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.logo, 32, 20);
        g.drawPixmap(Assets.mainMenu, 30, 160);

        //Debug.DrawSquare(g, g.getWidth() - 80, g.getHeight() - 80, 55, 55);

        //Debug.DrawSquare(g, 45, 170, 160, 60);
        //Debug.DrawSquare(g, 45, 240, 160, 50);


        if(Settings.soundEnabled)
            g.drawPixmap(Assets.mute, g.getWidth() - 80, g.getHeight() - 80);
        else
            g.drawPixmap(Assets.unmute, g.getWidth() - 80, g.getHeight() - 80);
    }

    @Override
    public void pause() {        
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
