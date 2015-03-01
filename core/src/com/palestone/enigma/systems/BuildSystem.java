package com.palestone.enigma.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.math.Rectangle;
import com.palestone.enigma.TextureAssets;
import com.palestone.enigma.Utility;
import com.palestone.enigma.World;
import com.palestone.enigma.components.*;
import com.palestone.enigma.enums.EntityType;
import com.palestone.enigma.enums.Layer;

public class BuildSystem extends EntitySystem{

    Engine engine;
    EntityType selectedBuildingType = EntityType.CONVEYOR;

    ComponentMapper<PlayerComponent> playerMapper;
    ComponentMapper<LiftComponent> canLiftMapper;
    ComponentMapper<BodyComponent> bodyMapper;
    ComponentMapper<SectionComponent> sectionMapper;

    public BuildSystem(Engine engine) {
        this.engine = engine;

        playerMapper = ComponentMapper.getFor(PlayerComponent.class);
        canLiftMapper = ComponentMapper.getFor(LiftComponent.class);
        bodyMapper = ComponentMapper.getFor(BodyComponent.class);
        sectionMapper = ComponentMapper.getFor(SectionComponent.class);
    }

    @Override
    public void update(float delta) {
        Entity player = engine.getEntity(PlayerSystem.id);
        LiftComponent canLiftComp = canLiftMapper.get(player);
        if(canLiftComp.lifting || !InputSystem.rightMouse)
            return;

        Entity selectedEntity = buildSelected();
        BodyComponent bodyCompSelectedEntity = bodyMapper.get(selectedEntity);

        if(Utility.collidesWithBodies(bodyCompSelectedEntity, InputSystem.xMouseGrid, InputSystem.yMouseGrid)) {
            System.out.println("Can't build here.");
            return;
        }

        SectionComponent sectionCompActive = sectionMapper.get(World.getActiveSection());
        sectionCompActive.allEntities.add(selectedEntity);
        engine.addEntity(selectedEntity);
    }

    private Entity buildSelected() {
        switch(selectedBuildingType) {
            case CONVEYOR:
                return buildConveyor();
            default:
                System.out.println("Building something that doesn't exist.");
                return null;
        }
    }

    private Entity buildConveyor(){
        Entity entity = new Entity();
        ConveyorComponent conveyorComp = new ConveyorComponent();
        TextureComponent textureComp = new TextureComponent();
        BodyComponent bodyComp = new BodyComponent();
        TransformComponent transformComp = new TransformComponent();
        StoreEntityComponent storeEntityComp = new StoreEntityComponent();

        textureComp.textureName = "structureSheet/conveyor";
        textureComp.region = TextureAssets.getRegion(textureComp.textureName);
        textureComp.layer = Layer.MIDDLE;

        transformComp.position.set(InputSystem.xMouseGrid, InputSystem.yMouseGrid);

        bodyComp.body = new Rectangle(transformComp.position.x, transformComp.position.y, 32, 32);

        entity.add(conveyorComp);
        entity.add(textureComp);
        entity.add(bodyComp);
        entity.add(transformComp);
        entity.add(storeEntityComp);

        return entity;
    }
}
