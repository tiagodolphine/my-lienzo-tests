package org.roger600.lienzo.client.toolboxNew.primitive.tooltip;

import com.ait.lienzo.shared.core.types.Direction;
import org.roger600.lienzo.client.toolboxNew.primitive.TooltipItem;

public interface TextTooltipItem<T extends TextTooltipItem> extends TooltipItem<T> {

    public T at(Direction at);

    public T towards(Direction towards);

    public T setText(String text);
}
