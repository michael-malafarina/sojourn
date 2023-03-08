package com.sojourn.game.reward;

import com.sojourn.game.Utility;
import com.sojourn.game.button.Button;
import com.sojourn.game.reward.teamBonus.*;
import com.sojourn.game.state.StateGameplay;

import java.util.ArrayList;
import java.util.List;

public class RewardMenu
{
    private List<Reward> rewards;
    private StateGameplay state;
    private boolean done;

    public RewardMenu(StateGameplay state)
    {
        this.state = state;
        rewards = new ArrayList<>();
        rewards.addAll(getRandomRewards(3, 5));
        position();
    }

    public List<Reward> getRandomRewards(int rewardCount, int numTypes)
    {
        List<Integer> rolls = Utility.uniqueRolls(3, 0, numTypes);
        List<Reward> newRewards = new ArrayList<>();

        for(int i = 0; i < rewardCount; i++)
        {
            newRewards.add(getReward(rolls.get(i)));
        }

        return newRewards;
    }

    public Reward getReward(int index)
    {
        return switch(index) {
            case 1 -> new Health(this);
            case 2 -> new Damage(this);
            case 3 -> new Range(this);
            case 4 -> new ControlRadius(this);
            case 5 -> new MunitionsCapacity(this);
            default -> new SquadSize(this);
        };
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
        state.restoreUnits();

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
