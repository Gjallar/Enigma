package com.palestone.enigma.components;

import com.badlogic.ashley.core.Component;
import com.palestone.enigma.Tile;

public class SectionComponent extends Component {
    public boolean generated;
    public int tileChance;
    public Tile[][] tileGrid;
}
