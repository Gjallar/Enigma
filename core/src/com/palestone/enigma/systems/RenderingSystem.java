package com.palestone.enigma.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.palestone.enigma.EnigmaMain;
import com.palestone.enigma.TextureAssets;
import com.palestone.enigma.components.TextureComponent;
import com.palestone.enigma.components.TransformComponent;
import com.palestone.enigma.enums.Layer;

import java.util.Comparator;

public class RenderingSystem extends IteratingSystem{

    private EnigmaMain game;
    private Comparator<Entity> comparator;
    private Array<Array<Entity>> renderQueues;
    private Color tint;

    private ComponentMapper<TextureComponent> textureMapper;
    private ComponentMapper<TransformComponent> transformMapper;

    public RenderingSystem(EnigmaMain game) {
        super(Family.all(TextureComponent.class).get());

        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);

        renderQueues = new Array<Array<Entity>>();
        for(int i = 0; i < Layer.size; i++) {
            renderQueues.add(new Array<Entity>());
        }

        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity entityA, Entity entityB) {
                return (int) Math.signum(transformMapper.get(entityB).position.y -
                                         transformMapper.get(entityA).position.y);
            }
        };

        this.game = game;
        tint = new Color(1f, 1f, 1f, 1f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        for(Array<Entity> renderQueue : renderQueues) {
            renderQueue.sort(comparator);
        }

        game.getActiveScreen().getCamera().update();
        game.batch.setProjectionMatrix(game.getActiveScreen().getCamera().combined);
        game.batch.begin();

        for(Array<Entity> renderQueue : renderQueues) {
            for (Entity entity : renderQueue) {
                TextureComponent textureComponent = textureMapper.get(entity);
                TransformComponent transformComponent = transformMapper.get(entity);

                float xPos = transformComponent.position.x;
                float yPos = transformComponent.position.y;
                xPos = MathUtils.round(xPos);
                yPos = MathUtils.round(yPos);
                float originX = 0f;
                float originY = 0f;

                if(textureComponent.alpha != 1) {
                    tint.set(1f, 1f, 1f, textureComponent.alpha);
                }
                else {
                    tint.set(1f, 1f, 1f, 1f);
                }

                game.batch.setColor(tint);
                game.batch.draw(textureComponent.region,
                        xPos, yPos,
                        originX, originY,
                        textureComponent.region.getRegionWidth(), textureComponent.region.getRegionHeight(),
                        transformComponent.scale.x, transformComponent.scale.y,
                        MathUtils.radiansToDegrees * transformComponent.rotation);
            }
        }

        game.batch.end();

        for(Array<Entity> renderQueue : renderQueues) {
            renderQueue.clear();
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TextureComponent textureComponent = textureMapper.get(entity);

        renderQueues.get(textureComponent.layer.getNumVal()).add(entity);
    }
}
