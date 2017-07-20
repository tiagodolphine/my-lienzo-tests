package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragMoveHandler;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Shape;
import com.google.gwt.event.shared.HandlerRegistration;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractPrimitiveItem;
import org.roger600.lienzo.client.toolboxNew.primitive.ButtonItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratorItem;

public class ButtonItemImpl
        extends AbstractPrimitiveItem<ButtonItem>
        implements ButtonItem {

    private final AbstractGroupItem item;
    private HandlerRegistration clickHandlerRegistration;
    private HandlerRegistration dragStartHandlerRegistration;
    private HandlerRegistration dragMoveHandlerRegistration;
    private HandlerRegistration dragEndHandlerRegistration;

    public ButtonItemImpl(final Shape<?> prim) {
        this(new ItemImpl(prim));
    }

    public ButtonItemImpl(final
                          Group group) {
        this(new GroupItem(group));
    }

    ButtonItemImpl(final AbstractGroupItem item) {
        this.item = item;
    }

    @Override
    public ButtonItemImpl show() {
        item.show();
        return this;
    }

    @Override
    public ButtonItemImpl hide() {
        item.hide();
        return this;
    }

    @Override
    public boolean isVisible() {
        return item.isVisible();
    }

    @Override
    public ButtonItem onFocus(final Runnable callback) {
        item.onFocus(callback);
        return this;
    }

    @Override
    public ButtonItem onUnFocus(final Runnable callback) {
        item.onUnFocus(callback);
        return this;
    }

    @Override
    public Group asPrimitive() {
        return item.asPrimitive();
    }

    @Override
    public ButtonItemImpl decorate(final DecoratorItem<?> decorator) {
        item.decorate(decorator);
        return this;
    }

    @Override
    public ButtonItemImpl onMouseEnter(final NodeMouseEnterHandler handler) {
        item.onMouseEnter(handler);
        return this;
    }

    @Override
    public ButtonItemImpl onMouseExit(final NodeMouseExitHandler handler) {
        item.onMouseExit(handler);
        return this;
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
