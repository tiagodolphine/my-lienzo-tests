package org.roger600.lienzo.client.toolboxNew.impl2;

import java.util.Iterator;

import com.ait.lienzo.shared.core.types.Direction;
import org.roger600.lienzo.client.toolboxNew.Grid;
import org.roger600.lienzo.client.toolboxNew.ItemsToolbox;

public abstract class DelegateItemsToolbox<T extends DelegateItemsToolbox, G extends Grid>
        implements ItemsToolbox<T, G, AbstractItem> {

    protected abstract ItemsToolbox<?, G, AbstractItem> getDelegate();

    @Override
    public T grid(final G grid) {
        getDelegate().grid(grid);
        return cast();
    }

    @Override
    public T at(final Direction at) {
        getDelegate().at(at);
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
    public T add(final AbstractItem... items) {
        getDelegate().add(items);
        return cast();
    }

    @Override
    public Iterator<AbstractItem> iterator() {
        return getDelegate().iterator();
    }

    @Override
    public void destroy() {
        getDelegate().destroy();
    }

    @SuppressWarnings("unchecked")
    private T cast() {
        return (T) this;
    }
}
