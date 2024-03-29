package com.mightyquark.systems;

import com.mightyquark.Filter;

/**
 * A system that processes entities at a interval in milliseconds.
 * A typical usage would be a collision system or physics system.
 */
public abstract class IntervalEntitySystem extends EntitySystem {
    protected float acc;
    protected float interval;

    public IntervalEntitySystem(Filter filter, float interval) {
        super(filter);
        this.interval = interval;
    }

    @Override
    protected boolean checkProcessing() {
        acc += world.getDelta();
        if(acc >= interval) {
            acc -= interval;
            return true;
        }
        return false;
    }

}
