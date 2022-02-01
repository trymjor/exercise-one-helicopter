package no.ntnu.helipeli.tasks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.helipeli.HeliPeli;
import no.ntnu.helipeli.mainmenu.MainMenu;
import no.ntnu.helipeli.helicopter.Helicopter;


public class GameScreenTaskTwo extends ScreenAdapter {

    HeliPeli game;
    Texture background;

    Helicopter helicopter;
    Vector2 mousePos;
    BitmapFont font;

    float stateTime;

    public GameScreenTaskTwo(HeliPeli game){
        this.stateTime = 0;
        this.game = game;
        this.background = new Texture("city.png");

        this.helicopter = new Helicopter(75, 75, 1);

        this.mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        stateTime += delta;

        mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());

        game.batch.begin();

        game.batch.draw(background, 0, 0, 480, 720);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isTouched()){
            mousePos.x = Gdx.input.getX();
            mousePos.y = HeliPeli.HEIGHT - Gdx.input.getY();
            helicopter.update(mousePos);
        }

        helicopter.render(game.batch, stateTime);
        String position = helicopter.position.toString();
        font.setColor(Color.BLACK);
        font.draw(game.batch, position, 330, 700);

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