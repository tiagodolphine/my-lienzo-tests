package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.Direction;
import org.roger600.lienzo.client.toolboxNew.Positions;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractTooltipItem;
import org.roger600.lienzo.client.toolboxNew.primitive.TooltipItem;
import org.roger600.lienzo.client.toolboxNew.util.Tooltip;

public class TooltipItemImpl
        extends AbstractTooltipItem<TooltipItem>
        implements TooltipItem {

    private final Tooltip tooltip;
    private Direction at;
    private String text;

    public static class Builder {

        public static TooltipItemImpl atEast(final String text) {
            return build(text)
                    .at(Direction.EAST);
        }

        public static TooltipItemImpl build(final String text) {
            return new TooltipItemImpl()
                    .setText(text);
        }
    }

    private TooltipItemImpl() {
        this.tooltip = new Tooltip();
        this.text = "-- No Text --";
    }

    public TooltipItemImpl setText(final String text) {
        this.text = text;
        return this;
    }

    public TooltipItemImpl at(final Direction at) {
        this.at = at;
        return this;
    }

    @Override
    public TooltipItem show() {
        final Point2D loc = Positions.anchorFor(getBoundingBox(),
                                                this.at);
        tooltip.setLocation(loc.offset(getAbsoluteLocation()));
        tooltip.show("",
                     text);
        return this;
    }

    @Override
    public TooltipItem hide() {
        tooltip.hide();
        return this;
    }

    @Override
    protected void doDestroy() {
        tooltip.destoy();
    }

    @Override
    public IPrimitive<?> asPrimitive() {
        return tooltip;
    }
}
