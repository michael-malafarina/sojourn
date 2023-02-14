package com.sojourn.game.entity;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.display.Shape;
import com.sojourn.game.entity.images.Image;
import com.sojourn.game.faction.Faction;
import com.sojourn.game.faction.PlayerFaction;


abstract public class Entity
{
    private Faction faction;
    private Image image;
    protected Rectangle box;
    private boolean selected;

    protected float xSpeed;
    protected float ySpeed;
    private boolean atMaxSpeed;
    private float theta;
    private float delta;

    abstract public int getNumLayers();
    abstract public int getMaxSpeedBase();
    abstract public int getAccelerationBase();
    abstract public void action();

    public float getMaxSpeed()
    {
        // Eventually multiply this by speed debuffs and other conditions
        return getMaxSpeedBase();
    }

    public float getAcceleration()
    {
        // Eventually multiply this by speed debuffs and other conditions
        return getAccelerationBase();
    }

    public Entity()
    {
        faction = new PlayerFaction();
        image = new Image(this);
        box = new Rectangle(0,0, 32, 32);
    }

    public Vector2 getPosition()
    {
        return new Vector2(box.x, box.y);
    }

    public Vector2 getCenterPosition()
    {
        return new Vector2(box.x + box.width/2, box.y + box.height/2);
    }

    public void setPosition(float x, float y)
    {
        box.x = x;
        box.y = y;
    }

    public float getX() {
        return box.x;
    }
    public float getY() {
        return box.y;
    }

    public float getCenterX() {
        return box.x + box.width/2;
    }

    public float getCenterY() {
        return box.y + box.height/2;
    }

    public int getWidth() {
        return Math.round(box.width);
    }

    public boolean isSelected()
    {
        return selected;
    }

    public int getHeight() {
        return Math.round(box.height);
    }

    public float getTheta() {
        return theta;
    }

    public Faction getFaction()
    {
        return faction;
    }

    public Rectangle getRectangle()
    {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void update(float delta)
    {
        this.delta = delta;

        move();
        action();
    }

    public void render()
    {
        image.render();
    }

    public void renderShapes()
    {
        if(isSelected())
        {
            Shape.getRenderer().setColor(Color.WHITE);
            Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
            Shape.getRenderer().rect(getX(), getY(), getWidth(), getHeight());
        }
    }

    public void clicked()
    {
        selected = true;
    }

    public void unselect()
    {
        selected = false;
    }


    /********************* MOVEMENT *********************/

    private void move()
    {
        changeSpeed(getAcceleration() * delta);

        box.x += xSpeed;
        box.y += ySpeed;
    }

    private void changeSpeed(float amount)
    {
        // Find the change in my speed from moving forward for one frame in the direction I'm facing
        float xDelta = (float) (amount * Math.cos(Math.toRadians(theta))); // Calculate change in x speed
        float yDelta = (float) (amount * Math.sin(Math.toRadians(theta))); // Calculate change in y speed

        xSpeed += xDelta; // Change xSpeed
        ySpeed += yDelta; // Change ySpeed

        // If the magnitude of the velocity vector is > maxSpeed, lower it.
        final float VelocityMagnitude = (float) Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed);

        if (VelocityMagnitude > getMaxSpeed()) {
            atMaxSpeed = true; // Begin max speed animation

            // Normalize the velocity vector, then multiply by maxSpeed so magnitude remains maxSpeed
            xSpeed = xSpeed / VelocityMagnitude * getMaxSpeed();
            ySpeed = ySpeed / VelocityMagnitude * getMaxSpeed();
        }

    }

    /********************* TURNING *********************/

    public final float getAngleToward(float targetX, float targetY)
    {
        float yDiff = targetY - getCenterY();
        float xDiff = targetX - getCenterX();

        float angle = (float) Math.toDegrees(Math.atan2(yDiff, xDiff));

        if (angle < 0)
        {
            angle = 360 + angle;
        }

        return angle;
    }

    public final void turnTo(float degrees)
    {
           // if (canMove())
            //{
        while (degrees > 360)   {
            degrees -= 360;
        }
        while (degrees < 0)     {
            degrees += 360;
        }

        theta = degrees;
              //  thetaOld = theta;
           // }
    }

    public final void turnTo(float x, float y)
    {
        turnTo((int) getAngleToward(x, y));
    }

    public final void turnTo(Vector2 p)
    {
        turnTo(p.x, p.y);
    }

    public final void turnTo(Entity e)
    {
        if (e != null)
        {
            turnTo(e.getCenterX(), e.getCenterY());
        }
    }

    public final void turn(float degrees)
    {
        turnTo(getTheta() + degrees);
    }

    public final void turnAround()
    {
        turn(180);
    }

}
