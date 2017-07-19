package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.shared.core.types.ColorName;
import org.roger600.lienzo.client.toolboxNew.primitive.DefaultDecoratorItem;

public class DecoratorsFactory {

    public static BoxDecorator box() {
        return new BoxDecorator(1,
                                1);
    }

    public static BoxDecorator box(final double width,
                                   final double height) {
        return new BoxDecorator(width,
                                height);
    }

    public static class BoxDecorator
            extends DefaultDecoratorItem<BoxDecorator> {

        //private static final String DECORATOR_STROKE_COLOR = "#BBBBBB";
        private static final String DECORATOR_STROKE_COLOR = "#FF0000";
        private static final double DECORATOR_STROKE_WIDTH = 3;
        private static final double DECORATOR_CORNER_RADIUS = 1;

        private final Rectangle decorator;

        private BoxDecorator(final double width,
                             final double height) {
            this.decorator = new Rectangle(width,
                                           height)
                    .setCornerRadius(DECORATOR_CORNER_RADIUS)
                    .setStrokeWidth(DECORATOR_STROKE_WIDTH)
                    .setStrokeWidth(5)
                    .setStrokeColor(DECORATOR_STROKE_COLOR)
                    .setStrokeColor(ColorName.RED)
                    .setDraggable(false)
                    .setFillAlpha(0)
                    .setFillBoundsForSelection(false);
        }

        public BoxDecorator setStrokeWidth(final double strokeWidth) {
            this.decorator.setStrokeWidth(strokeWidth);
            return this;
        }

        public BoxDecorator setStrokeColor(final String color) {
            this.decorator.setStrokeColor(color);
            return this;
        }

        public BoxDecorator setCornerRadius(final double radius) {
            this.decorator.setCornerRadius(radius);
            return this;
        }

        @Override
        public BoxDecorator setSize(final double width,
                                    final double height) {
            this.decorator
                    .setWidth(width)
                    .setHeight(height);
            return this;
        }

        @Override
        public Rectangle asPrimitive() {
            return decorator;
        }
    }
}
