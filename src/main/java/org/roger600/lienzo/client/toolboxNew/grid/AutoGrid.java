package org.roger600.lienzo.client.toolboxNew.grid;

import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.Direction;

public class AutoGrid extends AbstractLayoutGrid<AutoGrid> implements SizeConstrainedGrid<Point2D> {

    public enum GridDirection {
        HORIZONTAL,
        VERTICAL;

        public static boolean isHorizontal(final GridDirection direction) {
            return GridDirection.HORIZONTAL.equals(direction);
        }

        public static boolean isVertical(final GridDirection direction) {
            return GridDirection.VERTICAL.equals(direction);
        }
    }

    public static class Builder {

        private double pad = 5d;
        private double size = 15d;
        private GridDirection direction = GridDirection.HORIZONTAL;
        private WiresShape shape = null;

        public Builder withPadding(double size) {
            this.pad = size;
            return this;
        }

        public Builder withIconSize(double size) {
            this.size = size;
            return this;
        }

        public Builder towardsDirection(GridDirection direction) {
            this.direction = direction;
            return this;
        }

        public Builder forShape(WiresShape shape) {
            this.shape = shape;
            return this;
        }

        public AutoGrid build() {
            assert null != shape;
            final BoundingBox boundingBox = shape.getPath().getBoundingBox();
            final double max = GridDirection.isHorizontal(direction) ?
                    boundingBox.getWidth() :
                    boundingBox.getHeight();
            return new AutoGrid(pad,
                                size,
                                direction,
                                max);
        }
    }

    private GridDirection direction;
    private double maxSize;

    public AutoGrid(final double padding,
                    final double iconSize,
                    final GridDirection direction,
                    final double maxSize) {
        super(padding,
              iconSize);
        if (maxSize < 1 || null == direction) {
            throw new IllegalArgumentException("Not possible to instantiate grid.");
        }
        this.direction = direction;
        this.maxSize = maxSize;
    }

    public AutoGrid direction(final GridDirection direction) {
        this.direction = direction;
        return this;
    }

    @Override
    public void setSize(final double width,
                        final double height) {
        if (GridDirection.isHorizontal(getDirection())) {
            maxSize(width);
        } else {
            maxSize(height);
        }
    }

    public AutoGrid maxSize(final double size) {
        this.maxSize = size;
        return this;
    }

    public GridDirection getDirection() {
        return direction;
    }

    public double getMaxSize() {
        return maxSize;
    }

    @Override
    protected AbstractGridLayoutIterator createIterator() {
        return new AutoGridLayoutIterator(getPadding(),
                                          getIconSize(),
                                          getDirection(),
                                          getMaxSize());
    }

    private static class AutoGridLayoutIterator extends AbstractGridLayoutIterator {

        private final double padding;
        private final double iconSize;
        private final Direction towards;
        private final int maxRows;
        private final int maxCols;
        private int currentRow;
        private int currentColumn;

        private AutoGridLayoutIterator(final double padding,
                                       final double iconSize,
                                       final GridDirection direction,
                                       final double maxSize) {
            this.padding = padding;
            this.iconSize = iconSize;
            this.towards = translateDirection(direction);

            final double d1 = getPadding() + getIconSize();
            final int maxItems = (int) (maxSize / d1); // Round down to an integer index value.
            if (GridDirection.isHorizontal(direction)) {
                maxRows = -1;
                currentRow = 0;
                maxCols = maxItems;
                currentColumn = -1;
            } else {
                maxRows = maxItems;
                currentColumn = 0;
                maxCols = -1;
                currentRow = -1;
            }
        }

        @Override
        protected double getPadding() {
            return padding;
        }

        @Override
        protected double getIconSize() {
            return iconSize;
        }

        @Override
        protected Direction getTowards() {
            return towards;
        }

        // Provides an infinite grid, it just grows.
        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        protected int[] getNextIndex() {
            if (currentRow == (maxRows - 1)) {
                currentRow = -1;
                currentColumn++;
            } else if (currentColumn == (maxCols - 1)) {
                currentColumn = -1;
                currentRow++;
            }
            if (maxCols > -1) {
                currentColumn++;
            } else if (maxRows > -1) {
                currentRow++;
            }
            return new int[]{currentRow, currentColumn};
        }
    }

    private static Direction translateDirection(final GridDirection direction) {
        return GridDirection.isHorizontal(direction) ?
                Direction.EAST :
                Direction.SOUTH;
    }
}
