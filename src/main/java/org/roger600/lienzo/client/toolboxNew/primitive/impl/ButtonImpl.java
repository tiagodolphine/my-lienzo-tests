package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragMoveHandler;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.google.gwt.event.shared.HandlerRegistration;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractDefaultItem;
import org.roger600.lienzo.client.toolboxNew.primitive.Button;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratorItem;

public class ButtonImpl implements Button<ButtonImpl> {

    private final AbstractDefaultItem item;
    private HandlerRegistration clickHandlerRegistration;
    private HandlerRegistration dragStartHandlerRegistration;
    private HandlerRegistration dragMoveHandlerRegistration;
    private HandlerRegistration dragEndHandlerRegistration;

    public ButtonImpl(final
                      IPrimitive<?> prim) {
        this(new ItemImpl(prim));
    }

    ButtonImpl(final AbstractDefaultItem item) {
        this.item = item;
    }

    @Override
    public ButtonImpl show() {
        item.show();
        return this;
    }

    @Override
    public ButtonImpl hide() {
        item.hide();
        return this;
    }

    @Override
    public boolean isVisible() {
        return item.isVisible();
    }

    @Override
    public ButtonImpl decorate(final DecoratorItem<?> decorator) {
        item.decorate(decorator);
        return this;
    }

    @Override
    public ButtonImpl onMouseEnter(final NodeMouseEnterHandler handler) {
        item.onMouseEnter(handler);
        return this;
    }

    @Override
    public ButtonImpl onMouseExit(final NodeMouseExitHandler handler) {
        item.onMouseExit(handler);
        return this;
    }

    public ButtonImpl onDragStart(final NodeDragStartHandler handler) {
        assert null != handler;
        removeDragStartHandlerRegistration();
        dragStartHandlerRegistration = item
                .getPrimitive()
                .setDraggable(true)
                .addNodeDragStartHandler(handler);
        item.registrations().register(dragStartHandlerRegistration);
        return this;
    }

    public ButtonImpl onDragMove(final NodeDragMoveHandler handler) {
        assert null != handler;
        removeDragMoveHandlerRegistration();
        dragMoveHandlerRegistration = item
                .getPrimitive()
                .setDraggable(true)
                .addNodeDragMoveHandler(handler);
        item.registrations().register(dragMoveHandlerRegistration);
        return this;
    }

    public ButtonImpl onDragEnd(final NodeDragEndHandler handler) {
        assert null != handler;
        removeDragEndHandlerRegistration();
        dragEndHandlerRegistration = item
                .getPrimitive()
                .setDraggable(true)
                .addNodeDragEndHandler(handler);
        item.registrations().register(dragEndHandlerRegistration);
        return this;
    }

    @Override
    public ButtonImpl onClick(final NodeMouseClickHandler handler) {
        assert null != handler;
        removeClickHandlerRegistration();
        clickHandlerRegistration = item
                .getPrimitive()
                .setListening(true)
                .addNodeMouseClickHandler(handler);
        item.registrations().register(clickHandlerRegistration);
        return this;
    }

    @Override
    public void destroy() {
        item.destroy();
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
