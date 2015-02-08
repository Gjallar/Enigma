package com.palestone.enigma.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.palestone.enigma.Tile;

public class SectionComponent extends Component {
    public boolean initialSection;
    public Vector2 key;
    public boolean generated;
    public int tileChance;
    public Array<Entity> allEntities = new Array<Entity>();
    public Array<Entity> groundTiles = new Array<Entity>();
    public Array<Entity> environmentTiles = new Array<Entity>();
    public Array<Entity> doors = new Array<Entity>();
    public Tile[][] tileGrid;
}
