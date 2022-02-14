package no.ntnu.helipeli.tasks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import no.ntnu.helipeli.Achievements;
import no.ntnu.helipeli.HeliPeli;
import no.ntnu.helipeli.mainmenu.MainMenu;
import no.ntnu.helipeli.pong.PongBall;
import no.ntnu.helipeli.pong.PongPadel;
import no.ntnu.helipeli.pong.PongScoreboard;

/**
 * INSTEAD OF MAKING THE GAME GO TO
 */
public class GameScreenTaskFour extends ScreenAdapter {
    HeliPeli game;

    float stateTime;

    private PongBall pongBall;
    private PongPadel playerOnePadel;
    private PongPadel playerTwoPadel;
    private PongScoreboard pongScoreboard;
    private boolean gameOver;


    public GameScreenTaskFour(HeliPeli game){
        this.stateTime = 0;
        this.game = game;
        this.pongBall = new PongBall();
        this.playerOnePadel = new PongPadel(1);
        this.playerTwoPadel = new PongPadel(2);
        this.pongScoreboard = new PongScoreboard();
        this.gameOver = false;
        pongBall.addObserver(game.achievements);
        pongScoreboard.addObserver(game.achievements);
    }

    @Override
    public void render(float delta) {
        stateTime += delta;

        game.batch.begin();

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Controls - Player1: a and d || Player2: arrow left and arrow right
        if(Gdx.input.isKeyPressed(Input.Keys.A)) playerOnePadel.movePadelLeft();
        else if(Gdx.input.isKeyPressed(Input.Keys.D)) playerOnePadel.movePadelRight();
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) playerTwoPadel.movePadelLeft();
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) playerTwoPadel.movePadelRight();

        pongBall.update(stateTime);
        checkForCollision();

        // Draw ball on screen
        pongBall.render(game.batch);

        // Draw padels on screen
        playerOnePadel.render(game.batch);
        playerTwoPadel.render(game.batch);

        // Draw the score on the Screen
        pongScoreboard.render(game.batch);

        // Checks if a goal is scored
        if(pongBall.position.y > Gdx.graphics.getHeight()){
            gameOver = pongScoreboard.updatePlayerScore(1);
            pongBall.respawnBall(stateTime);
        }
        else if(pongBall.position.y < 0){
            gameOver = pongScoreboard.updatePlayerScore(2);
            pongBall.respawnBall(stateTime);
        }

        game.batch.end();

        // If one player has scored 4 goals, quit the game and go back to main menu screen.
        if(gameOver){
            gameOver();
        }

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

    /**
     * Checks if the pong ball has collided with one of the pong padels.
     */
    private void checkForCollision(){
        Rectangle pongBallRect = new Rectangle(pongBall.position.x, pongBall.position.y, pongBall.BALL_SIZE, pongBall.BALL_SIZE);
        Rectangle player1Rect = new Rectangle(playerOnePadel.position.x, playerOnePadel.position.y, playerOnePadel.PADEL_WIDTH, playerOnePadel.PADEL_HEIGHT);
        Rectangle player2Rect = new Rectangle(playerTwoPadel.position.x, playerTwoPadel.position.y, playerTwoPadel.PADEL_WIDTH, playerTwoPadel.PADEL_HEIGHT);
        if(Intersector.overlaps(player1Rect, pongBallRect) || Intersector.overlaps(player2Rect, pongBallRect)){
            pongBall.changeDirection();
        }
    }

    /**
     * Go back to main menu when a player wins
     */
    private void gameOver(){
        game.setScreen(new MainMenu(this.game));
        this.dispose();
    }
}
