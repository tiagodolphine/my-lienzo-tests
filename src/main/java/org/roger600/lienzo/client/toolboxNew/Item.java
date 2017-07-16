package org.roger600.lienzo.client.toolboxNew;

public interface Item<T extends Item> {

    public T show();

    public T hide();

    public void destroy();
}
