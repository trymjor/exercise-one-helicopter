package no.ntnu.helipeli.mainmenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MainMenuButton {
    private Texture currentButtonSprite;
    private Texture buttonActiveSprite;
    private Texture buttonInactiveSprite;
    private int button_x;
    private int button_y;
    private int buttonId;

    public static final int BUTTON_WIDTH = 150;
    public static final int BUTTON_HEIGHT = 80;

    public MainMenuButton(int x, int y, int buttonId){
        this.button_x = x;
        this.button_y = y;
        this.buttonId = buttonId;

        this.buttonActiveSprite = new Texture("mainmenu/task"+this.buttonId+"_active.png");
        this.buttonInactiveSprite = new Texture("mainmenu/task"+this.buttonId+"_inactive.png");
        this.currentButtonSprite = buttonInactiveSprite;
    }

    public void render(SpriteBatch batch){
        batch.draw(this.currentButtonSprite, this.button_x, this.button_y, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    public Rectangle getButtonRectangle(){
        Rectangle buttonRectangle = new Rectangle(this.button_x, this.button_y, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
        return buttonRectangle;
    }

    public void makeActive(){
        this.currentButtonSprite = this.buttonActiveSprite;
    }
    public void makeInactive(){
        this.currentButtonSprite = this.buttonInactiveSprite;
    }

    public int getButtonId(){
        return this.buttonId;
    }
}
