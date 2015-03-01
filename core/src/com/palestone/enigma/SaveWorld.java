package com.palestone.enigma;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.palestone.enigma.components.*;
import com.palestone.enigma.JsonClasses.*;

public class SaveWorld {

    ComponentMapper<SerializableComponent> serializableMapper;
    ComponentMapper<BodyComponent> bodyMapper;
    ComponentMapper<TransformComponent> transformMapper;
    ComponentMapper<TextureComponent> textureMapper;
    ComponentMapper<SectionComponent> sectionMapper;
    ComponentMapper<DoorComponent> doorMapper;
    ComponentMapper<LiftableComponent> liftableMapper;
    ComponentMapper<LiftComponent> canLiftMapper;

    public SaveWorld() {
        serializableMapper = ComponentMapper.getFor(SerializableComponent.class);
        bodyMapper = ComponentMapper.getFor(BodyComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        sectionMapper = ComponentMapper.getFor(SectionComponent.class);
        doorMapper = ComponentMapper.getFor(DoorComponent.class);
        liftableMapper = ComponentMapper.getFor(LiftableComponent.class);
        canLiftMapper = ComponentMapper.getFor(LiftComponent.class);
    }

    public JsonEntity generateJsonEntity(Entity entity) {
        SerializableComponent serializableComp = serializableMapper.get(entity);

        switch(serializableComp.entityType) {
            case GROUND:
                return saveGround(entity);
            case ENVIRONMENT:
                return saveEnvironment(entity);
            case DOOR:
                return saveDoor(entity);
            case LIFTABLE:
                return saveLiftable(entity);
        }
        return null;
    }

    public JsonPlayer savePlayer(Entity entity) {
        SerializableComponent serializableComp = serializableMapper.get(entity);
        TransformComponent transformComp = transformMapper.get(entity);
        TextureComponent textureComp = textureMapper.get(entity);
        LiftComponent canLiftComp = canLiftMapper.get(entity);
        BodyComponent bodyComp = bodyMapper.get(entity);
        JsonPlayer jsonPlayer = new JsonPlayer();
        jsonPlayer.type = serializableComp.entityType;
        jsonPlayer.x = transformComp.position.x;
        jsonPlayer.y = transformComp.position.y;
        jsonPlayer.textureName = textureComp.textureName;
        jsonPlayer.layer = textureComp.layer;
        jsonPlayer.collides = bodyComp.collides;
        jsonPlayer.lifting = canLiftComp.lifting;

        return jsonPlayer;
    }

    public JsonGround saveGround(Entity entity) {
        SerializableComponent serializableComp = serializableMapper.get(entity);
        TransformComponent transformComp = transformMapper.get(entity);
        TextureComponent textureComp = textureMapper.get(entity);
        JsonGround jsonGround = new JsonGround();
        jsonGround.type = serializableComp.entityType;
        jsonGround.x = transformComp.position.x;
        jsonGround.y = transformComp.position.y;
        jsonGround.textureName = textureComp.textureName;
        jsonGround.layer = textureComp.layer;

        return jsonGround;
    }

    public JsonEnvironment saveEnvironment(Entity entity) {
        SerializableComponent serializableComp = serializableMapper.get(entity);
        BodyComponent bodyComp = bodyMapper.get(entity);
        TransformComponent transformComp = transformMapper.get(entity);
        TextureComponent textureComp = textureMapper.get(entity);
        JsonEnvironment jsonEnvironment = new JsonEnvironment();
        jsonEnvironment.type = serializableComp.entityType;
        jsonEnvironment.x = transformComp.position.x;
        jsonEnvironment.y = transformComp.position.y;
        jsonEnvironment.textureName = textureComp.textureName;
        jsonEnvironment.layer = textureComp.layer;
        jsonEnvironment.collides = bodyComp.collides;

        return jsonEnvironment;
    }

    public JsonDoor saveDoor(Entity entity) {
        SerializableComponent serializableComp = serializableMapper.get(entity);
        BodyComponent bodyComp = bodyMapper.get(entity);
        DoorComponent doorComp = doorMapper.get(entity);
        TransformComponent transformComp = transformMapper.get(entity);
        TextureComponent textureComp = textureMapper.get(entity);
        JsonDoor jsonDoor = new JsonDoor();
        jsonDoor.type = serializableComp.entityType;
        jsonDoor.x = transformComp.position.x;
        jsonDoor.y = transformComp.position.y;
        jsonDoor.textureName = textureComp.textureName;
        jsonDoor.layer = textureComp.layer;
        jsonDoor.linkedSectionKey = doorComp.linkedSectionKey;
        jsonDoor.collides = bodyComp.collides;

        return jsonDoor;
    }

    public JsonLiftable saveLiftable(Entity entity) {
        SerializableComponent serializableComp = serializableMapper.get(entity);
        BodyComponent bodyComp = bodyMapper.get(entity);
        LiftableComponent liftableComp = liftableMapper.get(entity);
        TransformComponent transformComp = transformMapper.get(entity);
        TextureComponent textureComp = textureMapper.get(entity);
        JsonLiftable jsonLiftable = new JsonLiftable();
        jsonLiftable.type = serializableComp.entityType;
        jsonLiftable.x = transformComp.position.x;
        jsonLiftable.y = transformComp.position.y;
        jsonLiftable.textureName = textureComp.textureName;
        jsonLiftable.layer = textureComp.layer;
        jsonLiftable.collides = bodyComp.collides;
        jsonLiftable.lifted = liftableComp.lifted;
        jsonLiftable.liftedCollides = liftableComp.collides;

        return jsonLiftable;
    }
}
