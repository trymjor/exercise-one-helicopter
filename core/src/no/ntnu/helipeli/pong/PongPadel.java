package no.ntnu.helipeli.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.helipeli.HeliPeli;

public class PongPadel {
    public Vector2 position;
    int playerId;
    Texture padelSprite;
    public final int PADEL_WIDTH = 150;
    public final int PADEL_HEIGHT = 30;

    public PongPadel(int playerId){
        this.playerId = playerId;
        if (this.playerId == 1){
            position = new Vector2(Gdx.graphics.getWidth()/2, 50);
            padelSprite = new Texture("Pong/Pong_Player1.png");
        }
        else if(this.playerId == 2){
            position = new Vector2(Gdx.graphics.getWidth()/2, HeliPeli.HEIGHT-50);
            padelSprite = new Texture("Pong/Pong_Player2.png");
        }
    }

    /**
     * Draws the padel.
     * @param batch
     */
    public void render(SpriteBatch batch){
        batch.draw(padelSprite, this.position.x, this.position.y, PADEL_WIDTH, PADEL_HEIGHT);
    }

    /**
     * Moves the padel to the right
     */
    public void movePadelRight(){
        this.position.x += 5;
        if(this.position.x + this.PADEL_WIDTH > Gdx.graphics.getWidth()){
            this.position.x = Gdx.graphics.getWidth() - this.PADEL_WIDTH;
        }
    }

    /**
     * Moves the padel to the left
     */
    public void movePadelLeft(){
        this.position.x -= 5;
        if(this.position.x < 0){
            this.position.x = 0;
        }
    }
}
