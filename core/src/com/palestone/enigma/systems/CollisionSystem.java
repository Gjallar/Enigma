package com.palestone.enigma.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;
import com.palestone.enigma.components.*;

public class CollisionSystem extends IteratingSystem{
    private Engine engine;
    private boolean correctedX, correctedY;
    ComponentMapper<BodyComponent> bodyMapper;
    ComponentMapper<MovementComponent> movementMapper;
    ComponentMapper<TransformComponent> transformMapper;

    public CollisionSystem(Engine engine) {
        super(Family.all(BodyComponent.class, MovementComponent.class).get());
        this.engine = engine;

        bodyMapper = ComponentMapper.getFor(BodyComponent.class);
        movementMapper = ComponentMapper.getFor(MovementComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BodyComponent bodyCompEntity = bodyMapper.get(entity);
        if(!bodyCompEntity.collides)
            return;

        ImmutableArray<Entity> collidables = engine.getEntitiesFor(Family.all(BodyComponent.class)
                .exclude(MovementComponent.class).get());

        Array<Entity> collidingEntities = new Array<Entity>();
        MovementComponent entityMovementComp = movementMapper.get(entity);
        for(Entity collidable : collidables) {
            BodyComponent collidableCollisionComp = bodyMapper.get(collidable);
            if(!bodyCompEntity.body.overlaps(collidableCollisionComp.body))
                continue;

            collidingEntities.add(collidable);
        }

        if(collidingEntities.size == 0)
            return;

        Entity highestXOffsetEntity = getHighestXOffsetEntity(entity, collidingEntities);
        Entity highestYOffsetEntity = getHighestYOffsetEntity(entity, collidingEntities);

        correctPosition(entity, highestXOffsetEntity);
        correctPosition(entity, highestYOffsetEntity);

        if(correctedX)
            entityMovementComp.velocity.x = 0;
        if(correctedY)
            entityMovementComp.velocity.y = 0;

        correctedX = correctedY = false;
    }

    private Entity getHighestXOffsetEntity(Entity entity, Array<Entity> collidingEntities) {
        BodyComponent entityCollisionComp = bodyMapper.get(entity);
        MovementComponent entityMovementComp = movementMapper.get(entity);
        TransformComponent entityTransformComponent = transformMapper.get(entity);
        Entity highestXOffsetEntity = null;
        float xOffsetCurrent = 0;
        float xOffsetHighest = 0;
        for(Entity collidable : collidingEntities) {
            BodyComponent collidableCollisionComp = bodyMapper.get(collidable);
            TransformComponent collidableTransformComp = transformMapper.get(collidable);

            if(highestXOffsetEntity == null)
                highestXOffsetEntity = collidable;

            BodyComponent highestXCollisionComp = bodyMapper.get(highestXOffsetEntity);
            TransformComponent highestXTransformComp = transformMapper.get(highestXOffsetEntity);

            if(entityMovementComp.velocity.x > 0) {
                xOffsetHighest = entityTransformComponent.position.x + entityCollisionComp.body.width -
                        highestXTransformComp.position.x;
                xOffsetCurrent = entityTransformComponent.position.x + entityCollisionComp.body.width -
                        collidableTransformComp.position.x;
            }
            else if(entityMovementComp.velocity.x < 0) {
                xOffsetHighest = highestXTransformComp.position.x + highestXCollisionComp.body.width -
                        entityTransformComponent.position.x;
                xOffsetCurrent = collidableTransformComp.position.x + collidableCollisionComp.body.width -
                        entityTransformComponent.position.x;
            }

            if(entityMovementComp.velocity.x == 0)
                highestXOffsetEntity = null;
            else if(xOffsetCurrent > xOffsetHighest)
                highestXOffsetEntity = collidable;
        }
        return highestXOffsetEntity;
    }

    private Entity getHighestYOffsetEntity(Entity entity, Array<Entity> collidingEntities) {
        BodyComponent entityCollisionComp = bodyMapper.get(entity);
        MovementComponent entityMovementComp = movementMapper.get(entity);
        TransformComponent entityTransformComponent = transformMapper.get(entity);
        Entity highestYOffsetEntity = null;
        float yOffsetCurrent = 0;
        float yOffsetHighest = 0;
        for(Entity collidable : collidingEntities) {
            BodyComponent collidableCollisionComp = bodyMapper.get(collidable);
            TransformComponent collidableTransformComp = transformMapper.get(collidable);

            if(highestYOffsetEntity == null)
                highestYOffsetEntity = collidable;

            BodyComponent highestYCollisionComp = bodyMapper.get(highestYOffsetEntity);
            TransformComponent highestYTransformComp = transformMapper.get(highestYOffsetEntity);

            if(entityMovementComp.velocity.y > 0) {
                yOffsetHighest = entityTransformComponent.position.y + entityCollisionComp.body.height -
                        highestYTransformComp.position.y;
                yOffsetCurrent = entityTransformComponent.position.y + entityCollisionComp.body.height -
                        collidableTransformComp.position.y;
            }
            else if(entityMovementComp.velocity.y < 0) {
                yOffsetHighest = highestYTransformComp.position.y + highestYCollisionComp.body.height -
                        entityTransformComponent.position.y;
                yOffsetCurrent = collidableTransformComp.position.y + collidableCollisionComp.body.height -
                        entityTransformComponent.position.y;
            }

            if(entityMovementComp.velocity.y == 0)
                highestYOffsetEntity = null;
            else if(yOffsetCurrent > yOffsetHighest)
                highestYOffsetEntity = collidable;
        }
        return highestYOffsetEntity;
    }

    private void correctPosition(Entity entity, Entity collidingEntity) {
        float xOffset = 10000;
        float yOffset = 10000;

        if(collidingEntity == null)
            return;

        MovementComponent entityMovementComp = movementMapper.get(entity);
        TransformComponent entityTransformComp = transformMapper.get(entity);
        BodyComponent entityCollisionComp = bodyMapper.get(entity);

        TransformComponent collidingEntityTransformComp = transformMapper.get(collidingEntity);
        BodyComponent collidingEntityCollisionComp = bodyMapper.get(collidingEntity);

        if(entityMovementComp.velocity.x > 0)
            xOffset = entityTransformComp.position.x + entityCollisionComp.body.width -
                    collidingEntityTransformComp.position.x;
        else if(entityMovementComp.velocity.x < 0)
            xOffset = collidingEntityTransformComp.position.x + collidingEntityCollisionComp.body.width -
                    entityTransformComp.position.x;

        if(entityMovementComp.velocity.y > 0)
            yOffset = entityTransformComp.position.y + entityCollisionComp.body.height -
                    collidingEntityTransformComp.position.y;
        else if(entityMovementComp.velocity.y < 0)
            yOffset = collidingEntityTransformComp.position.y + collidingEntityCollisionComp.body.height -
                    entityTransformComp.position.y;

        if(yOffset > xOffset) {
            if(entityMovementComp.velocity.x > 0)
                entityTransformComp.position.x = entityTransformComp.position.x - xOffset;
            else if(entityMovementComp.velocity.x < 0)
                entityTransformComp.position.x = entityTransformComp.position.x + xOffset;
            correctedX = true;
        }
        else if(xOffset > yOffset) {
            if(entityMovementComp.velocity.y > 0)
                entityTransformComp.position.y = entityTransformComp.position.y - yOffset;
            else if(entityMovementComp.velocity.y < 0)
                entityTransformComp.position.y = entityTransformComp.position.y + yOffset;
            correctedY = true;
        }
    }
}
