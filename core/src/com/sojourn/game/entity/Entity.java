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
import com.sojourn.game.faction.Team;


abstract public class Entity
{
    final private float SPEED = 1.2f;
    final private float ACC = .02f;

    // Data

    private int timer;
    private Team team;
    protected Rectangle box;
    private boolean selected;
    protected EntityImage image;

    protected Vector2 speed;
    protected boolean isExpired;
    protected Attribute health;

    private float theta;
    private float delta;
    private boolean doneMovement;
    private boolean doneTurning;

    // Abstract Methods

    abstract public int getValueBase();
    abstract public int getWidth();
    abstract public int getHeight();
    abstract public int getNumLayers();
    abstract public float getMaxSpeedBase();
    abstract public float getAccelerationBase();
    abstract public Texture getSpriteSheet();
    abstract public void actionPlanning();
    abstract public void actionCombat();


    // Constructor

    public Entity()
    {
        box = new Rectangle(0,0, getWidth(), getHeight());
        speed = new Vector2(0, 0);

        health = new Attribute(1);
    }


    // Accessors

    public boolean canMove()    {
        return !doneMovement;
    }

    public boolean canTurn()    {
        return !doneTurning;
    }

    public EntityImage getImage()    {
        return image;
    }

    public Attribute getHealth()    {
        return health;
    }

    public float getValue()    {
        // Applies additional scalars, such as if it is an elite to the base unit archetype's value
        return getValueBase();
    }

    public float getMaxSpeed()    {

        return getMaxSpeedBase() * SPEED;
    }

    public float getAcceleration()    {
        // Eventually multiply this by speed debuffs and other conditions
        return getAccelerationBase() * ACC;
    }

    public Vector2 getPosition()    {
        return new Vector2(box.x+box.width/2, box.y+box.height/2);
    }

    public Vector2 getCenterPosition()    {
        return new Vector2(box.x + box.width/2, box.y + box.height/2);
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

    public Team getTeam()
    {
        return team;
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

    public boolean isExpired() { return isExpired; }
    // Mutators

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setPosition(float x, float y)    {
        box.x = x;
        box.y = y;
    }

    public void setExpired()
    {
        isExpired = true;
    }

    public void setPosition(Vector2 position)
    {
        setPosition(position.x, position.y);
    }

    public void update(boolean planning, float delta)
    {
        upkeep(delta);
        action(planning);
    }

    protected void upkeep(float delta)
    {
        this.delta = delta;
        timer++;

        doneMovement = false;
        doneTurning = false;

        health.update();

        if(health.getCurrent() <= 0)
        {
            setExpired();
        }
    }

    protected void action(boolean planning)
    {
        if(planning)
        {
            actionPlanning();
        }
        else
        {
            actionCombat();
        }
    }



    public void takeDamage(float amount, Entity source)
    {
        health.decrease(amount);
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
            final int SPACE = 10;
            Shape.getRenderer().setColor(Color.WHITE);
            Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
            Shape.getRenderer().rect(getX() - SPACE/2, getY() - SPACE/2,
                    getWidth() + SPACE, getHeight() + SPACE);
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


    protected void move()
    {
        if(canMove())
        {
            accelerate();
        }

    }

    protected void moveTo(Vector2 p)
    {
        turnTo(p);
        move();
    }
    protected void moveTo(Entity e)
    {
        turnTo(e);
        move();
    }

    protected void move(float normScaled)
    {
        if(canMove())
        {
            accelerate(normScaled);
        }

    }

    protected void pidTurn(Vector2 p) {
        double kD = 0.5 * getMaxSpeed() / getAcceleration();

        double xDist = (p.x - getCenterX()) - speed.x * kD;
        double yDist = (p.y - getCenterY()) - speed.y * kD;
        double angle = Math.atan2(yDist, xDist);
        turnTo((float) Math.toDegrees(angle));
      //  move();
   }

    private void accelerate()
    {
        changeSpeed(getAcceleration()  );
        box.x += speed.x * delta;
        box.y += speed.y * delta;
        doneMovement = true;
    }

    private void accelerate(float normScaled)
    {
        changeSpeed(getAcceleration());
        speed.nor().scl(normScaled );
        box.x += speed.x * delta;
        box.y += speed.y * delta;
        doneMovement = true;
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

    public final void turnLock()
    {
        doneTurning = true;
    }

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

    public final void rotate(float degrees)
    {
        while (degrees > 360)   {
            degrees -= 360;
        }
        while (degrees < 0)     {
            degrees += 360;
        }

        theta = degrees;
    }

    public final void turnTo(float degrees)
    {
        if (canTurn())
        {
           rotate(degrees);
        }
    }

    public final void turnTo(float x, float y)
    {
        turnTo((int) getAngleToward(x, y));
    }

    public final void turnTo(Vector2 p)
    {
        if(p != null) {
            turnTo(p.x, p.y);
        }
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
        if(e != null)
            return getPosition().dst(e.getCenterPosition());
        else
            return 0;
    }




}
