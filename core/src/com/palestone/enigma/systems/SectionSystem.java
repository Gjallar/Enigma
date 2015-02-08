package com.palestone.enigma.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.palestone.enigma.TextureAssets;
import com.palestone.enigma.Tile;
import com.palestone.enigma.components.*;
import com.palestone.enigma.enums.Layer;

public class SectionSystem extends IteratingSystem {
    private Engine engine;

    private ComponentMapper<SectionComponent> sectionMapper;

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

        Entity door = new Entity();

        DoorComponent doorComponent = new DoorComponent();
        TransformComponent transformComponentD = new TransformComponent();
        TextureComponent textureComponentD = new TextureComponent();

        textureComponentD.textureName = "door";
        textureComponentD.region.setRegion(TextureAssets.sectionMap.get(textureComponentD.textureName));
        textureComponentD.layer = Layer.MIDDLE;

        transformComponentD.position.set(32, 7 * 32 + 16);

        doorComponent.body = new Rectangle(transformComponentD.position.x, transformComponentD.position.y,
                textureComponentD.region.getRegionWidth(), textureComponentD.region.getRegionHeight());

        if(sectionComp.initialSection)
            doorComponent.linkedSectionKey = new Vector2(-1, 0);
        else
            doorComponent.linkedSectionKey = new Vector2(0, 0);

        door.add(doorComponent);
        door.add(transformComponentD);
        door.add(textureComponentD);

        engine.addEntity(door);
        sectionComp.doors.add(door);
        sectionComp.allEntities.add(door);

        for(int y = 0; y < 16; y++) {
            for(int x = 0; x < 16; x++) {
                Entity entity = new Entity();

                GroundTileComponent groundTileComponent = new GroundTileComponent();
                TransformComponent transformComponent = new TransformComponent();
                TextureComponent textureComponent = new TextureComponent();

                textureComponent.textureName = "ground";
                textureComponent.region.setRegion(TextureAssets.sectionMap.get(textureComponent.textureName));
                textureComponent.layer = Layer.BACKGROUND;

                transformComponent.position.set(x * 32, y * 32);

                entity.add(groundTileComponent);
                entity.add(transformComponent);
                entity.add(textureComponent);

                engine.addEntity(entity);
                sectionComp.groundTiles.add(entity);
                sectionComp.allEntities.add(entity);
            }
        }

        for(int i = 0; i < 16; i++) {

            Entity entity2 = new Entity();

            EnvironmentTileComponent environmentTileComponent2 = new EnvironmentTileComponent();
            TransformComponent transformComponent2 = new TransformComponent();
            TextureComponent textureComponent2 = new TextureComponent();
            CollisionComponent collisionComponent2 = new CollisionComponent();

            textureComponent2.textureName = "wall";
            textureComponent2.region.setRegion(TextureAssets.sectionMap.get(textureComponent2.textureName));
            textureComponent2.layer = Layer.MIDDLE;

            transformComponent2.position.set(0, i * 32);

            collisionComponent2.body = new Rectangle(transformComponent2.position.x, transformComponent2.position.y,
                    textureComponent2.region.getRegionWidth(), textureComponent2.region.getRegionHeight());

            entity2.add(transformComponent2);
            entity2.add(textureComponent2);
            entity2.add(collisionComponent2);
            entity2.add(environmentTileComponent2);

            engine.addEntity(entity2);
            sectionComp.environmentTiles.add(entity2);
            sectionComp.allEntities.add(entity2);
        }

        for(int i = 0; i < 16; i++) {

            Entity entity2 = new Entity();

            EnvironmentTileComponent environmentTileComponent2 = new EnvironmentTileComponent();
            TransformComponent transformComponent2 = new TransformComponent();
            TextureComponent textureComponent2 = new TextureComponent();
            CollisionComponent collisionComponent2 = new CollisionComponent();

            textureComponent2.textureName = "wall";
            textureComponent2.region.setRegion(TextureAssets.sectionMap.get(textureComponent2.textureName));
            textureComponent2.layer = Layer.MIDDLE;

            transformComponent2.position.set(15 * 32, i * 32);

            collisionComponent2.body = new Rectangle(transformComponent2.position.x, transformComponent2.position.y,
                    textureComponent2.region.getRegionWidth(), textureComponent2.region.getRegionHeight());

            entity2.add(transformComponent2);
            entity2.add(textureComponent2);
            entity2.add(collisionComponent2);
            entity2.add(environmentTileComponent2);

            engine.addEntity(entity2);
            sectionComp.environmentTiles.add(entity2);
            sectionComp.allEntities.add(entity2);
        }

        for(int i = 1; i < 15; i++) {

            Entity entity2 = new Entity();

            EnvironmentTileComponent environmentTileComponent2 = new EnvironmentTileComponent();
            TransformComponent transformComponent2 = new TransformComponent();
            TextureComponent textureComponent2 = new TextureComponent();
            CollisionComponent collisionComponent2 = new CollisionComponent();

            textureComponent2.textureName = "wall";
            textureComponent2.region.setRegion(TextureAssets.sectionMap.get(textureComponent2.textureName));
            textureComponent2.layer = Layer.MIDDLE;

            transformComponent2.position.set(i * 32, 0);

            collisionComponent2.body = new Rectangle(transformComponent2.position.x, transformComponent2.position.y,
                    textureComponent2.region.getRegionWidth(), textureComponent2.region.getRegionHeight());

            entity2.add(transformComponent2);
            entity2.add(textureComponent2);
            entity2.add(collisionComponent2);
            entity2.add(environmentTileComponent2);

            engine.addEntity(entity2);
            sectionComp.environmentTiles.add(entity2);
            sectionComp.allEntities.add(entity2);
        }

        for(int i = 1; i < 15; i++) {

            Entity entity2 = new Entity();

            EnvironmentTileComponent environmentTileComponent2 = new EnvironmentTileComponent();
            TransformComponent transformComponent2 = new TransformComponent();
            TextureComponent textureComponent2 = new TextureComponent();
            CollisionComponent collisionComponent2 = new CollisionComponent();

            textureComponent2.textureName = "wall";
            textureComponent2.region.setRegion(TextureAssets.sectionMap.get(textureComponent2.textureName));
            textureComponent2.layer = Layer.MIDDLE;

            transformComponent2.position.set(i * 32, 15 * 32);

            collisionComponent2.body = new Rectangle(transformComponent2.position.x, transformComponent2.position.y,
                    textureComponent2.region.getRegionWidth(), textureComponent2.region.getRegionHeight());

            entity2.add(transformComponent2);
            entity2.add(textureComponent2);
            entity2.add(collisionComponent2);
            entity2.add(environmentTileComponent2);

            engine.addEntity(entity2);
            sectionComp.environmentTiles.add(entity2);
            sectionComp.allEntities.add(entity2);
        }
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
