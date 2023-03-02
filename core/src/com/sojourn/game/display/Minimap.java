package com.sojourn.game.display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.civilian.Civilian;

import java.util.List;

public class Minimap extends HudElement
{
	final float WORLD_WIDTH = 1920*5;
	final float WORLD_HEIGHT = 1080 * 5 ;



	public Minimap(float x, float y, float w, float h)
	{
		super(x, y, w, h);
	}

	public void renderShapes()
	{
		super.renderShapes();
//		if(Settings.showBackground)
//		{
//			Image i = TerritoryManager.getBackground();
//			int bright = (int) (255 * Settings.backgroundBrightness);
//			Color c = new Color(bright, bright, bright);
//			i.draw(x+2,y+1, w-4, h-4, c);
//		}
		
//		renderNodes(g);
//		renderResources(g);
		renderCivilians();
		renderUnits();
		renderCamera();
	}


	public void renderCivilians()
	{
		List<Civilian> entities = EntityManager.getCivilians();

		for(Entity e : entities)
		{
				float xPos = e.getCenterX() / (float) WORLD_WIDTH * width + width/2;
				float yPos = e.getCenterY() / (float) WORLD_HEIGHT * height + y + height/2;

				int w = e.getWidth() / 40;
				int h = e.getHeight() / 40;

				Color inner = new Color(.2f, .2f, .2f, 1f);
				Color outer = e.getTeam().getFaction().getColor(0);

				Shape.getRenderer().setColor(inner);
				Shape.getRenderer().set(ShapeRenderer.ShapeType.Filled);
				Shape.getRenderer().rect(xPos-w/2, yPos-h/2, w, h);

				Shape.getRenderer().setColor(outer);
				Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
				Shape.getRenderer().rect(xPos-w/2, yPos-h/2, w, h);

		}

	}

	public void renderUnits()
	{
		List<Entity> entities = EntityManager.getEntities();

		for(Entity e : entities)
		{
			//if(u.isInBounds())
			{

				if(e instanceof Civilian)
				{
					continue;
				}
//				float xPos = e.getX() / (float) Values.PLAYFIELD_WIDTH * width + width/2;
//				float yPos = e.getY() / (float) Values.PLAYFIELD_HEIGHT * height + y + height/2;


				float xPos = e.getCenterX() / (float) WORLD_WIDTH * width + width/2;
				float yPos = e.getCenterY() / (float) WORLD_HEIGHT * height + y + height/2;

				int w = e.getWidth() / 12;
				int h = e.getHeight() / 12;

				Color inner = null;
				Color outer = null;


				inner = e.getTeam().getFaction().getColor(0);
//				outer = Color.GRAY;


				Shape.getRenderer().setColor(inner);
				Shape.getRenderer().set(ShapeRenderer.ShapeType.Filled);
				Shape.getRenderer().ellipse(xPos-w/2, yPos-h/2, w, h);

//				Shape.getRenderer().setColor(outer);
//				Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
//				Shape.getRenderer().ellipse(xPos-w/2, yPos-h/2, w, h);

			}
		}

	}

//	public void renderResources(Graphics g)
//	{
//		ArrayList<Resource> resources = ResourceManager.getResources();
//
//		for(Resource r : resources)
//		{
//			if(r.isInBounds())
//			{
//				float xPos = r.getX() / (float) Values.PLAYFIELD_WIDTH * w + w/2;
//				float yPos = r.getY() / (float) Values.PLAYFIELD_HEIGHT * h + y + h/2;
//				int size = 2;
//
//
//				if(r instanceof Minerals)
//				{
//					g.setColor(TerritoryManager.getMineralColor());
//				}
//				else
//				{
//					g.setColor(TerritoryManager.getSalvageColor());
//				}
//
//
//				g.fillOval(xPos-size/2, yPos-size/2, size, size);
//			}
//
//		}
//	}

//	public void renderNodes(Graphics g)
//	{
//		ArrayList<Node> nodes = NodeManager.getNodes();
//		for(Node n : nodes)
//		{
//			if(n.isInBounds())
//			{
//				int size = Math.round(6 * n.getNodeScale());
//
//
//				float xPos = n.getX() / (float) Values.PLAYFIELD_WIDTH * w + w/2;
//				float yPos = n.getY() / (float) Values.PLAYFIELD_HEIGHT * h + y + h/2;
//
//
////				if(n instanceof Asteroid)
////				{
////
////					g.setColor(TerritoryManager.getAsteroidColor());
////				}
////				else
////				{
////					g.setColor(TerritoryManager.getDerelictColor());
////				}
//				g.setColor(n.getColor());
//				g.fillOval(xPos-size/2, yPos-size/2, size, size);
//			}
//		}
//	}

	public void renderCamera()
	{
		// Maths

		OrthographicCamera cam = Display.getCamera().getGameCamera();


		float camX = cam.position.x / ((float) WORLD_WIDTH) * width + x + width / 2;
		float camY = cam.position.y / ((float) WORLD_HEIGHT) * height + y + height / 2;
		float camW = (cam.viewportWidth / ((float) WORLD_WIDTH)) * width;
		float camH = (cam.viewportHeight / ((float) WORLD_HEIGHT)) * height;

//		// Snap to right
//		if(camX + camW / 2 > width)
//		{
//			camX = width - camW/2;
//		}
//
//		// Snap to left
//		if(camX - camW / 2 < 0)
//		{
//			camX = camW / 2;
//		}
//
//		// Snap to bottom
//		if(camY + camH / 2 > y + height)
//		{
//			camY = y + height - camH/2;
//		}
//
//		// Snap to top
//		if(camY - camH/ 2 < y)
//		{
//			camY = y + camH / 2;
//		}

		Shape.getRenderer().setColor(Color.WHITE);
		Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
		Shape.getRenderer().rect(camX - camW / 2, camY - camH / 2, camW, camH);

		// Display box
//		g.setLineWidth(2);
//		g.setColor(new Color(200, 200, 200, 10));
//		g.fillRect(camX - camW / 2, camY - camH / 2, camW, camH);
//		g.setColor(new Color(200, 200, 200, 45));
//		g.drawRect(camX - camW / 2, camY - camH / 2, camW, camH);
//		g.resetLineWidth();
	}

}
