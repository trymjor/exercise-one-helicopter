package no.ntnu.helipeli.helicopter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Helicopter{
    public Vector2 position;
    private Animation[] anim;
    private int anim_state;
    private boolean movingRight;
    private int heliId;
    private int x_speed = 3;
    private int y_speed = 3;

    private static final float EASING_AMOUNT = (float) 0.02;
    private static final float FRAME_LENGTH = (float)0.1;
    public static final int HELI_WIDTH = 180;
    public static final int HELI_HEIGHT = 65;

    public Helicopter(float x, float y, int heliId){
        position = new Vector2(x, y);
        movingRight = false;
        this.heliId = heliId;

        anim = new Animation[2];
        anim_state = 0;
        TextureRegion[][] animSpriteSheet = TextureRegion.split(new Texture("HelicopterSpriteSheet.png"), 180, 65);
        anim[anim_state] = new Animation(FRAME_LENGTH, animSpriteSheet[0]);
    }

    /**
     * When the mouse button left is pressed the helicopter move smoothly towards the mouse position
     * @param mousePos the position of the mouse on the gamescreen
     */
    public void update(Vector2 mousePos){
        float x_dis = mousePos.x - (this.position.x + this.HELI_WIDTH/2 );
        float y_dis = mousePos.y - (this.position.y + this.HELI_HEIGHT/2 );

        if(x_dis > 0){
            this.movingRight = true;
        }
        else if(x_dis <0){
            this.movingRight = false;
        }

        System.out.println(this.position);
        this.position.x += x_dis * EASING_AMOUNT;
        this.position.y += y_dis * EASING_AMOUNT;

        // Check that helicopter is on screen
        if (this.position.x < 0) this.position.x = 0;
        if (this.position.y < 0) this.position.y = 0;
        if (this.position.x + this.HELI_WIDTH >= Gdx.graphics.getWidth()) this.position.x = Gdx.graphics.getWidth() - this.HELI_WIDTH;
        if (this.position.y + this.HELI_HEIGHT >= Gdx.graphics.getHeight()) this.position.y = Gdx.graphics.getHeight() - this.HELI_HEIGHT;
    }


    /**
     * Move the helicopter around randomly
     */
    public void randomUpdate(){
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

    /**
     * Render the helicopter on the screen
     * @param batch
     * @param stateTime
     */
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

    public void bounceOnCollision(){
        x_speed *= -1;
        y_speed *= -1;

        this.position.x += x_speed;
        this.position.y += y_speed;
    }

    public int getHeliId(){
        return heliId;
    }
}
