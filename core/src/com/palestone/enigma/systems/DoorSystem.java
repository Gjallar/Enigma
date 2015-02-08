package com.palestone.enigma.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.palestone.enigma.World;
import com.palestone.enigma.components.*;

public class DoorSystem extends IteratingSystem{
    private Engine engine;
    private World world;

    ComponentMapper<DoorComponent> doorMapper;
    ComponentMapper<SectionComponent> sectionMapper;
    ComponentMapper<CollisionComponent> collisionMapper;
    ComponentMapper<TransformComponent> transformMapper;

    public DoorSystem(Engine engine, World world) {
        super(Family.all(DoorComponent.class).get());

        this.engine = engine;
        this.world = world;
        doorMapper = ComponentMapper.getFor(DoorComponent.class);
        sectionMapper = ComponentMapper.getFor(SectionComponent.class);
        collisionMapper = ComponentMapper.getFor(CollisionComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Entity player = engine.getEntity(PlayerSystem.id);
        CollisionComponent collisionCompPlayer = collisionMapper.get(player);
        TransformComponent transformCompPlayer = transformMapper.get(player);

        DoorComponent doorCompDoor = doorMapper.get(entity);

        if(collisionCompPlayer.body.overlaps(doorCompDoor.body)) {
            Entity linkedSection = World.sections.get(doorCompDoor.linkedSectionKey);
            if(linkedSection == null) {
                world.removeActiveSection();
                transformCompPlayer.position.set(200, 200);
                collisionCompPlayer.body.setPosition(200, 200);

                Entity newSection = new Entity();

                SectionComponent sectionCompNewSection = new SectionComponent();
                sectionCompNewSection.tileChance = 2;
                sectionCompNewSection.initialSection = false;
                sectionCompNewSection.key = new Vector2(-1, 0);

                newSection.add(sectionCompNewSection);

                engine.addEntity(newSection);

                World.activeSection = sectionCompNewSection.key;
                World.sections.put(sectionCompNewSection.key, newSection);
            }
            else {
                SectionComponent sectionCompLinkedSection = sectionMapper.get(linkedSection);
                ComponentMapper<TextureComponent> textureMapper = ComponentMapper.getFor(TextureComponent.class);

                world.removeActiveSection();
                transformCompPlayer.position.set(200, 200);
                collisionCompPlayer.body.setPosition(200, 200);

                engine.addEntity(linkedSection);
                for(Entity sectionEntity : sectionCompLinkedSection.allEntities) {
                    TextureComponent texComp = textureMapper.get(sectionEntity);
                    engine.addEntity(sectionEntity);
                }

                World.activeSection = sectionCompLinkedSection.key;
            }
            System.out.println(World.activeSection);
        }
    }
}
