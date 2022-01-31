package no.ntnu.helipeli;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import java.awt.Rectangle;

public class MainMenu extends ScreenAdapter {
    HeliPeli game;
    OrthographicCamera camera;
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;

    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 100;
    private static final int PLAY_BUTTON_Y = 2*Gdx.graphics.getHeight()/3;

    private static final int EXIT_BUTTON_WIDTH = 150;
    private static final int EXIT_BUTTON_HEIGHT = 50;
    private static final int EXIT_BUTTON_Y = 1*Gdx.graphics.getHeight()/3;

    public MainMenu(HeliPeli game){
        this.game = game;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0);

        playButtonActive = new Texture("mainmenu/start_active.png");
        exitButtonActive = new Texture("mainmenu/exit_active.png");
        playButtonInactive = new Texture("mainmenu/start_inactive.png");
        exitButtonInactive = new Texture("mainmenu/exit_inactive.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        // Check if cursor is in play button
        int play_button_x = HeliPeli.WIDTH/2 - PLAY_BUTTON_WIDTH/2;
        if(Gdx.input.getX() > play_button_x
                && Gdx.input.getX() < play_button_x+PLAY_BUTTON_WIDTH
                && HeliPeli.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y
                && HeliPeli.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_HEIGHT+PLAY_BUTTON_Y){
            game.batch.draw(playButtonActive, play_button_x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new GameScreen(this.game));
            }
        }else{
            game.batch.draw(playButtonInactive, play_button_x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        // Check if cursor is in exit button
        int exit_button_x = HeliPeli.WIDTH/2 - EXIT_BUTTON_WIDTH/2;
        if(Gdx.input.getX() > exit_button_x
                && Gdx.input.getX() < exit_button_x+EXIT_BUTTON_WIDTH
                && HeliPeli.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y
                && HeliPeli.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_HEIGHT+EXIT_BUTTON_Y){
            game.batch.draw(exitButtonActive, exit_button_x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }else{
            game.batch.draw(exitButtonInactive, exit_button_x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        game.batch.end();
    }
}
