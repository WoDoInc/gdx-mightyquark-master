package com.mightyquark.systems;

import junit.framework.Assert;

import org.junit.Test;

import com.mightyquark.Component;
import com.mightyquark.ComponentMapper;
import com.mightyquark.Entity;
import com.mightyquark.Filter;
import com.mightyquark.World;
import com.badlogic.gdx.utils.Array;

public class EntitySystemTest {

    static class ComponentA implements Component {
        @Override
        public void reset() {
        }
    }

    static class ComponentB implements Component {
        @Override
        public void reset() {
        }
    }

    static class TestSystem extends EntitySystem {

        ComponentMapper<ComponentA> aMapper;
        ComponentMapper<ComponentB> bMapper;

        int numEntities;

        @SuppressWarnings("unchecked")
        public TestSystem() {
            super(Filter.allComponents(ComponentA.class).any(ComponentB.class));
        }

        public void initalize() {
            aMapper = world.getMapper(ComponentA.class);
            bMapper = world.getMapper(ComponentB.class);
        }

        @Override
        protected void processEntities(Array<Entity> entities) {
            numEntities = entities.size;
        }
    }

    @Test
    public void testCheck() {
        World world = new World();
        TestSystem system = new TestSystem();
        world.setSystem(system);
        world.initialize();

        Entity e = world.createEntity();
        e.addComponent(world.createComponent(ComponentB.class));
        e.addToWorld();

        world.process();

        Assert.assertEquals(0, system.numEntities);

        e = world.createEntity();
        e.addComponent(world.createComponent(ComponentB.class));
        e.addComponent(world.createComponent(ComponentA.class));
        e.addToWorld();

        world.process();

        Assert.assertEquals(1, system.numEntities);
    }

}
