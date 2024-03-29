package com.mightyquark.systems;

import com.mightyquark.Entity;
import com.mightyquark.Filter;
import com.badlogic.gdx.utils.Array;

/**
 * If you need to process entities at a certain interval then use this.
 *
 * A typical usage would be to regenerate ammo or health at certain intervals, no need to do that every game loop, but perhaps every 100 ms.
 * or every second.
 */
public abstract class IntervalEntityProcessingSystem extends IntervalEntitySystem {

    public IntervalEntityProcessingSystem(Filter filter, float interval) {
        super(filter, interval);
    }

    /**
     * Process a entity this system is interested in.
     * @param e the entity to process.
     */
    protected abstract void process(Entity e);


    @Override
    protected void processEntities(Array<Entity> entities) {
        for (int i = 0, s = entities.size; s > i; i++) {
            process(entities.get(i));
        }
    }

}
