package org.roger600.lienzo.client.toolboxNew.impl2.ext;

import com.ait.lienzo.client.core.shape.wires.WiresShape;

public class ToolboxFactory {

    public static WiresShapeToolbox forWiresShape(final WiresShape shape) {
        return new WiresShapeToolbox(shape);
    }
}
