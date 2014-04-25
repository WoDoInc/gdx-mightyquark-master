package com.mightyquark.systems;

/**
 * A passive entity system that does not perform any actions. This is useful for a system that strictly processes events
 * or perhaps an Entity/Component factory system.
 */
public abstract class PassiveEntitySystem extends VoidEntitySystem {

    public PassiveEntitySystem() {
        super.setPassive(true);
    }

    @Override
    protected final void processSystem() {
    }
}
