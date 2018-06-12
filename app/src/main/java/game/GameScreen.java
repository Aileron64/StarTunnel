package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Color;

import framework.Game;
import framework.Graphics;
import framework.Input.TouchEvent;
import framework.Pixmap;
import framework.Screen;
import android.util.Log;

public class GameScreen extends Screen
{
    enum GameState
    {
        Ready,
        Running,
        Paused,
        GameOver
    }

    List<Enemy> enemies = new ArrayList<>();
    List<Explosion> explosions = new ArrayList<>();

    Player player = new Player();
    Random rand = new Random();

    float spawnTimer = 0;
    float spawnDelay = 1.5f;
    final float spawnRateIncrease = 0.01f;
    final float minSpawnDelay = 0.1f;

    GameState state = GameState.Ready;

    int gameScore = 0;
    int oldScore = 0;
    String score = "0";

    float gameOverTimer = 0;

    int lives = 5;

    // screen = 480 * 320

    public GameScreen(Game game)
    {
        super(game);

        //if(Settings.soundEnabled)
        //    Assets.music.play(0.7f);


        Log.i("Debug", "Game Scene Started");
    }

    @Override
    public void update(float deltaTime)
    {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();


        if(state == GameState.Ready)
            updateReady(touchEvents);
        if(state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if(state == GameState.Paused)
            updatePaused(touchEvents);
        if(state == GameState.GameOver)
            updateGameOver(touchEvents, deltaTime);
    }
    
    private void updateReady(List<TouchEvent> touchEvents)
    {
        if(touchEvents.size() > 0)
            state = GameState.Running;
    }
    
    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime)
    {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++)
        {
            TouchEvent event = touchEvents.get(i);

            if(event.type == TouchEvent.TOUCH_UP)
            {
                if(event.x < 64 && event.y < 64)
                {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    state = GameState.Paused;
                    return;
                }
            }

            if(event.type == TouchEvent.TOUCH_DOWN
                    || event.type == TouchEvent.TOUCH_DRAGGED)
            {
                if(event.x > 300)
                {
                    gameScore++;
                    player.xVel = 2;
                }

                if(event.x < 200)
                {
                    gameScore++;
                    player.xVel = -2;
                }
            }
            else
            {
                player.xVel = 0;
            }

        }

        UpdateObjects(deltaTime);


        spawnTimer += deltaTime;

        if(spawnTimer >= spawnDelay)
        {
            spawnTimer = 0;
            Log.i("Debug", "Spawn Enemy");

            if (spawnDelay > minSpawnDelay)
            {
                spawnDelay -= spawnRateIncrease;
            }

            if(rand.nextInt(2) > 0)
                enemies.add(new Enemy(true, rand.nextInt(200) + 60));
            else
                enemies.add(new Enemy(false, rand.nextInt(200) + 60));
        }


        if(lives <= 0)
        {
            state = GameState.GameOver;
            player.alive = false;
            oldScore = player.score;
            score = "" + oldScore;

            explosions.add(new Explosion(
                    player.xPos, player.yPos, "#FF9730"));

            if(Settings.soundEnabled)
                Assets.trombone.play(1);
        }

    }
    
    private void updatePaused(List<TouchEvent> touchEvents)
    {
        int len = touchEvents.size();

        for(int i = 0; i < len; i++)
        {
            TouchEvent event = touchEvents.get(i);

            if(event.type == TouchEvent.TOUCH_UP)
            {
                if(inBounds(event, 160, 90, 160, 65) )
                {
                    if (Settings.soundEnabled)
                        Assets.click.play(1);
                    state = GameState.Running;
                    return;
                }

                if(inBounds(event, 160, 160, 160, 65) )
                {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
    }
    
    private void updateGameOver(List<TouchEvent> touchEvents, float deltaTime)
    {
        int len = touchEvents.size();

        gameOverTimer += deltaTime;
        UpdateObjects(deltaTime);

        if(touchEvents.size() > 0 && gameOverTimer >= 2)
        {
            if(Settings.soundEnabled)
                Assets.click.play(1);
            game.setScreen(new MainMenuScreen(game));
            return;
        }
    }
    

    @Override
    public void present(float deltaTime)
    {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.background, 0, 0);
        DrawObjects();

        if(state == GameState.Ready) 
            drawReadyUI();
        if(state == GameState.Running)
            drawRunningUI();
        if(state == GameState.Paused)
            drawPausedUI();
        if(state == GameState.GameOver)
            drawGameOverUI();

        // Draw hearts
        for (int i = 0; i < lives; i++)
        {
            g.drawPixmap(Assets.heart, 360 + (i * 20), 20);
        }

        score = "" + player.score;
        drawText(g, score, g.getWidth() / 2 - score.length()*20 / 2, 10);

        g.drawPixmap(Assets.pausebtn, 20, 15);

        // Top and bottom lines
        g.drawLine(0, player.TOP_BORDER, g.getWidth(),
                player.TOP_BORDER, Color.WHITE);

        g.drawLine(0, player.BOTTOM_BORDER, g.getWidth(),
                player.BOTTOM_BORDER, Color.WHITE);
    }

    private void UpdateObjects(float deltaTime)
    {
        player.Update();

        for (int i = enemies.size() - 1; i >= 0; i--)
        {
            enemies.get(i).Update();

            int playerSize = 25;
            int enemyWidth = 15;
            int enemyHight = 8;

            if(enemies.get(i).xPos < player.xPos + playerSize
            && enemies.get(i).xPos + enemyWidth > player.xPos
            && enemies.get(i).yPos < player.yPos + playerSize
            && enemies.get(i).yPos + enemyHight > player.yPos
            && player.alive)
            {
                Log.i("Debug", "Hit Enemy");


                explosions.add(new Explosion(
                        enemies.get(i).xPos, enemies.get(i).yPos, "#FF4C4C"));
                enemies.remove(i);

                lives--;
                break;
            }


            if(enemies.get(i).xPos > 600 ||
                    enemies.get(i).xPos < -200)
                enemies.remove(i);
        }

        for (int i = explosions.size() - 1; i >= 0; i--)
        {
            explosions.get(i).Update(deltaTime);

            if(explosions.get(i).timer <= 0)
                explosions.remove(i);
        }
    }

    private void DrawObjects()
    {
        Graphics g = game.getGraphics();
        player.Draw(g);

        for (int i = enemies.size() - 1; i >= 0; i--)
        {
            enemies.get(i).Draw(g);
        }

        for (int i = explosions.size() - 1; i >= 0; i--)
        {
            explosions.get(i).Draw(g);

        }
    }


    
    private void drawReadyUI()
    {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.ready, 125, 70);
    }
    
    private void drawRunningUI()
    {
        Graphics g = game.getGraphics();
    }
    
    private void drawPausedUI()
    {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.pause, g.getWidth() / 2 - 80, 100);

        //Debug.DrawSquare(g, 160, 90, 160, 65);
        //Debug.DrawSquare(g, 160, 160, 160, 65);
    }

    private void drawGameOverUI()
    {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.gameOver, g.getWidth() / 2 - 98 , 60);

    }
    
    public void drawText(Graphics g, String line, int x, int y)
    {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
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
    public void pause()
    {
        if(state == GameState.Running)
            state = GameState.Paused;
        
        if(lives <= 0)
        {
            Settings.addScore(player.score);
            Settings.save(game.getFileIO());
        }
    }

    @Override
    public void resume() { }

    @Override
    public void dispose() { }
}