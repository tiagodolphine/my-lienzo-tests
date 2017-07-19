package org.roger600.lienzo.client.toolboxNew.primitive;

import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragMoveHandler;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;

public interface Button extends DefaultItem<Button> {

    Button onClick(NodeMouseClickHandler clickHandler);

    Button onDragStart(NodeDragStartHandler handler);

    Button onDragMove(NodeDragMoveHandler handler);

    Button onDragEnd(NodeDragEndHandler handler);
}
