package org.roger600.lienzo.client.toolboxNew;

public interface ItemsToolbox<N, B extends ToolboxItem, T extends ItemsToolbox> extends Toolbox<N, T> {

    public T add(B... buttons);

    public boolean remove(B button);
}
