package org.roger600.lienzo.client.toolboxNew.impl2.item;

import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragMoveHandler;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;

public abstract class ButtonItem<T extends ButtonItem>
        extends PrimitiveItem<T> {

    public abstract T onClick(NodeMouseClickHandler clickHandler);

    public abstract T onDragStart(final NodeDragStartHandler handler);

    public abstract T onDragMove(final NodeDragMoveHandler handler);

    public abstract T onDragEnd(final NodeDragEndHandler handler);

}
