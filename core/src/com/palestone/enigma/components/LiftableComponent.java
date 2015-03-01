package com.palestone.enigma.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class LiftableComponent extends Component{
    public Entity ghost;
    public boolean invisibleGhost;
    public boolean lifted;
    public boolean collides;
}
