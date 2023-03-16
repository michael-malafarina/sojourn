package com.sojourn.game.button;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
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
    float modWidth;
    float modHeight;

    final float mouseoverSize = 8;

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
        modWidth = box.width;
        modHeight = box.height;
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

    public void setSize(float w, float h)
    {
        super.setSize(w, h);
        modWidth = box.width;
        modHeight = box.height;
    }

    public float getAlignedX()
    {
        if(modWidth > box.getWidth())
        {
            return super.getAlignedX() - mouseoverSize /2;
        }
        else
        {
            return super.getAlignedX();
        }
    }


    public void render()
    {
//        System.out.println(mouseOverTimer);


        // Background
        Display.draw(getImageCurrent(), new Color(color.r, color.g, color.b, .95f), getAlignedX(), getAlignedY(), modWidth, modHeight);

        // Title
        Text.setColor(Color.WHITE);
        Text.setFont(Fonts.title);
        Text.setAlignment(Alignment.CENTER, Alignment.CENTER);
        Text.draw(label, getAlignedX() + modWidth/2, getAlignedY() + modHeight * .85f);

        // Icon
        float iconSize = 96;
        Display.drawCentered(icon, iconColor, getX(), getAlignedY() + modHeight * .55f, iconSize, iconSize);


        // Description
        Text.setFont(Fonts.medium);
        Text.setAlignment(Alignment.CENTER, Alignment.CENTER);
        //Text.draw(description, getAlignedX()+box.width/2, getAlignedY() + 250);
        Text.drawWidthBounded(description, getAlignedX() + modWidth * .05f, getAlignedY() + modHeight * .3f, box.getWidth() * .85f);


    }


    public boolean mouseMoved(int screenX, int screenY)
    {
        super.mouseMoved(screenX, screenY);

        Vector3 tp = new Vector3();
        Display.getCamera().getHudCamera().unproject(tp.set(screenX, screenY, 0));

        Rectangle alignedBox = new Rectangle(getAlignedX(), getAlignedY(), box.getWidth(), box.getHeight());

        if(alignedBox.contains(tp.x, tp.y))
        {
            modWidth = box.width + mouseoverSize;
            modHeight = box.height + mouseoverSize;
            return true;
        }
        else
        {
            modWidth = box.width;
            modHeight = box.height;
            return false;
        }
    }


}
