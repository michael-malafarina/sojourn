package com.sojourn.game.state;

import com.badlogic.gdx.utils.ScreenUtils;
import com.sojourn.game.Sojourn;
import com.sojourn.game.display.Alignment;
import com.sojourn.game.display.Display;
import com.sojourn.game.display.Text;
import com.sojourn.game.entity.Entity;

public class StateGameplay extends State {

    Entity ship;

    public StateGameplay(final Sojourn game) {
        super(game);
        ship = new Entity();
    }

    @Override
    public void update(float delta) {
        ship.update(delta);
    }

    @Override
    protected void renderBackground(float delta) {
        ScreenUtils.clear(.02f, .02f, .02f, 1);
    }

    @Override
    protected void renderGameplay(float delta) {
        ship.render();
    }

    @Override
    protected void renderHud(float delta)
    {
        Text.setAlignment(Alignment.LEFT, Alignment.BOTTOM);
        Text.draw("Gameplay", 5, Display.HEIGHT-5);
    }

}
