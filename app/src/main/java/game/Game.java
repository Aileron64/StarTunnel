package game;

import framework.Screen;
import framework.impl.AndroidGame;

public class Game extends AndroidGame
{
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this); 
    }
}