package org.roger600.lienzo.client.toolboxNew.impl2.item;

import java.util.Iterator;

import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;
import org.roger600.lienzo.client.toolboxNew.impl2.DecoratorItem;
import org.roger600.lienzo.client.toolboxNew.impl2.ItemsGroup;

class DropDownItem
        extends CompositeItem<DropDownItem> {

    private static final int TIMER_DELAY_MILLIS = 500;

    private final PrimitiveItemImpl primitiveItem;
    private final ItemsGroup<Point2DGrid, PrimitiveItem> itemsGroup;
    private final Timer itemsGroupFocusTimer =
            new Timer() {
                @Override
                public void run() {
                    itemsGroup.hide();
                }
            };

    DropDownItem(final IPrimitive<?> prim) {
        this(new PrimitiveItemImpl(prim),
             new ItemsGroup<Point2DGrid, PrimitiveItem>());
    }

    DropDownItem(final PrimitiveItemImpl primitiveItem,
                 final ItemsGroup<Point2DGrid, PrimitiveItem> itemsGroup) {
        this.primitiveItem = primitiveItem;
        this.itemsGroup = itemsGroup;
        init();
    }

    private void init() {
        attachItemsGroup();
        this.primitiveItem
                .onFocus(focusCallback)
                .onUnFocus(unFocusCallback);
    }

    private void attachItemsGroup() {
        final Group primGroup = this.primitiveItem.asPrimitive();
        final BoundingBox boundingBox = this.primitiveItem.getDecoratedItem().getPrimitive().getBoundingBox();
        primGroup.add(itemsGroup.asPrimitive()
                              .setX(boundingBox.getX())
                              .setY(boundingBox.getHeight()));
    }

    @Override
    public DropDownItem grid(final Point2DGrid grid) {
        itemsGroup.grid(grid);
        return this;
    }

    @Override
    public DropDownItem decorate(final DecoratorItem<?> decorator) {
        primitiveItem.decorate(decorator);
        return this;
    }

    @Override
    public DropDownItem onMouseEnter(final NodeMouseEnterHandler handler) {
        primitiveItem.onMouseEnter(handler);
        return this;
    }

    @Override
    public DropDownItem onMouseExit(final NodeMouseExitHandler handler) {
        primitiveItem.onMouseExit(handler);
        return this;
    }

    @Override
    DropDownItem onFocus(final Runnable focusCallback) {
        throw new UnsupportedOperationException();
    }

    @Override
    DropDownItem onUnFocus(final Runnable unFocusCallback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DropDownItem add(final PrimitiveItem... items) {
        itemsGroup.add(items);
        for (final PrimitiveItem item : items) {
            item.onFocus(itemFocusCallback)
                .onUnFocus(itemUnFocusCallback);
        }
        return this;
    }

    @Override
    public Iterator<PrimitiveItem> iterator() {
        return itemsGroup.iterator();
    }

    @Override
    public DropDownItem focus() {
        primitiveItem.focus();
        return this;
    }

    @Override
    public DropDownItem unFocus() {
        primitiveItem.unFocus();
        return this;
    }

    @Override
    public DropDownItem show() {
        primitiveItem.show();
        return this;
    }

    @Override
    public DropDownItem hide() {
        itemsGroup.hide();
        primitiveItem.hide();
        return this;
    }

    @Override
    public void destroy() {
        stopTimer();
        itemsGroup.destroy();
        primitiveItem.destroy();
    }

    @Override
    public Group asPrimitive() {
        return primitiveItem.asPrimitive();
    }

    private final Runnable itemFocusCallback = new Runnable() {
        @Override
        public void run() {
            GWT.log("ITEM FOCUS");
            stopTimer();
        }
    };

    private final Runnable itemUnFocusCallback = new Runnable() {
        @Override
        public void run() {
            GWT.log("ITEM UNFOCUS");
            scheduleTimer();
        }
    };

    private final Runnable focusCallback = new Runnable() {
        @Override
        public void run() {
            GWT.log("ICON FOCUS");
            itemsGroup.show();
            stopTimer();
        }
    };

    private final Runnable unFocusCallback = new Runnable() {
        @Override
        public void run() {
            GWT.log("ICON UNFOCUS");
            scheduleTimer();
        }
    };

    private void scheduleTimer() {
        itemsGroupFocusTimer.schedule(TIMER_DELAY_MILLIS);
    }

    private void stopTimer() {
        itemsGroupFocusTimer.cancel();
    }

}
