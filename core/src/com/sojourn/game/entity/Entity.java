package com.sojourn.game.entity;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.Utility;
import com.sojourn.game.display.Shape;
import com.sojourn.game.entity.images.EntityImage;
import com.sojourn.game.faction.Faction;


abstract public class Entity
{
    private int timer;
    private Faction faction;
    protected Rectangle box;
    private boolean selected;
    protected EntityImage image;

    protected Vector2 speed;

    private float theta;
    private float delta;

    abstract public int getWidth();
    abstract public int getHeight();
    abstract public int getNumLayers();
    abstract public int getMaxSpeedBase();
    abstract public int getAccelerationBase();
    abstract public Texture getSpriteSheet();

    public Entity()
    {
        box = new Rectangle(0,0, getWidth(), getHeight());
        speed = new Vector2(0, 0);
    }

    public EntityImage getImage()
    {
        return image;
    }

    abstract public void actionPlanning();
    abstract public void actionCombat();

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



    public Vector2 getPosition()
    {
        return new Vector2(box.x+box.width/2, box.y+box.height/2);
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

    public void setPosition(Vector2 position)
    {
        setPosition(position.x, position.y);
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

    public boolean isSelected()
    {
        return selected;
    }

    public float getTheta() {
        return theta;
    }

    public Faction getFaction()
    {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public void setImage()
    {
        image = new EntityImage(this, getSpriteSheet());
    }

    public Rectangle getRectangle()
    {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public int getTimer()
    {
        return timer;
    }

    public void update(float delta)
    {
        this.delta = delta;
        timer++;

        move();
        actionPlanning();
    }

    public void render()
    {
        getImage().render();
    }

    public void renderShapes()
    {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        if(isSelected())
        {
            Shape.getRenderer().setColor(Color.WHITE);
            Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
            Shape.getRenderer().rect(getX() - getWidth() * .25f, getY() - getHeight() * .25f,getWidth() * 1.5f, getHeight() * 1.5f);
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

//        changeSpeed(getAcceleration() * delta);
//
//        box.x += speed.x;
//        box.y += speed.y;

        //System.out.println(xSpeed + " " + ySpeed + " " + currentSpeed + " " + getMaxSpeed());
    }

    protected void pidTurn(Vector2 p) {
        double kD = 0.5 * getMaxSpeed() / getAcceleration();

        double xDist = (p.x - getCenterX()) - speed.x * kD;
        double yDist = (p.y - getCenterY()) - speed.y * kD;
        double angle = Math.atan2(yDist, xDist);
        turnTo((float) Math.toDegrees(angle));
      //  move();
   }

    protected void accelerate()
    {
        changeSpeed(getAcceleration() * delta );
        box.x += speed.x;
        box.y += speed.y;
    }

    protected void accelerate(float normScaled)
    {
        changeSpeed(getAcceleration() * delta );
        speed.nor().scl(normScaled);
        box.x += speed.x;
        box.y += speed.y;
    }

    private void changeSpeed(float amount)
    {
        speed.add(Utility.makeVector(amount, theta));

        if (speed.len() > getMaxSpeed())
        {
            speed.nor();
            speed.scl(getMaxSpeed());
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

    public float getDistance(Vector2 p)
    {
        return getPosition().dst(p);
    }

    public float getDistance(Entity e)
    {
        return getPosition().dst(e.getCenterPosition());
    }




}
