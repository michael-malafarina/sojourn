package com.sojourn.game.reward;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.button.RewardButton;
import com.sojourn.game.display.Display;

abstract public class Reward
{

    private Rarity rarity;

    private float x;
    private float y;

    protected String name;
    protected String description;
    protected Texture icon;

    protected RewardButton rewardButton;
    protected RewardMenu owner;

    abstract public void apply();


    public Reward(RewardMenu owner)
    {
        this.owner = owner;
        rewardButton = new RewardButton();
        rewardButton.setClickEvent(this::apply);
        rewardButton.setClickEventTwo(owner::end);
        description = "";
        rarity = Rarity.COMMON;


    }

    protected void setRarity(Rarity rarity)
    {
        this.rarity = rarity;
        Color c = getRarity().getColor();
//        rewardButton.setColor(c);
        rewardButton.setIconColor(c);
        rewardButton.setColorBorder(c);
    }

    public String getName()
    {
        return name;
    }

    public void end()
    {
        owner.removeButton(rewardButton);
    }

    public Rarity getRarity()
    {
        return rarity;
    }

    public void begin(int i)
    {

        float numSections = owner.getNumberOfChoices();
        float menuLeftEdge = Display.WIDTH / 3;
        float menuWidth = Display.WIDTH * .40f;
        float sectionWidth = menuWidth / (numSections+.5f);
        float sectionSpacing = (menuWidth - sectionWidth * numSections) / (numSections - 1);

        x = menuLeftEdge + (i * sectionSpacing) + (i * sectionWidth) - sectionSpacing/2;
        y = (int) (Display.HEIGHT * .5f);

        rewardButton.setLabel(name);
        rewardButton.setSize(sectionWidth, 350);
        rewardButton.setPosition(x, y);
        rewardButton.setDescription(description);

        owner.addButton(rewardButton);

    }

    public void render()
    {
//        Display.drawCentered(icon, x, y+140, 32*3, 32*3);
//
//        Text.setFont(Fonts.medium);
//        Text.setAlignment(Alignment.CENTER, Alignment.CENTER);
//        Text.draw(description, x, y - 60);
    }

}
