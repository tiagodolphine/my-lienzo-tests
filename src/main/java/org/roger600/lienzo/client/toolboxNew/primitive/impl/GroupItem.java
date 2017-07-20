package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.types.BoundingBox;

public class GroupItem extends AbstractGroupItem<GroupItem> {

    private final MultiPath groupDecorator;

    GroupItem() {
        this(new Group());
    }

    GroupItem(final Group group) {
        super(new org.roger600.lienzo.client.toolboxNew.GroupItem(group));
        this.groupDecorator = registerGroupDecorator(group);
        initHandlers(groupDecorator);
    }

    @Override
    public IPrimitive<?> getPrimitive() {
        return getGroupItem().asPrimitive();
    }

    private static MultiPath registerGroupDecorator(final Group group) {
        final BoundingBox boundingBox = group.getBoundingBox();
        final MultiPath path = new MultiPath().rect(0,
                                                    0,
                                                    boundingBox.getWidth(),
                                                    boundingBox.getHeight())
                .setFillAlpha(0.01)
                .setStrokeAlpha(0.01)
                .setListening(true)
                .setFillBoundsForSelection(true)
                .moveToTop();
        group.add(path);
        return path;
    }
}
