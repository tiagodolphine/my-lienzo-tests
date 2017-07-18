package org.roger600.lienzo.client.toolboxNew.impl2.item;

import org.roger600.lienzo.client.toolboxNew.ContainerItem;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;

public abstract class CompositeItem<T extends CompositeItem>
        extends PrimitiveItem<T>
        implements ContainerItem<T, Point2DGrid, PrimitiveItem> {

}
