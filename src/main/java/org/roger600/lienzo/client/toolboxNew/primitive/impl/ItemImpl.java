package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.IPrimitive;
import org.roger600.lienzo.client.toolboxNew.GroupItem;

public class ItemImpl extends AbstractGroupItem<ItemImpl> {

    private final IPrimitive<?> primitive;

    public ItemImpl(final IPrimitive<?> primitive) {
        super(new GroupItem());
        this.primitive = primitive;
        getGroupItem().add(primitive);
        initHandlers(getPrimitive());
    }

    @Override
    public IPrimitive<?> getPrimitive() {
        return primitive;
    }
}
