package com.sojourn.game.button;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.display.Alignment;
import com.sojourn.game.display.Display;
import com.sojourn.game.display.Fonts;
import com.sojourn.game.display.Text;

public class RewardButton extends Button
{
    private Texture icon;
    private String description;
    private Color iconColor;

    public RewardButton()
    {
        super();
        setFont(Fonts.large);
        description = "";
        setColor(Color.WHITE);
        setIconColor(Color.WHITE);

        setImageBase(Textures.uiRewardBoxBase);
        setImageMouseover(Textures.uiRewardBoxMouseover);
        setAlignment(Alignment.CENTER, Alignment.CENTER);

        icon = Textures.uiUpgradeDefault;
    }

    public void setIcon(Texture icon)
    {
        this.icon = icon;
    }

    public void setIconColor(Color color)
    {
        iconColor = color;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }


    public void render()
    {
        Display.draw(getImageCurrent(), color, getAlignedX(), getAlignedY(), box.width, box.height);
        Display.drawCentered(icon, iconColor, getX(), getY()+100, 32*3, 32*3);

        Text.setFont(Fonts.title);
        Text.setAlignment(Alignment.CENTER, Alignment.CENTER);
        Text.draw(label, getAlignedX()+box.width/2, getAlignedY()+box.height - 50);

        Text.setFont(Fonts.medium);
        Text.setAlignment(Alignment.CENTER, Alignment.CENTER);
        //Text.draw(description, getAlignedX()+box.width/2, getAlignedY() + 250);
        Text.drawWidthBounded(description, getAlignedX() + box.width * .05f, getAlignedY() + 250, box.width * .9f);
    }
}
