package com.sojourn.game.menu;

import com.sojourn.game.button.Button;
import com.sojourn.game.state.State;

import java.util.ArrayList;
import java.util.List;

public class RewardMenu
{
    private List<Reward> rewards;
    private State state;
    private boolean done;

    public RewardMenu(State state)
    {
        this.state = state;
        rewards = new ArrayList<>();
        rewards.add(new ExtraHealthScaling(this));
        rewards.add(new ExtraDamageScaling(this));
        rewards.add(new ExtraRangeScaling(this));

        position();
    }

    public void addButton(Button b)
    {
        state.addButton(b);
    }

    public void removeButton(Button b)
    {
        state.removeButton(b);
    }

    public void done()
    {
        done = true;
        rewards.forEach(a -> a.done());

    }

    public void position()
    {
       for(int i = 0; i < rewards.size(); i++)
       {
          rewards.get(i).setup(i);
        }
    }


    public int getNumberOfChoices()
    {
        return rewards.size();
    }

    public void render()
    {
        if(!done)
        {
            rewards.forEach(a-> a.render());
        }

    }
}
