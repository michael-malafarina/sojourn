package com.sojourn.game.button;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.sojourn.game.Textures;
import com.sojourn.game.display.Display;
import com.sojourn.game.display.Fonts;
import com.sojourn.game.display.HudElement;


public class Button extends HudElement
{
    protected ButtonEvent clickEvent;
    protected ButtonEvent clickEventTwo;
    protected boolean disabled;

    public Button()
    {
        super();
        box = new Rectangle(0, 0, Display.WIDTH * .15f, Display.WIDTH * .15f * .25f);

        setImageBase(Textures.uiButtonBase);
        setImageMouseover(Textures.uiButtonMouseover);

        font = Fonts.title;
        label = "";
        color = Color.WHITE;
    }

    public Button(float x, float y, float w, float h)
    {
        this();
        set(x, y, w, h);
    }

    public void setClickEvent(ButtonEvent clickEvent)
    {
        this.clickEvent = clickEvent;
    }

    public void setClickEventTwo(ButtonEvent clickEvent)
    {
        this.clickEventTwo = clickEvent;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        Vector3 tp = new Vector3();
        Display.getCamera().getHudCamera().unproject(tp.set(screenX, screenY, 0));
        Rectangle alignedBox = new Rectangle(getAlignedX(), getAlignedY(), box.getWidth(), box.getHeight());

        if(alignedBox.contains(tp.x, tp.y) && !disabled) {
            clicked(clickEvent);
            clicked(clickEventTwo);
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

    public void disable()
    {
        disabled = true;
        color = new Color(.3f, .3f, .3f, 1f);
        colorText = new Color(.6f, .6f, .6f, 1f);
    }

    public void enable()
    {
        disabled = false;
        color = Color.WHITE;
        colorText = Color.WHITE;
    }



}