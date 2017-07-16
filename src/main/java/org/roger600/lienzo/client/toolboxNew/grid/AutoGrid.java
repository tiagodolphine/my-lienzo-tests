package org.roger600.lienzo.client.toolboxNew.grid;

import java.util.Iterator;

import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.Direction;
import org.roger600.lienzo.client.toolboxNew.Grid;

public class AutoGrid implements Grid {

    public enum GridDirection {
        HORIZONTAL,
        VERTICAL;
    }

    private final LayoutGrid delegate;
    private GridDirection direction;
    private int max;

    public AutoGrid(final double padding,
                    final double iconSize,
                    final GridDirection direction,
                    final int max) {
        if (padding < 0 || iconSize < 0 || max < 1 || null == direction) {
            throw new IllegalArgumentException("Not possible to instantiate grid.");
        }
        this.delegate = new LayoutGrid(padding,
                                       iconSize);
        this.direction = direction;
        this.max = max;
    }
/*
    @Override
    public Point findPosition(final Point anchorPoint) {
        return delegate.findPosition(anchorPoint);
    }

   */

    @Override
    public Iterator<Point2D> iterator() {
        return delegate.iterator();
    }

    public AutoGrid update(final Direction at,
                           final int itemsCount,
                           final int maxSize) {
        final double padding = delegate.getPadding();
        final double iconSize = delegate.getIconSize();
        final double size = padding + iconSize;
        int fixed = 0;
        int acc = 0;
        double currentSize = padding;
        for (int i = 0; i < itemsCount; i++) {
            if ((currentSize + size) > maxSize) {
                currentSize = padding;
                acc = 0;
                fixed += 1;
            }
            currentSize += size;
            acc++;
        }

        final int rows = isH() ? fixed : acc;
        final int cols = isV() ? acc : fixed;
        final Direction direction = translateDirection(at,
                                                       this.direction);
        delegate.towards(direction)
                .setRows(rows)
                .setCols(cols);
        return this;
    }

    private static Direction translateDirection(final Direction at,
                                                final GridDirection direction) {
        switch (direction) {
            case HORIZONTAL:
                return isN(at) || isNE(at)
                        || isS(at) || isSE(at) ?
                        Direction.EAST :
                        Direction.WEST;
            case VERTICAL:
                return isN(at) || isNE(at)
                        || isNW(at) ?
                        Direction.SOUTH :
                        Direction.NORTH;
        }
        return Direction.NONE;
    }

    private boolean isH() {
        return GridDirection.HORIZONTAL.equals(direction);
    }

    private boolean isV() {
        return GridDirection.VERTICAL.equals(direction);
    }

    private static boolean isN(final Direction at) {
        return Direction.NORTH.equals(at);
    }

    private static boolean isNE(final Direction at) {
        return Direction.NORTH_EAST.equals(at);
    }

    private static boolean isNW(final Direction at) {
        return Direction.NORTH_WEST.equals(at);
    }

    private static boolean isS(final Direction at) {
        return Direction.SOUTH.equals(at);
    }

    private static boolean isSE(final Direction at) {
        return Direction.SOUTH_EAST.equals(at);
    }

    private static boolean isSW(final Direction at) {
        return Direction.SOUTH_WEST.equals(at);
    }

    private static boolean isE(final Direction at) {
        return Direction.EAST.equals(at);
    }

    private static boolean isW(final Direction at) {
        return Direction.WEST.equals(at);
    }
}
