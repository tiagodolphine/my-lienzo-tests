package org.roger600.lienzo.client.toolboxNew.impl2;

import org.roger600.lienzo.client.toolboxNew.ContainerItem;
import org.roger600.lienzo.client.toolboxNew.Grid;

public abstract class AbstractGroupContainerItem<T extends AbstractGroupContainerItem, G extends Grid, I extends AbstractItem>
        extends AbstractGroupItem<T>
        implements ContainerItem<T, G, I> {

    public AbstractGroupContainerItem(final GroupItem groupItem) {
        super(groupItem);
    }

    public abstract G getGrid();
}
