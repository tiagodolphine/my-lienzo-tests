package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractDecoratedItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratedItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratorItem;
import org.roger600.lienzo.client.toolboxNew.primitive.TooltipItem;

public abstract class WrappedItem<T extends DecoratedItem>
        extends AbstractDecoratedItem<T> {

    protected abstract AbstractDecoratedItem<?> getWrapped();

    @Override
    public T show(final Runnable before,
                  final Runnable after) {
        getWrapped().show(before,
                          after);
        return cast();
    }

    @Override
    public T hide(final Runnable before,
                  final Runnable after) {
        getWrapped().hide(before,
                          after);
        return cast();
    }

    @Override
    public IPrimitive<?> getPrimitive() {
        return getWrapped().getPrimitive();
    }

    @Override
    public Group asPrimitive() {
        return (Group) getWrapped().asPrimitive();
    }

    @Override
    public boolean isVisible() {
        return getWrapped().isVisible();
    }

    @Override
    public T decorate(final DecoratorItem<?> decorator) {
        getWrapped().decorate(decorator);
        return cast();
    }

    @Override
    public T tooltip(final TooltipItem<?> tooltip) {
        getWrapped().tooltip(tooltip);
        return cast();
    }

    @Override
    public T onMouseEnter(final NodeMouseEnterHandler handler) {
        getWrapped().onMouseEnter(handler);
        return cast();
    }

    @Override
    public T onMouseExit(final NodeMouseExitHandler handler) {
        getWrapped().onMouseExit(handler);
        return cast();
    }

    @Override
    public void destroy() {
        getWrapped().destroy();
    }

    @SuppressWarnings("unchecked")
    private T cast() {
        return (T) this;
    }
}
