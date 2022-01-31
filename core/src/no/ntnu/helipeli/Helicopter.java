package no.ntnu.helipeli;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Array;
import java.util.Random;

public class Helicopter{
    public Vector2 position;
    private Animation[] anim;
    private int anim_state;
    boolean movingRight;
    private int heliId;

    private static final float EASING_AMOUNT = (float) 0.02;
    private static final float FRAME_LENGTH = (float)0.1;
    public static final int HELI_WIDTH = 180;
    public static final int HELI_HEIGHT = 65;

    public Helicopter(float x, float y, int heliId){
        position = new Vector2(x, y);
        anim = new Animation[2];
        anim_state = 0;
        movingRight = false;
        this.heliId = heliId;

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
        float angle = calculateHelicopterAngle(mousePos, x_dis, y_dis);

        if(angle > -90 && angle < 90){
            this.movingRight = true;
        }
        else if(angle>90 || angle<-90){
            this.movingRight = false;
        }

        System.out.println(angle);
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
        Random rand = new Random();
        int upperbound = 5;
        int step_x =  rand.nextInt(upperbound+upperbound+1)-upperbound;
        int step_y = rand.nextInt(upperbound+upperbound+1)-upperbound;

        this.position.x += step_x;
        this.position.y += step_y;

        // Check that helicopter is on screen
        if (this.position.x < 0) this.position.x = 0;
        if (this.position.y < 0) this.position.y = 0;
        if (this.position.x + this.HELI_WIDTH >= Gdx.graphics.getWidth()) this.position.x = Gdx.graphics.getWidth() - this.HELI_WIDTH;
        if (this.position.y + this.HELI_HEIGHT >= Gdx.graphics.getHeight()) this.position.y = Gdx.graphics.getHeight() - this.HELI_HEIGHT;
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

    /**
     *
     * @param mousePos
     * @param x_dis
     * @param y_dis
     * @return
     */
    private float calculateHelicopterAngle(Vector2 mousePos, float x_dis, float y_dis){
        mousePos.x = Gdx.input.getX();
        mousePos.y = HeliPeli.HEIGHT - Gdx.input.getY();
        float angle = (float) Math.toDegrees(Math.atan2(y_dis, x_dis));

        return angle;
    }
}
