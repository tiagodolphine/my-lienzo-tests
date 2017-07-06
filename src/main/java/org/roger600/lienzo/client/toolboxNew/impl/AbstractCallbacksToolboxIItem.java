package org.roger600.lienzo.client.toolboxNew.impl;

public abstract class AbstractCallbacksToolboxIItem<T> extends AbstractToolboxIItem {

    public abstract T onFocus(final Runnable callback);

    public abstract T onUnFocus(final Runnable callback);

    public abstract T onClick(final Runnable callback);

    public abstract T onDown(final Runnable callback);
}
