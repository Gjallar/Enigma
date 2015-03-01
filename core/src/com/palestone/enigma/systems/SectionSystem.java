package com.palestone.enigma.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.palestone.enigma.TextureAssets;
import com.palestone.enigma.Tile;
import com.palestone.enigma.components.*;
import com.palestone.enigma.enums.EntityType;
import com.palestone.enigma.enums.Layer;

public class SectionSystem extends IteratingSystem {
    private Engine engine;

    private ComponentMapper<SectionComponent> sectionMapper;

    //Debug
    private int size = 24;

    public SectionSystem(Engine engine) {
        super(Family.all(SectionComponent.class).get());

        this.engine = engine;

        sectionMapper = ComponentMapper.getFor(SectionComponent.class);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SectionComponent sectionComp = sectionMapper.get(entity);

        if(!sectionComp.generated) {
            generateSection(sectionComp);
            //populateTileGrid(sectionComp);
        }
    }

    private void generateSection(SectionComponent sectionComp) {
        sectionComp.generated = true;

        generateGround(sectionComp);
        generateWalls(sectionComp);
        generateDoors(sectionComp);
        generateLiftables(sectionComp);
    }

    private void generateGround(SectionComponent sectionComp) {
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                Entity entity = new Entity();

                SerializableComponent serializableComp = new SerializableComponent();
                GroundTileComponent groundTileComponent = new GroundTileComponent();
                TransformComponent transformComponent = new TransformComponent();
                TextureComponent textureComponent = new TextureComponent();

                serializableComp.entityType = EntityType.GROUND;

                int chance = MathUtils.random(2);
                switch(chance) {
                    case 0:
                        textureComponent.textureName = "structureSheet/ground0";
                        break;
                    case 1:
                        textureComponent.textureName = "structureSheet/ground1";
                        break;
                    case 2:
                        textureComponent.textureName = "structureSheet/ground2";
                        break;
                }
                textureComponent.region.setRegion(TextureAssets.getRegion(textureComponent.textureName));
                textureComponent.layer = Layer.BACKGROUND;

                transformComponent.position.set(x * 32, y * 32);

                entity.add(serializableComp);
                entity.add(groundTileComponent);
                entity.add(transformComponent);
                entity.add(textureComponent);

                engine.addEntity(entity);
                sectionComp.allEntities.add(entity);
            }
        }
    }

    private void generateWalls(SectionComponent sectionComp) {
        for(int i = 0; i < size; i++) {

            Entity entity2 = new Entity();

            SerializableComponent serializableComp = new SerializableComponent();
            EnvironmentTileComponent environmentTileComponent2 = new EnvironmentTileComponent();
            TransformComponent transformComponent2 = new TransformComponent();
            TextureComponent textureComponent2 = new TextureComponent();
            BodyComponent bodyComponent2 = new BodyComponent();

            serializableComp.entityType = EntityType.ENVIRONMENT;

            textureComponent2.textureName = "structureSheet/wall";
            textureComponent2.region.setRegion(TextureAssets.getRegion(textureComponent2.textureName));
            textureComponent2.layer = Layer.MIDDLE;

            transformComponent2.position.set(0, i * 32);

            bodyComponent2.collides = true;
            bodyComponent2.body = new Rectangle(transformComponent2.position.x, transformComponent2.position.y,
                    textureComponent2.region.getRegionWidth(), textureComponent2.region.getRegionHeight() / 2);

            entity2.add(serializableComp);
            entity2.add(transformComponent2);
            entity2.add(textureComponent2);
            entity2.add(bodyComponent2);
            entity2.add(environmentTileComponent2);

            engine.addEntity(entity2);
            sectionComp.allEntities.add(entity2);
        }

        for(int i = 0; i < size; i++) {

            Entity entity2 = new Entity();

            SerializableComponent serializableComp = new SerializableComponent();
            EnvironmentTileComponent environmentTileComponent2 = new EnvironmentTileComponent();
            TransformComponent transformComponent2 = new TransformComponent();
            TextureComponent textureComponent2 = new TextureComponent();
            BodyComponent bodyComponent2 = new BodyComponent();

            serializableComp.entityType = EntityType.ENVIRONMENT;

            textureComponent2.textureName = "structureSheet/wall";
            textureComponent2.region.setRegion(TextureAssets.getRegion(textureComponent2.textureName));
            textureComponent2.layer = Layer.MIDDLE;

            transformComponent2.position.set((size-1) * 32, i * 32);

            bodyComponent2.collides = true;
            bodyComponent2.body = new Rectangle(transformComponent2.position.x, transformComponent2.position.y,
                    textureComponent2.region.getRegionWidth(), textureComponent2.region.getRegionHeight() / 2);

            entity2.add(serializableComp);
            entity2.add(transformComponent2);
            entity2.add(textureComponent2);
            entity2.add(bodyComponent2);
            entity2.add(environmentTileComponent2);

            engine.addEntity(entity2);
            sectionComp.allEntities.add(entity2);
        }

        for(int i = 1; i < size-1; i++) {

            Entity entity2 = new Entity();

            SerializableComponent serializableComp = new SerializableComponent();
            EnvironmentTileComponent environmentTileComponent2 = new EnvironmentTileComponent();
            TransformComponent transformComponent2 = new TransformComponent();
            TextureComponent textureComponent2 = new TextureComponent();
            BodyComponent bodyComponent2 = new BodyComponent();

            serializableComp.entityType = EntityType.ENVIRONMENT;

            textureComponent2.textureName = "structureSheet/wall";
            textureComponent2.region.setRegion(TextureAssets.getRegion(textureComponent2.textureName));
            textureComponent2.layer = Layer.MIDDLE;

            transformComponent2.position.set(i * 32, 0);

            bodyComponent2.collides = true;
            bodyComponent2.body = new Rectangle(transformComponent2.position.x, transformComponent2.position.y,
                    textureComponent2.region.getRegionWidth(), textureComponent2.region.getRegionHeight() / 2);

            entity2.add(serializableComp);
            entity2.add(transformComponent2);
            entity2.add(textureComponent2);
            entity2.add(bodyComponent2);
            entity2.add(environmentTileComponent2);

            engine.addEntity(entity2);
            sectionComp.allEntities.add(entity2);
        }

        for(int i = 1; i < size-1; i++) {

            Entity entity2 = new Entity();

            SerializableComponent serializableComp = new SerializableComponent();
            EnvironmentTileComponent environmentTileComponent2 = new EnvironmentTileComponent();
            TransformComponent transformComponent2 = new TransformComponent();
            TextureComponent textureComponent2 = new TextureComponent();
            BodyComponent bodyComponent2 = new BodyComponent();

            serializableComp.entityType = EntityType.ENVIRONMENT;

            textureComponent2.textureName = "structureSheet/wall";
            textureComponent2.region.setRegion(TextureAssets.getRegion(textureComponent2.textureName));
            textureComponent2.layer = Layer.MIDDLE;

            bodyComponent2.collides = true;
            transformComponent2.position.set(i * 32, (size-1) * 32);

            bodyComponent2.body = new Rectangle(transformComponent2.position.x, transformComponent2.position.y,
                    textureComponent2.region.getRegionWidth(), textureComponent2.region.getRegionHeight() / 2);

            entity2.add(serializableComp);
            entity2.add(transformComponent2);
            entity2.add(textureComponent2);
            entity2.add(bodyComponent2);
            entity2.add(environmentTileComponent2);

            engine.addEntity(entity2);
            sectionComp.allEntities.add(entity2);
        }
    }

    private void generateDoors(SectionComponent sectionComp) {
        Entity door = new Entity();

        SerializableComponent serializableComp = new SerializableComponent();
        BodyComponent bodyComp = new BodyComponent();
        DoorComponent doorComponent = new DoorComponent();
        TransformComponent transformComponentD = new TransformComponent();
        TextureComponent textureComponentD = new TextureComponent();

        serializableComp.entityType = EntityType.DOOR;

        textureComponentD.textureName = "structureSheet/door";
        textureComponentD.region.setRegion(TextureAssets.getRegion(textureComponentD.textureName));
        textureComponentD.layer = Layer.MIDDLE;

        transformComponentD.position.set(48, (size/2-1) * 32 + 16);

        bodyComp.body = new Rectangle(transformComponentD.position.x, transformComponentD.position.y,
                textureComponentD.region.getRegionWidth(), textureComponentD.region.getRegionHeight());

        if(sectionComp.initialSection)
            doorComponent.linkedSectionKey = new Vector2(-1, 0);
        else
            doorComponent.linkedSectionKey = new Vector2(0, 0);

        door.add(serializableComp);
        door.add(doorComponent);
        door.add(transformComponentD);
        door.add(textureComponentD);
        door.add(bodyComp);

        engine.addEntity(door);
        sectionComp.allEntities.add(door);
    }

    private void generateLiftables(SectionComponent sectionComp) {
        Entity liftable = new Entity();

        SerializableComponent serializableComp = new SerializableComponent();
        LiftableComponent liftableComp = new LiftableComponent();
        TransformComponent transformComponent = new TransformComponent();
        TextureComponent textureComponent = new TextureComponent();
        BodyComponent bodyComponent = new BodyComponent();

        serializableComp.entityType = EntityType.LIFTABLE;

        textureComponent.textureName = "structureSheet/liftable";
        textureComponent.region.setRegion(TextureAssets.getRegion(textureComponent.textureName));
        textureComponent.layer = Layer.MIDDLE;

        transformComponent.position.set(128, (size/2-1) * 32 + 16);

        bodyComponent.collides = true;
        bodyComponent.body = new Rectangle(transformComponent.position.x, transformComponent.position.y,
                textureComponent.region.getRegionWidth(), textureComponent.region.getRegionHeight());

        bodyComponent.collides = true;
        bodyComponent.body = new Rectangle(transformComponent.position.x, transformComponent.position.y,
                textureComponent.region.getRegionWidth(), textureComponent.region.getRegionHeight());

        liftable.add(serializableComp);
        liftable.add(liftableComp);
        liftable.add(transformComponent);
        liftable.add(textureComponent);
        liftable.add(bodyComponent);

        engine.addEntity(liftable);
        sectionComp.allEntities.add(liftable);
    }

    private void populateTileGrid(SectionComponent sectionComp) {
        sectionComp.tileGrid = new Tile[32][32];
        for(int y = 0; y < 32; y++) {
            for(int x = 0; x < 32; x++) {
                sectionComp.tileGrid[x][y] = new Tile();
            }
        }
    }
}
