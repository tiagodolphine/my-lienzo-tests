package org.roger600.lienzo.client.toolboxNew.impl2;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.ait.lienzo.client.core.types.Point2D;
import com.google.gwt.core.client.GWT;
import org.roger600.lienzo.client.toolboxNew.ContainerItem;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;

public class ItemsGroup<G extends Point2DGrid, I extends AbstractItem>
        extends AbstractGroupContainerItem<ItemsGroup, G, I>
        implements ContainerItem<ItemsGroup, G, I> {

    private final List<I> items = new LinkedList<>();
    private G grid;
    private Runnable refreshCallback;

    public ItemsGroup() {
        this(new GroupItem());
    }

    public ItemsGroup(final GroupItem groupItem) {
        super(groupItem);
        this.refreshCallback = new Runnable() {
            @Override
            public void run() {
                asPrimitive().getLayer().batch();
            }
        };
    }

    @Override
    public ItemsGroup grid(final G grid) {
        this.grid = grid;
        return checkReposition();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ItemsGroup add(final I... items) {
        for (final I button : items) {
            this.items.add(button);
            if (getGroupItem().isVisible()) {
                button.show();
            } else {
                button.hide();
            }
            getGroupItem().add(button.asPrimitive());
        }
        return checkReposition();
    }

    @Override
    public ItemsGroup show() {
        super.getGroupItem().show(new Runnable() {
            @Override
            public void run() {
                repositionItems();
                for (final I button : items) {
                    button.show();
                }
            }
        });
        return this;
    }

    public ItemsGroup onRefresh(final Runnable refreshCallback) {
        this.refreshCallback = refreshCallback;
        return this;
    }

    public ItemsGroup refresh() {
        return checkReposition();
    }

    @Override
    public ItemsGroup hide() {
        super.getGroupItem().hide(new Runnable() {
            @Override
            public void run() {
                for (final I button : items) {
                    button.hide();
                }
                fireRefresh();
            }
        });
        return this;
    }

    @Override
    public Iterator<I> iterator() {
        return new ListItemsIterator<I>(items) {
            @Override
            protected void remove(final I item) {
                items.remove(item);
                checkReposition();
            }
        };
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
        items.clear();
        refreshCallback = null;
    }

    @Override
    public G getGrid() {
        return grid;
    }

    private ItemsGroup checkReposition() {
        if (getGroupItem().isVisible()) {
            return repositionItems();
        }
        return this;
    }

    private ItemsGroup repositionItems() {
        final Iterator<Point2D> gridIterator = getGrid().iterator();
        for (final AbstractItem button : items) {
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

    private abstract static class ListItemsIterator<I extends AbstractItem> implements Iterator<I> {

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
