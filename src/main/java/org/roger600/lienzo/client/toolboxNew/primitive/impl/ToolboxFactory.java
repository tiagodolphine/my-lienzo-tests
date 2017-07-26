package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.shape.wires.WiresShape;
import org.roger600.lienzo.client.toolboxNew.primitive.LayerToolbox;

public class ToolboxFactory {

    public static LayerToolbox forWiresShape(final WiresShape shape) {
        return new WiresShapeToolbox(shape);
    }
}
