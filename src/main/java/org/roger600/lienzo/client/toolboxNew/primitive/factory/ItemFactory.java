package org.roger600.lienzo.client.toolboxNew.primitive.factory;

import com.ait.lienzo.client.core.shape.IPrimitive;
import org.roger600.lienzo.client.toolboxNew.primitive.Button;
import org.roger600.lienzo.client.toolboxNew.primitive.DefaultItem;
import org.roger600.lienzo.client.toolboxNew.primitive.impl.ButtonImpl;
import org.roger600.lienzo.client.toolboxNew.primitive.impl.ItemImpl;

public class ItemFactory {

    public static DefaultItem<? extends DefaultItem> itemFor(final IPrimitive<?> icon) {
        return new ItemImpl(icon);
    }

    public static Button<? extends Button> buttonFor(final IPrimitive<?> icon) {
        return new ButtonImpl(icon);
    }

}
