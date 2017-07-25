package org.roger600.lienzo.client.toolboxNew.primitive;

import com.ait.lienzo.client.core.types.BoundingBox;
import org.roger600.lienzo.client.toolboxNew.Item;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public interface TooltipItem<T extends TooltipItem> extends Item<T> {

    public T forComputedBoundingBox(Supplier<BoundingBox> boundingBoxSupplier);

}
