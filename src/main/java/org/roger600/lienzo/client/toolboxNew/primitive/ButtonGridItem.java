package org.roger600.lienzo.client.toolboxNew.primitive;

import org.roger600.lienzo.client.toolboxNew.ItemGrid;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;

public interface ButtonGridItem extends ActionItem<ButtonGridItem>,
                                        ItemGrid<ButtonGridItem, Point2DGrid, DecoratedItem> {

    public ButtonGridItem showGrid();

    public ButtonGridItem hideGrid();

    public ButtonGridItem decorateGrid(DecoratorItem<?> decorator);
}
