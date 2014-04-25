package com.mightyquark.managers;

import com.mightyquark.Entity;
import com.mightyquark.EntityObserver;
import com.mightyquark.World;
import com.badlogic.gdx.utils.Disposable;

/**
 * A parent class for all managers in the World.
 */
public abstract class Manager implements EntityObserver, Disposable {
    protected World world;

    /**
     * This method is called during world.initialize().
     */
    public void initialize() {
    }

    /**
     * Sets the world this manager belongs to.
     * 
     * @param world The World instance.
     */
    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * @return Returns the world the manager belongs to.
     */
    protected World getWorld() {
        return world;
    }

    @Override
    public void added(Entity e) {
    }

    @Override
    public void changed(Entity e) {
    }

    @Override
    public void deleted(Entity e) {
    }

    @Override
    public void disabled(Entity e) {
    }

    @Override
    public void enabled(Entity e) {
    }
}
