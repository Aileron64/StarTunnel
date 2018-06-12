package game;

import framework.Game;
import framework.Graphics;
import framework.Screen;
import framework.Graphics.PixmapFormat;
import framework.Music;

public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime)
    {
        Graphics g = game.getGraphics();

        Assets.player = g.newPixmap("player.png", PixmapFormat.ARGB4444);
        Assets.enemy = g.newPixmap("red_square.png", PixmapFormat.ARGB4444);
        Assets.heart = g.newPixmap("heart.png", PixmapFormat.ARGB4444);

        Assets.background = g.newPixmap("background.png", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.mainMenu = g.newPixmap("mainmenu.png", PixmapFormat.ARGB4444);
        Assets.back = g.newPixmap("back.png", PixmapFormat.ARGB4444);
        Assets.mute = g.newPixmap("mute.png", PixmapFormat.ARGB4444);
        Assets.unmute = g.newPixmap("unmute.png", PixmapFormat.ARGB4444);
        Assets.pausebtn = g.newPixmap("pause_button.png", PixmapFormat.ARGB4444);

        Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);
        Assets.ready = g.newPixmap("ready.png", PixmapFormat.ARGB4444);
        Assets.pause = g.newPixmap("pausemenu.png", PixmapFormat.ARGB4444);
        Assets.gameOver = g.newPixmap("gameover.png", PixmapFormat.ARGB4444);

        Assets.click = game.getAudio().newSound("menu.wav");
        Assets.explosion = game.getAudio().newSound("explosion.wav");
        Assets.bump = game.getAudio().newSound("whoosh.wav");
        Assets.trombone = game.getAudio().newSound("trombone.mp3");

        Assets.music = game.getAudio().newMusic("music.mp3");
        Assets.music.setLooping(true);
        Assets.music.setVolume(0.6f);
        Assets.music.play();

        Settings.load(game.getFileIO());
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void present(float deltaTime) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}