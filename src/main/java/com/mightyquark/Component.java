package com.mightyquark;

import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * A tag class. All Components in the system must implement this interface.
 * 
 * WARNING: DO NOT CREATE COMPONENTS USING A CONSTRUCTOR.
 * Use World.createComponent(Class<T extends Component> type) instead
 * This will allow Components to be pooled.
 */
public interface Component extends Poolable {
}
