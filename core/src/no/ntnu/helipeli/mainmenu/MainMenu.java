package no.ntnu.helipeli.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import no.ntnu.helipeli.HeliPeli;
import no.ntnu.helipeli.tasks.GameScreenTaskFour;
import no.ntnu.helipeli.tasks.GameScreenTaskOne;
import no.ntnu.helipeli.tasks.GameScreenTaskThree;
import no.ntnu.helipeli.tasks.GameScreenTaskTwo;

public class MainMenu extends ScreenAdapter {
    HeliPeli game;

    Array<MainMenuButton> menuButtonList = new Array<>();
    public MainMenu(HeliPeli game){
        this.game = game;
        int buttonsToAdd = 4;
        for (int i = 1; i<buttonsToAdd+1; i++){
            MainMenuButton button = new MainMenuButton(Gdx.graphics.getWidth()/2 - MainMenuButton.BUTTON_WIDTH/2, ((Gdx.graphics.getHeight()-150)-(i*MainMenuButton.BUTTON_HEIGHT)), i);
            menuButtonList.add(button);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        Vector2 mousePos = new Vector2();
        mousePos.x = Gdx.input.getX();
        mousePos.y = HeliPeli.HEIGHT - Gdx.input.getY();

        for(MainMenuButton button : menuButtonList){
            if(button.getButtonRectangle().contains(mousePos.x, mousePos.y)){
                button.makeActive();
                if(Gdx.input.isTouched()){
                    changeScreen(button.getButtonId());
                }
            }else{
                button.makeInactive();
            }
        }

        for(MainMenuButton button : menuButtonList){
            button.render(game.batch);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DEL)){
            Gdx.app.exit();
        }
        game.batch.end();
    }

    private void changeScreen(int screenId){
        this.dispose();
        if(screenId == 1){
            game.setScreen(new GameScreenTaskOne(this.game));
        }
        else if(screenId == 2){
            game.setScreen(new GameScreenTaskTwo(this.game));
        }
        else if(screenId == 3){
            game.setScreen(new GameScreenTaskThree(this.game));
        }
        else if(screenId == 4){
            game.setScreen(new GameScreenTaskFour(this.game));
        }
    }
}
