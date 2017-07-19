package org.roger600.lienzo.client.toolboxNew.primitive;

import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import org.roger600.lienzo.client.toolboxNew.Item;

public interface DefaultItem<T extends DefaultItem> extends Item<T> {

    boolean isVisible();

    T decorate(DecoratorItem<?> decorator);

    T onMouseEnter(NodeMouseEnterHandler handler);

    T onMouseExit(NodeMouseExitHandler handler);
}
