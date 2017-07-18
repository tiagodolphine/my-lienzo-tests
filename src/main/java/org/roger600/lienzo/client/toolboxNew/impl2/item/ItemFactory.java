package org.roger600.lienzo.client.toolboxNew.impl2.item;

import com.ait.lienzo.client.core.shape.IPrimitive;

public class ItemFactory {

    public static PrimitiveItem<? extends PrimitiveItem> itemFor(final IPrimitive<?> icon) {
        return new PrimitiveItemImpl(icon);
    }

    public static ButtonItem<? extends ButtonItem> buttonFor(final IPrimitive<?> icon) {
        return new ButtonItemImpl(icon);
    }

    public static CompositeItem<? extends CompositeItem> dropDownFor(final IPrimitive<?> icon) {
        return new DropDownItem(icon);
    }
}
