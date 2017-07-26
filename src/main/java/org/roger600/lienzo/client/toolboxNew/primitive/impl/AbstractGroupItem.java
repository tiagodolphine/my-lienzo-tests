package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
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
import org.roger600.lienzo.client.toolboxNew.util.BiConsumer;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

public abstract class AbstractGroupItem<T extends AbstractGroupItem>
        extends AbstractDecoratedItem<T> {

    private final GroupItem groupItem;
    private final HandlerRegistrationManager registrations = new HandlerRegistrationManager();
    private DecoratorItem<?> decorator;
    private TooltipItem<?> tooltip;
    private HandlerRegistration mouseEnterHandlerRegistration;
    private HandlerRegistration mouseExitHandlerRegistration;

    private Supplier<BoundingBox> boundingBoxSupplier = new Supplier<BoundingBox>() {
        @Override
        public BoundingBox get() {
            return getPrimitive().getComputedBoundingPoints().getBoundingBox();
        }
    };

    protected AbstractGroupItem(final GroupItem groupItem) {
        this.groupItem = groupItem;
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

    public void showDecorator() {
        if (isDecorated()) {
            this.decorator.show();
            getDecoratorPrimitive().moveToBottom();
        }
    }

    public void showTooltip() {
        if (hasTooltip()) {
            this.tooltip.show();
        }
    }

    public void hideDecorator() {
        if (isDecorated()) {
            this.decorator.hide();
        }
    }

    public void hideTooltip() {
        if (hasTooltip()) {
            this.tooltip.hide();
        }
    }

    public T useShowExecutor(final BiConsumer<Group, Runnable> executor) {
        this.groupItem.useShowExecutor(executor);
        return cast();
    }

    public T useHideExecutor(final BiConsumer<Group, Runnable> executor) {
        this.groupItem.useHideExecutor(executor);
        return cast();
    }

    public boolean isDecorated() {
        return null != this.decorator;
    }

    public boolean hasTooltip() {
        return null != this.tooltip;
    }

    public HandlerRegistrationManager registrations() {
        return registrations;
    }

    protected T register(final HandlerRegistration registration) {
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

    @Override
    public Supplier<BoundingBox> getBoundingBox() {
        return boundingBoxSupplier;
    }

    @Override
    public T onMouseEnter(final NodeMouseEnterHandler handler) {
        if (null != mouseEnterHandlerRegistration) {
            mouseEnterHandlerRegistration.removeHandler();
        }
        mouseEnterHandlerRegistration = registerMouseEnterHandler(handler);
        return cast();
    }

    @Override
    public T onMouseExit(final NodeMouseExitHandler handler) {
        assert null != handler;
        if (null != mouseExitHandlerRegistration) {
            mouseExitHandlerRegistration.removeHandler();
        }
        mouseExitHandlerRegistration = registerMouseExitHandler(handler);
        return cast();
    }

    protected T setBoundingBox(final Supplier<BoundingBox> supplier) {
        this.boundingBoxSupplier = supplier;
        return cast();
    }

    protected HandlerRegistration registerMouseEnterHandler(final NodeMouseEnterHandler handler) {
        assert null != handler;
        HandlerRegistration reg =
                getPrimitive()
                        .setListening(true)
                        .addNodeMouseEnterHandler(handler);
        register(reg);
        return reg;
    }

    protected HandlerRegistration registerMouseExitHandler(final NodeMouseExitHandler handler) {
        assert null != handler;
        HandlerRegistration reg =
                getPrimitive()
                        .setListening(true)
                        .addNodeMouseExitHandler(handler);
        register(reg);
        return reg;
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
        tooltip.forComputedBoundingBox(new Supplier<BoundingBox>() {
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
        final Point2D computedLocation = asPrimitive().getComputedLocation();
        return new BoundingBox(computedLocation.getX(),
                               computedLocation.getY(),
                               computedLocation.getX() + bb.getWidth(),
                               computedLocation.getY() + bb.getHeight());
    }

    protected void updateAddOnsVisibility() {
        if (isVisible()) {
            showAddOns();
        } else {
            hideAddOns();
        }
    }

    protected void showAddOns() {
        showDecorator();
        showTooltip();
    }

    protected void hideAddOns() {
        hideDecorator();
        hideTooltip();
    }

    private void destroyHandlers() {
        registrations.removeHandler();
    }

    @SuppressWarnings("unchecked")
    private T cast() {
        return (T) this;
    }
}
