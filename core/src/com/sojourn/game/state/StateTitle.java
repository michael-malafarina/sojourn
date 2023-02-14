package com.sojourn.game.state;

import com.badlogic.gdx.utils.ScreenUtils;
import com.sojourn.game.Sojourn;
import com.sojourn.game.button.Button;

public class StateTitle extends State {

    public StateTitle(final Sojourn game)
    {
        super(game);
        Button startButton = new Button(game);
        startButton.setPosition(10, 40);
        startButton.setLabel("Play");
        startButton.setClickEvent(() -> game.setState(Sojourn.STATE_GAMEPLAY_ID));
        buttons.add(startButton);

        Button exitButton = new Button(game);
        exitButton.setPosition(10, 10);
        exitButton.setLabel("Quit");
        exitButton.setClickEvent(() -> game.exit());
        buttons.add(exitButton);

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void renderBackground(float delta) {
        ScreenUtils.clear(.1f, .1f, .6f, 1);
    }

    @Override
    public void renderGameplay(float delta)  {

    }

    @Override
    public void renderHud(float delta)
    {
        super.renderHud(delta);
//        Text.setAlignment(Alignment.LEFT, Alignment.BOTTOM);

//        startButton.render();
//       // exitButton.render();

    }

    public String toString()
    {
        return "Title";
    }

}
