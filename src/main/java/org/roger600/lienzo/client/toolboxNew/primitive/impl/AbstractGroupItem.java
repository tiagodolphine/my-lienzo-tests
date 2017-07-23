package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.event.NodeMouseEnterEvent;
import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitEvent;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.event.shared.HandlerRegistration;
import org.roger600.lienzo.client.toolboxNew.GroupItem;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractDecoratedItem;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractDecoratorItem;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractPrimitiveItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratorItem;
import org.roger600.lienzo.client.toolboxNew.primitive.TooltipItem;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public abstract class AbstractGroupItem<T extends AbstractGroupItem>
        extends AbstractDecoratedItem<T> {

    private static final double ALPHA_FOCUSED = 1d;
    private static final double ALPHA_UNFOCUSED = 0.5d;

    private final GroupItem groupItem;
    private final HandlerRegistrationManager registrations = new HandlerRegistrationManager();
    private final FocusGroupExecutor focusGroupExecutor;
    private DecoratorItem<?> decorator;
    private TooltipItem<?> tooltip;
    private HandlerRegistration mouseEnterHandlerRegistration;
    private HandlerRegistration mouseExitHandlerRegistration;

    protected AbstractGroupItem(final GroupItem groupItem) {
        this.groupItem = groupItem;
        this.focusGroupExecutor = new FocusGroupExecutor();
        this.groupItem.useShowExecutor(focusGroupExecutor);
    }

    public T setupFocusingHandlers() {
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

    T focus() {
        focusGroupExecutor.focus();
        return cast();
    }

    T unFocus() {
        focusGroupExecutor.unFocus();
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

    public T refresh() {
        if (isDecorated()) {
            ((AbstractDecoratorItem<?>) decorator).refresh();
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
        groupItem.destroy();
        initDecorator(null);
        destroyHandlers();
        getPrimitive().removeFromParent();
    }

    @Override
    public Group asPrimitive() {
        return groupItem.asPrimitive();
    }

    protected GroupItem getGroupItem() {
        return groupItem;
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
            refresh();
            updateAddOnsVisibility();
        }
    }

    private void attachDecorator() {
        decorator.forBoundingBox(new Supplier<BoundingBox>() {
            @Override
            public BoundingBox get() {
                return getPrimitive().getBoundingBox();
            }
        });
        if (decorator instanceof AbstractPrimitiveItem) {
            groupItem.add(((AbstractPrimitiveItem) decorator).asPrimitive());
        }
    }

    private void attachTooltip() {
        tooltip.forBoundingBox(new Supplier<BoundingBox>() {
            @Override
            public BoundingBox get() {
                return computeBoundingBox();
            }
        });
        if (tooltip instanceof AbstractPrimitiveItem) {
            groupItem.add(((AbstractPrimitiveItem) tooltip).asPrimitive());
        }
    }

    private BoundingBox computeBoundingBox() {
        final BoundingBox bb = getPrimitive().getBoundingBox();
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
