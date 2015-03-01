package com.palestone.enigma.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.palestone.enigma.World;
import com.palestone.enigma.components.*;

public class LiftSystem extends IteratingSystem {
    private Engine engine;

    ComponentMapper<LiftComponent> canLiftMapper;
    ComponentMapper<LiftableComponent> liftableMapper;
    ComponentMapper<BodyComponent> bodyMapper;
    ComponentMapper<TransformComponent> transformMapper;
    ComponentMapper<TextureComponent> textureMapper;
    ComponentMapper<SectionComponent> sectionMapper;

    public LiftSystem(Engine engine) {
        super(Family.all(LiftComponent.class).get());

        this.engine = engine;
        canLiftMapper = ComponentMapper.getFor(LiftComponent.class);
        liftableMapper = ComponentMapper.getFor(LiftableComponent.class);
        bodyMapper = ComponentMapper.getFor(BodyComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        sectionMapper = ComponentMapper.getFor(SectionComponent.class);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        LiftComponent liftComp = canLiftMapper.get(entity);
        TransformComponent transformCompEntity = transformMapper.get(entity);

        if(liftComp.lifting) {
            //Update lifted Entity
            TransformComponent transformCompLiftedEntity = transformMapper.get(liftComp.entity);
            BodyComponent bodyCompLiftedEntity = bodyMapper.get(liftComp.entity);
            LiftableComponent liftableComp = liftableMapper.get(liftComp.entity);

            Family bodyFamily = Family.all(BodyComponent.class).get();
            ImmutableArray<Entity> bodyEntities = engine.getEntitiesFor(bodyFamily);

            Rectangle bodyNewPosition = new Rectangle(InputSystem.xMouseGrid, InputSystem.yMouseGrid,
                    bodyCompLiftedEntity.body.getWidth(), bodyCompLiftedEntity.body.getHeight());
            boolean collision = false;
            for(Entity bodyEntity : bodyEntities) {
                if(bodyEntity == liftComp.entity)
                    continue;

                BodyComponent bodyCompBodyEntity = bodyMapper.get(bodyEntity);
                if(bodyNewPosition.overlaps(bodyCompBodyEntity.body))
                    collision = true;
            }
            liftableComp.invisibleGhost = collision;

            if(liftableComp.ghost == null)
                createGhost(liftComp.entity);
            if(liftableComp.invisibleGhost)
                engine.removeEntity(liftableComp.ghost);
            else if(engine.getEntity(liftableComp.ghost.getId()) == null)
                engine.addEntity(liftableComp.ghost);

            TransformComponent transformCompGhost = transformMapper.get(liftableComp.ghost);

            transformCompLiftedEntity.position.set(transformCompEntity.position.x, transformCompEntity.position.y + 64);
            bodyCompLiftedEntity.body.setPosition(transformCompEntity.position.x, transformCompEntity.position.y + 64);
            transformCompGhost.position.set(InputSystem.xMouseGrid, InputSystem.yMouseGrid);

            if(InputSystem.leftMouse) {
                putDownEntity(liftComp, bodyCompLiftedEntity, transformCompLiftedEntity);
            }
            return;
        }
        pickUpEntity(entity,liftComp);
    }

    private void pickUpEntity(Entity entity, LiftComponent liftComp) {
        Family liftableFamily = Family.all(LiftableComponent.class).get();
        ImmutableArray<Entity> liftableEntities = engine.getEntitiesFor(liftableFamily);

        if(entity.getId() == PlayerSystem.id) {
            for(Entity liftableEntity : liftableEntities) {
                LiftableComponent liftableComp = liftableMapper.get(liftableEntity);
                BodyComponent bodyCompLiftableEntity = bodyMapper.get(liftableEntity);
                if(!liftableComp.lifted && bodyCompLiftableEntity.body.contains(InputSystem.xMouse, InputSystem.yMouse)
                        && InputSystem.leftMouse) {
                    SectionComponent sectionComp = sectionMapper.get(World.sections.get(World.activeSection));
                    TransformComponent transformCompLiftableEntity = transformMapper.get(liftableEntity);
                    TextureComponent textureCompLiftableEntity = textureMapper.get(liftableEntity);

                    sectionComp.allEntities.removeValue(liftableEntity, true);
                    liftComp.entity = liftableEntity;
                    liftComp.lifting = true;

                    liftableComp.lifted = true;
                    liftableComp.collides = bodyCompLiftableEntity.collides;

                    createGhost(liftableEntity);

                    bodyCompLiftableEntity.collides = false;
                }
            }
        }
    }

    private void putDownEntity(LiftComponent liftComp, BodyComponent bodyCompLiftedEntity, TransformComponent transformCompLiftedEntity) {

        Family bodyFamily = Family.all(BodyComponent.class).get();
        ImmutableArray<Entity> bodyEntities = engine.getEntitiesFor(bodyFamily);

        LiftableComponent liftableComp = liftableMapper.get(liftComp.entity);
        Rectangle bodyNewPosition = new Rectangle(InputSystem.xMouseGrid, InputSystem.yMouseGrid,
                bodyCompLiftedEntity.body.getWidth(), bodyCompLiftedEntity.body.getHeight());
        boolean collision = false;
        for(Entity bodyEntity : bodyEntities) {
            if(bodyEntity == liftComp.entity)
                continue;

            BodyComponent bodyCompBodyEntity = bodyMapper.get(bodyEntity);
            if(bodyNewPosition.overlaps(bodyCompBodyEntity.body))
                collision = true;
        }

        if(!collision) {
            engine.removeEntity(liftableComp.ghost);
            SectionComponent sectionComp = sectionMapper.get(World.sections.get(World.activeSection));
            liftComp.lifting = false;
            liftableComp.lifted = false;

            transformCompLiftedEntity.position.set(InputSystem.xMouseGrid, InputSystem.yMouseGrid);
            bodyCompLiftedEntity.collides = liftableComp.collides;
            bodyCompLiftedEntity.body.setPosition(transformCompLiftedEntity.position);

            sectionComp.allEntities.add(liftComp.entity);
            liftComp.entity = null;
        }
    }

    private void createGhost(Entity liftedEntity) {
        LiftableComponent liftableComp = liftableMapper.get(liftedEntity);
        TextureComponent textureCompLiftableEntity = textureMapper.get(liftedEntity);

        Entity ghost = new Entity();
        TextureComponent textureCompGhost = new TextureComponent();
        TransformComponent transformCompGhost = new TransformComponent();
        textureCompGhost.textureName = textureCompLiftableEntity.textureName;
        textureCompGhost.region = textureCompLiftableEntity.region;
        textureCompGhost.layer = textureCompLiftableEntity.layer;
        textureCompGhost.alpha = 0.3f;
        transformCompGhost.position.set(InputSystem.xMouseGrid, InputSystem.yMouseGrid);
        ghost.add(textureCompGhost);
        ghost.add(transformCompGhost);
        engine.addEntity(ghost);

        liftableComp.ghost = ghost;
    }
}
