package org.roger600.lienzo.client.toolboxNew;

public interface ContainerItem<T extends ContainerItem, G extends Grid, I extends Item>
        extends Item<T>,
                Iterable<I> {

    public T grid(G grid);

    public T add(I... items);
}
