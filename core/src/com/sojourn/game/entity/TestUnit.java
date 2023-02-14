package com.sojourn.game.entity;

public class TestUnit extends Unit
{
    public TestUnit()
    {
        super();
        setDestination(200, 200);
    }

    public void action()
    {
        super.action();
       // setDestinationMouse();

//        System.out.println("Location: " + getPosition());
//
//        System.out.println("Destination: " + getDestination());
    }



}
