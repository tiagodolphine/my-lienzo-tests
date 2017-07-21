package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragMoveHandler;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Shape;
import com.google.gwt.event.shared.HandlerRegistration;
import org.roger600.lienzo.client.toolboxNew.primitive.ButtonItem;

public class ButtonItemImpl
        extends WrappedItem<ButtonItem>
        implements ButtonItem {

    private final AbstractGroupItem<?> item;
    private HandlerRegistration clickHandlerRegistration;
    private HandlerRegistration dragStartHandlerRegistration;
    private HandlerRegistration dragMoveHandlerRegistration;
    private HandlerRegistration dragEndHandlerRegistration;

    public ButtonItemImpl(final Shape<?> prim) {
        this(new ItemImpl(prim)
                     .setupFocusingHandlers());
    }

    public ButtonItemImpl(final
                          Group group) {
        this(new ItemImpl(group)
                     .setupFocusingHandlers());
    }

    ButtonItemImpl(final AbstractGroupItem item) {
        this.item = item;
    }

    public ButtonItemImpl onDragStart(final NodeDragStartHandler handler) {
        assert null != handler;
        removeDragStartHandlerRegistration();
        dragStartHandlerRegistration = item
                .getPrimitive()
                .setDraggable(true)
                .addNodeDragStartHandler(handler);
        item.registrations().register(dragStartHandlerRegistration);
        return this;
    }

    public ButtonItemImpl onDragMove(final NodeDragMoveHandler handler) {
        assert null != handler;
        removeDragMoveHandlerRegistration();
        dragMoveHandlerRegistration = item
                .getPrimitive()
                .setDraggable(true)
                .addNodeDragMoveHandler(handler);
        item.registrations().register(dragMoveHandlerRegistration);
        return this;
    }

    public ButtonItemImpl onDragEnd(final NodeDragEndHandler handler) {
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
    public ButtonItemImpl onClick(final NodeMouseClickHandler handler) {
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
        getWrapped().destroy();
        removeClickHandlerRegistration();
        removeDragStartHandlerRegistration();
        removeDragMoveHandlerRegistration();
        removeDragEndHandlerRegistration();
    }

    @Override
    protected AbstractGroupItem<?> getWrapped() {
        return item;
    }

    AbstractGroupItem<?> getGroupItem() {
        return item;
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
