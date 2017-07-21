package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.types.BoundingBox;
import org.roger600.lienzo.client.toolboxNew.GroupItem;

class ItemImpl extends AbstractGroupItem<ItemImpl> {

    private final IPrimitive<?> primitive;
    private final Shape<?> attachable;

    ItemImpl(final Group group) {
        super(new GroupItem(group));
        this.primitive = group;
        this.attachable = registerGroupDecorator(group);
    }

    ItemImpl(final Shape<?> shape) {
        super(new GroupItem());
        this.primitive = shape;
        this.attachable = shape;
        getGroupItem().add(primitive);
    }

    @Override
    public Shape<?> getAttachable() {
        return attachable;
    }

    @Override
    public IPrimitive<?> getPrimitive() {
        return primitive;
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
