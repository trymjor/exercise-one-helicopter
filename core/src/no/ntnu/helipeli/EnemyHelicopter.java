package no.ntnu.helipeli;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;


public class EnemyHelicopter {
    public Vector2 position;
    private Animation[] anim;
    private int anim_state;
    boolean movingRight;

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
        Random rand = new Random();
        int choice =  rand.nextInt(4);

        if (choice == 0) {
            this.position.x++;
        } else if (choice == 1) {
            this.position.x--;
        } else if (choice == 2) {
            this.position.y++;
        } else {
            this.position.y--;
        }

        if (this.position.x < 0) this.position.x = 0;
        if (this.position.y < 0) this.position.y = 0;
        if (this.position.x + this.HELI_WIDTH >= Gdx.graphics.getWidth()) this.position.x = Gdx.graphics.getWidth() - this.HELI_WIDTH;
        if (this.position.y + this.HELI_HEIGHT >= Gdx.graphics.getHeight()) this.position.y = Gdx.graphics.getHeight() - this.HELI_HEIGHT;
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
