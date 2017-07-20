package org.roger600.lienzo.client.toolboxNew.primitive.factory;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Shape;
import org.roger600.lienzo.client.toolboxNew.primitive.ButtonGridItem;
import org.roger600.lienzo.client.toolboxNew.primitive.ButtonItem;
import org.roger600.lienzo.client.toolboxNew.primitive.ShapeItem;
import org.roger600.lienzo.client.toolboxNew.primitive.impl.ButtonGridItemImpl;
import org.roger600.lienzo.client.toolboxNew.primitive.impl.ButtonItemImpl;
import org.roger600.lienzo.client.toolboxNew.primitive.impl.ShapeItemImpl;

public class ItemFactory {

    public static ShapeItem shapeFor(final Shape<?> shape) {
        return new ShapeItemImpl(shape);
    }

    public static ButtonItem buttonFor(final Shape<?> shape) {
        return new ButtonItemImpl(shape);
    }

    public static ButtonItem buttonFor(final Group group) {
        return new ButtonItemImpl(group);
    }

    public static ButtonGridItem buttonItemsFor(final Shape<?> shape) {
        return new ButtonGridItemImpl(shape);
    }

    public static ButtonGridItem buttonItemsFor(final Group group) {
        return new ButtonGridItemImpl(group);
    }
}
