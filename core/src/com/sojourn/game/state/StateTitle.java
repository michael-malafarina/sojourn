package com.sojourn.game.state;

import com.badlogic.gdx.utils.ScreenUtils;
import com.sojourn.game.Sojourn;
import com.sojourn.game.Textures;
import com.sojourn.game.button.Button;
import com.sojourn.game.display.Display;

public class StateTitle extends State {

    public StateTitle(final Sojourn game)
    {
        super(game);
        Button startButton = new Button();
        startButton.setPosition(100, 450);
        startButton.setLabel("Play");
        startButton.setClickEvent(() -> game.setState(Sojourn.STATE_GAMEPLAY_ID));
        addButton(startButton);

        Button exitButton = new Button();
        exitButton.setPosition(100, 325);
        exitButton.setLabel("Quit");
        exitButton.setClickEvent(game::exit);
        addButton(exitButton);

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
    protected void renderGameplayShapes() {

    }

    @Override
    protected void renderHudShapes() {

    }

    @Override
    public void renderHud(float delta)
    {
        Display.draw(Textures.title, 0, 0, 1920, 1080);
        Display.draw(Textures.titleBlack, 50, 0, 400, 1080);
        Display.draw(Textures.titleName, 75, 800);

        super.renderHud(delta);
    }

    public String toString()
    {
        return "Title";
    }

}
