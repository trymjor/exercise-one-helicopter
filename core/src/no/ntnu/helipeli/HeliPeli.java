package no.ntnu.helipeli;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.helipeli.mainmenu.MainMenu;

public class HeliPeli extends Game {
    private static HeliPeli game;

    public SpriteBatch batch;
    public static final int WIDTH = 480;
    public static final int HEIGHT = 720;
    public Achievements achievements;
    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MainMenu(this));
        this.achievements = new Achievements();
    }

    @Override
    public void render(){
        super.render();
    }

    public static HeliPeli getInstance(){
        if(game == null){
            game = new HeliPeli();
        }
        return game;
    }
}
