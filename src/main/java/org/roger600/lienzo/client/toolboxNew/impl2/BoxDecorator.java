package org.roger600.lienzo.client.toolboxNew.impl2;

import com.ait.lienzo.client.core.shape.Rectangle;
import org.roger600.lienzo.client.toolboxNew.grid.AbstractLayoutGrid;

public class BoxDecorator
        extends DecoratorItem<BoxDecorator> {

    private static final String DECORATOR_STROKE_COLOR = "#BBBBBB";
    private static final double DECORATOR_STROKE_WIDTH = 10;
    private static final double DECORATOR_CORNER_RADIUS = 0;

    public static class Builder {

        public static BoxDecorator build(final double width,
                                         final double height) {
            return new BoxDecorator(width,
                                    height);
        }

        public static BoxDecorator build(final AbstractLayoutGrid layoutGrid) {
            final double padding = layoutGrid.getPadding();
            final double iconSize = layoutGrid.getIconSize();
            final double width = iconSize + padding;
            final double height = iconSize + padding;
            return new BoxDecorator(width,
                                    height);
        }
    }

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
        ;
    }

    @Override
    public Rectangle asPrimitive() {
        return decorator;
    }

    @Override
    public BoxDecorator show() {
        decorator.setStrokeAlpha(1);
        return this;
    }

    @Override
    public BoxDecorator hide() {
        decorator.setStrokeAlpha(0);
        return this;
    }

    @Override
    public void destroy() {
        decorator.removeFromParent();
    }
}
