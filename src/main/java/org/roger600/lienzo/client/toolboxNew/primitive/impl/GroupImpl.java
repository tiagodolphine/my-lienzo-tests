package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.types.BoundingBox;
import org.roger600.lienzo.client.toolboxNew.GroupItem;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

class GroupImpl extends AbstractGroupItem<GroupImpl> {

    private final Group primitive;

    GroupImpl(final Group group) {
        super(new GroupItem(group));
        this.primitive = group;
    }

    @Override
    public GroupImpl show(final Runnable before,
                          final Runnable after) {
        getGroupItem().show(before,
                            new Runnable() {
                                @Override
                                public void run() {
                                    showAddOns();
                                    after.run();
                                }
                            });
        return this;
    }

    @Override
    public GroupImpl hide(final Runnable before,
                          final Runnable after) {
        getGroupItem().hide(new Runnable() {
                                @Override
                                public void run() {
                                    hideAddOns();
                                    before.run();
                                }
                            },
                            after);
        return this;
    }

    @Override
    public IPrimitive<?> getPrimitive() {
        return primitive;
    }

    @Override
    public Supplier<BoundingBox> getBoundingBox() {
        return new Supplier<BoundingBox>() {
            @Override
            public BoundingBox get() {
                if (primitive.getChildNodes().size() == 0) {
                    return new BoundingBox(0,
                                           0,
                                           1,
                                           1);
                }
                return GroupImpl.super.getBoundingBox().get();
            }
        };
    }

    @Override
    public GroupImpl onMouseEnter(NodeMouseEnterHandler handler) {
        throw new UnsupportedOperationException();
    }

    @Override
    public GroupImpl onMouseExit(NodeMouseExitHandler handler) {
        throw new UnsupportedOperationException();
    }
}
