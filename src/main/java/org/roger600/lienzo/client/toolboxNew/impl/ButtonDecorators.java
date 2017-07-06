package org.roger600.lienzo.client.toolboxNew.impl;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Rectangle;

public class ButtonDecorators {

    public static BoxDecorator box(final double width,
                                   final double height) {
        return new BoxDecorator(width,
                                height);
    }

    private static class BoxDecorator implements DecoratedButtonItem.Decorator {

        private static final String DECORATOR_STROKE_COLOR = "#BBBBBB";
        private static final double DECORATOR_STROKE_WIDTH = 3;
        private static final double DECORATOR_CORNER_RADIUS = 0;

        private final Rectangle decorator;

        private BoxDecorator(final double width,
                             final double height) {
            this.decorator = new Rectangle(width,
                                           height)
                    .setCornerRadius(DECORATOR_CORNER_RADIUS)
                    .setStrokeWidth(DECORATOR_STROKE_WIDTH)
                    .setStrokeColor(DECORATOR_STROKE_COLOR)
                    .setDraggable(false)
                    .setFillAlpha(0)
                    .setFillBoundsForSelection(false);
        }

        @Override
        public IPrimitive<?> getPrimitive() {
            return decorator;
        }

        @Override
        public void show() {
            decorator.setStrokeAlpha(1);
        }

        @Override
        public void hide() {
            decorator.setStrokeAlpha(0);
        }
    }
}
