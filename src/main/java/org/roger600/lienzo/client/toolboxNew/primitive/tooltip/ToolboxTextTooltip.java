package org.roger600.lienzo.client.toolboxNew.primitive.tooltip;

import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.shared.core.types.Direction;
import org.roger600.lienzo.client.toolboxNew.primitive.LayerToolbox;
import org.roger600.lienzo.client.toolboxNew.util.Consumer;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public class ToolboxTextTooltip implements TextTooltipItem<ToolboxTextTooltip> {

    private final PrimitiveTextTooltip tooltip;
    private final TextTooltipItemImpl delegate;

    public ToolboxTextTooltip(final LayerToolbox toolbox) {
        this.tooltip = PrimitiveTextTooltip.Builder.build("");
        this.delegate = new ToolboxTextItem();
        attachTo(toolbox);
    }

    public TextTooltipItemImpl createIem(final String text) {
        return new TextTooltipItemImpl(new Supplier<TextTooltipItem>() {
            @Override
            public TextTooltipItem get() {
                return tooltip;
            }
        }, text);
    }

    @Override
    public ToolboxTextTooltip at(final Direction at) {
        delegate.at(at);
        return this;
    }

    @Override
    public ToolboxTextTooltip towards(final Direction towards) {
        delegate.towards(towards);
        return this;
    }

    @Override
    public ToolboxTextTooltip setText(final String text) {
        delegate.setText(text);
        return this;
    }

    public ToolboxTextTooltip withText(final Consumer<Text> textConsumer) {
        tooltip.withText(textConsumer);
        return this;
    }

    @Override
    public ToolboxTextTooltip forBoundingBox(final Supplier<BoundingBox> boundingBoxSupplier) {
        delegate.forBoundingBox(boundingBoxSupplier);
        return this;
    }

    @Override
    public ToolboxTextTooltip show() {
        delegate.show();
        return this;
    }

    @Override
    public ToolboxTextTooltip hide() {
        delegate.hide();
        return this;
    }

    @Override
    public void destroy() {
        delegate.destroy();
    }

    private ToolboxTextTooltip attachTo(final LayerToolbox toolbox) {
        toolbox
                .getLayer()
                .add(tooltip.asPrimitive());
        return this;
    }

    private class ToolboxTextItem extends TextTooltipItemImpl {

        private ToolboxTextItem() {
            super(new Supplier<TextTooltipItem>() {
                      @Override
                      public TextTooltipItem get() {
                          return tooltip;
                      }
                  },
                  "");
        }

        @Override
        public void destroy() {
            super.destroy();
            tooltip.destroy();
        }
    }
}
