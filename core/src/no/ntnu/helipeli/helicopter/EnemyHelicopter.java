package no.ntnu.helipeli.helicopter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;


/**
 * Tried making an enemy helicopter to attack main helicopter.
 * WORK IN PROGRESS.
 */
public class EnemyHelicopter {
    public Vector2 position;
    private Animation[] anim;
    private int anim_state;
    boolean movingRight;
    private int x_speed;
    private int y_speed;

    private static final float FRAME_LENGTH = (float)0.1;
    public static final int HELI_WIDTH = 90;
    public static final int HELI_HEIGHT = 32;


    public EnemyHelicopter(float x, float y){
        position = new Vector2(x, y);
        anim = new Animation[4];
        anim_state = 0;
        movingRight = true;

        TextureRegion[][] animSpriteSheet = TextureRegion.split(new Texture("EnemyHelicopterSpriteSheet"), 423, 150);
        anim[anim_state] = new Animation(FRAME_LENGTH, animSpriteSheet[0]);
    }

    public void update(){
        // Check that helicopter is on screen
        if (this.position.x < 0 || this.position.x + this.HELI_WIDTH >= Gdx.graphics.getWidth()) this.x_speed *= -1;
        if (this.position.y < 0 || this.position.y + this.HELI_HEIGHT >= Gdx.graphics.getHeight()) this.y_speed *= -1;
        if(this.x_speed > 0){
            movingRight = true;
        }else{
            movingRight = false;
        }
        this.position.x += x_speed;
        this.position.y += y_speed;
    }

    public void render (SpriteBatch batch, float stateTime){
        TextureRegion currentFrame = (TextureRegion) anim[anim_state].getKeyFrame(stateTime, true);
        if(movingRight && !currentFrame.isFlipX()){
            currentFrame.flip(true, false);
        }
        if(!movingRight && currentFrame.isFlipX()){
            currentFrame.flip(true, false);
        }
        batch.draw(currentFrame, position.x, position.y, HELI_WIDTH, HELI_HEIGHT);
    }
}
