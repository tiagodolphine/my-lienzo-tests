package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.Direction;
import org.roger600.lienzo.client.toolboxNew.Positions;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractPrimitiveItem;
import org.roger600.lienzo.client.toolboxNew.primitive.TooltipItem;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;
import org.roger600.lienzo.client.toolboxNew.util.TooltipOLD;

public class TextTooltipItem
        extends AbstractPrimitiveItem<TextTooltipItem>
        implements TooltipItem<TextTooltipItem> {

    private final TooltipOLD tooltip;
    private Supplier<BoundingBox> boundingBoxSupplier;
    private Direction at;
    private String text;

    public static class Builder {

        public static TextTooltipItem atEast(final String text) {
            return build(text)
                    .at(Direction.EAST);
        }

        public static TextTooltipItem build(final String text) {
            return new TextTooltipItem(new TooltipOLD())
                    .setText(text);
        }
    }

    private TextTooltipItem(final TooltipOLD tooltip) {
        this.tooltip = tooltip;
        this.text = "-- No Text --";
    }

    public TextTooltipItem setText(final String text) {
        this.text = text;
        return this;
    }

    public TextTooltipItem at(final Direction at) {
        this.at = at;
        return this;
    }

    @Override
    public TextTooltipItem forBoundingBox(final Supplier<BoundingBox> boundingBoxSupplier) {
        this.boundingBoxSupplier = boundingBoxSupplier;
        return this;
    }

    @Override
    public TextTooltipItem show() {
        final Point2D loc = Positions.anchorFor(boundingBoxSupplier.get(),
                                                this.at);
        tooltip.show(text,
                     loc,
                     TooltipOLD.Direction.WEST);
        return this;
    }

    @Override
    public TextTooltipItem hide() {
        tooltip.hide();
        return this;
    }

    @Override
    public void destroy() {
        tooltip.destroy();
        boundingBoxSupplier = null;
    }

    @Override
    public IPrimitive<?> asPrimitive() {
        return tooltip.asPrimitive();
    }
}
