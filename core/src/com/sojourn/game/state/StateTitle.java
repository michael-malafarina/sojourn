package com.sojourn.game.state;

import com.badlogic.gdx.utils.ScreenUtils;
import com.sojourn.game.Sojourn;
import com.sojourn.game.button.Button;
import com.sojourn.game.display.Alignment;
import com.sojourn.game.display.Display;
import com.sojourn.game.display.Text;

public class StateTitle extends State {
    Button startButton;
    Button exitButton;

    public StateTitle(final Sojourn game)
    {
        super(game);
        startButton = new Button(game);
        startButton.setPosition(10, 40);
        startButton.setLabel("Play");
        startButton.setClickEvent(() -> game.setState(Sojourn.STATE_GAMEPLAY_ID));

      //  exitButton = new Button(game);
//        exitButton.setPosition(10, 10);
//        exitButton.setLabel("Quit");
//        exitButton.setClickEvent(() -> game.exit());
    }

    @Override
    public void update(float delta) {
        startButton.update();
     //   exitButton.update();
    }

    @Override
    public void renderBackground(float delta) {
        ScreenUtils.clear(.1f, .1f, .6f, 1);
    }

    @Override
    public void renderGameplay(float delta)  {

    }

    @Override
    public void renderHud(float delta) {
        Text.setAlignment(Alignment.LEFT, Alignment.BOTTOM);

        Text.draw("Title", 5, Display.HEIGHT-5);
        startButton.render();
       // exitButton.render();

    }



}
