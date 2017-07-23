package org.roger600.lienzo.client.toolboxNew.primitive;

import com.ait.lienzo.client.core.shape.Layer;
import org.roger600.lienzo.client.toolboxNew.Toolbox;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;

public interface LayerToolbox extends Toolbox<LayerToolbox, Point2DGrid, DecoratedItem>,
                                      DecoratedItem<LayerToolbox> {

    public LayerToolbox attachTo(Layer layer);

    Layer getLayer();
}
