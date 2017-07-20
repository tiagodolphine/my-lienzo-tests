package org.roger600.lienzo.client.toolboxNew;

public interface ItemGrid<T extends ItemGrid, G extends Grid, I extends Item>
        extends Item<T>,
                Iterable<I> {

    public T grid(G grid);

    public T add(I... items);
}
