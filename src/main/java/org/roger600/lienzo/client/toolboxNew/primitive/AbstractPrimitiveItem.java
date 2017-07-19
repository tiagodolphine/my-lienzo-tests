package org.roger600.lienzo.client.toolboxNew.primitive;

import com.ait.lienzo.client.core.shape.IPrimitive;

public abstract class AbstractPrimitiveItem<T extends AbstractPrimitiveItem>
        implements DefaultItem<T> {

    public abstract IPrimitive<?> asPrimitive();
}
