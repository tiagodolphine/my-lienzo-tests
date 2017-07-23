package org.roger600.lienzo.client.toolboxNew.primitive.tooltip;

import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.shared.core.types.Direction;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public class TextTooltipItemImpl
        implements TextTooltipItem<TextTooltipItemImpl> {

    private final Supplier<TextTooltipItem> textTooltipSupplier;
    private String text;
    private Direction at;
    private Direction towards;
    private Supplier<BoundingBox> boundingBoxSupplier;

    TextTooltipItemImpl(final Supplier<TextTooltipItem> textTooltipSupplier,
                        final String text) {
        this.textTooltipSupplier = textTooltipSupplier;
        this.text = text;
        this.at = Direction.EAST;
        this.towards = Direction.EAST;
    }

    @Override
    public TextTooltipItemImpl at(final Direction at) {
        this.at = at;
        return this;
    }

    @Override
    public TextTooltipItemImpl towards(final Direction towards) {
        this.towards = towards;
        return this;
    }

    @Override
    public TextTooltipItemImpl setText(final String text) {
        this.text = text;
        return this;
    }

    @Override
    public TextTooltipItemImpl forBoundingBox(final Supplier<BoundingBox> boundingBoxSupplier) {
        this.boundingBoxSupplier = boundingBoxSupplier;
        return this;
    }

    @Override
    public TextTooltipItemImpl show() {
        hide();
        if (null != text && text.trim().length() > 0) {
            getTextTooltipItem()
                    .forBoundingBox(boundingBoxSupplier)
                    .at(at)
                    .towards(towards)
                    .setText(text)
                    .show();
        }
        return this;
    }

    @Override
    public TextTooltipItemImpl hide() {
        getTextTooltipItem().hide();
        return this;
    }

    @Override
    public void destroy() {
        this.text = null;
        this.boundingBoxSupplier = null;
    }

    @SuppressWarnings("unchecked")
    private TextTooltipItem<TextTooltipItem> getTextTooltipItem() {
        return textTooltipSupplier.get();
    }
}
