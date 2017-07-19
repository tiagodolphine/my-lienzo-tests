package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.IPrimitive;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractDefaultItem;

public class GroupItem extends AbstractDefaultItem<GroupItem> {

    @Override
    public IPrimitive<?> getPrimitive() {
        return asPrimitive();
    }
}
