package org.roger600.lienzo.client.toolboxNew.primitive;

import com.ait.lienzo.client.core.types.BoundingBox;
import org.roger600.lienzo.client.toolboxNew.Item;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public interface DecoratorItem<T extends DecoratorItem> extends Item<T> {

    public T forBoundingBox(Supplier<BoundingBox> boundingBoxSupplier);
}
