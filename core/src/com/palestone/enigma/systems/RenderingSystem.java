package com.palestone.enigma.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.palestone.enigma.TextureAssets;
import com.palestone.enigma.components.TextureComponent;
import com.palestone.enigma.components.TransformComponent;

import java.util.Comparator;

public class RenderingSystem extends IteratingSystem{

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private Comparator<Entity> comparator;
    private Array<Entity> renderQueue;

    private ComponentMapper<TextureComponent> textureMapper;
    private ComponentMapper<TransformComponent> transformMapper;

    public RenderingSystem(SpriteBatch batch, OrthographicCamera camera) {
        super(Family.getFor(TextureComponent.class));

        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);

        renderQueue = new Array<Entity>();

        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity entityA, Entity entityB) {
                return (int) Math.signum(transformMapper.get(entityB).position.y -
                                         transformMapper.get(entityA).position.y);
            }
        };

        this.batch = batch;
        this.camera = camera;

    }

    @Override
    public void update(float delta) {
        super.update(delta);

        renderQueue.sort(comparator);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for(Entity entity : renderQueue) {
            TextureComponent textureComponent = textureMapper.get(entity);
            TransformComponent transformComponent = transformMapper.get(entity);

            float originX = 0f;
            float originY = 0f;

            batch.draw(textureComponent.region,
                       transformComponent.position.x, transformComponent.position.y,
                       originX, originY,
                       textureComponent.region.getRegionWidth(), textureComponent.region.getRegionHeight(),
                       transformComponent.scale.x, transformComponent.scale.y,
                       MathUtils.radiansToDegrees * transformComponent.rotation);
        }

        batch.end();
        renderQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}
