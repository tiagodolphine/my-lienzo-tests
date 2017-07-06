package org.roger600.lienzo.client.toolboxNew.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.ait.lienzo.client.core.shape.IPrimitive;
import org.roger600.lienzo.client.toolboxNew.Grid;
import org.roger600.lienzo.client.toolboxNew.ItemsToolbox;

public class IPrimitiveItemsToolbox
        extends DelegateToolbox<IPrimitive<?>, IPrimitiveItemsToolbox>
        implements ItemsToolbox<IPrimitive<?>, AbstractToolboxIItem, IPrimitiveItemsToolbox> {

    private final List<AbstractToolboxIItem> items = new LinkedList<>();
    private final AbstractToolbox<IPrimitive<?>, ?> toolbox;

    public IPrimitiveItemsToolbox(final IPrimitive<?> shape) {
        this(new IPrimitiveToolbox(shape));
    }

    IPrimitiveItemsToolbox(final AbstractToolbox<IPrimitive<?>, ?> toolbox) {
        this.toolbox = toolbox;
        init();
    }

    private void init() {
        toolbox.onRefresh(new Runnable() {
            @Override
            public void run() {
                repositionItems();
            }
        });
    }

    @Override
    protected AbstractToolbox<IPrimitive<?>, ?> getDelegate() {
        return toolbox;
    }

    @Override
    public IPrimitiveItemsToolbox add(final AbstractToolboxIItem... buttons) {
        for (final AbstractToolboxIItem button : buttons) {
            button
                    .attachTo(toolbox.getGroup())
                    .hide();
            this.items.add(button);
        }
        repositionItems();
        return this;
    }

    @Override
    public boolean remove(final AbstractToolboxIItem button) {
        final int i = items.indexOf(button);
        if (i > -1) {
            destroyItem(items.get(i));
            repositionItems();
            return true;
        }
        return false;
    }

    @Override
    public IPrimitiveItemsToolbox show() {
        super.show();
        for (final AbstractToolboxIItem item : items) {
            item.show();
        }
        return this;
    }

    @Override
    public IPrimitiveItemsToolbox hide() {
        super.hide();
        for (final AbstractToolboxIItem item : items) {
            item.hide();
        }
        return this;
    }

    @Override
    public void destroy() {
        for (final AbstractToolboxIItem item : items) {
            destroyItem(item);
        }
        super.destroy();
    }

    private void repositionItems() {
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
