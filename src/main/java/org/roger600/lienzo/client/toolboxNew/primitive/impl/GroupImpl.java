package org.roger600.lienzo.client.toolboxNew.primitive.impl;

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
        init();
    }

    GroupImpl(final Group group,
              final Supplier<BoundingBox> boundingBoxSupplier) {
        super(new GroupItem(group),
              boundingBoxSupplier);
        this.primitive = group;
        init();
    }

    @Override
    public GroupImpl show(final Runnable before,
                          final Runnable after) {
        return super.show(before,
                          new Runnable() {
                              @Override
                              public void run() {
                                  focus();
                                  after.run();
                              }
                          });
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

    private void init() {
        setFocusDelay(0);
    }
}
