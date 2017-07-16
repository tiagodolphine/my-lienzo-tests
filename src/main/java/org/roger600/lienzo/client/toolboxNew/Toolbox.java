package org.roger600.lienzo.client.toolboxNew;

import com.ait.lienzo.shared.core.types.Direction;

public interface Toolbox<T extends Toolbox> extends Item<T> {

    public T at(Direction at);
}
