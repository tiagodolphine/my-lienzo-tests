package org.roger600.lienzo.client.toolboxNew.impl2;

import java.util.Iterator;

import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.Direction;
import org.roger600.lienzo.client.toolboxNew.ItemsToolbox;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public class ItemsToolboxImpl<G extends Point2DGrid, I extends AbstractItem>
        extends AbstractGroupContainerItem<ItemsToolboxImpl, G, I>
        implements ItemsToolbox<ItemsToolboxImpl, G, I> {

    private final ToolboxImpl toolbox;
    private final ItemsGroup<G, I> itemsGroup;

    public ItemsToolboxImpl(final Supplier<BoundingBox> boundingBoxSupplier) {
        this(new ItemsGroup<G, I>(),
             new ToolboxImpl(boundingBoxSupplier));
    }

    ItemsToolboxImpl(final ItemsGroup<G, I> itemsGroup,
                     final ToolboxImpl toolbox) {
        super(toolbox.getGroupItem());
        this.itemsGroup = itemsGroup;
        this.toolbox = toolbox;
        init();
    }

    private final Runnable refreshCallback = new Runnable() {
        @Override
        public void run() {
            toolbox.asPrimitive().getLayer().batch();
        }
    };

    private void init() {
        toolbox.getGroupItem().add(itemsGroup.asPrimitive());
        toolbox.onRefresh(refreshCallback);
        itemsGroup.onRefresh(refreshCallback);
    }

    @Override
    public ItemsToolboxImpl at(final Direction at) {
        toolbox.at(at);
        return this;
    }

    public ItemsToolboxImpl offset(final Point2D p) {
        toolbox.offset(p);
        return this;
    }

    @Override
    public ItemsToolboxImpl grid(final G grid) {
        itemsGroup.grid(grid);
        return this;
    }

    @Override
    public ItemsToolboxImpl add(final I... items) {
        itemsGroup.add(items);
        return this;
    }

    @Override
    public Iterator<I> iterator() {
        return itemsGroup.iterator();
    }

    @Override
    public ItemsToolboxImpl show() {
        toolbox.show();
        itemsGroup.show();
        return this;
    }

    public ItemsToolboxImpl refresh() {
        toolbox.refresh();
        itemsGroup.refresh();
        return this;
    }

    @Override
    public ItemsToolboxImpl hide() {
        toolbox.hide();
        itemsGroup.hide();
        return this;
    }

    @Override
    public void destroy() {
        super.destroy();
        itemsGroup.destroy();
        toolbox.destroy();
    }

    @Override
    public G getGrid() {
        return itemsGroup.getGrid();
    }
}
