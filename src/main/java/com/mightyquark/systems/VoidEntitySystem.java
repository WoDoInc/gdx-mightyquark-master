package com.mightyquark.systems;

import com.mightyquark.Entity;
import com.mightyquark.Filter;
import com.badlogic.gdx.utils.Array;

/**
 * This system has an empty filter so it processes no entities, but it still gets invoked. You can use this system if you need
 * to execute some game logic and not have to concern yourself about filters or entities.
 */
public abstract class VoidEntitySystem extends EntitySystem {

    public VoidEntitySystem() {
        super(Filter.getEmpty());
    }

    @Override
    protected final void processEntities(Array<Entity> entities) {
        processSystem();
    }

    /**
     * Processes the system.
     */
    protected abstract void processSystem();
}
