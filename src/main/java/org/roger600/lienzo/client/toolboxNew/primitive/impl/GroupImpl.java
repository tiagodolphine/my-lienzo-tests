package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import org.roger600.lienzo.client.toolboxNew.GroupItem;

class GroupImpl extends AbstractGroupItem<GroupImpl> {

    private final IPrimitive<?> primitive;

    GroupImpl(final Group group) {
        super(new GroupItem(group));
        this.primitive = group;
        setFocusDelay(0);
    }

    @Override
    public IPrimitive<?> getPrimitive() {
        return primitive;
    }
}
