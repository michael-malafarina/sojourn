package com.sojourn.game.entity.component.module;

import java.util.ArrayList;

public class ModuleSet
{
    ArrayList<Module> modules;

    public ModuleSet()    {
        modules = new ArrayList<>();
    }

    public void add(Module m)    {
        modules.add(m);
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public void update()
    {
        modules.forEach(Module::update);
    }

    public boolean hasModule(Class<? extends Module> clazz)
    {
        if(getModule(clazz) == null)
        {
            return false;
        }
        return true;
    }

    public Module getModule(Class<? extends Module> clazz)
    {
        for(Module m : modules)
        {
            if(clazz.isInstance(m))
            {
                return m;
            }
        }

        return null;
    }
}
