package org.roger600.lienzo.client.toolboxNew.impl;

import com.ait.lienzo.client.core.Attribute;
import com.ait.lienzo.client.core.event.AnimationFrameAttributesChangedBatcher;
import com.ait.lienzo.client.core.event.AttributesChangedEvent;
import com.ait.lienzo.client.core.event.AttributesChangedHandler;
import com.ait.lienzo.client.core.event.IAttributesChangedBatcher;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IContainer;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.Direction;
import com.ait.tooling.common.api.flow.Flows;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import org.roger600.lienzo.client.toolboxNew.Grid;
import org.roger600.lienzo.client.toolboxNew.Positions;

import static com.ait.lienzo.client.core.AttributeOp.any;

public class IPrimitiveToolbox extends AbstractToolbox<IPrimitive<?>, IPrimitiveToolbox> {

    private static final Flows.BooleanOp XYWH_OP = any(Attribute.X,
                                                       Attribute.Y,
                                                       Attribute.WIDTH,
                                                       Attribute.HEIGHT);

    protected final HandlerRegistrationManager handlerRegistrationManager = new HandlerRegistrationManager();
    protected final IAttributesChangedBatcher attributesChangedBatcher = new AnimationFrameAttributesChangedBatcher();
    private final Group group;
    private final IPrimitive<?> node;
    private Grid grid;
    private Direction at;
    private Layer layer;
    private Runnable repositionCallback;

    public IPrimitiveToolbox(final IPrimitive<?> node) {
        this.group = new Group()
                .setDraggable(false)
                .setAlpha(0);
        this.node = node;
        initHandlers();
    }

    @Override
    public IPrimitiveToolbox attachTo(IContainer<?, IPrimitive<?>> parent) {
        this.layer = parent.getLayer();
        parent.add(group);
        return this;
    }

    @Override
    public IPrimitiveToolbox onRefresh(final Runnable repositionCallback) {
        this.repositionCallback = repositionCallback;
        return this;
    }

    @Override
    public IPrimitiveToolbox at(final Direction at) {
        this.at = at;
        return checkReposition();
    }

    @Override
    public IPrimitiveToolbox grid(final Grid grid) {
        this.grid = grid;
        return checkReposition();
    }

    @Override
    public IPrimitiveToolbox show() {
        show(new Runnable() {
            @Override
            public void run() {

            }
        });
        return this;
    }

    @Override
    public IPrimitiveToolbox hide() {
        hide(new Runnable() {
            @Override
            public void run() {

            }
        });
        return this;
    }

    IPrimitiveToolbox show(final Runnable post) {
        assert null != layer;
        if (!isVisible()) {
            reposition();
            post.run();
            group.setAlpha(1);
        }
        return this;
    }

    IPrimitiveToolbox hide(final Runnable post) {
        if (isVisible()) {
            post.run();
            group.setAlpha(0);
        }
        return this;
    }

    public boolean isVisible() {
        return 1 == group.getAlpha();
    }

    @Override
    public void destroy() {
        attributesChangedBatcher.cancelAttributesChangedBatcher();
        handlerRegistrationManager.removeHandler();
        node.removeFromParent();
        group.removeAll();
        group.removeFromParent();
    }

    public Grid getGrid() {
        return grid;
    }

    public Direction getAt() {
        return at;
    }

    protected Group getGroup() {
        return group;
    }

    HandlerRegistrationManager registrations() {
        return handlerRegistrationManager;
    }

    IPrimitiveToolbox checkReposition() {
        if (isVisible()) {
            reposition();
        }
        return this;
    }

    // TODO: Reduce number of calls during drags, resizes?
    private void reposition() {
        final Point2D computedLocation = node.getComputedLocation();
        final Point2D anchorPoint = Positions.anchorFor(this.node.getBoundingBox(),
                                                        this.at);
        final Grid.Point toolboxPosition = this.grid.findPosition(new Grid.Point((int) anchorPoint.getX(),
                                                                                 (int) anchorPoint.getY()));
        group.setX(computedLocation.getX() + toolboxPosition.getX());
        group.setY(computedLocation.getY() + toolboxPosition.getY());
        if (null != repositionCallback) {
            repositionCallback.run();
        }
        if (null != layer) {
            layer.batch();
        }
    }

    private final AttributesChangedHandler repositionHandler = new AttributesChangedHandler() {
        @Override
        public void onAttributesChanged(final AttributesChangedEvent event) {
            if (event.evaluate(XYWH_OP)) {
                reposition();
            }
        }
    };

    protected void initHandlers() {
        node.setAttributesChangedBatcher(attributesChangedBatcher);

        // Attribute change handlers.
        handlerRegistrationManager.register(
                node.addAttributesChangedHandler(Attribute.X,
                                                 repositionHandler)
        );
        handlerRegistrationManager.register(
                node.addAttributesChangedHandler(Attribute.Y,
                                                 repositionHandler)
        );
        handlerRegistrationManager.register(
                node.addAttributesChangedHandler(Attribute.WIDTH,
                                                 repositionHandler)
        );
        handlerRegistrationManager.register(
                node.addAttributesChangedHandler(Attribute.HEIGHT,
                                                 repositionHandler)
        );

        // TODO: Handle Wires Shape resize too (add handlers).
    }
}
