package org.roger600.lienzo.client.toolboxNew.primitive;

import org.roger600.lienzo.client.toolboxNew.Items;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;

public interface DefaultItems<T extends DefaultItems> extends Items<T, Point2DGrid, DefaultItem>,
                                                              DefaultItem<T> {

    Point2DGrid getGrid();
}
