package no.ntnu.helipeli;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HeliPeli extends Game{
    public SpriteBatch batch;
    public static final int WIDTH = 480;
    public static final int HEIGHT = 720;
    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MainMenu(this));
    }

    @Override
    public void render(){
        super.render();
    }
}
