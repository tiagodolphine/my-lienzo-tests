package org.roger600.lienzo.client.toolboxNew.impl2.ext;

import java.util.Iterator;

import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import org.roger600.lienzo.client.toolboxNew.Grid;
import org.roger600.lienzo.client.toolboxNew.ItemsToolbox;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;
import org.roger600.lienzo.client.toolboxNew.impl2.AbstractItem;
import org.roger600.lienzo.client.toolboxNew.impl2.DelegateItemsToolbox;
import org.roger600.lienzo.client.toolboxNew.impl2.ItemsToolboxImpl;
import org.roger600.lienzo.client.toolboxNew.util.Function;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public class WiresShapeToolbox extends DelegateItemsToolbox<WiresShapeToolbox, Point2DGrid> {

    private final ItemsToolboxImpl<Point2DGrid, AbstractItem> toolbox;

    public WiresShapeToolbox(final WiresShape shape) {
        // Create the toolbox.
        this.toolbox =
                new ItemsToolboxImpl<>(new Supplier<BoundingBox>() {
                    @Override
                    public BoundingBox get() {
                        return shape.getPath().getBoundingBox();
                    }
                });
        // Attach it into the shape.
        shape.addChild(toolbox.asPrimitive());
    }

    private static final class PointsSupplier implements Function<Grid<Point2D>, Iterator<Point2D>> {

        @Override
        public Iterator<Point2D> apply(Grid<Point2D> grid) {
            return grid.iterator();
        }
    }

    @Override
    protected ItemsToolbox<?, Point2DGrid, AbstractItem> getDelegate() {
        return toolbox;
    }
}
