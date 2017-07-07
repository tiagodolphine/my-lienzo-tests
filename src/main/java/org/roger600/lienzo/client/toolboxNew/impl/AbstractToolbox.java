package org.roger600.lienzo.client.toolboxNew.impl;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.shared.core.types.Direction;
import org.roger600.lienzo.client.toolboxNew.Grid;
import org.roger600.lienzo.client.toolboxNew.Toolbox;

public abstract class AbstractToolbox<N, T extends Toolbox> implements Toolbox<N, T> {

    public abstract Grid getGrid();

    public abstract Direction getAt();

    protected abstract T onRefresh(Runnable callback);

    protected abstract Group getGroup();
}
