package com.mightyquark.systems;

import com.mightyquark.Entity;
import com.mightyquark.Filter;
import com.badlogic.gdx.utils.Array;

/**
 * DEPRECATED This system is currently deprecated because of poor API and buggy
 * implementation beyond redemption. Use SkipEntityProcessingSystem instead.
 * 
 * The purpose of this class is to allow systems to execute at varying intervals.
 * 
 * An example system would be an ExpirationSystem, that deletes entities after a certain
 * lifetime. Instead of running a system that decrements a timeLeft value for each
 * entity, you can simply use this system to execute in a future at a time of the shortest
 * lived entity, and then reset the system to run at a time in a future at a time of the
 * shortest lived entity, etc.
 * 
 * Another example system would be an AnimationSystem. You know when you have to animate
 * a certain entity, e.g. in 300 milliseconds. So you can set the system to run in 300 ms.
 * to perform the animation.
 * 
 * This will save CPU cycles in some scenarios.
 * 
 * Implementation notes:
 * In order to start the system you need to override the inserted(Entity e) method,
 * look up the delay time from that entity and offer it to the system by using the
 * offerDelay(float delay) method.
 *
 * Also, when processing the entities you must also call offerDelay(float delay)
 * for all valid entities.
 */
@Deprecated
public abstract class DelayedEntityProcessingSystem extends EntitySystem {
    protected float delay;
    protected boolean running;
    protected float acc;

    public DelayedEntityProcessingSystem(Filter filter) {
        super(filter);
    }

    @Override
    protected final void processEntities(Array<Entity> entities) {
        delay = Float.MAX_VALUE;
        for (int i = 0, s = entities.size; s > i; i++) {
            Entity entity = entities.get(i);
            processDelta(entity, acc);
            float remaining = getRemainingDelay(entity);
            if(remaining <= 0) {
                processExpired(entity);
            } else {
                offerDelay(remaining);
            }
        }

        acc = 0;
        if (actives.size == 0) {
            stop();
        }
    }

    @Override
    protected void inserted(Entity e) {
        float delay = getRemainingDelay(e);
        processDelta(e, -acc);
        if(delay > 0) {
            offerDelay(delay);
        }
    }

    /**
     * Return the delay until this entity should be processed.
     * 
     * @param e entity
     * @return delay
     */
    protected abstract float getRemainingDelay(Entity e);

    @Override
    protected final boolean checkProcessing() {
        if(running) {
            acc += world.getDelta();

            if(acc >= delay) {
                return true;
            }
        }
        return false;
    }


    /**
     * Process a entity this system is interested in. Substract the accumulatedDelta
     * from the entities defined delay.
     * 
     * @param e the entity to process.
     * @param accumulatedDelta the delta time since this system was last executed.
     */
    protected abstract void processDelta(Entity e, float accumulatedDelta);

    protected abstract void processExpired(Entity e);


    /**
     * Start processing of entities after a certain amount of delta time.
     * 
     * Cancels current delayed run and starts a new one.
     * 
     * @param delay time delay until processing starts.
     */
    @Deprecated
    public void restart(float delay) {
        this.delay = delay;
        this.acc = 0;
        running = true;
    }

    /**
     * Restarts the system only if the delay offered is shorter than the
     * time that the system is currently scheduled to execute at.
     * 
     * If the system is already stopped (not running) then the offered
     * delay will be used to restart the system with no matter its value.
     * 
     * If the system is already counting down, and the offered delay is
     * larger than the time remaining, the system will ignore it. If the
     * offered delay is shorter than the time remaining, the system will
     * restart itself to run at the offered delay.
     * 
     * @param offeredDelay
     */
    public void offerDelay(float offeredDelay) {
        if (!running) {
            running = true;
            delay = offeredDelay;
        } else {
            delay = Math.min(delay, offeredDelay);
        }
    }


    /**
     * Get the initial delay that the system was ordered to process entities after.
     * 
     * @return the originally set delay.
     */
    public float getInitialTimeDelay() {
        return delay;
    }

    /**
     * Get the time until the system is scheduled to run at.
     * Returns zero (0) if the system is not running.
     * Use isRunning() before checking this value.
     * 
     * @return time when system will run at.
     */
    public float getRemainingTimeUntilProcessing() {
        if(running) {
            return delay-acc;
        }
        return 0;
    }

    /**
     * Check if the system is counting down towards processing.
     * 
     * @return true if it's counting down, false if it's not running.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Stops the system from running, aborts current countdown.
     * Call offerDelay or restart to run it again.
     */
    public void stop() {
        this.running = false;
        this.acc = 0;
    }

}
