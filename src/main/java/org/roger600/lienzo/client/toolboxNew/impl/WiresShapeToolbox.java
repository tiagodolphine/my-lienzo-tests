package org.roger600.lienzo.client.toolboxNew.impl;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.shape.wires.event.AbstractWiresResizeEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeEndEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeEndHandler;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStartEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStartHandler;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStepEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStepHandler;

public class WiresShapeToolbox extends DelegateToolbox<IPrimitive<?>, WiresShapeToolbox> {

    private final IPrimitiveToolbox toolbox;

    public WiresShapeToolbox(final WiresShape shape) {
        toolbox = new IPrimitiveToolbox(shape.getGroup());
        toolbox.registrations().register(
                shape.addWiresResizeStartHandler(new WiresResizeStartHandler() {
                    @Override
                    public void onShapeResizeStart(WiresResizeStartEvent event) {
                        onShapeResized(event);
                    }
                })
        );
        toolbox.registrations().register(
                shape.addWiresResizeStepHandler(new WiresResizeStepHandler() {
                    @Override
                    public void onShapeResizeStep(WiresResizeStepEvent event) {
                        onShapeResized(event);
                    }
                })
        );
        toolbox.registrations().register(
                shape.addWiresResizeEndHandler(new WiresResizeEndHandler() {
                    @Override
                    public void onShapeResizeEnd(WiresResizeEndEvent event) {
                        onShapeResized(event);
                    }
                })
        );
    }

    private void onShapeResized(final AbstractWiresResizeEvent event) {
        toolbox.checkReposition();
    }

    @Override
    protected AbstractToolbox<IPrimitive<?>, ?> getDelegate() {
        return toolbox;
    }
}
