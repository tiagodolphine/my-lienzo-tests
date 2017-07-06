package org.roger600.lienzo.client.toolboxNew.impl;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import org.roger600.lienzo.client.toolboxNew.ItemsToolbox;

public class WiresShapeItemsToolbox
        extends DelegateToolbox<IPrimitive<?>, WiresShapeItemsToolbox>
        implements ItemsToolbox<IPrimitive<?>, AbstractToolboxIItem, WiresShapeItemsToolbox> {

    private final IPrimitiveItemsToolbox toolbox;

    public WiresShapeItemsToolbox(final WiresShape shape) {
        this.toolbox = new IPrimitiveItemsToolbox(new WiresShapeToolbox(shape));
    }

    @Override
    protected AbstractToolbox<IPrimitive<?>, ?> getDelegate() {
        return toolbox;
    }

    @Override
    public WiresShapeItemsToolbox add(final AbstractToolboxIItem... buttons) {
        toolbox.add(buttons);
        return this;
    }

    @Override
    public boolean remove(final AbstractToolboxIItem button) {
        return toolbox.remove(button);
    }
}
