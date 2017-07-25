package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import org.roger600.lienzo.client.toolboxNew.ItemGrid;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractDecoratedItem;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractPrimitiveItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratedItem;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public class ItemGridImpl
        extends WrappedItem<ItemGridImpl>
        implements ItemGrid<ItemGridImpl, Point2DGrid, DecoratedItem> {

    private final AbstractGroupItem groupPrimitiveItem;
    private final List<AbstractDecoratedItem> items = new LinkedList<>();
    private Point2DGrid grid;
    private Runnable refreshCallback;
    private BoundingBox boundingBox;

    private Supplier<BoundingBox> boundingBoxSupplier = new Supplier<BoundingBox>() {
        @Override
        public BoundingBox get() {
            return boundingBox;
        }
    };

    public ItemGridImpl() {
        this.groupPrimitiveItem =
                new GroupImpl(new Group())
                        .setBoundingBox(boundingBoxSupplier);
        this.boundingBox = new BoundingBox(groupPrimitiveItem.asPrimitive().getBoundingBox());
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
                getWrapped().getGroupItem().add(button.asPrimitive());
            } catch (final ClassCastException e) {
                throw new UnsupportedOperationException("This item only supports subtypes " +
                                                                "of " + AbstractDecoratedItem.class.getName());
            }
        }
        return itemsUpdated();
    }

    @Override
    public Iterator<DecoratedItem> iterator() {
        return new ListItemsIterator<AbstractDecoratedItem>(items) {
            @Override
            protected void remove(final AbstractDecoratedItem item) {
                items.remove(item);
                itemsUpdated();
            }
        };
    }

    @Override
    public ItemGridImpl show(final Runnable before,
                             final Runnable after) {
        return super.show(new Runnable() {
                              @Override
                              public void run() {
                                  repositionItems();
                                  for (final DecoratedItem button : items) {
                                      button.show();
                                  }
                                  before.run();
                              }
                          },
                          new Runnable() {
                              @Override
                              public void run() {

                                  after.run();
                              }
                          });
    }

    @Override
    public ItemGridImpl hide(final Runnable before,
                             final Runnable after) {
        return super.hide(new Runnable() {
                              @Override
                              public void run() {
                                  before.run();
                              }
                          },
                          new Runnable() {
                              @Override
                              public void run() {
                                  for (final DecoratedItem button : items) {
                                      button.hide();
                                  }
                                  after.run();
                                  fireRefresh();
                              }
                          });
    }

    public ItemGridImpl onRefresh(final Runnable refreshCallback) {
        this.refreshCallback = refreshCallback;
        return this;
    }

    public ItemGridImpl refresh() {
        return checkReposition();
    }

    public int size() {
        return items.size();
    }

    @Override
    public void destroy() {
        getWrapped().destroy();
        items.clear();
        refreshCallback = null;
        boundingBoxSupplier = null;
        boundingBox = null;
    }

    public Point2DGrid getGrid() {
        return grid;
    }

    private ItemGridImpl itemsUpdated() {
        // Reposition items as for the given grid.
        repositionItems();
        // Calculate BB.
        double maxw = 0;
        double maxh = 0;
        for (final AbstractDecoratedItem item : items) {
            final Point2D location = item.asPrimitive().getLocation();
            final BoundingBox itemBB = (BoundingBox) item.getBoundingBox().get();
            final double itemw = itemBB.getWidth() + location.getX();
            final double itemh = itemBB.getHeight() + location.getY();
            if (itemw > maxw) {
                maxw = itemw;
            }
            if (itemh > maxh) {
                maxh = itemh;
            }
        }

        boundingBox = new BoundingBox(0,
                                      0,
                                      maxw,
                                      maxh);
        // Update decorator.
        if (null != getWrapped().getDecorator()) {
            getWrapped().getDecorator().setBoundingBox(getBoundingBox().get());
            if (getWrapped().getDecorator() instanceof AbstractPrimitiveItem) {
                ((AbstractPrimitiveItem) getWrapped().getDecorator()).asPrimitive().moveToBottom();
            }
        }
        return this;
    }

    @Override
    public Supplier<BoundingBox> getBoundingBox() {
        return boundingBoxSupplier;
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
