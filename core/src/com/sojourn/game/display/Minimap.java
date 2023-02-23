package com.sojourn.game.display;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;

import java.util.List;

public class Minimap extends HudElement
{
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
		renderUnits();
//		renderCamera(g);
	}

	public void renderUnits()
	{
		List<Entity> entities = EntityManager.getEntities();

		for(Entity e : entities)
		{
			//if(u.isInBounds())
			{
//				float xPos = e.getX() / (float) Values.PLAYFIELD_WIDTH * width + width/2;
//				float yPos = e.getY() / (float) Values.PLAYFIELD_HEIGHT * height + y + height/2;


				float xPos = e.getX() / (float) 5000 * width + width/2;
				float yPos = e.getY() / (float) 5000 * height + y + height/2;

//				if(u instanceof BaseShip)
//				{
//					int width = 20;
//					int height = 10;
//					g.setColor(u.getColorPrimary().darker().darker());
//					g.fillRect(xPos-width/2, yPos-height/2, width, height);
//					g.setLineWidth(1);
//					g.setColor(u.getColorPrimary().darker());
//					g.drawRect(xPos-width/2, yPos-height/2, width, height);
//
//				}
//				else
				{
					int size = 4;//u.getFrame().getImageSize() / 12;

					Shape.getRenderer().setColor(e.getTeam().getFaction().getColor(0));
					Shape.getRenderer().set(ShapeRenderer.ShapeType.Filled);
					Shape.getRenderer().ellipse(xPos-size/2, yPos-size/2, size, size);


				}
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

//	public void renderCamera(Graphics g)
//	{
//		// Maths
//
//		float camX = Camera.getX() / ((float) Game.getMapWidth()) * w + x + w / 2;
//		float camY = Camera.getY() / ((float) Game.getMapHeight()) * h + y + h / 2;
//		float camW = ((Camera.getViewWidth() / ((float) Game.getMapWidth())) * w);
//		float camH = ((Camera.getViewHeight() / ((float) Game.getMapHeight())) * h);
//
//		// Snap to right
//		if(camX + camW / 2 > w)
//		{
//			camX = w - camW/2;
//		}
//
//		// Snap to left
//		if(camX - camW / 2 < 0)
//		{
//			camX = camW / 2;
//		}
//
//		// Snap to bottom
//		if(camY + camH / 2 > y + h)
//		{
//			camY = y + h - camH/2;
//		}
//
//		// Snap to top
//		if(camY - camH/ 2 < y)
//		{
//			camY = y + camH / 2;
//		}
//
//		// Display box
//		g.setLineWidth(2);
//		g.setColor(new Color(200, 200, 200, 10));
//		g.fillRect(camX - camW / 2, camY - camH / 2, camW, camH);
//		g.setColor(new Color(200, 200, 200, 45));
//		g.drawRect(camX - camW / 2, camY - camH / 2, camW, camH);
//		g.resetLineWidth();
//	}

}
