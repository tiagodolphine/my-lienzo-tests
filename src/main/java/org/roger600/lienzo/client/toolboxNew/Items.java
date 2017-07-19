package org.roger600.lienzo.client.toolboxNew;

public interface Items<T extends Items, G extends Grid, I extends Item>
        extends Item<T>,
                Iterable<I> {

    public T grid(G grid);

    public T add(I... items);
}
