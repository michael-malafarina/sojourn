package com.sojourn.game.menu;

import com.sojourn.game.button.Button;
import com.sojourn.game.state.State;

public class RewardMenu
{
    private Button one;
    private State state;
    private boolean done;

    public RewardMenu(State state)
    {
        one = new Button();
        Reward r = new ExtraHealth();
        one.setPosition(600, 600);
        one.setLabel("Reward");
        one.setClickEvent(r::apply);
        one.setClickEventTwo(this::done);

//        one.setClickEvent(() -> System.out.println("waaaa"));
//        one.setMouseoverEvent(() -> System.out.println("fdsafdsafds"));

        System.out.println(one);
        this.state = state;
        state.addButton(one);
    }

    public void done()
    {
        done = true;
        state.removeButton(one);

    }

    public void update()
    {
        if(!done)
        {
            one.update();
        }

    }

    public void render()
    {
        if(!done)
        {
            one.render();
        }

    }
}
