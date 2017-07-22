package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.Direction;
import org.roger600.lienzo.client.toolboxNew.Positions;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractPrimitiveItem;
import org.roger600.lienzo.client.toolboxNew.primitive.TooltipItem;
import org.roger600.lienzo.client.toolboxNew.util.Consumer;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;
import org.roger600.lienzo.client.toolboxNew.util.Tooltip;

public class TextTooltipItem
        extends AbstractPrimitiveItem<TextTooltipItem>
        implements TooltipItem<TextTooltipItem> {

    private final Tooltip tooltip;
    private Supplier<BoundingBox> boundingBoxSupplier;
    private Direction at;
    private Direction towards;
    private String text;

    public static class Builder {

        public static TextTooltipItem atEast(final String text) {
            return build(text)
                    .at(Direction.EAST)
                    .towards(Direction.EAST);
        }

        public static TextTooltipItem build(final String text) {
            return new TextTooltipItem(new Tooltip())
                    .setText(text);
        }
    }

    private TextTooltipItem(final Tooltip tooltip) {
        this.tooltip = tooltip;
        this.text = "-- No Text --";
        this.at = Direction.EAST;
        this.towards = Direction.EAST;
    }

    public TextTooltipItem setText(final String text) {
        this.text = text;
        return this;
    }

    public TextTooltipItem at(final Direction at) {
        this.at = at;
        return this;
    }

    public TextTooltipItem towards(final Direction towards) {
        this.towards = towards;
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
        tooltip
                .setPadding(10d)
                .setLocation(loc)
                .setDirection(this.towards)
                .withText(new Consumer<Text>() {
                    @Override
                    public void apply(Text text) {
                        text
                                //.setFontSize(10)
                                //.setFontFamily("Verdana")
                                .setText(TextTooltipItem.this.text);
                    }
                })
                .show();

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
