package org.roger600.lienzo.client.toolboxNew.primitive.factory;

import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.types.BoundingBox;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractDecoratorItem;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public class DecoratorsFactory {

    public static BoxDecorator box() {
        return new BoxDecorator();
    }

    public static class BoxDecorator
            extends AbstractDecoratorItem<BoxDecorator> {

        private static final String DECORATOR_STROKE_COLOR = "#595959";
        private static final double DECORATOR_STROKE_WIDTH = 1.5;
        private static final double DECORATOR_CORNER_RADIUS = 1.5;
        private static final double PADDING = 5;
        private static final double OFFSET = -(PADDING / 2);

        private final Rectangle decorator;

        private BoxDecorator() {
            this.decorator = new Rectangle(1,
                                           1)
                    .setCornerRadius(DECORATOR_CORNER_RADIUS)
                    .setStrokeWidth(DECORATOR_STROKE_WIDTH)
                    .setStrokeColor(DECORATOR_STROKE_COLOR)
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
        public BoxDecorator forBoundingBox(final Supplier<BoundingBox> boundingBoxSupplier) {
            this.decorator
                    .setWidth(boundingBoxSupplier.get().getWidth() + PADDING)
                    .setHeight(boundingBoxSupplier.get().getHeight() + PADDING)
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
