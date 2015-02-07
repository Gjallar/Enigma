package com.palestone.enigma.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.palestone.enigma.enums.Layer;

public class TextureComponent extends Component {
    public String textureName;
    public TextureRegion region = new TextureRegion();
    public Layer layer;
}
