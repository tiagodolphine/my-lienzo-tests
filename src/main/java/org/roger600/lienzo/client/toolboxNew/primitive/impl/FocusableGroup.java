package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.google.gwt.core.client.GWT;
import org.roger600.lienzo.client.toolboxNew.GroupItem;

class FocusableGroup extends AbstractGroupItem<FocusableGroup> {

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

    @Override
    public FocusableGroup refresh() {
        updateDecorator();
        return super.refresh();
    }

    private FocusableGroup updateDecorator() {
        setUpGroupDecorator(primitive,
                            asPrimitive());
        return this;
    }

    private static MultiPath setUpGroupDecorator(final MultiPath primitive,
                                                 final Group group) {
        final boolean hasChildren = group.getChildNodes().size() > 0;
        final BoundingBox boundingBox = group.getBoundingBox();
        //final double width = hasChildren ? boundingBox.getWidth() : 0d;
        //final double height = hasChildren ? boundingBox.getHeight() : 0d;
        final double width = boundingBox.getWidth();
        final double height = boundingBox.getHeight();
        GWT.log("BB SIZE FOR GROUPIMPL IS [" + width + ", " + height + "]");
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
