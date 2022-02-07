package no.ntnu.helipeli.tasks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import no.ntnu.helipeli.HeliPeli;
import no.ntnu.helipeli.mainmenu.MainMenu;
import no.ntnu.helipeli.helicopter.Helicopter;

public class GameScreenTaskOne extends ScreenAdapter {
    HeliPeli game;
    Texture background;

    Helicopter helicopter;

    float stateTime;

    public GameScreenTaskOne(HeliPeli game){
        this.stateTime = 0;
        this.game = game;
        this.background = new Texture("city.png");

        this.helicopter = new Helicopter(75, 75, 1, false);
    }

    @Override
    public void render(float delta) {
        stateTime += delta;

        game.batch.begin();
        game.batch.draw(background, 0, 0, 480, 720);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        helicopter.randomUpdate();
        helicopter.render(game.batch, stateTime);
        game.batch.end();

        // Go to MainMenu if backspace is pressed
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            this.dispose();
            game.setScreen(new MainMenu(this.game));
        }
        // Exit game by pressing backspace key
        if(Gdx.input.isKeyPressed(Input.Keys.DEL)){
            Gdx.app.exit();
        }
    }
}
