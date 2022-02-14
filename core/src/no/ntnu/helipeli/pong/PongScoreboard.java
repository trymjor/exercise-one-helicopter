package no.ntnu.helipeli.pong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.helipeli.HeliPeli;
import no.ntnu.helipeli.observer.Notifyer;

public class PongScoreboard extends Notifyer {
    private int playerOneScore;
    private int playerTwoScore;
    private Texture playerOneScoreTexture;
    private Texture playerTwoScoreTexture;

    private final int X_POS = 50;
    private final int SCORE_WIDTH = 32;
    private final int SCORE_HEIGHT = 48;

    public PongScoreboard(){
        this.playerOneScore = 0;
        this.playerTwoScore = 0;

        this.playerOneScoreTexture = new Texture("Pong/Score_0.png");
        this.playerTwoScoreTexture = new Texture("Pong/Score_0.png");
    }

    /**
     * Update the players score
     * this shit really needs a rework.. jesus
     * @param player
     * @return gameOver true if one player has scored 4 goals
     */
    public boolean updatePlayerScore(int player){
        boolean gameOver = false;
        if(player == 1){
            playerOneScore++;
            this.playerOneScoreTexture = new Texture("Pong/Score_"+playerOneScore+".png");
            if(playerOneScore == 4){
                if(playerTwoScore == 0){
                    Notify("Easy Stomp", 1);
                }
                else if(playerTwoScore == 3){
                    Notify("Hard Fought Victory", 2);
                }
                gameOver = true;
            }
        }
        else if(player == 2){
            playerTwoScore++;
            this.playerTwoScoreTexture = new Texture("Pong/Score_"+playerTwoScore+".png");
            if(playerTwoScore == 4){
                if(playerOneScore == 0){
                    Notify("Easy Stomp", 1);
                }
                else if(playerOneScore == 3){
                    Notify("Hard Fought Victory", 2);
                }
                gameOver = true;
            }
        }
        return gameOver;
    }

    public void render(SpriteBatch batch){
        batch.draw(playerOneScoreTexture, X_POS, HeliPeli.HEIGHT/2-50, SCORE_WIDTH, SCORE_HEIGHT);
        batch.draw(playerTwoScoreTexture, X_POS, HeliPeli.HEIGHT/2+50, SCORE_WIDTH, SCORE_HEIGHT);
    }
}
