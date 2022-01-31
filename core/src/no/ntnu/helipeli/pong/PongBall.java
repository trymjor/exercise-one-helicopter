package no.ntnu.helipeli.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class PongBall {
    public Vector2 position;
    public Texture ballSprite;

    private float x_speed = 5;
    private float y_speed = 5;
    public final float BALL_SIZE = 30;

    public PongBall(){
        position = new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        ballSprite = new Texture("Pong/Pong_Ball.png");
        Random random = new Random();

        boolean switchDirection = random.nextBoolean();
        if(switchDirection){
            y_speed *= -1;
        }
    }

    public void update(){
        if (this.position.x < 0 || this.position.x + this.BALL_SIZE >= Gdx.graphics.getWidth()) this.x_speed *= -1;

        this.position.x += x_speed;
        this.position.y += y_speed;
    }

    public void render(SpriteBatch batch){
        batch.draw(ballSprite, this.position.x, this.position.y, BALL_SIZE, BALL_SIZE);
    }

    public void changeDirection(){
        this.y_speed *= -1;
    }

    public void respawnBall(){
        this.position.y = Gdx.graphics.getHeight()/2;
    }
}