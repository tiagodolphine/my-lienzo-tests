package org.roger600.lienzo.client.toolboxNew.impl2.item;

import com.ait.lienzo.client.core.event.NodeMouseClickHandler;

public abstract class ButtonItem<T extends ButtonItem>
        extends PrimitiveItem<T> {

    public abstract T onClick(NodeMouseClickHandler clickHandler);
}
