package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.Rectangle;
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

        private static final String DECORATOR_STROKE_COLOR = "#595959";
        private static final double DECORATOR_STROKE_WIDTH = 1.5;
        private static final double DECORATOR_CORNER_RADIUS = 1.5;
        private static final double PADDING = 5;
        private static final double OFFSET = -(PADDING / 2);

        private final Rectangle decorator;

        private BoxDecorator(final double width,
                             final double height) {
            this.decorator = new Rectangle(1,
                                           1)
                    .setCornerRadius(DECORATOR_CORNER_RADIUS)
                    .setStrokeWidth(DECORATOR_STROKE_WIDTH)
                    .setStrokeColor(DECORATOR_STROKE_COLOR)
                    .setDraggable(false)
                    .setFillAlpha(0)
                    .setFillBoundsForSelection(false);
            setSize(width,
                    height);
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
                    .setWidth(width + PADDING)
                    .setHeight(height + PADDING)
                    .setX(OFFSET)
                    .setY(OFFSET);
            return this;
        }

        @Override
        public Rectangle asPrimitive() {
            return decorator;
        }
    }
}
