package com.mightyquark.systems;

import com.mightyquark.Entity;
import com.mightyquark.Filter;
import com.badlogic.gdx.utils.Array;

/**
 * A typical entity system. Use this when you need to process entities possessing the
 * provided component types.
 */
public abstract class EntityProcessingSystem extends EntitySystem {

    public EntityProcessingSystem(Filter filter) {
        super(filter);
    }

    /**
     * Process a entity this system is interested in.
     * @param e the entity to process.
     */
    protected abstract void process(Entity e);

    @Override
    protected final void processEntities(Array<Entity> entities) {
        for (int i = 0, s = entities.size; s > i; i++) {
            process(entities.get(i));
        }
    }
}
