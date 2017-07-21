package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.event.NodeMouseEnterEvent;
import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitEvent;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.event.shared.HandlerRegistration;
import org.roger600.lienzo.client.toolboxNew.GroupItem;
import org.roger600.lienzo.client.toolboxNew.primitive.AbstractDecoratedItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DecoratorItem;
import org.roger600.lienzo.client.toolboxNew.primitive.DefaultDecoratorItem;

public abstract class AbstractGroupItem<T extends AbstractGroupItem>
        extends AbstractDecoratedItem<T> {

    private static final double ALPHA_FOCUSED = 1d;
    private static final double ALPHA_UNFOCUSED = 0.5d;

    private final GroupItem groupItem;
    private final HandlerRegistrationManager registrations = new HandlerRegistrationManager();
    private final FocusGroupExecutor focusGroupExecutor;
    private DefaultDecoratorItem<?> decorator;
    private HandlerRegistration mouseEnterHandlerRegistration;
    private HandlerRegistration mouseExitHandlerRegistration;

    protected AbstractGroupItem(final GroupItem groupItem) {
        this.groupItem = groupItem;
        this.focusGroupExecutor = new FocusGroupExecutor();
        this.groupItem.useShowExecutor(focusGroupExecutor);
    }

    public abstract IPrimitive<?> getPrimitive();

    public T setupFocusingHandlers() {
        getAttachable().setListening(true);
        registrations.register(
                getAttachable().addNodeMouseEnterHandler(new NodeMouseEnterHandler() {
                    @Override
                    public void onNodeMouseEnter(NodeMouseEnterEvent event) {
                        focus();
                    }
                })
        );
        registrations.register(
                getAttachable().addNodeMouseExitHandler(new NodeMouseExitHandler() {
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
            initDecorator((DefaultDecoratorItem<?>) decorator);
        } catch (final ClassCastException e) {
            throw new UnsupportedOperationException("This item only supports decorators " +
                                                            "of type " + DefaultDecoratorItem.class.getName());
        }
        return cast();
    }

    @Override
    public boolean isVisible() {
        return groupItem.isVisible();
    }

    @Override
    public T hide() {
        return hide(new Runnable() {
                        @Override
                        public void run() {
                            hideDecorator();
                        }
                    },
                    new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
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
        groupItem.hide(before,
                       after);
        return cast();
    }

    @Override
    public T onMouseEnter(final NodeMouseEnterHandler handler) {
        assert null != handler;
        if (null != mouseEnterHandlerRegistration) {
            mouseEnterHandlerRegistration.removeHandler();
        }
        mouseEnterHandlerRegistration = getAttachable()
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
        mouseExitHandlerRegistration = getAttachable()
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

    private boolean isDecorated() {
        return null != this.decorator;
    }

    private void initDecorator(final DefaultDecoratorItem<?> decorator) {
        if (isDecorated()) {
            this.decorator.destroy();
        }
        this.decorator = decorator;
        if (isDecorated()) {
            attachDecorator();
        }
    }

    private void attachDecorator() {
        final BoundingBox boundingBox = getPrimitive().getBoundingBox();
        decorator.setSize(boundingBox.getWidth(),
                          boundingBox.getHeight());
        groupItem.add(decorator.asPrimitive());
        if (isVisible()) {
            showDecorator();
        } else {
            hideDecorator();
        }
    }

    private void showDecorator() {
        if (isDecorated()) {
            this.decorator.show();
        }
    }

    private void hideDecorator() {
        if (isDecorated()) {
            this.decorator.hide();
        }
    }

    private void destroyHandlers() {
        registrations.removeHandler();
    }

    private class FocusGroupExecutor
            extends GroupItem.AnimatedGroupExecutor {

        public FocusGroupExecutor() {
            setAnimationTweener(AnimationTweener.LINEAR);
            setAlpha(ALPHA_UNFOCUSED);
        }

        public void focus() {
            if (isDecorated()) {
                showDecorator();
            }
            setAlpha(ALPHA_FOCUSED);
            apply(asPrimitive());
        }

        public void unFocus() {
            if (isDecorated()) {
                hideDecorator();
            }
            setAlpha(ALPHA_UNFOCUSED);
            apply(asPrimitive());
        }
    }

    @SuppressWarnings("unchecked")
    private T cast() {
        return (T) this;
    }
}
