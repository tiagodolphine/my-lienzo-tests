package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.Shape;
import org.roger600.lienzo.client.toolboxNew.primitive.ShapeItem;

public class ShapeItemImpl
        extends WrappedItem<ShapeItem>
        implements ShapeItem {

    private final ItemImpl item;

    public ShapeItemImpl(final Shape<?> shape) {
        this.item = new ItemImpl(shape);
    }

    @Override
    protected AbstractGroupItem<?> getWrapped() {
        return item;
    }
}
