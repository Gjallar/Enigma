package com.palestone.enigma;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.palestone.enigma.components.BodyComponent;
import com.palestone.enigma.systems.InputSystem;

public abstract class Utility {

    private static Engine engine;
    private static ComponentMapper<BodyComponent> bodyMapper;

    public static void initUtility(Engine engineInstance) {
        engine = engineInstance;

        bodyMapper = ComponentMapper.getFor(BodyComponent.class);
    }

    /**
     * Returns true if the body would collide with other bodies at the specified location.
     */
    public static boolean collidesWithBodies(BodyComponent bodyComp, float x, float y) {
        Family bodyFamily = Family.all(BodyComponent.class).get();
        ImmutableArray<Entity> bodyEntities = engine.getEntitiesFor(bodyFamily);

        Rectangle buildPosBody = new Rectangle(x, y, bodyComp.body.getWidth(), bodyComp.body.getHeight());

        for(Entity bodyEntity : bodyEntities) {

            BodyComponent bodyCompBodyEntity = bodyMapper.get(bodyEntity);
            if(buildPosBody.overlaps(bodyCompBodyEntity.body))
                return true;
        }
        return false;
    }
}
