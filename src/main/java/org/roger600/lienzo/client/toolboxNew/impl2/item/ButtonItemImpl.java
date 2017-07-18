package org.roger600.lienzo.client.toolboxNew.impl2.item;

import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragMoveHandler;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.google.gwt.event.shared.HandlerRegistration;
import org.roger600.lienzo.client.toolboxNew.impl2.DecoratorItem;

class ButtonItemImpl extends ButtonItem<ButtonItemImpl> {

    private final PrimitiveItemImpl primitiveItem;
    private HandlerRegistration clickHandlerRegistration;
    private HandlerRegistration dragStartHandlerRegistration;
    private HandlerRegistration dragMoveHandlerRegistration;
    private HandlerRegistration dragEndHandlerRegistration;

    ButtonItemImpl(final IPrimitive<?> prim) {
        this.primitiveItem = new PrimitiveItemImpl(prim);
    }

    @Override
    public ButtonItemImpl focus() {
        primitiveItem.focus();
        return this;
    }

    @Override
    public ButtonItemImpl unFocus() {
        primitiveItem.unFocus();
        return this;
    }

    @Override
    public ButtonItemImpl decorate(final DecoratorItem<?> decorator) {
        primitiveItem.decorate(decorator);
        return this;
    }

    @Override
    public ButtonItemImpl onMouseEnter(final NodeMouseEnterHandler handler) {
        primitiveItem.onMouseEnter(handler);
        return this;
    }

    @Override
    public ButtonItemImpl onMouseExit(final NodeMouseExitHandler handler) {
        primitiveItem.onMouseExit(handler);
        return this;
    }

    @Override
    ButtonItemImpl onFocus(final Runnable focusCallback) {
        primitiveItem.onFocus(focusCallback);
        return this;
    }

    @Override
    ButtonItemImpl onUnFocus(final Runnable unFocusCallback) {
        primitiveItem.onUnFocus(unFocusCallback);
        return this;
    }

    @Override
    public ButtonItemImpl show() {
        primitiveItem.show();
        return this;
    }

    @Override
    public ButtonItemImpl hide() {
        primitiveItem.hide();
        return this;
    }

    public ButtonItemImpl onDragStart(final NodeDragStartHandler handler) {
        assert null != handler;
        removeDragStartHandlerRegistration();
        dragStartHandlerRegistration = primitiveItem.getDecoratedItem()
                .getPrimitive()
                .setDraggable(true)
                .addNodeDragStartHandler(handler);
        primitiveItem.getDecoratedItem().register(dragStartHandlerRegistration);
        return this;
    }

    public ButtonItemImpl onDragMove(final NodeDragMoveHandler handler) {
        assert null != handler;
        removeDragMoveHandlerRegistration();
        dragMoveHandlerRegistration = primitiveItem.getDecoratedItem()
                .getPrimitive()
                .setDraggable(true)
                .addNodeDragMoveHandler(handler);
        primitiveItem.getDecoratedItem().register(dragMoveHandlerRegistration);
        return this;
    }

    public ButtonItemImpl onDragEnd(final NodeDragEndHandler handler) {
        assert null != handler;
        removeDragEndHandlerRegistration();
        dragEndHandlerRegistration = primitiveItem.getDecoratedItem()
                .getPrimitive()
                .setDraggable(true)
                .addNodeDragEndHandler(handler);
        primitiveItem.getDecoratedItem().register(dragEndHandlerRegistration);
        return this;
    }

    @Override
    public ButtonItemImpl onClick(final NodeMouseClickHandler handler) {
        assert null != handler;
        removeClickHandlerRegistration();
        clickHandlerRegistration = primitiveItem.getDecoratedItem()
                .getPrimitive()
                .setListening(true)
                .addNodeMouseClickHandler(handler);
        primitiveItem.getDecoratedItem().register(clickHandlerRegistration);
        return this;
    }

    @Override
    public void destroy() {
        primitiveItem.destroy();
    }

    @Override
    public Group asPrimitive() {
        return primitiveItem.asPrimitive();
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
