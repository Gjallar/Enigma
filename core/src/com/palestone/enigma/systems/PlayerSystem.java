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

    private Vector2 acceleration;
    private float deceleration = 0.2f;
    private float maxSpeed = 5f;

    private ComponentMapper<MovementComponent> movementMapper;
    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<TextureComponent> textureMapper;


    public PlayerSystem() {
        super(Family.getFor(PlayerComponent.class));

        acceleration = new Vector2();

        movementMapper = ComponentMapper.getFor(MovementComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        textureMapper = ComponentMapper.getFor(TextureComponent.class);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        acceleration.set(0, 0);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent movementComponent = movementMapper.get(entity);
        movementComponent.acceleration.set(acceleration);

        if (acceleration.x == 0) {
            if(!(movementComponent.velocity.x > -deceleration && movementComponent.velocity.x < deceleration)) {
                if (movementComponent.velocity.x > 0) {
                    movementComponent.velocity.x -= deceleration;
                }
                else {
                    movementComponent.velocity.x += deceleration;
                }
            }
            else {
                movementComponent.velocity.x = 0;
            }
        }
        if (acceleration.y == 0) {
            if(!(movementComponent.velocity.y > -deceleration && movementComponent.velocity.y < deceleration)) {
                if (movementComponent.velocity.y > 0) {
                    movementComponent.velocity.y -= deceleration;
                }
                else {
                    movementComponent.velocity.y += deceleration;
                }
            }
            else {
                movementComponent.velocity.y = 0;
            }
        }

        if (movementComponent.velocity.x < maxSpeed && movementComponent.velocity.x > -maxSpeed) {
            movementComponent.velocity.x += acceleration.x;
        }
        if (movementComponent.velocity.y < maxSpeed && movementComponent.velocity.y > -maxSpeed) {
            movementComponent.velocity.y += acceleration.y;
        }

        movementComponent.acceleration.set(new Vector2(0, 0));
        
        TransformComponent transformComponent = transformMapper.get(entity);
        transformComponent.position.add(movementComponent.velocity);
    }

    public void addAcceleration(Vector2 acceleration) {
        this.acceleration.add(acceleration);
    }
}
