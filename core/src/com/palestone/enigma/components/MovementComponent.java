package com.palestone.enigma.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class MovementComponent extends Component{
    public final Vector2 velocity = new Vector2();
    public final Vector2 acceleration = new Vector2();
}
