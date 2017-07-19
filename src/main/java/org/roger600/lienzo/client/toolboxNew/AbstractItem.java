package org.roger600.lienzo.client.toolboxNew;

import com.ait.lienzo.client.core.shape.IPrimitive;

public abstract class AbstractItem<T extends AbstractItem, P extends IPrimitive<?>> implements Item<T> {

    public abstract P asPrimitive();
}
