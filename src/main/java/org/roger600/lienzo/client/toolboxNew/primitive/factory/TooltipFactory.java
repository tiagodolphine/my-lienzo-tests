package org.roger600.lienzo.client.toolboxNew.primitive.factory;

import org.roger600.lienzo.client.toolboxNew.primitive.LayerToolbox;
import org.roger600.lienzo.client.toolboxNew.primitive.tooltip.PrimitiveTextTooltip;
import org.roger600.lienzo.client.toolboxNew.primitive.tooltip.TextTooltipItem;
import org.roger600.lienzo.client.toolboxNew.primitive.tooltip.ToolboxTextTooltip;

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
