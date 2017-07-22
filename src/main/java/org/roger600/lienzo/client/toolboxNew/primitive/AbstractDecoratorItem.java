package org.roger600.lienzo.client.toolboxNew.primitive;

public abstract class AbstractDecoratorItem<T extends AbstractDecoratorItem>
        extends AbstractPrimitiveItem<T>
        implements DecoratorItem<T> {

    public abstract T refresh();

    public abstract T copy();

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
