package org.roger600.lienzo.client.toolboxNew.primitive.factory;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.types.BoundingBox;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractDecoratorItem;

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

        private final MultiPath decorator;
        private double padding;

        private BoxDecorator() {
            this(rect(new MultiPath(),
                      1,
                      1,
                      DECORATOR_CORNER_RADIUS)
                         .setStrokeWidth(DECORATOR_STROKE_WIDTH)
                         .setStrokeColor(DECORATOR_STROKE_COLOR)
                         .setFillAlpha(0)
                         .setDraggable(false)
                         .setListening(false)
                         .setFillBoundsForSelection(false),
                 PADDING);
        }

        private BoxDecorator(final MultiPath decorator,
                             final double padding) {
            this.decorator = decorator;
            this.padding = padding;
        }

        public BoxDecorator setFillColor(final String color) {
            this.decorator
                    .setFillAlpha(1)
                    .setFillColor(color);
            return this;
        }

        public BoxDecorator setStrokeWidth(final double strokeWidth) {
            this.decorator.setStrokeWidth(strokeWidth);
            return this;
        }

        public BoxDecorator setStrokeColor(final String color) {
            this.decorator.setStrokeColor(color);
            return this;
        }

        public BoxDecorator setPadding(final double padding) {
            this.padding = padding;
            return this;
        }

        @Override
        public BoxDecorator setBoundingBox(final BoundingBox boundingBox) {
            final double offset = -(padding / 2);
            rect(decorator,
                 boundingBox.getWidth() + padding,
                 boundingBox.getHeight() + padding,
                 DECORATOR_CORNER_RADIUS)
                    .setX(offset)
                    .setY(offset);
            return this;
        }

        @Override
        public MultiPath asPrimitive() {
            return decorator;
        }

        @Override
        public BoxDecorator copy() {
            return new BoxDecorator(decorator.copy(),
                                    padding);
        }

        private static MultiPath rect(final MultiPath path,
                                      final double w,
                                      final double h,
                                      final double r) {
            if ((w > 0) && (h > 0)) {
                path.clear();
                if ((r > 0) && (r < (w / 2)) && (r < (h / 2))) {
                    path.M(r,
                           0);
                    path.L(w - r,
                           0);
                    path.A(w,
                           0,
                           w,
                           r,
                           r);
                    path.L(w,
                           h - r);
                    path.A(w,
                           h,
                           w - r,
                           h,
                           r);
                    path.L(r,
                           h);
                    path.A(0,
                           h,
                           0,
                           h - r,
                           r);
                    path.L(0,
                           r);
                    path.A(0,
                           0,
                           r,
                           0,
                           r);
                } else {
                    path.rect(0,
                              0,
                              w,
                              h);
                }
                path.Z();
            }
            return path;
        }
    }
}
