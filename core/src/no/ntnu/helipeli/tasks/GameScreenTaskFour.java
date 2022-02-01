package no.ntnu.helipeli.tasks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import no.ntnu.helipeli.HeliPeli;
import no.ntnu.helipeli.mainmenu.MainMenu;
import no.ntnu.helipeli.pong.PongBall;
import no.ntnu.helipeli.pong.PongPadel;

/**
 * INSTEAD OF MAKING THE GAME GO TO
 */
public class GameScreenTaskFour extends ScreenAdapter {
    HeliPeli game;

    BitmapFont font;

    float stateTime;

    PongBall pongBall;
    PongPadel playerOnePadel;
    PongPadel playerTwoPadel;
    private int playerOneScore;
    private int playerTwoScore;
    private Texture playerOneScoreTexture;
    private Texture playerTwoScoreTexture;


    public GameScreenTaskFour(HeliPeli game){
        this.stateTime = 0;
        this.game = game;
            this.pongBall = new PongBall();
            this.playerOnePadel = new PongPadel(1);
            this.playerTwoPadel = new PongPadel(2);

            this.playerOneScoreTexture = new Texture("Pong/Score_Zero.png");
            this.playerTwoScoreTexture = new Texture("Pong/Score_Zero.png");
        font = new BitmapFont();
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

        pongBall.update();
        checkForCollision();

        pongBall.render(game.batch);
        playerOnePadel.render(game.batch);
        playerTwoPadel.render(game.batch);

        // Checks if a goal is scored
        if(pongBall.position.y > Gdx.graphics.getHeight()){
            playerOneScore += 1;
            updatePlayerScore(1);
            pongBall.respawnBall();
        }
        else if(pongBall.position.y < 0){
            playerTwoScore += 1;
            updatePlayerScore(2);
            pongBall.respawnBall();
        }

        // Draw the score on the Screen
        game.batch.draw(playerOneScoreTexture, 50, HeliPeli.HEIGHT/2-50, 32, 44);
        game.batch.draw(playerTwoScoreTexture, 50, HeliPeli.HEIGHT/2+50, 32, 44);

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
     * Update the players score
     * @param player
     */
    private void updatePlayerScore(int player){
        if(player == 1){
            if(playerOneScore == 1){
                this.playerOneScoreTexture = new Texture("Pong/Score_One.png");
            }
            else if(playerOneScore == 2){
                this.playerOneScoreTexture = new Texture("Pong/Score_Two.png");
            }
            else if(playerOneScore == 3){
                this.playerOneScoreTexture = new Texture("Pong/Score_Three.png");
            }
            else if(playerOneScore == 4){
                updatePlayerWin(player);
            }
        }
        else if(player == 2){
            if(playerTwoScore == 1){
                this.playerTwoScoreTexture = new Texture("Pong/Score_One.png");
            }
            else if(playerTwoScore == 2){
                this.playerTwoScoreTexture = new Texture("Pong/Score_Two.png");
            }
            else if(playerTwoScore == 3){
                this.playerTwoScoreTexture = new Texture("Pong/Score_Three.png");
            }
            else if(playerTwoScore == 4){
                updatePlayerWin(player);
            }
        }
    }

    /**
     * WORK IN PROGRESS.
     * @param player
     */
    private void updatePlayerWin(int player){
        Gdx.app.exit();
    }
}
