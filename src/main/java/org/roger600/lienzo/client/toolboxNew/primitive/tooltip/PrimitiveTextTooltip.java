package org.roger600.lienzo.client.toolboxNew.primitive.tooltip;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.shared.core.types.Direction;
import org.roger600.lienzo.client.toolboxNew.Positions;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractPrimitiveItem;
import org.roger600.lienzo.client.toolboxNew.util.Consumer;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;
import org.roger600.lienzo.client.toolboxNew.util.Tooltip;

public class PrimitiveTextTooltip
        extends AbstractPrimitiveItem<PrimitiveTextTooltip>
        implements TextTooltipItem<PrimitiveTextTooltip> {

    private final Tooltip tooltip;
    private BoundingLocationExecutor locationExecutor;

    public static class Builder {

        public static PrimitiveTextTooltip atEast(final String text) {
            return build(text)
                    .at(Direction.EAST)
                    .towards(Direction.EAST);
        }

        public static PrimitiveTextTooltip build(final String text) {
            return new PrimitiveTextTooltip(text);
        }
    }

    private PrimitiveTextTooltip(final String text) {
        this.tooltip =
                new Tooltip()
                        .withText(new Consumer<Text>() {
                            @Override
                            public void apply(Text textPrim) {
                                textPrim.setText(text);
                            }
                        });
        this.locationExecutor =
                new BoundingLocationExecutor()
                        .at(Direction.EAST);
        this.towards(Direction.EAST);
    }

    @Override
    public PrimitiveTextTooltip setText(final String text) {
        return withText(new Consumer<Text>() {
            @Override
            public void apply(Text prim) {
                prim.setText(text);
            }
        });
    }

    public PrimitiveTextTooltip withText(final Consumer<Text> textConsumer) {
        this.tooltip.withText(textConsumer);
        return this;
    }

    public PrimitiveTextTooltip at(final Direction at) {
        this.locationExecutor
                .at(at)
                .apply(tooltip);
        return this;
    }

    public PrimitiveTextTooltip towards(final Direction towards) {
        this.tooltip.setDirection(towards);
        return this;
    }

    public PrimitiveTextTooltip setPadding(final double padding) {
        this.tooltip.setPadding(padding);
        return this;
    }

    @Override
    public PrimitiveTextTooltip forBoundingBox(final Supplier<BoundingBox> boundingBoxSupplier) {
        this.locationExecutor
                .forBoundingBox(boundingBoxSupplier)
                .apply(tooltip);
        return this;
    }

    @Override
    public PrimitiveTextTooltip show() {
        tooltip.show();
        return this;
    }

    @Override
    public PrimitiveTextTooltip hide() {
        tooltip.hide();
        return this;
    }

    @Override
    public void destroy() {
        tooltip.destroy();
        locationExecutor = null;
    }

    @Override
    public IPrimitive<?> asPrimitive() {
        return tooltip.asPrimitive();
    }

    private static class BoundingLocationExecutor implements Consumer<Tooltip> {

        private Supplier<BoundingBox> boundingBoxSupplier;
        private Direction at;

        public BoundingLocationExecutor at(final Direction at) {
            this.at = at;
            return this;
        }

        public BoundingLocationExecutor forBoundingBox(final Supplier<BoundingBox> supplier) {
            this.boundingBoxSupplier = supplier;
            return this;
        }

        @Override
        public void apply(final Tooltip tooltip) {
            tooltip.setLocation(Positions.anchorFor(boundingBoxSupplier.get(),
                                                    this.at));
        }
    }
}
