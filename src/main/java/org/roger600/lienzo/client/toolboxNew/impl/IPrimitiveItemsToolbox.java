package org.roger600.lienzo.client.toolboxNew.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.ait.lienzo.client.core.shape.IContainer;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.shared.core.types.Direction;
import org.roger600.lienzo.client.toolboxNew.Grid;
import org.roger600.lienzo.client.toolboxNew.ItemsToolbox;

public class IPrimitiveItemsToolbox implements ItemsToolbox<IPrimitive<?>, AbstractToolboxIItem, IPrimitiveItemsToolbox> {

    private final List<AbstractToolboxIItem> items = new LinkedList<>();
    private final IPrimitiveToolbox toolbox;

    public IPrimitiveItemsToolbox(final IPrimitive<?> shape) {
        this.toolbox = new IPrimitiveToolbox(shape);
    }

    @Override
    public IPrimitiveItemsToolbox attachTo(final IContainer<?, IPrimitive<?>> parent) {
        toolbox.attachTo(parent);
        return this;
    }

    @Override
    public IPrimitiveItemsToolbox at(final Direction at) {
        toolbox.at(at);
        return this;
    }

    @Override
    public IPrimitiveItemsToolbox towards(final Direction towards) {
        toolbox.towards(towards);
        return this;
    }

    @Override
    public IPrimitiveItemsToolbox grid(final Grid grid) {
        toolbox.grid(grid);
        return this;
    }

    @Override
    public IPrimitiveItemsToolbox add(final AbstractToolboxIItem... buttons) {
        for (final AbstractToolboxIItem button : buttons) {
            button
                    .attachTo(toolbox.getGroup())
                    .hide();
            this.items.add(button);
        }
        repositionItem();
        return this;
    }

    @Override
    public boolean remove(final AbstractToolboxIItem button) {
        final int i = items.indexOf(button);
        if (i > -1) {
            destroyItem(items.get(i));
            repositionItem();
            return true;
        }
        return false;
    }

    @Override
    public IPrimitiveItemsToolbox show() {
        toolbox.show(new Runnable() {
            @Override
            public void run() {
                for (final AbstractToolboxIItem item : items) {
                    item.show();
                }
            }
        });
        return this;
    }

    @Override
    public IPrimitiveItemsToolbox hide() {
        toolbox.hide(new Runnable() {
            @Override
            public void run() {
                for (final AbstractToolboxIItem item : items) {
                    item.hide();
                }
            }
        });
        return this;
    }

    @Override
    public void destroy() {
        for (final AbstractToolboxIItem item : items) {
            destroyItem(item);
        }
        toolbox.destroy();
    }

    private void repositionItem() {
        final Iterator<Grid.Point> gridIterator = toolbox.getGrid().iterator();
        for (final AbstractToolboxIItem button : items) {
            final Grid.Point point = gridIterator.next();
            button.setLocation(point.asPoint2D());
        }
    }

    private void destroyItem(final AbstractToolboxIItem button) {
        button.destroy();
    }
}
