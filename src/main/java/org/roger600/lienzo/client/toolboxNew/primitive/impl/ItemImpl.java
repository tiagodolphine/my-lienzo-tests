package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Shape;
import org.roger600.lienzo.client.toolboxNew.GroupItem;

class ItemImpl extends AbstractFocusableGroupItem<ItemImpl> {

    private final IPrimitive<?> primitive;

    ItemImpl(final Shape<?> shape) {
        super(new GroupItem());
        this.primitive = shape;
        getGroupItem().add(primitive);
        setupFocusingHandlers();
    }

    @Override
    public IPrimitive<?> getPrimitive() {
        return primitive;
    }
}
