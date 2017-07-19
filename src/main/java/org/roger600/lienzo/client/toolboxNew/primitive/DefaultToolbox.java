package org.roger600.lienzo.client.toolboxNew.primitive;

import com.ait.lienzo.client.core.shape.Layer;
import org.roger600.lienzo.client.toolboxNew.Toolbox;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;

public interface DefaultToolbox extends Toolbox<DefaultToolbox, Point2DGrid, DefaultItem>,
                                        DefaultItem<DefaultToolbox>,
                                        DefaultItems<DefaultToolbox> {

    public DefaultToolbox attachTo(Layer layer);
}
