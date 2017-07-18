package org.roger600.lienzo.client.toolboxNew.impl2.item;

import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragMoveHandler;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.google.gwt.event.shared.HandlerRegistration;
import org.roger600.lienzo.client.toolboxNew.impl2.DecoratorItem;

class ButtonItemImpl extends ButtonItem<ButtonItemImpl> {

    private final DecoratedPrimitiveItem<?> decoratedItem;
    private HandlerRegistration clickHandlerRegistration;
    private HandlerRegistration dragStartHandlerRegistration;
    private HandlerRegistration dragMoveHandlerRegistration;
    private HandlerRegistration dragEndHandlerRegistration;

    ButtonItemImpl(final IPrimitive<?> prim) {
        this.decoratedItem = new DecoratedPrimitiveItem<>(prim);
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

    public ButtonItemImpl onDragStart(final NodeDragStartHandler handler) {
        assert null != handler;
        removeDragStartHandlerRegistration();
        dragStartHandlerRegistration = decoratedItem.getPrimitive()
                .setDraggable(true)
                .addNodeDragStartHandler(handler);
        decoratedItem.register(dragStartHandlerRegistration);
        return this;
    }

    public ButtonItemImpl onDragMove(final NodeDragMoveHandler handler) {
        assert null != handler;
        removeDragMoveHandlerRegistration();
        dragMoveHandlerRegistration = decoratedItem.getPrimitive()
                .setDraggable(true)
                .addNodeDragMoveHandler(handler);
        decoratedItem.register(dragMoveHandlerRegistration);
        return this;
    }

    public ButtonItemImpl onDragEnd(final NodeDragEndHandler handler) {
        assert null != handler;
        removeDragEndHandlerRegistration();
        dragEndHandlerRegistration = decoratedItem.getPrimitive()
                .setDraggable(true)
                .addNodeDragEndHandler(handler);
        decoratedItem.register(dragEndHandlerRegistration);
        return this;
    }

    @Override
    public ButtonItemImpl onClick(final NodeMouseClickHandler handler) {
        assert null != handler;
        removeClickHandlerRegistration();
        clickHandlerRegistration = decoratedItem.getPrimitive()
                .setListening(true)
                .addNodeMouseClickHandler(handler);
        decoratedItem.register(clickHandlerRegistration);
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

    private void removeClickHandlerRegistration() {
        if (null != clickHandlerRegistration) {
            clickHandlerRegistration.removeHandler();
        }
    }

    private void removeDragStartHandlerRegistration() {
        if (null != dragStartHandlerRegistration) {
            dragStartHandlerRegistration.removeHandler();
        }
    }

    private void removeDragMoveHandlerRegistration() {
        if (null != dragMoveHandlerRegistration) {
            dragMoveHandlerRegistration.removeHandler();
        }
    }

    private void removeDragEndHandlerRegistration() {
        if (null != dragEndHandlerRegistration) {
            dragEndHandlerRegistration.removeHandler();
        }
    }

}
