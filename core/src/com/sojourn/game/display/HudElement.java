package com.sojourn.game.display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HudElement 
{
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	
	protected final Color COLOR_BORDER = new Color(.2f, .2f, .2f, 1f);
	protected final Color COLOR_BACKGROUND = new Color(.03f, .03f, .03f, 1f);
	
	public HudElement(float x, float y, float w, float h)
	{
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}
	
	public void renderShapes()
	{

		Shape.getRenderer().setColor(COLOR_BACKGROUND);
		Shape.getRenderer().set(ShapeRenderer.ShapeType.Filled);
		Shape.getRenderer().rect(x, y, width, height);

		Shape.getRenderer().setColor(COLOR_BORDER);
		Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
		Shape.getRenderer().rect(x, y, width, height);

	}

}
