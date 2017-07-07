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

package org.roger600.lienzo.client.toolboxNew;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.ait.lienzo.shared.core.types.Direction;

public class LayoutGrid implements Grid {

    private int padding;
    private int iconSize;
    private int rows;
    private int cols;
    private Direction towards;

    public LayoutGrid(final int padding,
                      final int iconSize,
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

    LayoutGrid(final int padding,
               final int iconSize) {
        this(padding,
             iconSize,
             Direction.NORTH,
             1,
             1);
    }

    @Override
    public Point findPosition(final Point anchorPoint) {
        int width = getWidth();
        int height = getHeight();
        int x = anchorPoint.getX();
        int y = anchorPoint.getY();
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
        return new Grid.Point(x,
                              y);
    }

    @Override
    public Iterator<Grid.Point> iterator() {
        return new GridIterator(this);
    }

    public LayoutGrid towards(Direction towards) {
        this.towards = towards;
        return this;
    }

    public LayoutGrid setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public LayoutGrid setCols(int cols) {
        this.cols = cols;
        return this;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getPadding() {
        return padding;
    }

    public int getIconSize() {
        return iconSize;
    }

    public Direction getTowards() {
        return towards;
    }

    public int size() {
        return getRows() * getCols();
    }

    public int getWidth() {
        return calculateDistance(getCols());
    }

    public int getHeight() {
        return calculateDistance(getRows());
    }

    protected static class GridIterator implements Iterator<Grid.Point> {

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
        public Grid.Point next() {
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
            return this.grid.findPosition(row,
                                          column);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Grid.Point findPosition(final int row,
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
        int x = calculateDistance(col);
        int y = calculateDistance(row);
        return new Grid.Point(x,
                              y);
    }

    private int calculateDistance(final int position) {
        return padding + (position * (padding + iconSize));
    }

    private boolean isInRange(final int value,
                              final int max) {
        return value >= 0 && value < max;
    }
}
