package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import java.util.Iterator;

import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.Direction;
import org.roger600.lienzo.client.toolboxNew.Positions;
import org.roger600.lienzo.client.toolboxNew.Toolbox;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractDecoratedItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratedItem;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public class ToolboxImpl
        extends WrappedItem<ToolboxImpl>
        implements Toolbox<ToolboxImpl, Point2DGrid, DecoratedItem>,
                   DecoratedItem<ToolboxImpl> {

    private static final int PADDING = 5;

    private final ItemGridImpl items;
    private Supplier<BoundingBox> shapeBoundingBoxSupplier;
    private Direction at;
    private Point2D offset;
    private Runnable refreshCallback;

    public ToolboxImpl(final Supplier<BoundingBox> shapeBoundingBoxSupplier) {
        this.shapeBoundingBoxSupplier = shapeBoundingBoxSupplier;
        this.at = Direction.NORTH_EAST;
        this.offset = new Point2D(0d,
                                  0d);
        this.refreshCallback = new Runnable() {
            @Override
            public void run() {
                if (null != items.getPrimitive().getLayer()) {
                    items.getPrimitive().getLayer().batch();
                }
            }
        };
        this.items = new ItemGridImpl()
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

    @Override
    public void destroy() {
        items.destroy();
        at = null;
        refreshCallback = null;
        this.shapeBoundingBoxSupplier = null;
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
        // Obtain the toolbox's location relative to the cardinal direction.
        final Point2D location = Positions.anchorFor(shapeBoundingBoxSupplier.get(),
                                                     this.at);
        // Add some padding.
        final Point2D pad = new Point2D(0,
                                        0);
        switch (this.at) {
            case EAST:
            case NORTH_EAST:
            case SOUTH_EAST:
                pad.setX(PADDING);
                break;
            case SOUTH:
            case SOUTH_WEST:
                pad.setY(PADDING);
                break;
        }
        // Set the toolbox primitive's location.
        asPrimitive().setLocation(location
                                          .offset(this.offset)
                                          .offset(pad));
        fireRefresh();
    }

    private void fireRefresh() {
        if (null != refreshCallback) {
            refreshCallback.run();
        }
    }
}
