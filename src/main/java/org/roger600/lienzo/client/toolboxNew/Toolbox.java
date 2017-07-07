package org.roger600.lienzo.client.toolboxNew;

import com.ait.lienzo.client.core.shape.IContainer;
import com.ait.lienzo.shared.core.types.Direction;

public interface Toolbox<N, T extends Toolbox> {

    public T attachTo(IContainer<?, N> parent);

    public T at(Direction at);

    public T grid(Grid grid);

    public T show();

    public T hide();

    public void destroy();
}
