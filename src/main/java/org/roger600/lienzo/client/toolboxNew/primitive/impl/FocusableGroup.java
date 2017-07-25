package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.types.BoundingBox;
import org.roger600.lienzo.client.toolboxNew.GroupItem;

class FocusableGroup extends AbstractFocusableGroupItem<FocusableGroup> {

    private final MultiPath primitive;

    FocusableGroup(final Group group) {
        super(new GroupItem(group));
        this.primitive = setUpGroupDecorator(new MultiPath(),
                                             group);
        getGroupItem().add(primitive);
        setupFocusingHandlers();
    }

    @Override
    public IPrimitive<?> getPrimitive() {
        return primitive;
    }

    private static MultiPath setUpGroupDecorator(final MultiPath primitive,
                                                 final Group group) {
        final BoundingBox boundingBox = group.getBoundingBox();
        final double width = boundingBox.getWidth();
        final double height = boundingBox.getHeight();
        return primitive
                .clear()
                .rect(0,
                      0,
                      width,
                      height)
                .setFillAlpha(0.01)
                .setStrokeAlpha(0.01)
                .setListening(true)
                .setFillBoundsForSelection(true)
                .moveToTop();
    }
}
