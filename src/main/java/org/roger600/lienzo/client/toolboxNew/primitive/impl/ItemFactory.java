package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.Shape;
import org.roger600.lienzo.client.toolboxNew.primitive.ShapeItem;

public class ItemFactory {

    public static final ButtonItemImpl.ButtonFactory BUTTON_FACTORY = new ButtonItemImpl.ButtonFactory();
    public static final ButtonGridItemImpl.DropDownFactory DROP_DOWN_FACTORY = new ButtonGridItemImpl.DropDownFactory();

    public static ShapeItem shapeFor(final Shape<?> shape) {
        return new ShapeItemImpl(shape);
    }
}
