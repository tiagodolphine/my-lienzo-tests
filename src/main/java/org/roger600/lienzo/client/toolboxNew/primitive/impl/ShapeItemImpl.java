package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Shape;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractPrimitiveItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratorItem;
import org.roger600.lienzo.client.toolboxNew.primitive.ShapeItem;

public class ShapeItemImpl
        extends AbstractPrimitiveItem<ShapeItem>
        implements ShapeItem {

    private final ItemImpl item;

    public ShapeItemImpl(final Shape<?> shape) {
        this.item = new ItemImpl(shape);
    }

    @Override
    public ShapeItem onFocus(final Runnable callback) {
        item.onFocus(callback);
        return this;
    }

    @Override
    public ShapeItem onUnFocus(final Runnable callback) {
        item.onUnFocus(callback);
        return this;
    }

    @Override
    public IPrimitive<?> asPrimitive() {
        return item.asPrimitive();
    }

    @Override
    public boolean isVisible() {
        return item.isVisible();
    }

    @Override
    public ShapeItem decorate(final DecoratorItem<?> decorator) {
        item.decorate(decorator);
        return this;
    }

    @Override
    public ShapeItem onMouseEnter(final NodeMouseEnterHandler handler) {
        item.onMouseEnter(handler);
        return this;
    }

    @Override
    public ShapeItem onMouseExit(final NodeMouseExitHandler handler) {
        item.onMouseExit(handler);
        return this;
    }

    @Override
    public ShapeItem show() {
        item.show();
        return this;
    }

    @Override
    public ShapeItem hide() {
        item.hide();
        return this;
    }

    @Override
    public void destroy() {
        item.destroy();
    }
}
