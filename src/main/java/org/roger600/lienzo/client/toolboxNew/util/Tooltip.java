package org.roger600.lienzo.client.toolboxNew.util;

import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationProperty;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.Direction;

public class Tooltip {

    private static final double DURATION = 150;
    private final MultiPath path;
    private final Text text;
    private final Group group;
    private Direction direction;
    private double padding;
    private Consumer<Group> showExecutor;
    private Consumer<Group> hideExecutor;

    public Tooltip() {
        this(new AnimatedTooltipShowExecutor(DURATION),
             new AnimatedTooltipHideExecutor(DURATION));
    }

    public Tooltip(final Consumer<Group> showExecutor,
                   final Consumer<Group> hideExecutor) {
        this.path = new MultiPath();
        this.text = new Text("");
        this.group =
                new Group()
                        .setAlpha(0)
                        .add(path)
                        .add(text);
        this.padding = 5;
        this.direction = Direction.EAST;
        this.showExecutor = showExecutor;
        this.hideExecutor = hideExecutor;
    }

    public Tooltip setLocation(final Point2D location) {
        this.group.setLocation(location);
        return checkRefresh();
    }

    public Tooltip setDirection(final Direction direction) {
        if (!isSupported(direction)) {
            throw new UnsupportedOperationException("Only NSEW directions are supported.");
        }
        this.direction = direction;
        return checkRefresh();
    }

    public Tooltip setPadding(final double value) {
        this.padding = value;
        return checkRefresh();
    }

    public Tooltip withText(final Consumer<Text> text) {
        text.apply(this.text);
        return checkRefresh();
    }

    public Tooltip show() {
        if (!isVisible()) {
            refresh();
            showExecutor.apply(group);
        }
        return this;
    }

    public Tooltip hide() {
        if (isVisible()) {
            hideExecutor.apply(group);
        }
        return this;
    }

    public boolean isVisible() {
        return group.getAlpha() > 0;
    }

    public void destroy() {
        group.removeAll();
        group.removeFromParent();
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

    private void refresh() {

        final boolean isH = isHorizontal(direction);

        // Dimensions for text.
        final BoundingBox textBB = text.getBoundingBox();
        final double tw = textBB.getWidth();
        final double th = textBB.getHeight();

        // Head dimensions.
        final double hw = isH ? th / 2 : tw / 2;
        final double hl = tw / 6;

        // Body dimensions.
        final double cbw = (isH ? hl : 0)
                + tw + (padding * 2);
        final double cbh = (!isH ? hl : 0) +
                th + (padding * 2);
        final double bw = isH ? cbw : cbh;
        final double bh = isH ? cbh : cbw;
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

        path.setFillColor(ColorName.WHITE)
                .setStrokeColor(ColorName.BLACK);

        // Direction.
        final double tpw = (bw - tw - (padding * 2)) > 0 ? padding : 0;
        final double tph = (bh - th - (padding * 2)) > 0 ? padding : 0;
        final Point2D textLoc = new Point2D();
        switch (direction) {
            case WEST:
                path.setRotationDegrees(180);
                textLoc.setX(-hl - tpw - tw)
                        .setY((th / 2) - tph);
                break;
            case NORTH:
                path.setRotationDegrees(270);
                textLoc.setX(-tpw - (tw / 2))
                        .setY(-tph - hl - (th / 2));
                break;
            case SOUTH:
                path.setRotationDegrees(90);
                textLoc.setX(-tpw - (tw / 2))
                        .setY(hl + th + tph);
                break;
            default:
                path.setRotationDegrees(0);
                textLoc.setX(hl + tpw)
                        .setY((th / 2) - tph);
        }

        // Location.
        text.setLocation(textLoc);
        group.moveToTop();
    }

    private static boolean isHorizontal(final Direction direction) {
        return Direction.EAST.equals(direction) ||
                Direction.WEST.equals(direction);
    }

    private static boolean isVertical(final Direction direction) {
        return Direction.NORTH.equals(direction) ||
                Direction.SOUTH.equals(direction);
    }

    private static boolean isSupported(final Direction direction) {
        return isHorizontal(direction) ||
                isVertical(direction);
    }

    public static class AnimatedTooltipShowExecutor implements Consumer<Group> {

        private final double duration;

        public AnimatedTooltipShowExecutor(final double duration) {
            this.duration = duration;
        }

        @Override
        public void apply(final Group group) {
            group
                    .setScale(0.1,
                              1)
                    .setAlpha(1)
                    .animate(AnimationTweener.LINEAR,
                             AnimationProperties.toPropertyList(AnimationProperty.Properties.SCALE(1,
                                                                                                   1)),
                             duration);
        }
    }

    public static class AnimatedTooltipHideExecutor implements Consumer<Group> {

        private final double duration;

        public AnimatedTooltipHideExecutor(final double duration) {
            this.duration = duration;
        }

        @Override
        public void apply(final Group group) {
            group
                    .animate(AnimationTweener.LINEAR,
                             AnimationProperties.toPropertyList(AnimationProperty.Properties.ALPHA(0)),
                             duration);
        }
    }
}
