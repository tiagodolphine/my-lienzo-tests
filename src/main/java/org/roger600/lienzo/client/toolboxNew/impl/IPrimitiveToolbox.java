package org.roger600.lienzo.client.toolboxNew.impl;

import com.ait.lienzo.client.core.Attribute;
import com.ait.lienzo.client.core.event.AnimationFrameAttributesChangedBatcher;
import com.ait.lienzo.client.core.event.AttributesChangedEvent;
import com.ait.lienzo.client.core.event.AttributesChangedHandler;
import com.ait.lienzo.client.core.event.IAttributesChangedBatcher;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IContainer;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.Direction;
import com.ait.tooling.common.api.flow.Flows;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import org.roger600.lienzo.client.toolboxNew.Grid;
import org.roger600.lienzo.client.toolboxNew.Positions;
import org.roger600.lienzo.client.toolboxNew.Toolbox;

import static com.ait.lienzo.client.core.AttributeOp.any;

public class IPrimitiveToolbox implements Toolbox<IPrimitive<?>, IPrimitiveToolbox> {

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
    private Direction towards;

    public IPrimitiveToolbox(final IPrimitive<?> node) {
        this.group = new Group()
                .setDraggable(false)
                .setAlpha(0);
        this.node = node;
        initHandlers();
    }

    @Override
    public IPrimitiveToolbox attachTo(IContainer<?, IPrimitive<?>> parent) {
        parent.add(group);
        return this;
    }

    @Override
    public IPrimitiveToolbox at(final Direction at) {
        this.at = at;
        return checkReposition();
    }

    @Override
    public IPrimitiveToolbox towards(final Direction towards) {
        this.towards = towards;
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

    public Direction getTowards() {
        return towards;
    }

    Group getGroup() {
        return group;
    }

    IPrimitiveToolbox checkReposition() {
        if (isVisible()) {
            reposition();
        }
        return this;
    }

    private void reposition() {
        final Point2D computedLocation = node.getComputedLocation();
        final Point2D anchorPoint = Positions.anchorFor(this.node.getBoundingBox(),
                                                        this.at);
        final Grid.Point toolboxPosition = this.grid.findPosition(new Grid.Point((int) anchorPoint.getX(),
                                                                                 (int) anchorPoint.getY()),
                                                                  this.towards);
        group.setX(computedLocation.getX() + toolboxPosition.getX());
        group.setY(computedLocation.getY() + toolboxPosition.getY());
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
