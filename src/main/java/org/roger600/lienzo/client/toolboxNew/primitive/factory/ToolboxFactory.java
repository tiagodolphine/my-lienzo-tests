package org.roger600.lienzo.client.toolboxNew.primitive.factory;

import com.ait.lienzo.client.core.shape.wires.WiresShape;
import org.roger600.lienzo.client.toolboxNew.primitive.DefaultToolbox;
import org.roger600.lienzo.client.toolboxNew.primitive.impl.WiresShapeToolbox;

public class ToolboxFactory {

    public static DefaultToolbox forWiresShape(final WiresShape shape) {
        return new WiresShapeToolbox(shape);
    }
}
