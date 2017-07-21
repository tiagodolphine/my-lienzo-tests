package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import java.util.Iterator;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.Direction;
import org.roger600.lienzo.client.toolboxNew.Positions;
import org.roger600.lienzo.client.toolboxNew.Toolbox;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratedItem;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public class ToolboxImpl
        extends WrappedItem<ToolboxImpl>
        implements Toolbox<ToolboxImpl, Point2DGrid, DecoratedItem>,
                   DecoratedItem<ToolboxImpl> {

    private final AbstractGroupItem groupPrimitiveItem;
    private Supplier<BoundingBox> boundingBoxSupplier;
    private Direction at;
    private Point2D offset;
    private Runnable refreshCallback;
    private final ItemGridImpl items;

    public ToolboxImpl(final Supplier<BoundingBox> boundingBoxSupplier) {
        this(boundingBoxSupplier,
             new ItemImpl(new Group())
                     .setupFocusingHandlers());
    }

    ToolboxImpl(final Supplier<BoundingBox> boundingBoxSupplier,
                final AbstractGroupItem groupPrimitiveItem) {
        this.boundingBoxSupplier = boundingBoxSupplier;
        this.groupPrimitiveItem = groupPrimitiveItem;
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
    public ToolboxImpl add(final DecoratedItem... items) {
        this.items.add(items);
        return this;
    }

    @Override
    public Iterator<DecoratedItem> iterator() {
        return items.iterator();
    }

    @Override
    public ToolboxImpl show() {
        getWrapped().show(new Runnable() {
                              @Override
                              public void run() {
                                  reposition();
                              }
                          },
                          new Runnable() {
                              @Override
                              public void run() {
                                  items.show();
                              }
                          });
        return this;
    }

    @Override
    public ToolboxImpl hide() {
        getWrapped().hide(new Runnable() {
            @Override
            public void run() {
                fireRefresh();
            }
        });
        return this;
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
        super.destroy();
    }

    @Override
    protected AbstractGroupItem<?> getWrapped() {
        return groupPrimitiveItem;
    }

    @Override
    public Shape<?> getAttachable() {
        return groupPrimitiveItem.getAttachable();
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
        groupPrimitiveItem.asPrimitive().setLocation(loc.offset(offset));
        fireRefresh();
    }

    private void fireRefresh() {
        if (null != refreshCallback) {
            refreshCallback.run();
        }
    }
}
