package org.roger600.lienzo.client.toolboxNew.impl2.item;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import org.roger600.lienzo.client.toolboxNew.impl2.DecoratorItem;

class PrimitiveItemImpl extends PrimitiveItem<PrimitiveItemImpl> {

    private final DecoratedPrimitiveItem<?> decoratedItem;

    PrimitiveItemImpl(final IPrimitive<?> prim) {
        this.decoratedItem = new DecoratedPrimitiveItem<>(prim);
    }

    @Override
    public PrimitiveItemImpl focus() {
        decoratedItem.focus();
        return this;
    }

    @Override
    public PrimitiveItemImpl unFocus() {
        decoratedItem.unFocus();
        return this;
    }

    @Override
    public PrimitiveItemImpl decorate(final DecoratorItem<?> decorator) {
        decoratedItem.decorate(decorator);
        return this;
    }

    @Override
    public PrimitiveItemImpl show() {
        decoratedItem.show();
        return this;
    }

    @Override
    public PrimitiveItemImpl hide() {
        decoratedItem.hide();
        return this;
    }

    @Override
    public void destroy() {
        decoratedItem.destroy();
    }

    @Override
    public Group asPrimitive() {
        return decoratedItem.asPrimitive();
    }
}
