package com.sojourn.game.display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.sojourn.game.entity.Entity;


public class EntityMessage
{
    private final int BASE_TIME = 180;

    protected BitmapFont myFont;
    protected String message;
    protected float x;
    protected float y;
    protected int duration;
    private int time;
    protected float alpha;
    protected Color color;

    public EntityMessage(String message, Entity e)
    {
        this.message = message;
        this.color = Color.WHITE;
        this.x = e.getX();
        this.y = e.getY();

        alpha = 1;
        myFont = Fonts.small;
        duration = BASE_TIME;
    }

    public int getTime()
    {
        return time;
    }

    public int getDuration()
    {
        return duration;
    }

    public float getPercentProgress()
    {
        return (float) getTime() / (float) getDuration();
    }

    public float getPercentLeft()
    {
        return 1 - getPercentProgress();
    }

    public boolean isExpired()
    {
        return getTime() >= getDuration();
    }

    public void setDuration(int time)
    {
        this.time = time;
    }

    public void update(float delta)
    {
        time++;
    }

    public void render()
    {
        Text.setFont(myFont);
        Text.setColor(color);
        Text.setAlpha(alpha);
        Text.draw(message, x, y);
        Text.resetAlpha();
    }

}
