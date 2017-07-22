package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.google.gwt.user.client.Timer;
import org.roger600.lienzo.client.toolboxNew.GroupItem;

class ItemImpl extends AbstractGroupItem<ItemImpl> {

    private final static int FOCUS_DELAY_MILLIS = 200;
    private final IPrimitive<?> primitive;
    private final Timer focusDelayTimer = new Timer() {
        @Override
        public void run() {
            ItemImpl.this.doFocus();
        }
    };

    ItemImpl(final Group group) {
        super(new GroupItem(group));
        this.primitive = createGroupDecorator(group);
        getGroupItem().add(primitive);
    }

    ItemImpl(final Shape<?> shape) {
        super(new GroupItem());
        this.primitive = shape;
        getGroupItem().add(primitive);
    }

    @Override
    ItemImpl focus() {
        focusDelayTimer.schedule(FOCUS_DELAY_MILLIS);
        return this;
    }

    @Override
    ItemImpl unFocus() {
        cancelFocusTimer();
        return super.unFocus();
    }

    @Override
    public void destroy() {
        cancelFocusTimer();
        super.destroy();
    }

    @Override
    public IPrimitive<?> getPrimitive() {
        return primitive;
    }

    private ItemImpl doFocus() {
        return super.focus();
    }

    private void cancelFocusTimer() {
        if (focusDelayTimer.isRunning()) {
            focusDelayTimer.cancel();
        }
    }

    private static MultiPath createGroupDecorator(final Group group) {
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
        return path;
    }
}
