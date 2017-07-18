package org.roger600.lienzo.client.toolboxNew.impl2;

import com.ait.lienzo.client.core.shape.IPrimitive;

public abstract class DecoratorItem<T extends DecoratorItem> extends AbstractItem<T, IPrimitive<?>> {

    public abstract T setSize(double width,
                              double height);

    @Override
    public final T show() {
        doShow();
        return batch();
    }

    @Override
    public final T hide() {
        doHide();
        return batch();
    }

    @Override
    public void destroy() {
        asPrimitive().removeFromParent();
    }

    protected void doShow() {
        asPrimitive().setStrokeAlpha(1);
    }

    protected void doHide() {
        asPrimitive().setStrokeAlpha(0);
    }

    @SuppressWarnings("unchecked")
    private T batch() {
        if (null != asPrimitive().getLayer()) {
            asPrimitive().getLayer().batch();
        }
        return (T) this;
    }
}
