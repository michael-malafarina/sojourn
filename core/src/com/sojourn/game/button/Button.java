package com.sojourn.game.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.sojourn.game.Sojourn;
import com.sojourn.game.display.Alignment;
import com.sojourn.game.display.Display;
import com.sojourn.game.display.Text;



public class Button implements InputProcessor
{
    protected final Sojourn game;
    private Texture image;
    private Rectangle box;

    private String label;

    private Texture buttonBase;
    private Texture buttonMouseover;

    protected ButtonEvent clickEvent;
    protected ButtonEvent mouseoverEvent;

    public Button(final Sojourn game)
    {
        this.game = game;
        buttonBase = new Texture(Gdx.files.internal("ui/button/button.png"));
        buttonMouseover = new Texture(Gdx.files.internal("ui/button/buttonOver.png"));
        box = new Rectangle(0, 0, Display.WIDTH * .15f, Display.HEIGHT * .15f * .25f);

        image = buttonBase;
        label = "";
        Gdx.input.setInputProcessor(this);

//        clickEvent = () -> System.out.println("Clicked");
//        mouseoverEvent = () -> System.out.println("Mouseover");

    }

    public void set(int x, int y, int w, int h) {
        box.set(x, y, w, h);
    }

    public void setClickEvent(ButtonEvent clickEvent)
    {
        this.clickEvent = clickEvent;
    }

    public void setMouseoverEvent(ButtonEvent mouseoverEvent)
    {
        this.mouseoverEvent = mouseoverEvent;
    }

    public void setPosition(int x, int y) {
        box.setPosition(x, y);
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public void update() {
       // execute(() -> System.out.println("Hello, World!"));

    }

    public void render() {
        Display.draw(image, box.x, box.y, box.width, box.height);
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

        if(box.contains(tp.x, tp.y)) {
            clicked(clickEvent);
            return true;
        }
        return false;
    }

    protected void clicked(ButtonEvent f)    {
        if(f != null ) {
            f.activate();
//            System.out.println("clicked " + label);
        }
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
    public boolean mouseMoved(int screenX, int screenY)
    {
        Vector3 tp = new Vector3();
        Display.getHUDCamera().unproject(tp.set(screenX, screenY, 0));

        if(box.contains(tp.x, tp.y)) {
            image = buttonMouseover;
            mouseover(mouseoverEvent);
            return true;
        }
        else {
            image = buttonBase;
            return false;
        }
    }

    protected void mouseover(ButtonEvent f) {
        if(f != null ) {
            f.activate();
        }
    };

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}