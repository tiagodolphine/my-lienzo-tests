package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.types.Point2D;
import com.google.gwt.core.client.GWT;
import org.roger600.lienzo.client.toolboxNew.ItemGrid;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractDecoratedItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratedItem;

public class ItemGridImpl
        extends WrappedItem<ItemGridImpl>
        implements ItemGrid<ItemGridImpl, Point2DGrid, DecoratedItem> {

    private final AbstractGroupItem groupPrimitiveItem;
    private final List<AbstractDecoratedItem> items = new LinkedList<>();
    private Point2DGrid grid;
    private Runnable refreshCallback;

    public ItemGridImpl() {
        this(new ItemImpl(new Group())
                     .setupFocusingHandlers());
    }

    ItemGridImpl(final AbstractGroupItem groupPrimitiveItem) {
        this.groupPrimitiveItem = groupPrimitiveItem;
        this.refreshCallback = new Runnable() {
            @Override
            public void run() {
                if (null != groupPrimitiveItem.asPrimitive().getLayer()) {
                    groupPrimitiveItem.asPrimitive().getLayer().batch();
                }
            }
        };
    }

    @Override
    public ItemGridImpl grid(final Point2DGrid grid) {
        this.grid = grid;
        return checkReposition();
    }

    @Override
    public ItemGridImpl add(final DecoratedItem... items) {
        for (final DecoratedItem item : items) {
            try {
                final AbstractDecoratedItem button = (AbstractDecoratedItem) item;
                this.items.add(button);
                if (isVisible()) {
                    button.show();
                } else {
                    button.hide();
                }
                groupPrimitiveItem.getGroupItem().add(button.asPrimitive());
            } catch (final ClassCastException e) {
                throw new UnsupportedOperationException("This item only supports subtypes " +
                                                                "of " + AbstractDecoratedItem.class.getName());
            }
        }
        return checkReposition();
    }

    @Override
    public Iterator<DecoratedItem> iterator() {
        return new ListItemsIterator<AbstractDecoratedItem>(items) {
            @Override
            protected void remove(final AbstractDecoratedItem item) {
                items.remove(item);
                checkReposition();
            }
        };
    }

    @Override
    public ItemGridImpl show() {
        getWrapped().show(new Runnable() {
                              @Override
                              public void run() {

                              }
                          },
                          new Runnable() {
                              @Override
                              public void run() {
                                  repositionItems();
                                  for (final DecoratedItem button : items) {
                                      button.show();
                                  }
                              }
                          });
        return this;
    }

    public ItemGridImpl onRefresh(final Runnable refreshCallback) {
        this.refreshCallback = refreshCallback;
        return this;
    }

    public ItemGridImpl refresh() {
        return checkReposition();
    }

    @Override
    public ItemGridImpl hide() {
        getWrapped().hide(new Runnable() {
            @Override
            public void run() {
                for (final DecoratedItem button : items) {
                    button.hide();
                }
                fireRefresh();
            }
        });
        return this;
    }

    @Override
    public void destroy() {
        getWrapped().destroy();
        items.clear();
        refreshCallback = null;
    }

    public Point2DGrid getGrid() {
        return grid;
    }

    AbstractGroupItem getGroup() {
        return groupPrimitiveItem;
    }

    private ItemGridImpl checkReposition() {
        if (isVisible()) {
            return repositionItems();
        }
        return this;
    }

    private ItemGridImpl repositionItems() {
        final Iterator<Point2D> gridIterator = grid.iterator();
        for (final AbstractDecoratedItem button : items) {
            final Point2D point = gridIterator.next();
            GWT.log("BUTTON AT = " + point);
            button.asPrimitive().setLocation(point);
        }
        fireRefresh();
        return this;
    }

    private void fireRefresh() {
        if (null != refreshCallback) {
            refreshCallback.run();
        }
    }

    @Override
    protected AbstractGroupItem<?> getWrapped() {
        return groupPrimitiveItem;
    }

    private abstract static class ListItemsIterator<I extends AbstractDecoratedItem> implements Iterator<DecoratedItem> {

        private final List<I> items;
        private int index;

        private ListItemsIterator(final List<I> items) {
            this.items = new LinkedList<>(items);
            this.index = 0;
        }

        protected abstract void remove(I item);

        @Override
        public boolean hasNext() {
            return index < items.size();
        }

        @Override
        public I next() {
            return items.get(index++ - 1);
        }

        @Override
        public void remove() {
            I item = items.get(index);
            remove(item);
        }
    }
}
