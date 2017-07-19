package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.IPrimitive;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractDefaultItem;

public class ItemImpl extends AbstractDefaultItem<ItemImpl> {

    private final IPrimitive<?> primitive;

    public ItemImpl(final IPrimitive<?> primitive) {
        this.primitive = primitive;
    }

    @Override
    public IPrimitive<?> getPrimitive() {
        return primitive;
    }
}
