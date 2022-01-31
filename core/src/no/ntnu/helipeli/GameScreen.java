package no.ntnu.helipeli;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class GameScreen extends ScreenAdapter {
    HeliPeli game;
    Helicopter helicopter;
    OrthographicCamera camera;
    Vector2 mousePos;
    BitmapFont font;

    float stateTime;

    public GameScreen(HeliPeli game){
        this.stateTime = 0;
        this.camera = new OrthographicCamera(HeliPeli.WIDTH, HeliPeli.HEIGHT);
        this.game = game;
        this.helicopter = new Helicopter(75, 75 , 1);
        this.mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        stateTime += delta;
        mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.isTouched()){
            mousePos.x = Gdx.input.getX();
            mousePos.y = HeliPeli.HEIGHT - Gdx.input.getY();
            helicopter.update(mousePos);
        }else{
        helicopter.randomUpdate();
        }
        game.batch.begin();
        helicopter.render(game.batch, stateTime);
        String position = helicopter.position.toString();
        font.setColor(Color.BLACK);
        font.draw(game.batch, position, 330, 700);
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.DEL)){
            Gdx.app.exit();
        }
    }
}
