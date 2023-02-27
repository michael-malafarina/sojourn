package com.sojourn.game.display;

import java.util.ArrayList;
import java.util.List;

public class EntityMessageManager
{
    private static List<EntityMessage> entityMessages;

    public EntityMessageManager()
    {
        entityMessages = new ArrayList<>();
    }

    public static void addMessage(EntityMessage entityMessage)
    {
        entityMessages.add(entityMessage);
    }

    public void update(float delta)
    {
        entityMessages.forEach(n -> n.update(delta));

        for(int i = 0; i < entityMessages.size(); i++)
        {
            if(entityMessages.get(i).isExpired())
            {
                entityMessages.remove(i);
                i--;
            }
        }
    }

    public void render()
    {
        entityMessages.forEach(n -> n.render());
    }
}
