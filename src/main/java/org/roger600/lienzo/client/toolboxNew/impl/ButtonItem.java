package org.roger600.lienzo.client.toolboxNew.impl;

import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationProperty;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.event.AbstractNodeMouseEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.event.NodeMouseDownEvent;
import com.ait.lienzo.client.core.event.NodeMouseDownHandler;
import com.ait.lienzo.client.core.event.NodeMouseEnterEvent;
import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitEvent;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.ContainerNode;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.user.client.Timer;

public class ButtonItem extends AbstractCallbacksToolboxIItem<ButtonItem> {

    private static final double NODE_ALPHA_FOCUSED = 1;
    private static final double NODE_ALPHA_UNFOCUSED = 0.5;
    private static final int CLICK_HANDLER_TIMER_DURATION = 100;
    private final HandlerRegistrationManager handlerRegistrationManager = new HandlerRegistrationManager();
    private final IPrimitiveItem item;
    private final IPrimitive<?> shape;

    private Runnable focusCallback;
    private Runnable unFocusCallback;
    private Runnable clickCallback;
    private Runnable downCallback;
    private Runnable highlightCallback;
    private Runnable unHighlightCallback;
    private Timer clickHandlerTimer;

    public ButtonItem(final IPrimitive<?> shape) {
        this.item = new IPrimitiveItem(shape);
        this.shape = shape;
        this.clickHandlerTimer = null;
        registerHandlers();
    }

    public ButtonItem onFocus(final Runnable callback) {
        this.focusCallback = callback;
        return this;
    }

    public ButtonItem onUnFocus(final Runnable callback) {
        this.unFocusCallback = callback;
        return this;
    }

    public ButtonItem onClick(final Runnable callback) {
        this.clickCallback = callback;
        return this;
    }

    public ButtonItem onDown(final Runnable callback) {
        this.downCallback = callback;
        return this;
    }

    public ButtonItem onHighlight(final Runnable callback) {
        this.highlightCallback = callback;
        return this;
    }

    public ButtonItem onUnHighlight(final Runnable callback) {
        this.unHighlightCallback = callback;
        return this;
    }

    @Override
    public ButtonItem attachTo(final ContainerNode<IPrimitive<?>, ?> parent) {
        item.attachTo(parent);
        return this;
    }

    @Override
    public ButtonItem setLocation(final Point2D location) {
        item.setLocation(location);
        return this;
    }

    @Override
    public ButtonItem show() {
        item.show();
        unHighlight();
        return this;
    }

    @Override
    public ButtonItem hide() {
        item.hide();
        return this;
    }

    public void highlight() {
        setShapeAlpha(NODE_ALPHA_FOCUSED);
        if (null != highlightCallback) {
            highlightCallback.run();
        }
    }

    public void unHighlight() {
        setShapeAlpha(NODE_ALPHA_UNFOCUSED);
        if (null != unHighlightCallback) {
            unHighlightCallback.run();
        }
    }

    @Override
    public void destroy() {
        clearClickHandlerTimer();
        handlerRegistrationManager.removeHandler();
        item.destroy();
    }

    Group getGroup() {
        return item.getGroup();
    }

    private void setShapeAlpha(final double alpha) {
        shape.
                animate(AnimationTweener.LINEAR,
                        AnimationProperties.toPropertyList(AnimationProperty.Properties.ALPHA(alpha)),
                        IPrimitiveItem.ANIMATION_DURATION);
    }

    private void registerHandlers() {
        // Add mouse enter event handlers for the wiresshape's multipath.
        handlerRegistrationManager.register(
                shape.addNodeMouseEnterHandler(new NodeMouseEnterHandler() {
                                                   @Override
                                                   public void onNodeMouseEnter(NodeMouseEnterEvent event) {
                                                       ButtonItem.this.onMouseEnter(event);
                                                   }
                                               }
                ));
        // Add mouse exit event handlers for the wiresshape's multipath.
        handlerRegistrationManager.register(
                shape.addNodeMouseExitHandler(new NodeMouseExitHandler() {
                                                  @Override
                                                  public void onNodeMouseExit(NodeMouseExitEvent event) {
                                                      ButtonItem.this.onMouseExit(event);
                                                  }
                                              }
                ));
        // Add mouse click event handlers for the primitive shape.
        handlerRegistrationManager.register(
                shape.addNodeMouseClickHandler(new NodeMouseClickHandler() {
                                                   @Override
                                                   public void onNodeMouseClick(NodeMouseClickEvent event) {
                                                       ButtonItem.this.onMouseClick(event);
                                                   }
                                               }
                ));
        // Add mouse down event handlers for the primitive shape.
        handlerRegistrationManager.register(
                shape.addNodeMouseDownHandler(new NodeMouseDownHandler() {
                                                  @Override
                                                  public void onNodeMouseDown(NodeMouseDownEvent event) {
                                                      ButtonItem.this.onMouseDown(event);
                                                  }
                                              }
                ));
    }

    private void onMouseEnter(final AbstractNodeMouseEvent event) {
        // GWT.log("ButtonItem - MOUSE ENTER");
        highlight();
        if (null != focusCallback) {
            focusCallback.run();
        }
    }

    private void onMouseExit(final AbstractNodeMouseEvent event) {
        // GWT.log("ButtonItem - MOUSE EXIT");
        unHighlight();
        if (null != unFocusCallback) {
            unFocusCallback.run();
        }
    }

    private void onMouseClick(final AbstractNodeMouseEvent event) {
        // GWT.log("ButtonItem - MOUSE CLICK");
        clearClickHandlerTimer();
        unHighlight();
        if (null != clickCallback) {
            clickCallback.run();
        }
    }

    private void onMouseDown(final AbstractNodeMouseEvent event) {
        // GWT.log("ButtonItem - MOUSE DOWN");
        if (null != downCallback && null == clickHandlerTimer) {
            clickHandlerTimer = new Timer() {
                @Override
                public void run() {
                    unHighlight();
                    if (null != downCallback) {
                        downCallback.run();
                    }
                    clickHandlerTimer = null;
                }
            };
            clickHandlerTimer.schedule(CLICK_HANDLER_TIMER_DURATION);
        }
    }

    private void clearClickHandlerTimer() {
        if (null != this.clickHandlerTimer) {
            if (this.clickHandlerTimer.isRunning()) {
                this.clickHandlerTimer.cancel();
            }
            this.clickHandlerTimer = null;
        }
    }
}
