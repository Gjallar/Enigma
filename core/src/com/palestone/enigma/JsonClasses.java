package com.palestone.enigma;

import com.badlogic.gdx.math.Vector2;
import com.palestone.enigma.enums.EntityType;
import com.palestone.enigma.enums.Layer;

import java.util.ArrayList;

public class JsonClasses {

    public static class JsonWorld {
        public JsonPlayer player;
        public Vector2 activeSection;
        public ArrayList<JsonSection> sections = new ArrayList<JsonSection>();
    }

    public static class JsonSection {
        public Vector2 key;
        public ArrayList<JsonEntity> entities = new ArrayList<JsonEntity>();
    }

    public static class JsonEntity {
        public EntityType type;
    }

    public static class JsonPlayer extends JsonEntity {
        public float x;
        public float y;
        public String textureName;
        public Layer layer;
        public boolean collides;
        public boolean lifting;
        public JsonEntity liftable;
    }

    public static class JsonGround extends JsonEntity {
        public float x;
        public float y;
        public String textureName;
        public Layer layer;
        public Vector2 parentKey;
    }

    public static class JsonEnvironment extends JsonEntity {
        public float x;
        public float y;
        public String textureName;
        public Layer layer;
        public boolean collides;
        public Vector2 parentKey;
    }

    public static class JsonDoor extends JsonEntity {
        public float x;
        public float y;
        public String textureName;
        public Layer layer;
        public boolean collides;
        public Vector2 parentKey;
        public Vector2 linkedSectionKey;
    }

    public static class JsonLiftable extends JsonEntity {
        public float x;
        public float y;
        public String textureName;
        public Layer layer;
        public boolean collides;
        public boolean lifted;
        public boolean liftedCollides;
    }
}
