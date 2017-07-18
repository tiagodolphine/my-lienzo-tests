package org.roger600.lienzo.client.toolboxNew.impl2.item;

import com.ait.lienzo.client.core.shape.Group;
import org.roger600.lienzo.client.toolboxNew.Item;
import org.roger600.lienzo.client.toolboxNew.impl2.AbstractItem;
import org.roger600.lienzo.client.toolboxNew.impl2.DecoratorItem;

public abstract class PrimitiveItem<T extends PrimitiveItem>
        extends AbstractItem<T, Group>
        implements Item<T> {

    public abstract T focus();

    public abstract T unFocus();

    public abstract T decorate(DecoratorItem<?> decorator);
}
