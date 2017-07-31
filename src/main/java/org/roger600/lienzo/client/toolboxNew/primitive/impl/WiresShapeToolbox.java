package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import java.util.Iterator;

import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
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
import com.ait.lienzo.shared.core.types.Direction;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import org.roger600.lienzo.client.toolboxNew.grid.Point2DGrid;
import org.roger600.lienzo.client.toolboxNew.grid.SizeConstrainedGrid;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratedItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratorItem;
import org.roger600.lienzo.client.toolboxNew.primitive.LayerToolbox;
import org.roger600.lienzo.client.toolboxNew.primitive.TooltipItem;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public class WiresShapeToolbox
        implements LayerToolbox {

    private final HandlerRegistrationManager registrations = new HandlerRegistrationManager();
    private final ToolboxImpl toolbox;

    public WiresShapeToolbox(final WiresShape shape) {
        this.toolbox = new ToolboxImpl(new Supplier<BoundingBox>() {
            @Override
            public BoundingBox get() {
                return shape.getPath().getBoundingBox();
            }
        });
        initHandlers(shape);
        hide();
    }

    @Override
    public WiresShapeToolbox attachTo(final Layer layer) {
        layer.add(toolbox.asPrimitive());
        return this;
    }

    @Override
    public WiresShapeToolbox at(final Direction at) {
        toolbox.at(at);
        return this;
    }

    @Override
    public WiresShapeToolbox offset(final Point2D offset) {
        toolbox.offset(offset);
        return this;
    }

    @Override
    public WiresShapeToolbox grid(final Point2DGrid grid) {
        toolbox.grid(grid);
        return this;
    }

    @Override
    public WiresShapeToolbox add(final DecoratedItem... items) {
        toolbox.add(items);
        return this;
    }

    @Override
    public Iterator<DecoratedItem> iterator() {
        return toolbox.iterator();
    }

    @Override
    public WiresShapeToolbox show() {
        toolbox.show();
        return this;
    }

    @Override
    public WiresShapeToolbox hide() {
        toolbox.hide();
        return this;
    }

    @Override
    public boolean isVisible() {
        return toolbox.isVisible();
    }

    @Override
    public WiresShapeToolbox decorate(final DecoratorItem<?> decorator) {
        toolbox.decorate(decorator);
        return this;
    }

    @Override
    public LayerToolbox tooltip(final TooltipItem tooltip) {
        toolbox.tooltip(tooltip);
        return this;
    }

    @Override
    public WiresShapeToolbox onMouseEnter(final NodeMouseEnterHandler handler) {
        toolbox.onMouseEnter(handler);
        return this;
    }

    @Override
    public WiresShapeToolbox onMouseExit(final NodeMouseExitHandler handler) {
        toolbox.onMouseExit(handler);
        return this;
    }

    @Override
    public Layer getLayer() {
        return toolbox.asPrimitive().getLayer();
    }

    public BoundingBox getBoundingBox() {
        return toolbox.getBoundingBox().get();
    }

    @Override
    public void destroy() {
        toolbox.destroy();
        registrations.removeHandler();
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
}
