package no.ntnu.helipeli.tasks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import no.ntnu.helipeli.HeliPeli;
import no.ntnu.helipeli.mainmenu.MainMenu;
import no.ntnu.helipeli.helicopter.Helicopter;

public class GameScreenTaskThree extends ScreenAdapter {

        HeliPeli game;
        Texture background;
        BitmapFont font;

        Array<Helicopter> helicopterList = new Array<>();
        float stateTime;

    public GameScreenTaskThree(HeliPeli game){
            this.stateTime = 0;
            this.game = game;
            this.background = new Texture("city.png");

            for (int i = 0; i < 3; i++) {
                Helicopter heli = new Helicopter(Gdx.graphics.getWidth() / 2 - Helicopter.HELI_WIDTH / 2, 300 * i, i, true);
                helicopterList.add(heli);
            }

            font = new BitmapFont();
        }

        @Override
        public void render(float delta) {
            stateTime += delta;

            game.batch.begin();
            game.batch.draw(background, 0, 0, 480, 720);
            Gdx.gl.glClearColor(0,0,0,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            for(Helicopter heli : helicopterList){
                heli.randomUpdate();
            }

            for(int i = 0; i < helicopterList.size; i++){
                Helicopter heli1 = helicopterList.get(i);
                Rectangle heli1_rectangle = new Rectangle(heli1.position.x, heli1.position.y, heli1.HELI_WIDTH, heli1.HELI_HEIGHT);
                for (Helicopter heli2 : helicopterList){
                    Rectangle heli2_rectangle = new Rectangle(heli2.position.x, heli2.position.y, heli2.HELI_WIDTH, heli2.HELI_HEIGHT);
                    if(heli1.getHeliId() != heli2.getHeliId() && Intersector.overlaps(heli1_rectangle, heli2_rectangle)){
                        heli1.bounceOnCollision();
                        heli2.bounceOnCollision();
                    }
                }
            }

            for(Helicopter heli : helicopterList){
                heli.render(game.batch, stateTime);
            }

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
