package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import java.util.Iterator;

import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragMoveHandler;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.event.NodeMouseEnterEvent;
import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitEvent;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.Direction;
import com.google.gwt.user.client.Timer;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractDecoratedItem;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractDecoratorItem;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractPrimitiveItem;
import org.roger600.lienzo.client.toolboxNew.primitive.ButtonGridItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratedItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratorItem;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public class ButtonGridItemImpl
        extends WrappedItem<ButtonGridItem>
        implements ButtonGridItem {

    private static final int TIMER_DELAY_MILLIS = 1000;

    private final ButtonItemImpl button;
    private final ToolboxImpl toolbox;
    private final Timer itemsGroupFocusTimer =
            new Timer() {
                @Override
                public void run() {
                    hideGrid(new Runnable() {
                        @Override
                        public void run() {
                            button.getWrapped().unFocus();
                            batch();
                        }
                    });
                }
            };

    public ButtonGridItemImpl(final Shape<?> prim) {
        this.button = new ButtonItemImpl(prim);
        this.toolbox = new ToolboxImpl(new DecoratedButtonBoundingBoxSupplier());
        init();
    }

    public ButtonGridItemImpl(final Group group) {
        this.button = new ButtonItemImpl(group);
        this.toolbox = new ToolboxImpl(new DecoratedButtonBoundingBoxSupplier());
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
    public ButtonGridItem decorateGrid(final DecoratorItem<?> decorator) {
        if (toolbox.getItems().size() > 0) {
            if (decorator instanceof AbstractDecoratorItem) {
                toolbox.decorate(decorator);
            }
        } else {
            throw new IllegalStateException("Cannot decorate until no items added.");
        }
        return this;
    }

    @Override
    public ButtonGridItem show(final Runnable before,
                               final Runnable after) {
        button.show(before,
                    after);
        return this;
    }

    @Override
    public ButtonGridItem hide(final Runnable before,
                               final Runnable after) {
        hideGrid(new Runnable() {
            @Override
            public void run() {
                button.hide();
                batch();
            }
        });
        return this;
    }

    @Override
    public ButtonGridItem showGrid() {
        toolbox.show();
        return this;
    }

    @Override
    public ButtonGridItem hideGrid() {
        hideGrid(new Runnable() {
            @Override
            public void run() {
            }
        });
        return this;
    }

    private ButtonGridItem hideGrid(final Runnable after) {
        toolbox.hide(new Runnable() {
                         @Override
                         public void run() {
                         }
                     },
                     after);
        return this;
    }

    @Override
    public ButtonGridItem add(final DecoratedItem... items) {
        for (final DecoratedItem item : items) {
            try {
                final AbstractDecoratedItem primitiveItem = (AbstractDecoratedItem) item;
                registerItemFocusHandler(primitiveItem,
                                         itemFocusCallback);
                registerItemUnFocusHandler(primitiveItem,
                                           itemUnFocusCallback);
            } catch (final ClassCastException e) {
                throw new UnsupportedOperationException("The button only supports subtypes " +
                                                                "of " + AbstractDecoratedItem.class.getName());
            }
        }
        toolbox.add(items);
        return this;
    }

    @Override
    public Iterator<DecoratedItem> iterator() {
        return toolbox.iterator();
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
        super.destroy();
        button.destroy();
        toolbox.destroy();
    }

    @Override
    protected AbstractGroupItem<?> getWrapped() {
        return button.getWrapped();
    }

    private void init() {
        button.getWrapped().setUnFocusDelay(TIMER_DELAY_MILLIS);
        // TODO toolbox.getWrapped().getWrapped().getGroupItem().useShowExecutor(new GroupItemVisibilityExecutors.AnimatedScaleXGroupExecutor().setAnimationDuration(1500));
        // Register custom focus/un-focus behaviors.
        registerItemFocusHandler(button,
                                 focusCallback);
        registerItemUnFocusHandler(button,
                                   unFocusCallback);
        // Attach the toolbox's primiitive into the button group.
        this.button.asPrimitive()
                .setDraggable(false)
                .add(toolbox.asPrimitive());
    }

    private void registerItemFocusHandler(final AbstractDecoratedItem item,
                                          final Runnable callback) {
        button.getWrapped()
                .registrations()
                .register(
                        item.getPrimitive().addNodeMouseEnterHandler(new NodeMouseEnterHandler() {
                            @Override
                            public void onNodeMouseEnter(NodeMouseEnterEvent event) {
                                callback.run();
                            }
                        })
                );
    }

    private void registerItemUnFocusHandler(final AbstractDecoratedItem item,
                                            final Runnable callback) {
        button.getWrapped()
                .registrations()
                .register(
                        item.getPrimitive().addNodeMouseExitHandler(new NodeMouseExitHandler() {
                            @Override
                            public void onNodeMouseExit(NodeMouseExitEvent event) {
                                callback.run();
                            }
                        })
                );
    }

    public ButtonGridItemImpl focus() {
        button.getWrapped().focus();
        showGrid();
        stopTimer();
        return this;
    }

    public ButtonGridItemImpl unFocus() {
        scheduleTimer();
        return this;
    }

    private final Runnable itemFocusCallback = new Runnable() {
        @Override
        public void run() {
            focus();
        }
    };

    private final Runnable itemUnFocusCallback = new Runnable() {
        @Override
        public void run() {
            unFocus();
        }
    };

    private final Runnable focusCallback = new Runnable() {
        @Override
        public void run() {
            focus();
        }
    };

    private final Runnable unFocusCallback = new Runnable() {
        @Override
        public void run() {
            unFocus();
        }
    };

    private void scheduleTimer() {
        itemsGroupFocusTimer.schedule(TIMER_DELAY_MILLIS);
    }

    private void stopTimer() {
        itemsGroupFocusTimer.cancel();
    }

    private void batch() {
        button.asPrimitive().batch();
    }

    // Provides the bounding box of the button plus the decorator, as for further toolbox positioning.
    private class DecoratedButtonBoundingBoxSupplier implements Supplier<BoundingBox> {

        @Override
        public BoundingBox get() {
            final DecoratorItem<?> buttonDecorator = button.getWrapped().getDecorator();
            if (null != buttonDecorator && buttonDecorator instanceof AbstractPrimitiveItem) {
                return ((AbstractPrimitiveItem) buttonDecorator).asPrimitive().getBoundingBox();
            }
            return button.getBoundingBox().get();
        }
    }
}
