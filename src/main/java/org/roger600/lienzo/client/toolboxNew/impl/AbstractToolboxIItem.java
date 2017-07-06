package org.roger600.lienzo.client.toolboxNew.impl;

import com.ait.lienzo.client.core.shape.ContainerNode;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.types.Point2D;
import org.roger600.lienzo.client.toolboxNew.ToolboxItem;

public abstract class AbstractToolboxIItem implements ToolboxItem {

    public abstract AbstractToolboxIItem attachTo(final ContainerNode<IPrimitive<?>, ?> parent);

    public abstract AbstractToolboxIItem setLocation(final Point2D location);

    public abstract void destroy();
}
