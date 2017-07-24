package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.event.NodeMouseEnterEvent;
import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitEvent;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import org.roger600.lienzo.client.toolboxNew.GroupItem;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractDecoratedItem;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractDecoratorItem;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractPrimitiveItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratorItem;
import org.roger600.lienzo.client.toolboxNew.primitive.TooltipItem;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public abstract class AbstractGroupItem<T extends AbstractGroupItem>
        extends AbstractDecoratedItem<T> {

    private final static int FOCUS_DELAY_MILLIS = 200;
    private static final double ALPHA_FOCUSED = 1d;
    private static final double ALPHA_UNFOCUSED = 0.5d;

    private final GroupItem groupItem;
    private final HandlerRegistrationManager registrations = new HandlerRegistrationManager();
    private final FocusGroupExecutor focusGroupExecutor;
    private int focusDelay;
    private int unFocusDelay;
    private DecoratorItem<?> decorator;
    private TooltipItem<?> tooltip;
    private HandlerRegistration mouseEnterHandlerRegistration;
    private HandlerRegistration mouseExitHandlerRegistration;

    private final Timer focusDelayTimer = new Timer() {
        @Override
        public void run() {
            cancelUnFocusTimer();
            doFocus();
        }
    };
    private final Timer unFocusDelayTimer = new Timer() {
        @Override
        public void run() {
            cancelFocusTimer();
            doUnFocus();
        }
    };

    private Supplier<BoundingBox> boundingBoxSupplier = new Supplier<BoundingBox>() {
        @Override
        public BoundingBox get() {
            return getPrimitive().getBoundingBox();
        }
    };

    protected AbstractGroupItem(final GroupItem groupItem) {
        this.groupItem = groupItem;
        this.focusDelay = FOCUS_DELAY_MILLIS;
        this.unFocusDelay = 0;
        this.focusGroupExecutor = new FocusGroupExecutor();
        this.groupItem.useShowExecutor(focusGroupExecutor);
    }

    protected AbstractGroupItem(final GroupItem groupItem,
                                final Supplier<BoundingBox> boundingBoxSupplier) {
        this(groupItem);
        this.boundingBoxSupplier = boundingBoxSupplier;
    }

    T focus() {
        if (focusDelay > 0) {
            focusDelayTimer.schedule(focusDelay);
        } else {
            focusDelayTimer.run();
        }
        return cast();
    }

    T unFocus() {
        if (unFocusDelay > 0) {
            unFocusDelayTimer.schedule(unFocusDelay);
        } else {
            unFocusDelayTimer.run();
        }
        return cast();
    }

    public T setFocusDelay(final int delay) {
        this.focusDelay = delay;
        return cast();
    }

    public T setUnFocusDelay(final int delay) {
        this.unFocusDelay = delay;
        return cast();
    }

    @Override
    public T decorate(final DecoratorItem<?> decorator) {
        try {
            initDecorator((AbstractDecoratorItem<?>) decorator);
        } catch (final ClassCastException e) {
            throw new UnsupportedOperationException("This item only supports decorators " +
                                                            "of type " + AbstractDecoratorItem.class.getName());
        }
        return cast();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T tooltip(final TooltipItem tooltip) {
        initTooltip(tooltip);
        return cast();
    }

    @Override
    public boolean isVisible() {
        return groupItem.isVisible();
    }

    @Override
    public T show(final Runnable before,
                  final Runnable after) {
        groupItem.show(before,
                       after);
        return cast();
    }

    @Override
    public T hide(final Runnable before,
                  final Runnable after) {
        groupItem.hide(new Runnable() {
                           @Override
                           public void run() {
                               unFocus();
                               before.run();
                           }
                       },
                       after);
        return cast();
    }

    @Override
    public T onMouseEnter(final NodeMouseEnterHandler handler) {
        assert null != handler;
        if (null != mouseEnterHandlerRegistration) {
            mouseEnterHandlerRegistration.removeHandler();
        }
        mouseEnterHandlerRegistration = getPrimitive()
                .addNodeMouseEnterHandler(handler);
        register(mouseEnterHandlerRegistration);
        return cast();
    }

    @Override
    public T onMouseExit(final NodeMouseExitHandler handler) {
        assert null != handler;
        if (null != mouseExitHandlerRegistration) {
            mouseExitHandlerRegistration.removeHandler();
        }
        mouseExitHandlerRegistration = getPrimitive()
                .addNodeMouseExitHandler(handler);
        register(mouseExitHandlerRegistration);
        return cast();
    }

    public HandlerRegistrationManager registrations() {
        return registrations;
    }

    private T register(final HandlerRegistration registration) {
        registrations.register(registration);
        return cast();
    }

    @Override
    public void destroy() {
        cancelFocusTimer();
        cancelUnFocusTimer();
        groupItem.destroy();
        initDecorator(null);
        destroyHandlers();
        getPrimitive().removeFromParent();
    }

    @Override
    public Group asPrimitive() {
        return groupItem.asPrimitive();
    }

    @Override
    public Supplier<BoundingBox> getBoundingBox() {
        return boundingBoxSupplier;
    }

    protected GroupItem getGroupItem() {
        return groupItem;
    }

    protected DecoratorItem<?> getDecorator() {
        return decorator;
    }

    protected TooltipItem<?> getTooltip() {
        return tooltip;
    }

    protected T setupFocusingHandlers() {
        getPrimitive().setListening(true);
        registrations.register(
                getPrimitive().addNodeMouseEnterHandler(new NodeMouseEnterHandler() {
                    @Override
                    public void onNodeMouseEnter(NodeMouseEnterEvent event) {
                        focus();
                    }
                })
        );
        registrations.register(
                getPrimitive().addNodeMouseExitHandler(new NodeMouseExitHandler() {
                    @Override
                    public void onNodeMouseExit(NodeMouseExitEvent event) {
                        unFocus();
                    }
                })
        );
        return cast();
    }

    private void doFocus() {
        focusGroupExecutor.focus();
    }

    private void doUnFocus() {
        focusGroupExecutor.unFocus();
    }

    private void cancelFocusTimer() {
        if (focusDelayTimer.isRunning()) {
            focusDelayTimer.cancel();
        }
    }

    private void cancelUnFocusTimer() {
        if (unFocusDelayTimer.isRunning()) {
            unFocusDelayTimer.cancel();
        }
    }

    @SuppressWarnings("unchecked")
    private void initTooltip(final TooltipItem<?> tooltipItem) {
        if (hasTooltip()) {
            this.tooltip.destroy();
        }
        this.tooltip = tooltipItem;
        if (hasTooltip()) {
            attachTooltip();
            updateAddOnsVisibility();
        }
    }

    private void initDecorator(final AbstractDecoratorItem<?> decorator) {
        if (isDecorated()) {
            this.decorator.destroy();
        }
        this.decorator = decorator;
        if (isDecorated()) {
            attachDecorator();
            updateAddOnsVisibility();
        }
    }

    private void attachDecorator() {
        decorator.setBoundingBox(getBoundingBox().get());
        final IPrimitive<?> primitive = getDecoratorPrimitive();
        if (null != primitive) {
            groupItem.add(primitive);
        }
    }

    private IPrimitive<?> getDecoratorPrimitive() {
        if (null != decorator && decorator instanceof AbstractPrimitiveItem) {
            return ((AbstractPrimitiveItem) decorator).asPrimitive();
        }
        return null;
    }

    private void attachTooltip() {
        tooltip.forBoundingBox(new Supplier<BoundingBox>() {
            @Override
            public BoundingBox get() {
                return computeAbsoluteBoundingBox();
            }
        });
        if (tooltip instanceof AbstractPrimitiveItem) {
            groupItem.add(((AbstractPrimitiveItem) tooltip).asPrimitive());
        }
    }

    private BoundingBox computeAbsoluteBoundingBox() {
        final BoundingBox bb = getBoundingBox().get();
        final Point2D computedLocation = asPrimitive().getAbsoluteLocation();
        return new BoundingBox(computedLocation.getX(),
                               computedLocation.getY(),
                               computedLocation.getX() + bb.getWidth(),
                               computedLocation.getY() + bb.getHeight());
    }

    private void updateAddOnsVisibility() {
        if (isVisible()) {
            showAddOns();
        } else {
            hideAddOns();
        }
    }

    private void showAddOns() {
        if (isDecorated()) {
            this.decorator.show();
            getDecoratorPrimitive().moveToBottom();
        }
        if (hasTooltip()) {
            this.tooltip.show();
        }
    }

    private void hideAddOns() {
        if (isDecorated()) {
            this.decorator.hide();
        }
        if (hasTooltip()) {
            this.tooltip.hide();
        }
    }

    private void destroyHandlers() {
        registrations.removeHandler();
    }

    private boolean isDecorated() {
        return null != this.decorator;
    }

    private boolean hasTooltip() {
        return null != this.tooltip;
    }

    private class FocusGroupExecutor
            extends GroupItem.AnimatedGroupExecutor {

        public FocusGroupExecutor() {
            setAnimationTweener(AnimationTweener.LINEAR);
            setAlpha(ALPHA_UNFOCUSED);
        }

        public void focus() {
            showAddOns();
            setAlpha(ALPHA_FOCUSED);
            apply(asPrimitive(),
                  new Runnable() {
                      @Override
                      public void run() {
                      }
                  });
        }

        public void unFocus() {
            hideAddOns();
            setAlpha(ALPHA_UNFOCUSED);
            apply(asPrimitive(),
                  new Runnable() {
                      @Override
                      public void run() {
                      }
                  });
        }
    }

    @SuppressWarnings("unchecked")
    private T cast() {
        return (T) this;
    }
}
