package org.roger600.lienzo.client.toolboxNew.primitive;

import com.ait.lienzo.client.core.shape.Layer;
import org.roger600.lienzo.client.toolboxNew.Toolbox;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;

public interface DefaultToolbox<T extends DefaultToolbox> extends Toolbox<T, Point2DGrid, DefaultItem>,
                                                                  DefaultItem<T>,
                                                                  DefaultItems<T> {
    public T attachTo(Layer layer);

}
