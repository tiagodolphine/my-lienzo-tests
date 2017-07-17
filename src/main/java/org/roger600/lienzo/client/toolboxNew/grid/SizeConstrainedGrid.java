package org.roger600.lienzo.client.toolboxNew.grid;

import org.roger600.lienzo.client.toolboxNew.Grid;

public interface SizeConstrainedGrid<P> extends Grid<P> {

    void setSize(double width,
                 double height);
}
