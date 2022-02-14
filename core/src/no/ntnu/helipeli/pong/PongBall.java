package no.ntnu.helipeli.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import no.ntnu.helipeli.observer.Notifyer;

public class PongBall extends Notifyer {
    public Vector2 position;
    public Texture ballSprite;

    private float x_speed = 5;
    private float y_speed = 5;
    private Random random;
    private float respawnTimer;

    public final float BALL_SIZE = 30;


    public PongBall(){
        position = new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        ballSprite = new Texture("Pong/Pong_Ball.png");
        random = new Random();

        boolean randomDirection = random.nextBoolean();
        if(randomDirection){
            y_speed *= -1;
        }
    }

    public void update(float stateTime){
        if(respawnTimer + 3 > stateTime){
            this.position.y = Gdx.graphics.getHeight()/2;
            this.position.x = Gdx.graphics.getWidth()/2;
            return;
        }
        if(x_speed > 0) {
            this.x_speed += 0.01;
        }else{
            this.x_speed -= 0.01;
        }
        if(y_speed > 0) {
            this.y_speed += 0.01;
        }else{
            this.y_speed -= 0.01;
        }

        if(y_speed > 10 || y_speed < -10){
            Notify("The fast - (Reach a 'ball speed' of 10)", 3);
        }
        if(y_speed > 20 || y_speed < -20){
            Notify("Wanna see some real speed??? - (Reach a 'ball speed' of 20)", 4);
        }

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

    public void respawnBall(float stateTime){
        respawnTimer = stateTime;
        boolean randomDirection = random.nextBoolean();
        if(randomDirection){
            this.x_speed = 5;
            this.y_speed = 5;
        }else{
            this.x_speed = -5;
            this.y_speed = -5;
        }
    }
}
