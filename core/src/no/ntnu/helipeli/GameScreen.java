package no.ntnu.helipeli;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import no.ntnu.helipeli.helicopter.Helicopter;
import no.ntnu.helipeli.pong.PongBall;
import no.ntnu.helipeli.pong.PongPadel;


public class GameScreen extends ScreenAdapter {
    // Set to 0 to control helicopter with mouse button left, else set to 1 for random bouncing helicopters
    private static final int TASK = 2;

    HeliPeli game;
    Texture background;

    Helicopter helicopter;
    Vector2 mousePos;
    BitmapFont font;

    Array<Helicopter> helicopterList = new Array<>();
    float stateTime;

    PongBall pongBall;
    PongPadel playerOnePadel;
    PongPadel playerTwoPadel;
    private int playerOneScore;
    private int playerTwoScore;

    public GameScreen(HeliPeli game){
        this.stateTime = 0;
        this.game = game;
        this.background = new Texture("city.png");

        if(TASK == 0){
            this.helicopter = new Helicopter(75, 75, 1);
        }

        else if(TASK == 1) {
            for (int i = 0; i < 3; i++) {
                Helicopter heli = new Helicopter(Gdx.graphics.getWidth() / 2 - Helicopter.HELI_WIDTH / 2, 300 * i, i);
                helicopterList.add(heli);
            }
        }

        else if (TASK == 2){
            this.pongBall = new PongBall();
            this.playerOnePadel = new PongPadel(1);
            this.playerTwoPadel = new PongPadel(2);
        }

        else{
            Gdx.app.exit();
        }

        this.mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        stateTime += delta;

        game.batch.begin();
        if(TASK == 0 || TASK == 1) {
            game.batch.draw(background, 0, 0, 480, 720);
        }
        mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Task 1: control the helicopter with the mouse button.
        if(TASK == 0){
            if(Gdx.input.isTouched()){
                mousePos.x = Gdx.input.getX();
                mousePos.y = HeliPeli.HEIGHT - Gdx.input.getY();
                helicopter.update(mousePos);
            }

            helicopter.render(game.batch, stateTime);
            String position = helicopter.position.toString();
            font.setColor(Color.BLACK);
            font.draw(game.batch, position, 330, 700);
        }

        // Task 2: Random helicopters moving around and bouncing.
        else if(TASK == 1){
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
        }

        // Task 3: Pong game
        else if(TASK == 2){
            // Controls - Player1: a and d || Player2: arrow left and arrow right
            if(Gdx.input.isKeyPressed(Input.Keys.A)) playerOnePadel.movePadelLeft();
            else if(Gdx.input.isKeyPressed(Input.Keys.D)) playerOnePadel.movePadelRight();
            else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) playerTwoPadel.movePadelLeft();
            else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) playerTwoPadel.movePadelRight();

            pongBall.update();
            Rectangle pongBallRect = new Rectangle(pongBall.position.x, pongBall.position.y, pongBall.BALL_SIZE, pongBall.BALL_SIZE);
            Rectangle player1Rect = new Rectangle(playerOnePadel.position.x, playerOnePadel.position.y, playerOnePadel.PADEL_WIDTH, playerOnePadel.PADEL_HEIGHT);
            Rectangle player2Rect = new Rectangle(playerTwoPadel.position.x, playerTwoPadel.position.y, playerTwoPadel.PADEL_WIDTH, playerTwoPadel.PADEL_HEIGHT);
            if(Intersector.overlaps(player1Rect, pongBallRect) || Intersector.overlaps(player2Rect, pongBallRect)){
                pongBall.changeDirection();
            }

            pongBall.render(game.batch);
            playerOnePadel.render(game.batch);
            playerTwoPadel.render(game.batch);

            if(pongBall.position.y > Gdx.graphics.getHeight()){
                playerOneScore += 1;
                pongBall.respawnBall();
            }
            else if(pongBall.position.y < 0){
                playerTwoScore += 1;
                pongBall.respawnBall();
            }
            String score = ("" + playerOneScore + " - " + playerTwoScore + "");
            GlyphLayout layout = new GlyphLayout(font, score);

            font.draw(game.batch, layout, (HeliPeli.WIDTH-layout.width)/2, HeliPeli.HEIGHT/2);
        }

        game.batch.end();

        // Close the game if backspace is pressed
        if(Gdx.input.isKeyPressed(Input.Keys.DEL)){
            Gdx.app.exit();
        }
    }
}
