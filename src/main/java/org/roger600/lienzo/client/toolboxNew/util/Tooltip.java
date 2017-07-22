package org.roger600.lienzo.client.toolboxNew.util;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.Direction;

public class Tooltip {

    private final MultiPath path;
    private final Text text;
    private final Group group;
    private Direction direction;
    private double padding;

    public Tooltip() {
        this.group = new Group();
        this.path = new MultiPath();
        this.text = new Text("");
        this.padding = 5;
        this.group
                .add(path)
                .add(text);
        doHide();
    }

    public Tooltip setDirection(final Direction direction) {
        this.direction = direction;
        return checkRefresh();
    }

    public Tooltip setPadding(final double value) {
        this.padding = value;
        return checkRefresh();
    }

    public Tooltip text(Consumer<Text> text) {
        text.apply(this.text);
        return checkRefresh();
    }

    public Tooltip show() {
        if (!isVisible()) {
            refresh();
            doShow();
        }
        return this;
    }

    public Tooltip hide() {
        if (isVisible()) {
            doHide();
        }
        return this;
    }

    public boolean isVisible() {
        return group.getAlpha() > 0;
    }

    public IPrimitive<?> asPrimitive() {
        return group;
    }

    private Tooltip checkRefresh() {
        if (isVisible()) {
            refresh();
        }
        return this;
    }

    private void doShow() {
        group.setAlpha(1);
    }

    private void doHide() {
        group.setAlpha(0);
    }

    private void refresh() {

        // Dimensions for text.
        final BoundingBox textBB = text.getBoundingBox();
        final double tw = textBB.getWidth();
        final double th = textBB.getHeight();

        // Head dimensions.
        final double hw = 25;
        final double hl = 25;

        // Body dimensions.
        final double cbw = (isHorizontal() ? hl : 0)
                + tw + (padding * 2);
        final double cbh = (!isHorizontal() ? hl : 0) +
                th + (padding * 2);
        final double bw = isHorizontal() ? cbw : cbh;
        final double bh = isHorizontal() ? cbh : cbw;
        final double br = 5;

        // Some pre-calculations.
        final double hd = (bh - hw) / 2;
        final double y = -(bh / 2);

        path
                .clear()
                .M(hl + br,
                   y + 0)
                .L(bw - br,
                   y + 0)
                .A(bw,
                   y + 0,
                   bw,
                   y + br,
                   br)
                .L(bw,
                   y + (bh - br))
                .A(bw,
                   y + bh,
                   bw - br,
                   y + bh,
                   br)
                .L(hl + br,
                   y + bh)
                .A(hl,
                   y + bh,
                   hl,
                   y + (bh - br),
                   br)
                .L(hl,
                   y + (bh - hd))
                .L(0,
                   y + (bh / 2))
                .L(hl,
                   y + hd)
                .L(hl,
                   y + br)
                .A(hl,
                   y + 0,
                   hl + br,
                   y + 0,
                   br)
                .Z();

        path.setFillColor(ColorName.GREY)
                .setStrokeColor(ColorName.BLACK);

        // Direction.
        final Point2D textLoc = new Point2D();
        switch (direction) {
            case EAST:
                path.setRotationDegrees(180);
                textLoc.setX(-hl - padding - tw)
                        .setY((th / 2) - padding);
                break;
            case NORTH:
                path.setRotationDegrees(270);
                textLoc.setX(-padding - (tw / 2))
                        .setY(-padding - th + (th / 2));
                break;
            case SOUTH:
                path.setRotationDegrees(90);
                textLoc.setX(-padding - (tw / 2))
                        .setY(hl + th + padding);
                break;
            default:
                path.setRotationDegrees(0);
                textLoc.setX(hl + padding)
                        .setY((th / 2) - padding);
        }

        // Location.
        text.setLocation(textLoc);
    }

    private boolean isHorizontal() {
        return Direction.EAST.equals(direction) ||
                Direction.WEST.equals(direction);
    }
}
