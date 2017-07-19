package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import java.util.Iterator;

import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractPrimitiveItem;
import org.roger600.lienzo.client.toolboxNew.primitive.ButtonItems;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratorItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DefaultItem;

public class ButtonItemsImpl
        extends AbstractPrimitiveItem<ButtonItems>
        implements ButtonItems {

    private static final int TIMER_DELAY_MILLIS = 500;

    private final AbstractGroupItem item;
    private final ItemsImpl itemsGrid;
    private final Timer itemsGroupFocusTimer =
            new Timer() {
                @Override
                public void run() {
                    itemsGrid.hide();
                }
            };

    public ButtonItemsImpl(final
                           IPrimitive<?> prim) {
        this(new ItemImpl(prim),
             new ItemsImpl());
    }

    ButtonItemsImpl(final AbstractGroupItem item,
                    final ItemsImpl itemsGrid) {
        this.item = item;
        this.itemsGrid = itemsGrid;
        init();
    }

    @Override
    public ButtonItems grid(final Point2DGrid grid) {
        itemsGrid.grid(grid);
        return this;
    }

    @Override
    public ButtonItems decorate(final DecoratorItem<?> decorator) {
        item.decorate(decorator);
        // TODO: items.
        return this;
    }

    @Override
    public ButtonItems show() {
        item.show();
        return this;
    }

    @Override
    public ButtonItems hide() {
        item.hide();
        itemsGrid.hide();
        return this;
    }

    @Override
    public boolean isVisible() {
        return item.isVisible();
    }

    @Override
    public ButtonItems add(final DefaultItem... items) {
        itemsGrid.add(items);
        for (final DefaultItem item : items) {
            try {
                final AbstractPrimitiveItem primitiveItem = (AbstractPrimitiveItem) item;
                primitiveItem.onFocus(itemFocusCallback);
                primitiveItem.onUnFocus(itemUnFocusCallback);
            } catch (final ClassCastException e) {
                throw new UnsupportedOperationException("The button only supports subtypes " +
                                                                "of " + AbstractPrimitiveItem.class.getName());
            }
        }
        return this;
    }

    @Override
    public Iterator<DefaultItem> iterator() {
        return itemsGrid.iterator();
    }

    @Override
    public ButtonItems onMouseEnter(final NodeMouseEnterHandler handler) {
        item.onMouseEnter(handler);
        return this;
    }

    @Override
    public ButtonItems onMouseExit(final NodeMouseExitHandler handler) {
        item.onMouseExit(handler);
        return this;
    }

    @Override
    public void destroy() {
        item.destroy();
        itemsGrid.destroy();
    }

    @Override
    public Point2DGrid getGrid() {
        return itemsGrid.getGrid();
    }

    @Override
    public ButtonItems onFocus(final Runnable callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ButtonItems onUnFocus(final Runnable callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IPrimitive<?> asPrimitive() {
        return item.asPrimitive();
    }

    private void init() {
        attachItemsGroup();
        this.item
                .onFocus(focusCallback)
                .onUnFocus(unFocusCallback)
                .asPrimitive()
                .setDraggable(false);
    }

    private void attachItemsGroup() {
        final Group primGroup = item.asPrimitive();
        final BoundingBox boundingBox = item.getPrimitive().getBoundingBox();
        primGroup.add(itemsGrid.asPrimitive()
                              .setX(boundingBox.getX())
                              .setY(boundingBox.getHeight()));
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
            itemsGrid.show();
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
