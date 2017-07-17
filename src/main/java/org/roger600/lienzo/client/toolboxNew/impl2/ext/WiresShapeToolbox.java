package org.roger600.lienzo.client.toolboxNew.impl2.ext;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.wires.WiresContainer;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.shape.wires.event.AbstractWiresDragEvent;
import com.ait.lienzo.client.core.shape.wires.event.AbstractWiresResizeEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresDragEndEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresDragEndHandler;
import com.ait.lienzo.client.core.shape.wires.event.WiresDragMoveEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresDragMoveHandler;
import com.ait.lienzo.client.core.shape.wires.event.WiresDragStartEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresDragStartHandler;
import com.ait.lienzo.client.core.shape.wires.event.WiresMoveEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresMoveHandler;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeEndEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeEndHandler;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStartEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStartHandler;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStepEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStepHandler;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import org.roger600.lienzo.client.toolboxNew.ItemsToolbox;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;
import org.roger600.lienzo.client.toolboxNew.grid.SizeConstrainedGrid;
import org.roger600.lienzo.client.toolboxNew.impl2.AbstractItem;
import org.roger600.lienzo.client.toolboxNew.impl2.DelegateItemsToolbox;
import org.roger600.lienzo.client.toolboxNew.impl2.ItemsToolboxImpl;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public class WiresShapeToolbox extends DelegateItemsToolbox<WiresShapeToolbox, Point2DGrid> {

    private final HandlerRegistrationManager registrations = new HandlerRegistrationManager();
    private final ItemsToolboxImpl<Point2DGrid, AbstractItem> toolbox;

    WiresShapeToolbox(final WiresShape shape) {
        // Create the toolbox.
        this.toolbox =
                new ItemsToolboxImpl<>(new Supplier<BoundingBox>() {
                    @Override
                    public BoundingBox get() {
                        return shape.getPath().getBoundingBox();
                    }
                });
        initHandlers(shape);
        hide();
    }

    public WiresShapeToolbox attachTo(final Layer layer) {
        layer.add(toolbox.asPrimitive());
        return this;
    }

    private void initHandlers(final WiresShape shape) {
        registrations.register(
                shape.addWiresMoveHandler(new WiresMoveHandler() {
                    @Override
                    public void onShapeMoved(WiresMoveEvent event) {
                        onMove(event);
                    }
                })
        );
        registrations.register(
                shape.addWiresDragStartHandler(new WiresDragStartHandler() {
                    @Override
                    public void onShapeDragStart(WiresDragStartEvent event) {
                        onMove(event);
                    }
                })
        );
        registrations.register(
                shape.addWiresDragMoveHandler(new WiresDragMoveHandler() {
                    @Override
                    public void onShapeDragMove(WiresDragMoveEvent event) {
                        onMove(event);
                    }
                })
        );
        registrations.register(
                shape.addWiresDragEndHandler(new WiresDragEndHandler() {
                    @Override
                    public void onShapeDragEnd(WiresDragEndEvent event) {
                        onMove(event);
                    }
                })
        );
        registrations.register(
                shape.addWiresResizeStartHandler(new WiresResizeStartHandler() {
                    @Override
                    public void onShapeResizeStart(WiresResizeStartEvent event) {
                        onResize(event);
                    }
                })
        );
        registrations.register(
                shape.addWiresResizeStepHandler(new WiresResizeStepHandler() {
                    @Override
                    public void onShapeResizeStep(WiresResizeStepEvent event) {
                        onResize(event);
                    }
                })
        );
        registrations.register(
                shape.addWiresResizeEndHandler(new WiresResizeEndHandler() {
                    @Override
                    public void onShapeResizeEnd(WiresResizeEndEvent event) {
                        onResize(event);
                    }
                })
        );
    }

    private void onResize(final AbstractWiresResizeEvent event) {
        offset((WiresContainer) event.getShape());
        // If the grid is constrained by size, update it.
        final Point2DGrid grid = toolbox.getGrid();
        if (grid instanceof SizeConstrainedGrid) {
            ((SizeConstrainedGrid) grid).setSize(event.getWidth(),
                                                 event.getHeight());
        }
        toolbox.refresh();
    }

    private void onMove(final WiresMoveEvent event) {
        offset(event.getShape());
    }

    private void onMove(final AbstractWiresDragEvent event) {
        offset((WiresContainer) event.getShape());
    }

    private void offset(final WiresContainer shape) {
        offset(shape.getGroup().getComputedLocation());
    }

    private void offset(final Point2D offset) {
        toolbox.offset(offset);
    }

    @Override
    public void destroy() {
        super.destroy();
        registrations.removeHandler();
    }

    @Override
    protected ItemsToolbox<?, Point2DGrid, AbstractItem> getDelegate() {
        return toolbox;
    }

    private ItemsToolboxImpl getItemsToolbox() {
        return (ItemsToolboxImpl) getDelegate();
    }
}
