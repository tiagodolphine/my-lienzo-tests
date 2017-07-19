package org.roger600.lienzo.client.toolboxNew.primitive;

import org.roger600.lienzo.client.toolboxNew.Item;

public interface DecoratorItem<T extends DecoratorItem> extends Item<T> {

    public T setSize(double width,
                     double height);
}
