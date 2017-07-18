package org.roger600.lienzo.client.toolboxNew.impl2.toolbox;

import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.Direction;
import com.google.gwt.core.client.GWT;
import org.roger600.lienzo.client.toolboxNew.Positions;
import org.roger600.lienzo.client.toolboxNew.Toolbox;
import org.roger600.lienzo.client.toolboxNew.impl2.AbstractGroupItem;
import org.roger600.lienzo.client.toolboxNew.impl2.GroupItem;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public class ToolboxImpl
        extends AbstractGroupItem<ToolboxImpl>
        implements Toolbox<ToolboxImpl> {

    private Supplier<BoundingBox> boundingBoxSupplier;
    private Direction at;
    private Point2D offset;
    private Runnable refreshCallback;

    public ToolboxImpl(final Supplier<BoundingBox> boundingBoxSupplier) {
        this(new GroupItem(),
             boundingBoxSupplier);
    }

    public ToolboxImpl(final GroupItem groupItem,
                       final Supplier<BoundingBox> boundingBoxSupplier) {
        super(groupItem);
        this.boundingBoxSupplier = boundingBoxSupplier;
        this.at = Direction.NORTH_EAST;
        this.offset = new Point2D(0d,
                                  0d);
        this.refreshCallback = new Runnable() {
            @Override
            public void run() {
                asPrimitive().getLayer().batch();
            }
        };
    }

    @Override
    public ToolboxImpl at(final Direction at) {
        this.at = at;
        return checkReposition();
    }

    public ToolboxImpl offset(final Point2D p) {
        this.offset = p;
        return checkReposition();
    }

    @Override
    public ToolboxImpl show() {
        super.getGroupItem().show(new Runnable() {
            @Override
            public void run() {
                reposition();
            }
        });
        return this;
    }

    @Override
    public ToolboxImpl hide() {
        super.getGroupItem().hide(new Runnable() {
            @Override
            public void run() {
                fireRefresh();
            }
        });
        return this;
    }

    public ToolboxImpl refresh() {
        return checkReposition();
    }

    public ToolboxImpl onRefresh(final Runnable refreshCallback) {
        this.refreshCallback = refreshCallback;
        return this;
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
        at = null;
        refreshCallback = null;
    }

    private ToolboxImpl checkReposition() {
        if (getGroupItem().isVisible()) {
            reposition();
        }
        return this;
    }

    private void reposition() {
        GWT.log("BB is = " + boundingBoxSupplier.get());
        Point2D loc = Positions.anchorFor(boundingBoxSupplier.get(),
                                          this.at);
        GWT.log("OFFSET = " + offset);
        GWT.log("LOC = " + loc);
        asPrimitive().setLocation(loc.offset(offset));
        fireRefresh();
    }

    private void fireRefresh() {
        if (null != refreshCallback) {
            refreshCallback.run();
        }
    }
}
