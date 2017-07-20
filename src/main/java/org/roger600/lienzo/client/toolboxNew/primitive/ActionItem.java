package org.roger600.lienzo.client.toolboxNew.primitive;

import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragMoveHandler;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;

public interface ActionItem<T extends ActionItem> extends DecoratedItem<T> {

    T onClick(NodeMouseClickHandler clickHandler);

    T onDragStart(NodeDragStartHandler handler);

    T onDragMove(NodeDragMoveHandler handler);

    T onDragEnd(NodeDragEndHandler handler);
}
