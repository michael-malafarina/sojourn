package com.sojourn.game.reward;

import com.sojourn.game.Sojourn;
import com.sojourn.game.Utility;
import com.sojourn.game.button.Button;
import com.sojourn.game.entity.unit.ship.*;
import com.sojourn.game.reward.teamBonus.*;
import com.sojourn.game.state.StateGameplay;

import java.util.ArrayList;
import java.util.List;

public class RewardMenu
{
    private List<Reward> currentRewards;
    private List<Reward> currentRewardPool;
    private List<Reward> potentialShipUnlocks;
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
        potentialTeamBonusUpgrades = new ArrayList<>();

        // Set up the overall pools of potental upgrades
        loadRewardsTeamBonus();
        loadRewardsShipUnlocks();
    }

    public void begin()
    {

        // Select which sort of reward pool is being used
        if(Sojourn.player.getResearch() == 1)
        {
            setPoolToRewardShipUnlocks();
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

    public void loadRewardsTeamBonus()
    {
         loadRewardsTeamBonus(Rarity.COMMON);
         loadRewardsTeamBonus(Rarity.UNCOMMON);
         loadRewardsTeamBonus(Rarity.RARE);
         loadRewardsTeamBonus(Rarity.EPIC);
         loadRewardsTeamBonus(Rarity.LEGENDARY);
    }

    public void loadRewardsTeamBonus(Rarity rarity)
    {
        potentialTeamBonusUpgrades.add(new Health(this, rarity));
        potentialTeamBonusUpgrades.add(new Damage(this, rarity));
        potentialTeamBonusUpgrades.add(new Range(this, rarity));
        potentialTeamBonusUpgrades.add(new ControlRadius(this, rarity));
        potentialTeamBonusUpgrades.add(new MunitionsCapacity(this, rarity));
        potentialTeamBonusUpgrades.add(new SquadSize(this, rarity));
        potentialTeamBonusUpgrades.add(new Speed(this,rarity));
        potentialTeamBonusUpgrades.add(new CritChance(this, rarity));
        potentialTeamBonusUpgrades.add(new CritDamage(this, rarity));
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
