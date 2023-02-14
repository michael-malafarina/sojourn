package com.sojourn.game.state;

import com.badlogic.gdx.utils.ScreenUtils;
import com.sojourn.game.Sojourn;
import com.sojourn.game.Utility;
import com.sojourn.game.display.Display;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.Unit;

import java.util.ArrayList;

public class StateGameplay extends State
{
    ArrayList<Entity> entities;

    public StateGameplay(final Sojourn game)
    {
        super(game);

        entities = new ArrayList<>();

        for(int i = 0; i < 5; i++)
        {
            Unit u = new Unit();
            u.setPosition(0, Utility.random(Display.HEIGHT));
            entities.add(u);
        }
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);
        entities.forEach((n)->n.update(delta));
    }

    @Override
    protected void renderBackground(float delta) {
        ScreenUtils.clear(.02f, .02f, .02f, 1);
    }

    @Override
    protected void renderGameplay(float delta) {
        entities.forEach((n)->n.render());
    }

    @Override
    protected void renderHud(float delta)
    {
        super.renderHud(delta);

    }

    public String toString()
    {
        return "Gameplay";
    }

    @Override
    public boolean scrolled(float amountX, float amountY)
    {
        //super.scrolled(amountX, amountY);
        System.out.println("scrolled: " + amountY);
        Display.cameraZoom(amountY);
        return false;
    }

}
