package org.roger600.lienzo.client.toolboxNew.impl2;

import com.ait.lienzo.client.core.shape.IPrimitive;

public abstract class DelegateItem<T extends DelegateItem, P extends IPrimitive<?>>
        extends AbstractItem<T, P> {

    protected abstract AbstractItem<?, P> getDelegate();

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
    public void destroy() {
        getDelegate().destroy();
    }

    @Override
    public P asPrimitive() {
        return getDelegate().asPrimitive();
    }

    @SuppressWarnings("unchecked")
    private T cast() {
        return (T) this;
    }
}
