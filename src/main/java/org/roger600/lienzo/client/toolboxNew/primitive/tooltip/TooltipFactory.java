package org.roger600.lienzo.client.toolboxNew.primitive.tooltip;

import org.roger600.lienzo.client.toolboxNew.primitive.LayerToolbox;

public class TooltipFactory {

    public static TextTooltipItem<?> forItem(final String text) {
        return PrimitiveTextTooltip.Builder
                .atEast(text);
    }

    public static ToolboxTextTooltip forToolbox(final LayerToolbox layerToolbox) {
        final ToolboxTextTooltip tooltip = new ToolboxTextTooltip(layerToolbox);
        layerToolbox.tooltip(tooltip);
        return tooltip;
    }
}
