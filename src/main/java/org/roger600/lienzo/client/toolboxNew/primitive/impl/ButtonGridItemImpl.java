package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import java.util.Iterator;

import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragMoveHandler;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.Direction;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractPrimitiveItem;
import org.roger600.lienzo.client.toolboxNew.primitive.ButtonGridItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratedItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratorItem;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public class ButtonGridItemImpl
        extends AbstractPrimitiveItem<ButtonGridItem>
        implements ButtonGridItem {

    private static final int TIMER_DELAY_MILLIS = 500;

    private final ButtonItemImpl button;
    private final ToolboxImpl toolbox;
    private final Timer itemsGroupFocusTimer =
            new Timer() {
                @Override
                public void run() {
                    hideGrid();
                }
            };

    public ButtonGridItemImpl(final Shape<?> prim) {
        this(new ButtonItemImpl(prim),
             new ToolboxImpl(new Supplier<BoundingBox>() {
                 @Override
                 public BoundingBox get() {
                     return prim.getBoundingBox();
                 }
             }));
    }

    public ButtonGridItemImpl(final Group group) {
        this(new ButtonItemImpl(group),
             new ToolboxImpl(new Supplier<BoundingBox>() {
                 @Override
                 public BoundingBox get() {
                     return group.getBoundingBox();
                 }
             }));
    }

    ButtonGridItemImpl(final ButtonItemImpl button,
                       final ToolboxImpl toolbox) {
        this.button = button;
        this.toolbox = toolbox;
        init();
    }

    public ButtonGridItem at(final Direction at) {
        toolbox.at(at);
        return this;
    }

    public ButtonGridItem offset(final Point2D offset) {
        toolbox.offset(offset);
        return this;
    }

    @Override
    public ButtonGridItem grid(final Point2DGrid grid) {
        toolbox.grid(grid);
        return this;
    }

    @Override
    public ButtonGridItem decorate(final DecoratorItem<?> decorator) {
        button.decorate(decorator);
        return this;
    }

    @Override
    public ButtonGridItem show() {
        button.show();
        return this;
    }

    @Override
    public ButtonGridItem showGrid() {
        toolbox.show();
        return this;
    }

    @Override
    public ButtonGridItem hide() {
        button.hide();
        hideGrid();
        return this;
    }

    @Override
    public ButtonGridItem hideGrid() {
        toolbox.hide();
        return this;
    }

    @Override
    public boolean isVisible() {
        return button.isVisible();
    }

    @Override
    public ButtonGridItem add(final DecoratedItem... items) {
        toolbox.add(items);
        for (final DecoratedItem item : items) {
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
    public Iterator<DecoratedItem> iterator() {
        return toolbox.iterator();
    }

    @Override
    public ButtonGridItem onMouseEnter(final NodeMouseEnterHandler handler) {
        button.onMouseEnter(handler);
        return this;
    }

    @Override
    public ButtonGridItem onMouseExit(final NodeMouseExitHandler handler) {
        button.onMouseExit(handler);
        return this;
    }

    @Override
    public ButtonGridItem onClick(final NodeMouseClickHandler handler) {
        button.onClick(handler);
        return this;
    }

    @Override
    public ButtonGridItem onDragStart(final NodeDragStartHandler handler) {
        button.onDragStart(handler);
        return this;
    }

    @Override
    public ButtonGridItem onDragMove(final NodeDragMoveHandler handler) {
        button.onDragMove(handler);
        return this;
    }

    @Override
    public ButtonGridItem onDragEnd(final NodeDragEndHandler handler) {
        button.onDragEnd(handler);
        return this;
    }

    @Override
    public void destroy() {
        button.destroy();
        toolbox.destroy();
    }

    @Override
    public ButtonGridItem onFocus(final Runnable callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ButtonGridItem onUnFocus(final Runnable callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IPrimitive<?> asPrimitive() {
        return button.asPrimitive();
    }

    private void init() {
        this.button.onFocus(focusCallback);
        this.button.onUnFocus(unFocusCallback);
        this.button.asPrimitive()
                .setDraggable(false)
                .add(toolbox.asPrimitive());
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
            showGrid();
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
