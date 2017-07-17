package org.roger600.lienzo.client.toolboxNew.impl2.item;

import com.ait.lienzo.client.core.shape.IPrimitive;
import org.roger600.lienzo.client.toolboxNew.impl2.AbstractGroupItem;

public class PrimitiveItem extends AbstractGroupItem<PrimitiveItem> {

    public PrimitiveItem(final IPrimitive<?> prim) {
        super();
        init(prim);
    }

    private void init(final IPrimitive<?> prim) {
        getGroupItem().add(prim);
    }
}
