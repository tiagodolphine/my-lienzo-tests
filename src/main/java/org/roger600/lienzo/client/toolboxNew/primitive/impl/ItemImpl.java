package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.google.gwt.core.client.GWT;
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
    private int focusDelay = FOCUS_DELAY_MILLIS;

    ItemImpl(final Group group) {
        super(new GroupItem(group));
        this.primitive = setUpGroupDecorator(new MultiPath(),
                                             group);
        getGroupItem().add(primitive);
    }

    ItemImpl(final Shape<?> shape) {
        super(new GroupItem());
        this.primitive = shape;
        getGroupItem().add(primitive);
    }

    public ItemImpl setFocusDelay(final int millis) {
        this.focusDelay = millis;
        return this;
    }

    @Override
    ItemImpl focus() {
        if (focusDelay > 0) {
            focusDelayTimer.schedule(focusDelay);
        } else {
            focusDelayTimer.run();
        }
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

    @Override
    public ItemImpl refresh() {
        // TODO: setUpGroupDecorator((MultiPath) getPrimitive(), asPrimitive());
        return super.refresh();
    }

    private ItemImpl doFocus() {
        return super.focus();
    }

    private void cancelFocusTimer() {
        if (focusDelayTimer.isRunning()) {
            focusDelayTimer.cancel();
        }
    }

    private static MultiPath setUpGroupDecorator(final MultiPath path,
                                                 final Group group) {
        final BoundingBox boundingBox = group.getBoundingBox();
        GWT.log("BB [" + boundingBox.getWidth() + ", " + boundingBox.getHeight() + "]");
        path.
                rect(0,
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
