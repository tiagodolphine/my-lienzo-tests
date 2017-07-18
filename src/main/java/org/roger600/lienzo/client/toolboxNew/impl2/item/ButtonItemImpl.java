package org.roger600.lienzo.client.toolboxNew.impl2.item;

import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.google.gwt.event.shared.HandlerRegistration;
import org.roger600.lienzo.client.toolboxNew.impl2.DecoratorItem;

class ButtonItemImpl extends ButtonItem<ButtonItemImpl> {

    private final DecoratedPrimitiveItem<?> decoratedItem;
    private HandlerRegistration clickHandlerRegistration;

    ButtonItemImpl(final IPrimitive<?> prim) {
        this.decoratedItem = new DecoratedPrimitiveItem<>(prim);
    }

    @Override
    public ButtonItemImpl onClick(NodeMouseClickHandler handler) {
        assert null != handler;
        final IPrimitive<?> prim = decoratedItem.getPrimitive();
        prim.setListening(true);
        if (null != clickHandlerRegistration) {
            clickHandlerRegistration.removeHandler();
        }
        clickHandlerRegistration = prim.addNodeMouseClickHandler(handler);
        decoratedItem.register(clickHandlerRegistration);
        return this;
    }

    @Override
    public ButtonItemImpl focus() {
        decoratedItem.focus();
        return this;
    }

    @Override
    public ButtonItemImpl unFocus() {
        decoratedItem.unFocus();
        return this;
    }

    @Override
    public ButtonItemImpl decorate(final DecoratorItem<?> decorator) {
        decoratedItem.decorate(decorator);
        return this;
    }

    @Override
    public ButtonItemImpl show() {
        decoratedItem.show();
        return this;
    }

    @Override
    public ButtonItemImpl hide() {
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
