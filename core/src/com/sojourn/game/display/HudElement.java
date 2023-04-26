package com.sojourn.game.display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.sojourn.game.Textures;
import com.sojourn.game.button.ButtonEvent;

public class HudElement 
{
	protected Rectangle box;
	private Texture imageCurrent;
	private Texture imageBase;
	private Texture imageMouseover;
	private Texture imageBorder;
	private Alignment alignHorizontal;
	private Alignment alignVertical;

	protected BitmapFont font;
	protected String label;
	protected Color color;
	protected Color colorBorder;
	protected Color colorText;

	protected ButtonEvent mouseoverEvent;

//	protected boolean showBackground;
//	protected boolean showBorder;

//	protected final Color COLOR_BORDER = new Color(.2f, .2f, .2f, 1f);
//	protected final Color COLOR_BACKGROUND = new Color(.03f, .03f, .03f, 1f);

	public HudElement()
	{
		alignHorizontal = Alignment.LEFT;
		alignVertical = Alignment.BOTTOM;
		imageBase = Textures.uiButtonBase;
		imageMouseover = Textures.uiButtonMouseover;
		imageCurrent = imageBase;
		colorText = Color.WHITE;
		box = new Rectangle(0, 0, 100, 100);
	}

	public HudElement(float x, float y, float w, float h)
	{
		this();
		set(x, y, w, h);
//		showBackground = true;
//		showBorder = true;
	}

	public void set(float x, float y, float w, float h) {
		box.set(x, y, w, h);
	}

	public void setMouseoverEvent(ButtonEvent mouseoverEvent)
	{
		this.mouseoverEvent = mouseoverEvent;
	}


	protected void mouseover(ButtonEvent f) {
		if(f != null ) {
			f.activate();
		}
	};

	public boolean mouseMoved(int screenX, int screenY)
	{
		Vector3 tp = new Vector3();
		Display.getCamera().getHudCamera().unproject(tp.set(screenX, screenY, 0));

		Rectangle alignedBox = new Rectangle(getAlignedX(), getAlignedY(), box.getWidth(), box.getHeight());

		if(alignedBox.contains(tp.x, tp.y)) {
			mouseover(mouseoverEvent);
			imageCurrent = imageMouseover;
			return true;
		}
		else {
			imageCurrent = imageBase;
			return false;
		}
	}

	public float getX()
	{
		return box.getX();
	}

	public float getY()
	{
		return box.getY();
	}

	public float getWidth()
	{
		return box.getWidth();
	}

	public float getHeight()
	{
		return box.getHeight();
	}

	public Texture getImageCurrent()
	{
		return imageCurrent;
	}

	public Texture getImageBorder()	{ return imageBorder;	}

	public void setAlignment(Alignment horizontal, Alignment vertical)  {
		alignHorizontal = horizontal;
		alignVertical = vertical;


	}

	public void setImageBase(Texture image)
	{
		imageBase = image;
		imageCurrent = imageBase;
	}


	public void setImageBorder(Texture image)
	{
		imageBorder = image;
	}

	public void setImageMouseover(Texture image)
	{
		imageMouseover = image;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public void setColorBorder(Color color)
	{
		this.colorBorder = color;
	}


	public void setLabel(String label)
	{
		this.label = label;
	}

	public void setFont(BitmapFont font)
	{
		this.font = font;
	}

	public void setPosition(float x, float y) {
		box.setPosition(x, y);
	}

	public void setSize(float w, float h) {
		box.width = w;
		box.height = h;
	}


	public void update()
	{

	}

	public void render()
	{
//		System.out.println(alignVertical);
//		System.out.println(box.y + " " + getAlignedY());

		Display.draw(imageCurrent, color, getAlignedX(), getAlignedY(), box.width, box.height);

		if(imageBorder != null)
		{
//			System.out.println("drawing image border in hud element");
			Display.draw(imageBorder, color, getAlignedX(), getAlignedY(), box.width, box.height);
		}

		Text.setFont(font);
		Text.setColor(colorText);
		Text.setAlignment(Alignment.CENTER, Alignment.CENTER);
		Text.draw(label, getAlignedX()+box.width/2, getAlignedY()+box.height/2);
	}

	protected float getAlignedX()
	{
		float x = box.x;

		if(alignHorizontal == Alignment.RIGHT)
		{
			x -= box.width;
		}

		if(alignHorizontal == Alignment.CENTER)
		{
			x -= box.width/2;
		}
		return x;
	}

	protected float getAlignedY()
	{
		float y = box.y;

		if(alignVertical == Alignment.TOP)
		{
			y -= box.height;
		}

		if(alignVertical == Alignment.CENTER)
		{
			y -= box.height/2;
		}

		return y;
	}

	public void renderShapes()
	{
//		Shape.getRenderer().set(ShapeRenderer.ShapeType.Filled);
//		Shape.getRenderer().rect(getAlignedX(), getAlignedY(), box.width, box.height);


//		if(showBackground)
//		{
//			Shape.getRenderer().setColor(COLOR_BACKGROUND);
//			Shape.getRenderer().set(ShapeRenderer.ShapeType.Filled);
//			Shape.getRenderer().rect(box.x, box.y, box.width, box.height);
//		}
//
//		if(showBorder)
//		{
//			Shape.getRenderer().setColor(COLOR_BORDER);
//			Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
//			Shape.getRenderer().rect(box.x, box.y, box.width, box.height);
//		}

	}

}
