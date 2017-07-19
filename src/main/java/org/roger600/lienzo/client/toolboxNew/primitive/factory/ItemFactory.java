package org.roger600.lienzo.client.toolboxNew.primitive.factory;

import com.ait.lienzo.client.core.shape.IPrimitive;
import org.roger600.lienzo.client.toolboxNew.primitive.Button;
import org.roger600.lienzo.client.toolboxNew.primitive.ButtonItems;
import org.roger600.lienzo.client.toolboxNew.primitive.DefaultItem;
import org.roger600.lienzo.client.toolboxNew.primitive.impl.ButtonImpl;
import org.roger600.lienzo.client.toolboxNew.primitive.impl.ButtonItemsImpl;
import org.roger600.lienzo.client.toolboxNew.primitive.impl.ItemImpl;

public class ItemFactory {

    public static DefaultItem<? extends DefaultItem> itemFor(final IPrimitive<?> icon) {
        return new ItemImpl(icon);
    }

    public static Button buttonFor(final IPrimitive<?> icon) {
        return new ButtonImpl(icon);
    }

    public static ButtonItems buttonItemsFor(final IPrimitive<?> icon) {
        return new ButtonItemsImpl(icon);
    }
}
