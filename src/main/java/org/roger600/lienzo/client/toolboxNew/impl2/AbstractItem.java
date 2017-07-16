package org.roger600.lienzo.client.toolboxNew.impl2;

import com.ait.lienzo.client.core.shape.IPrimitive;
import org.roger600.lienzo.client.toolboxNew.Item;

public abstract class AbstractItem<T extends AbstractItem, P extends IPrimitive<?>> implements Item<T> {

    public abstract P asPrimitive();
}
