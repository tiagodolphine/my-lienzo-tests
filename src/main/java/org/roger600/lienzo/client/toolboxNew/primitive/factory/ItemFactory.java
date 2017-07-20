package org.roger600.lienzo.client.toolboxNew.primitive.factory;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.shared.core.types.Direction;
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

    public static ButtonGridItem dropDownFor(final Shape<?> shape) {
        return buttonItemsFor(shape, Direction.SOUTH_WEST);
    }

    public static ButtonGridItem dropDownFor(final Group group) {
        return buttonItemsFor(group, Direction.SOUTH_WEST);
    }

    private static ButtonGridItem buttonItemsFor(final Shape<?> shape,
                                                 final Direction at) {
        return new ButtonGridItemImpl(shape)
                .at(at);
    }

    private static ButtonGridItem buttonItemsFor(final Group group,
                                                 final Direction at) {
        return new ButtonGridItemImpl(group)
                .at(at);
    }
}
