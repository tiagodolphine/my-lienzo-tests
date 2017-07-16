/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.roger600.lienzo.client.toolboxNew.grid;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.Direction;
import com.google.gwt.core.client.GWT;

public class LayoutGrid implements Point2DGrid {

    private double padding;
    private double iconSize;
    private int rows;
    private int cols;
    private Direction towards;

    public LayoutGrid(final double padding,
                      final double iconSize,
                      final Direction towards,
                      final int rows,
                      final int cols) {
        if (padding < 0 || iconSize < 0 || rows < 1 || cols < 1) {
            throw new IllegalArgumentException("Not possible to instantiate grid.");
        }
        this.padding = padding;
        this.iconSize = iconSize;
        this.towards = towards;
        this.rows = rows;
        this.cols = cols;
    }

    LayoutGrid(final double padding,
               final double iconSize) {
        this(padding,
             iconSize,
             Direction.NORTH,
             1,
             1);
    }

    @Override
    public Iterator<Point2D> iterator() {
        return new GridIterator(this);
    }

    public LayoutGrid towards(final Direction towards) {
        this.towards = towards;
        return this;
    }

    public LayoutGrid setRows(final int rows) {
        this.rows = rows;
        return this;
    }

    public LayoutGrid setCols(final int cols) {
        this.cols = cols;
        return this;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double getPadding() {
        return padding;
    }

    public double getIconSize() {
        return iconSize;
    }

    private static class GridIterator implements Iterator<Point2D> {

        private final LayoutGrid grid;
        private int currentRow = 0;
        private int currentColumn = 0;

        public GridIterator(LayoutGrid grid) {
            this.grid = grid;
        }

        @Override
        public boolean hasNext() {
            return currentColumn <= lastColumn() && currentRow <= lastRow();
        }

        private int lastColumn() {
            return this.grid.getCols() - 1;
        }

        private int lastRow() {
            return this.grid.getRows() - 1;
        }

        @Override
        public Point2D next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int column = currentColumn;
            int row = currentRow;
            if (currentColumn == (this.grid.getCols() - 1)) {
                currentColumn = 0;
                currentRow++;
            } else {
                currentColumn++;
            }
            final Point2D location = this.grid.getLocation(row,
                                                           column);
            GWT.log("LAYOUT GRID LOCATION = " + location);
            int ox = isEast(grid.towards) ? 1 : -1;
            int oy = isNorth(grid.towards) ? -1 : 1;
            GWT.log("LAYOUT GRID OFFSET = [" + ox + ", " + oy + " ]");
            return new Point2D(location.getX() * ox,
                               location.getY() * oy);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private double getWidth() {
            return grid.calculateDistance(currentColumn);
        }

        private double getHeight() {
            return grid.calculateDistance(currentRow);
        }
    }

    private Point2D getLocation(final int row,
                                final int col) {
        if (!isInRange(row,
                       getRows())) {
            throw new IllegalArgumentException(
                    row + " is incorrect row value. Value have to be from 0 to " + (getRows() - 1)
            );
        }
        if (!isInRange(col,
                       getCols())) {
            throw new IllegalArgumentException(
                    col + " is incorrect col value. Value have to be from 0 to " + (getCols() - 1)
            );
        }
        double x = calculateDistance(col);
        double y = calculateDistance(row);
        return new Point2D(x,
                           y);
    }

    private double calculateDistance(final int position) {
        return padding + (position * (padding + iconSize));
    }

    private boolean isInRange(final int value,
                              final int max) {
        return value >= 0 && value < max;
    }

    private static Point2D getOffset(final double width,
                                     final double height,
                                     final Direction towards) {
        double x = 0d;
        double y = 0d;
        switch (towards) {
            case NORTH:
                x -= width / 2;
                y -= height;
                break;
            case SOUTH:
                x -= width / 2;
                break;
            case EAST:
                y -= height / 2;
                break;
            case WEST:
                x -= width;
                y -= height / 2;
                break;
            case NONE:
                x -= width / 2;
                y -= height / 2;
                break;
            case NORTH_EAST:
                y -= height;
                break;
            case SOUTH_EAST:
                break;
            case SOUTH_WEST:
                x -= width;
                break;
            case NORTH_WEST:
                x -= width;
                y -= height;
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return new Point2D(x,
                           y);
    }

    private static boolean isEast(final Direction towards) {
        boolean east = true;
        switch (towards) {
            case WEST:
                east = false;
                break;
            case SOUTH_WEST:
                east = false;
                break;
            case NORTH_WEST:
                east = false;
                break;
        }
        return east;
    }

    private static boolean isNorth(final Direction towards) {
        boolean north = true;
        switch (towards) {
            case SOUTH:
                north = false;
                break;
            case SOUTH_EAST:
                north = false;
                break;
            case SOUTH_WEST:
                north = false;
                break;
        }
        return north;
    }
}
