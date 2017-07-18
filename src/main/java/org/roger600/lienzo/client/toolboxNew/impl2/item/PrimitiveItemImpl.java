package org.roger600.lienzo.client.toolboxNew.impl2.item;

import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.google.gwt.event.shared.HandlerRegistration;
import org.roger600.lienzo.client.toolboxNew.impl2.DecoratorItem;

class PrimitiveItemImpl extends PrimitiveItem<PrimitiveItemImpl> {

    private final DecoratedItem<?> decoratedItem;
    private HandlerRegistration mouseEnterHandlerRegistration;
    private HandlerRegistration mouseExitHandlerRegistration;

    PrimitiveItemImpl(final IPrimitive<?> prim) {
        this.decoratedItem = new DecoratedItem<>(prim);
    }

    public PrimitiveItemImpl onMouseEnter(final NodeMouseEnterHandler handler) {
        assert null != handler;
        if (null != mouseEnterHandlerRegistration) {
            mouseEnterHandlerRegistration.removeHandler();
        }
        mouseEnterHandlerRegistration = decoratedItem.getPrimitive()
                .addNodeMouseEnterHandler(handler);
        decoratedItem.register(mouseEnterHandlerRegistration);
        return this;
    }

    public PrimitiveItemImpl onMouseExit(final NodeMouseExitHandler handler) {
        assert null != handler;
        if (null != mouseExitHandlerRegistration) {
            mouseExitHandlerRegistration.removeHandler();
        }
        mouseExitHandlerRegistration = decoratedItem.getPrimitive()
                .addNodeMouseExitHandler(handler);
        decoratedItem.register(mouseExitHandlerRegistration);
        return this;
    }

    @Override
    PrimitiveItemImpl onFocus(final Runnable focusCallback) {
        decoratedItem.onFocus(focusCallback);
        return this;
    }

    @Override
    PrimitiveItemImpl onUnFocus(final Runnable unFocusCallback) {
        decoratedItem.onUnFocus(unFocusCallback);
        return this;
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

    DecoratedItem<?> getDecoratedItem() {
        return decoratedItem;
    }
}
