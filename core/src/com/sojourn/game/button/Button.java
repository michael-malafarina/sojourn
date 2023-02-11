package com.sojourn.game.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.sojourn.game.display.Alignment;
import com.sojourn.game.display.Display;
import com.sojourn.game.display.Text;

public class Button implements InputProcessor
{
    private Texture image;
    private Rectangle box;

    private String label;

    private Texture buttonBase;
    private Texture buttonMouseover;

    public Button()
    {
        buttonBase = new Texture(Gdx.files.internal("box.png"));
        buttonMouseover = new Texture(Gdx.files.internal("droplet.png"));
        box = new Rectangle(50, 50, 100, 40);
        image = buttonBase;
        label = "";
        Gdx.input.setInputProcessor(this);
    }

    public void set(int x, int y, int w, int h)
    {
        box.set(x, y, w, h);
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public void update()
    {

    }

    public void render()
    {
        Display.draw(image, box.x, box.y, 200, 60);
        Text.setAlignment(Alignment.CENTER, Alignment.CENTER);
        Text.draw(label, box.x+box.width/2, box.y+box.height/2);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        Vector3 tp = new Vector3();
        Display.getHUDCamera().unproject(tp.set(screenX, screenY, 0));
        if(box.contains(tp.x, tp.y))
        {
            System.out.println("Click");
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector3 tp = new Vector3();
        Display.getHUDCamera().unproject(tp.set(screenX, screenY, 0));

        if(box.contains(tp.x, tp.y))
        {
            image = buttonMouseover;
            return true;
        }
        else
        {
            image = buttonBase;
            return false;

        }
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}