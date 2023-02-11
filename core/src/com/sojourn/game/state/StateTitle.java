package com.sojourn.game.state;

import com.badlogic.gdx.utils.ScreenUtils;
import com.sojourn.game.Sojourn;
import com.sojourn.game.button.Button;
import com.sojourn.game.button.StartButton;
import com.sojourn.game.display.Alignment;
import com.sojourn.game.display.Display;
import com.sojourn.game.display.Text;

public class StateTitle extends State {
    static Button test;

    public StateTitle(final Sojourn game)
    {
        super(game);
        test = new StartButton(game);
        test.set(10, 10, 200, 60);
        test.setLabel("Example Button");
    }

    @Override
    public void update(float delta)
    {
        test.update();
    }
    @Override
    public void renderBackground(float delta)
    {
        ScreenUtils.clear(.1f, .1f, .6f, 1);
    }
    @Override
    public void renderGameplay(float delta)
    {

    }

    @Override
    public void renderHud(float delta)
    {
        Text.setAlignment(Alignment.LEFT, Alignment.BOTTOM);

        Text.draw("Title", 5, Display.HEIGHT-5);
        test.render();
    }



}
