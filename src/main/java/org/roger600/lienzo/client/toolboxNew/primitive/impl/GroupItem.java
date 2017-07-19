package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.IPrimitive;

public class GroupItem extends AbstractGroupItem<GroupItem> {

    public GroupItem() {
        super(new org.roger600.lienzo.client.toolboxNew.GroupItem());
        initHandlers(getPrimitive());
    }

    @Override
    public IPrimitive<?> getPrimitive() {
        return getGroupItem().asPrimitive();
    }
}
