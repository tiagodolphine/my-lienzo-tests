package org.roger600.lienzo.client.toolboxNew;

import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.Direction;

public interface Toolbox<T extends Toolbox, G extends Grid, I extends Item>
        extends ItemGrid<T, G, I> {

    public T at(Direction at);

    public T offset(Point2D offset);
}
