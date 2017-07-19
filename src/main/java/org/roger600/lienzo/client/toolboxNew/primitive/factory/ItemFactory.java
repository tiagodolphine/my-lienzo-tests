package org.roger600.lienzo.client.toolboxNew.primitive.factory;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Shape;
import org.roger600.lienzo.client.toolboxNew.primitive.Button;
import org.roger600.lienzo.client.toolboxNew.primitive.ButtonItems;
import org.roger600.lienzo.client.toolboxNew.primitive.ShapeItem;
import org.roger600.lienzo.client.toolboxNew.primitive.impl.ButtonImpl;
import org.roger600.lienzo.client.toolboxNew.primitive.impl.ButtonItemsImpl;
import org.roger600.lienzo.client.toolboxNew.primitive.impl.ShapeItemImpl;

public class ItemFactory {

    public static ShapeItem shapeFor(final Shape<?> shape) {
        return new ShapeItemImpl(shape);
    }

    public static Button buttonFor(final Shape<?> shape) {
        return new ButtonImpl(shape);
    }

    public static Button buttonFor(final Group group) {
        return new ButtonImpl(group);
    }

    public static ButtonItems buttonItemsFor(final Shape<?> shape) {
        return new ButtonItemsImpl(shape);
    }

    public static ButtonItems buttonItemsFor(final Group group) {
        return new ButtonItemsImpl(group);
    }
}
