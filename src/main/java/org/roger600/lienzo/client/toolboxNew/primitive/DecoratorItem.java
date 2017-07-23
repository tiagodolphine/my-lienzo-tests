package org.roger600.lienzo.client.toolboxNew.primitive;

import com.ait.lienzo.client.core.types.BoundingBox;
import org.roger600.lienzo.client.toolboxNew.Item;

public interface DecoratorItem<T extends DecoratorItem> extends Item<T> {

    public T setBoundingBox(BoundingBox boundingBoxSupplier);
}
