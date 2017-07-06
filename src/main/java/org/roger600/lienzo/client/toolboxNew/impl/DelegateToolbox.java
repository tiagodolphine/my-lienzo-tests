package org.roger600.lienzo.client.toolboxNew.impl;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IContainer;
import com.ait.lienzo.shared.core.types.Direction;
import org.roger600.lienzo.client.toolboxNew.Grid;
import org.roger600.lienzo.client.toolboxNew.Toolbox;

public abstract class DelegateToolbox<N, T extends Toolbox> extends AbstractToolbox<N, T> {

    protected abstract AbstractToolbox<N, ?> getDelegate();

    @Override
    public T attachTo(final IContainer<?, N> parent) {
        getDelegate().attachTo(parent);
        return cast();
    }

    @Override
    public T at(final Direction at) {
        getDelegate().at(at);
        return cast();
    }

    @Override
    public T towards(final Direction towards) {
        getDelegate().towards(towards);
        return cast();
    }

    @Override
    public T grid(final Grid grid) {
        getDelegate().grid(grid);
        return cast();
    }

    @Override
    protected T onRefresh(final Runnable callback) {
        getDelegate().onRefresh(callback);
        return cast();
    }

    @Override
    public T show() {
        getDelegate().show();
        return cast();
    }

    @Override
    public T hide() {
        getDelegate().hide();
        return cast();
    }

    @Override
    public Grid getGrid() {
        return getDelegate().getGrid();
    }

    @Override
    public Direction getAt() {
        return getDelegate().getAt();
    }

    @Override
    public Direction getTowards() {
        return getDelegate().getTowards();
    }

    @Override
    protected Group getGroup() {
        return getDelegate().getGroup();
    }

    @Override
    public void destroy() {
        getDelegate().destroy();
    }

    private T cast() {
        return (T) this;
    }
}
