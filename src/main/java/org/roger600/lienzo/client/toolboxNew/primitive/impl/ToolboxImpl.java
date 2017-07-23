package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import java.util.Iterator;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.Direction;
import org.roger600.lienzo.client.toolboxNew.Positions;
import org.roger600.lienzo.client.toolboxNew.Toolbox;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractDecoratedItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratedItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratorItem;
import org.roger600.lienzo.client.toolboxNew.primitive.factory.DecoratorsFactory;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public class ToolboxImpl
        extends WrappedItem<ToolboxImpl>
        implements Toolbox<ToolboxImpl, Point2DGrid, DecoratedItem>,
                   DecoratedItem<ToolboxImpl> {

    private final ItemGridImpl items;
    private Supplier<BoundingBox> boundingBoxSupplier;
    private Direction at;
    private Point2D offset;
    private Runnable refreshCallback;

    public ToolboxImpl(final Supplier<BoundingBox> boundingBoxSupplier) {
        this(boundingBoxSupplier,
             new ItemImpl(new Group())
                     .setFocusDelay(0));
    }

    ToolboxImpl() {
        this(null);
    }

    private ToolboxImpl(final Supplier<BoundingBox> boundingBoxSupplier,
                        final ItemImpl groupPrimitiveItem) {
        this.boundingBoxSupplier = boundingBoxSupplier;
        this.at = Direction.NORTH_EAST;
        this.offset = new Point2D(0d,
                                  0d);
        this.refreshCallback = new Runnable() {
            @Override
            public void run() {
                if (null != groupPrimitiveItem.getPrimitive().getLayer()) {
                    groupPrimitiveItem.getPrimitive().getLayer().batch();
                }
            }
        };
        this.items = new ItemGridImpl(groupPrimitiveItem)
                .onRefresh(refreshCallback);
    }

    @Override
    public ToolboxImpl at(final Direction at) {
        this.at = at;
        return checkReposition();
    }

    public Point2DGrid getGrid() {
        return items.getGrid();
    }

    @Override
    public ToolboxImpl offset(final Point2D offset) {
        this.offset = offset;
        return checkReposition();
    }

    @Override
    public ToolboxImpl grid(final Point2DGrid grid) {
        items.grid(grid);
        return this;
    }

    @Override
    public ToolboxImpl decorate(final DecoratorItem<?> decorator) {
        // TODO
        super.decorate(DecoratorsFactory.box()
                               .setStrokeColor(ColorName.RED.getColorString())
                               .setStrokeWidth(10));
        return this;
    }

    @Override
    public ToolboxImpl add(final DecoratedItem... items) {
        this.items.add(items);
        return this;
    }

    @Override
    public Iterator<DecoratedItem> iterator() {
        return items.iterator();
    }

    @Override
    public ToolboxImpl show(final Runnable before,
                            final Runnable after) {
        return super.show(new Runnable() {
                              @Override
                              public void run() {
                                  reposition();
                                  before.run();
                              }
                          },
                          new Runnable() {
                              @Override
                              public void run() {
                                  //getWrapped().focus();
                                  fireRefresh();
                                  after.run();
                              }
                          });
    }

    @Override
    public ToolboxImpl hide(final Runnable before,
                            final Runnable after) {
        return super.hide(before,
                          new Runnable() {
                              @Override
                              public void run() {
                                  fireRefresh();
                                  after.run();
                              }
                          });
    }

    public ToolboxImpl refresh() {
        checkReposition();
        items.refresh();
        return this;
    }

    public ToolboxImpl forBoundingBox(final Supplier<BoundingBox> supplier) {
        this.boundingBoxSupplier = supplier;
        return this;
    }

    @Override
    public void destroy() {
        items.destroy();
        at = null;
        refreshCallback = null;
        super.destroy();
    }

    @Override
    protected AbstractDecoratedItem<?> getWrapped() {
        return items;
    }

    ItemGridImpl getItems() {
        return items;
    }

    private ToolboxImpl checkReposition() {
        if (isVisible()) {
            reposition();
        }
        return this;
    }

    private void reposition() {
        final Point2D loc = Positions.anchorFor(boundingBoxSupplier.get(),
                                                this.at);
        asPrimitive().setLocation(loc.offset(offset));
        fireRefresh();
    }

    private void fireRefresh() {
        if (null != refreshCallback) {
            refreshCallback.run();
        }
    }
}
