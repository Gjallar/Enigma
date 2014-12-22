package com.palestone.enigma.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.palestone.enigma.World;
import com.palestone.enigma.components.MovementComponent;
import com.palestone.enigma.components.PlayerComponent;
import com.palestone.enigma.components.TextureComponent;
import com.palestone.enigma.components.TransformComponent;

public class PlayerSystem extends IteratingSystem{

    private boolean walkingUp, walkingDown, walkingLeft, walkingRight;
    private float accelerationAmount = 20f;
    private float decelerationAmount = 20f;
    private float maxSpeed = 200f;

    private ComponentMapper<MovementComponent> movementMapper;
    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<TextureComponent> textureMapper;


    public PlayerSystem() {
        super(Family.getFor(PlayerComponent.class));

        movementMapper = ComponentMapper.getFor(MovementComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        textureMapper = ComponentMapper.getFor(TextureComponent.class);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    protected void processEntity(Entity entity, float delta) {
        MovementComponent movementComponent = movementMapper.get(entity);

        if (!walkingRight && !walkingLeft) {
            if(!(movementComponent.velocity.x > -(decelerationAmount * delta) && movementComponent.velocity.x < (decelerationAmount * delta))) {
                if (movementComponent.velocity.x > 0) {
                    movementComponent.velocity.x -= (decelerationAmount * delta);
                }
                else {
                    movementComponent.velocity.x += (decelerationAmount * delta);
                }
            }
            else {
                movementComponent.velocity.x = 0;
            }
        }
        if (!walkingUp && !walkingDown) {
            if(!(movementComponent.velocity.y > -(decelerationAmount * delta) && movementComponent.velocity.y < (decelerationAmount * delta))) {
                if (movementComponent.velocity.y > 0) {
                    movementComponent.velocity.y -= (decelerationAmount * delta);
                }
                else {
                    movementComponent.velocity.y += (decelerationAmount * delta);
                }
            }
            else {
                movementComponent.velocity.y = 0;
            }
        }

        if(walkingUp) {
            movementComponent.acceleration.add(0, accelerationAmount * delta);
        }
        if(walkingDown) {
            movementComponent.acceleration.add(0, -accelerationAmount * delta);
        }
        if(walkingRight) {
            movementComponent.acceleration.add(accelerationAmount * delta, 0);
        }
        if(walkingLeft) {
            movementComponent.acceleration.add(-accelerationAmount * delta, 0);
        }

        movementComponent.velocity.add(movementComponent.acceleration);
        movementComponent.velocity.limit(maxSpeed * delta);

        movementComponent.acceleration.set(0, 0);

        TransformComponent transformComponent = transformMapper.get(entity);
        transformComponent.position.add(movementComponent.velocity);
    }

    public boolean isWalkingUp() {
        return walkingUp;
    }

    public void setWalkingUp(boolean walkingUp) {
        this.walkingUp = walkingUp;
    }

    public boolean isWalkingDown() {
        return walkingDown;
    }

    public void setWalkingDown(boolean walkingDown) {
        this.walkingDown = walkingDown;
    }

    public boolean isWalkingLeft() {
        return walkingLeft;
    }

    public void setWalkingLeft(boolean walkingLeft) {
        this.walkingLeft = walkingLeft;
    }

    public boolean isWalkingRight() {
        return walkingRight;
    }

    public void setWalkingRight(boolean walkingRight) {
        this.walkingRight = walkingRight;
    }
}
