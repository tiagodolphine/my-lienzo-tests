package org.roger600.lienzo.client.toolboxNew;

import com.ait.lienzo.client.core.types.Point2D;

public interface Grid extends Iterable<Grid.Point> {

    public static class Point {

        private final int x;
        private final int y;

        public Point(int x,
                     int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Point2D asPoint2D() {
            return new Point2D(x,
                               y);
        }
    }

    public Point findPosition(Point anchorPoint);
}
