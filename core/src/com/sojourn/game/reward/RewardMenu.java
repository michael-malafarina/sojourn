package com.sojourn.game.reward;

import com.sojourn.game.Utility;
import com.sojourn.game.button.Button;
import com.sojourn.game.entity.unit.civilian.MunitionsShip;
import com.sojourn.game.entity.unit.civilian.RepairShip;
import com.sojourn.game.entity.unit.ship.*;
import com.sojourn.game.reward.teamBonus.*;
import com.sojourn.game.state.gameplay.StateGameplay;

import java.util.ArrayList;
import java.util.List;

public class RewardMenu
{
    private List<Reward> currentRewards;
    private List<Reward> currentRewardPool;
    private List<Reward> potentialShipUnlocks;
    private List<Reward> potentialCivilianUnlocks;

    private List<Reward> potentialTeamBonusUpgrades;

    private StateGameplay state;
    private boolean hidden;

    public RewardMenu(StateGameplay state)
    {
        this.state = state;

        // Initialize reward sets
        currentRewards = new ArrayList<>();
        currentRewardPool = new ArrayList<>();
        potentialShipUnlocks = new ArrayList<>();
        potentialCivilianUnlocks = new ArrayList<>();

        potentialTeamBonusUpgrades = new ArrayList<>();

        // Set up the overall pools of potental upgrades
        loadRewardsTeamBonus();
        loadRewardsCivilianUnlocks();
        loadRewardsShipUnlocks();
    }

    public void begin()
    {
        int wave = StateGameplay.getWaveNumber();
        // Select which sort of reward pool is being used

        if(wave == 1 || wave == 3 || wave == 6 || wave == 10 || wave == 15)
        {
            setPoolToRewardShipUnlocks();
        }
        else if(wave == 5 || wave == 8 || wave == 12 || wave == 17)
        {
            setPoolToRewardCivilianUnlocks();
        }
        else
        {
            setPoolToRewardStandard();
        }

        currentRewards.clear();
        currentRewards.addAll(getRandomRewards(3));

        for(int i = 0; i < currentRewards.size(); i++)
        {
            currentRewards.get(i).begin(i);
        }

        hidden = false;
    }

    public void end()
    {
        hidden = true;
        currentRewards.forEach(a -> a.end());
        currentRewards.clear();
        state.restoreUnits();
    }

    public List<Reward> getRandomRewards(int rewardCount)
    {
//        List<Integer> rolls = Utility.uniqueRolls(3, 0, currentRewardPool.size()-1);
        List<Reward> newRewards = new ArrayList<>();

        for(int i = 0; i < rewardCount; i++)
        {
            boolean repeat = false;
            Reward newReward = getRandomReward();

            // No duplicates
            for(Reward r : newRewards)
            {
                if(newReward.getName() == r.getName()) {
                    repeat = true;
                    i--;
                }

            }

            if(!repeat)
            {
                newRewards.add(newReward);
            }
        }

        return newRewards;
    }

    public void loadRewardsShipUnlocks()
    {
        potentialShipUnlocks.add(new RewardUnlockShip(this, Scout.class));
        potentialShipUnlocks.add(new RewardUnlockShip(this, Guardian.class));
        potentialShipUnlocks.add(new RewardUnlockShip(this, Lancer.class));
        potentialShipUnlocks.add(new RewardUnlockShip(this, Raider.class));
        potentialShipUnlocks.add(new RewardUnlockShip(this, Dreadnought.class));
    }

    public void loadRewardsCivilianUnlocks()
    {
        potentialCivilianUnlocks.add(new RewardUnlockCivilian(this, MunitionsShip.class));
        potentialCivilianUnlocks.add(new RewardUnlockCivilian(this, RepairShip.class));
    }

    public void loadRewardsTeamBonus()
    {
        potentialTeamBonusUpgrades.add(new Health(this));
        potentialTeamBonusUpgrades.add(new Damage(this));
        potentialTeamBonusUpgrades.add(new Range(this));
        potentialTeamBonusUpgrades.add(new ControlRadius(this));
        potentialTeamBonusUpgrades.add(new MunitionsCapacity(this));
        potentialTeamBonusUpgrades.add(new SquadSize(this));
        potentialTeamBonusUpgrades.add(new Speed(this));
        potentialTeamBonusUpgrades.add(new CritChance(this));
        potentialTeamBonusUpgrades.add(new CritDamage(this));
    }


    public void setPoolToRewardShipUnlocks()
    {
        currentRewardPool.clear();
        currentRewardPool.addAll(potentialShipUnlocks);

        if(currentRewardPool.size() < 3)
        {
            currentRewardPool.addAll(potentialTeamBonusUpgrades);
        }
    }

    public void setPoolToRewardCivilianUnlocks()
    {
        currentRewardPool.clear();
        currentRewardPool.addAll(potentialCivilianUnlocks);

        if(currentRewardPool.size() < 3)
        {
            currentRewardPool.addAll(potentialTeamBonusUpgrades);
        }
    }


    public void setPoolToRewardStandard()
    {
        currentRewardPool.clear();
        currentRewardPool.addAll(potentialTeamBonusUpgrades);
    }

    public Reward getRandomReward()
    {
        float rarityScore = Utility.random(1f);
        Reward nextReward;

        do {
            nextReward = currentRewardPool.get(Utility.random(currentRewardPool.size()));
        } while(nextReward.getRarity().getRating() > rarityScore);

        return nextReward;
    }


    public void removeCivilianUnlock(RewardUnlockCivilian r)
    {
        potentialCivilianUnlocks.remove(r);
    }

    public void removeShipUnlock(RewardUnlockShip r)
    {
        potentialShipUnlocks.remove(r);
    }

    public void addButton(Button b)
    {
        state.addButton(b);
    }

    public void removeButton(Button b)
    {
        state.removeButton(b);
    }

    public int getNumberOfChoices()
    {
        return currentRewards.size();
    }

    public void render()
    {
        if(!hidden)
        {
       //     Display.draw(Textures.uiBar, new Color(.1f, .1f, .1f, 1f), Display.WIDTH * .20f, Display.HEIGHT * .25f, Display.WIDTH * .60f, Display.HEIGHT * .25f);
            currentRewards.forEach(a-> a.render());
        }

    }
}
