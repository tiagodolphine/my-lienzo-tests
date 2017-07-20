package org.roger600.lienzo.client.toolboxNew.primitive;

import com.ait.lienzo.client.core.shape.IPrimitive;

public abstract class AbstractPrimitiveItem<T extends DecoratedItem>
        implements DecoratedItem<T> {

    public abstract T onFocus(Runnable callback);

    public abstract T onUnFocus(Runnable callback);

    public abstract IPrimitive<?> asPrimitive();
}
