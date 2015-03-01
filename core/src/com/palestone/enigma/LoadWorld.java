package com.palestone.enigma;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.palestone.enigma.components.*;
import com.palestone.enigma.JsonClasses.*;

public class LoadWorld {

    ComponentMapper<SerializableComponent> serializableMapper;
    ComponentMapper<BodyComponent> bodyMapper;
    ComponentMapper<TransformComponent> transformMapper;
    ComponentMapper<TextureComponent> textureMapper;
    ComponentMapper<SectionComponent> sectionMapper;
    ComponentMapper<DoorComponent> doorMapper;
    ComponentMapper<LiftableComponent> liftableMapper;
    ComponentMapper<LiftComponent> canLiftMapper;

    public LoadWorld() {
        serializableMapper = ComponentMapper.getFor(SerializableComponent.class);
        bodyMapper = ComponentMapper.getFor(BodyComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        sectionMapper = ComponentMapper.getFor(SectionComponent.class);
        doorMapper = ComponentMapper.getFor(DoorComponent.class);
        liftableMapper = ComponentMapper.getFor(LiftableComponent.class);
        canLiftMapper = ComponentMapper.getFor(LiftComponent.class);
    }

    public Entity generateEntity(JsonEntity jsonEntity) {
        switch(jsonEntity.type) {
            case GROUND:
                return loadGround((JsonGround) jsonEntity);
            case ENVIRONMENT:
                return loadEnvironment((JsonEnvironment) jsonEntity);
            case DOOR:
                return loadDoor((JsonDoor) jsonEntity);
            case LIFTABLE:
                return loadLiftable((JsonLiftable) jsonEntity);
        }
        return null;
    }

    public Entity loadPlayer(JsonPlayer jsonPlayer) {
        Entity entity = new Entity();

        SerializableComponent serializableComp = new SerializableComponent();
        BodyComponent bodyComp = new BodyComponent();
        MovementComponent movementComp = new MovementComponent();
        TransformComponent transformComp = new TransformComponent();
        TextureComponent textureComp = new TextureComponent();
        PlayerComponent playerComp = new PlayerComponent();
        LiftComponent canLiftComp = new LiftComponent();

        serializableComp.entityType = jsonPlayer.type;

        textureComp.textureName = jsonPlayer.textureName;
        textureComp.region.setRegion(TextureAssets.getRegion(textureComp.textureName));
        textureComp.layer = jsonPlayer.layer;

        transformComp.position.set(jsonPlayer.x, jsonPlayer.y);

        bodyComp.collides = jsonPlayer.collides;
        bodyComp.body = new Rectangle(transformComp.position.x, transformComp.position.y,
                32, 32);

        canLiftComp.lifting = jsonPlayer.lifting;
        if(jsonPlayer.lifting) {
            Entity liftable = generateEntity(jsonPlayer.liftable);
            canLiftComp.entity = liftable;
        }

        entity.add(serializableComp);
        entity.add(movementComp);
        entity.add(transformComp);
        entity.add(textureComp);
        entity.add(playerComp);
        entity.add(bodyComp);
        entity.add(canLiftComp);
        return entity;
    }

    public Entity loadGround(JsonGround jsonGround) {
        Entity entity = new Entity();

        SerializableComponent serializableComp = new SerializableComponent();
        GroundTileComponent groundTileComp = new GroundTileComponent();
        TransformComponent transformComp = new TransformComponent();
        TextureComponent textureComp = new TextureComponent();

        serializableComp.entityType = jsonGround.type;

        textureComp.textureName = jsonGround.textureName;
        textureComp.region.setRegion(TextureAssets.getRegion(jsonGround.textureName));
        textureComp.layer = jsonGround.layer;

        transformComp.position.set(jsonGround.x, jsonGround.y);

        entity.add(serializableComp);
        entity.add(serializableComp);
        entity.add(groundTileComp);
        entity.add(transformComp);
        entity.add(textureComp);

        return  entity;
    }

    public Entity loadEnvironment(JsonEnvironment jsonEnvironment) {
        Entity entity = new Entity();

        SerializableComponent serializableComp = new SerializableComponent();
        EnvironmentTileComponent environmentTileComp = new EnvironmentTileComponent();
        TransformComponent transformComp = new TransformComponent();
        TextureComponent textureComp = new TextureComponent();
        BodyComponent bodyComp = new BodyComponent();

        serializableComp.entityType = jsonEnvironment.type;

        textureComp.textureName = jsonEnvironment.textureName;
        textureComp.region.setRegion(TextureAssets.getRegion(textureComp.textureName));
        textureComp.layer = jsonEnvironment.layer;


        transformComp.position.set(jsonEnvironment.x, jsonEnvironment.y);

        bodyComp.collides = jsonEnvironment.collides;
        bodyComp.body = new Rectangle(transformComp.position.x, transformComp.position.y,
                textureComp.region.getRegionWidth(), textureComp.region.getRegionHeight());

        entity.add(serializableComp);
        entity.add(transformComp);
        entity.add(textureComp);
        entity.add(bodyComp);
        entity.add(environmentTileComp);

        return entity;
    }

    public Entity loadDoor(JsonDoor jsonDoor) {
        Entity entity = new Entity();

        SerializableComponent serializableComp = new SerializableComponent();
        DoorComponent doorComp = new DoorComponent();
        BodyComponent bodyComp = new BodyComponent();
        TransformComponent transformComp = new TransformComponent();
        TextureComponent textureComp = new TextureComponent();

        serializableComp.entityType = jsonDoor.type;

        doorComp.linkedSectionKey = jsonDoor.linkedSectionKey;

        textureComp.textureName = jsonDoor.textureName;
        textureComp.region.setRegion(TextureAssets.getRegion(textureComp.textureName));
        textureComp.layer = jsonDoor.layer;

        transformComp.position.set(jsonDoor.x, jsonDoor.y);

        bodyComp.collides = jsonDoor.collides;
        bodyComp.body = new Rectangle(transformComp.position.x, transformComp.position.y,
                textureComp.region.getRegionWidth(), textureComp.region.getRegionHeight());

        entity.add(serializableComp);
        entity.add(transformComp);
        entity.add(textureComp);
        entity.add(doorComp);
        entity.add(bodyComp);

        return entity;
    }


    public Entity loadLiftable(JsonLiftable jsonLiftable) {
        Entity entity = new Entity();

        SerializableComponent serializableComp = new SerializableComponent();
        LiftableComponent liftableComp = new LiftableComponent();
        BodyComponent bodyComp = new BodyComponent();
        TransformComponent transformComp = new TransformComponent();
        TextureComponent textureComp = new TextureComponent();

        serializableComp.entityType = jsonLiftable.type;

        liftableComp.lifted = jsonLiftable.lifted;
        liftableComp.collides = jsonLiftable.liftedCollides;

        textureComp.textureName = jsonLiftable.textureName;
        textureComp.region.setRegion(TextureAssets.getRegion(textureComp.textureName));
        textureComp.layer = jsonLiftable.layer;

        transformComp.position.set(jsonLiftable.x, jsonLiftable.y);

        bodyComp.collides = jsonLiftable.collides;
        bodyComp.body = new Rectangle(transformComp.position.x, transformComp.position.y,
                textureComp.region.getRegionWidth(), textureComp.region.getRegionHeight());

        entity.add(serializableComp);
        entity.add(transformComp);
        entity.add(textureComp);
        entity.add(liftableComp);
        entity.add(bodyComp);

        return entity;
    }
}
