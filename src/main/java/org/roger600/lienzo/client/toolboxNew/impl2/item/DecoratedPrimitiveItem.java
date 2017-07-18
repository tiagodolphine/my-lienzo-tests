package org.roger600.lienzo.client.toolboxNew.impl2.item;

import com.ait.lienzo.client.core.event.NodeMouseEnterEvent;
import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitEvent;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import org.roger600.lienzo.client.toolboxNew.impl2.AbstractGroupItem;
import org.roger600.lienzo.client.toolboxNew.impl2.DecoratorItem;
import org.roger600.lienzo.client.toolboxNew.impl2.GroupItem;

class DecoratedPrimitiveItem<T extends DecoratedPrimitiveItem>
        extends AbstractGroupItem<T> {

    private final HandlerRegistrationManager registrations = new HandlerRegistrationManager();
    private final IPrimitive<?> primitive;
    private DecoratorItem<?> decorator;

    DecoratedPrimitiveItem(final IPrimitive<?> primitive) {
        super(new GroupItem());
        this.primitive = primitive;
        getGroupItem().add(primitive);
        initHandlers(primitive);
    }

    public DecoratedPrimitiveItem decorate(final DecoratorItem<?> decorator) {
        initDecorator(decorator);
        return cast();
    }

    @Override
    public T hide() {
        super.hide();
        hideDecorator();
        return cast();
    }

    public DecoratedPrimitiveItem focus() {
        GWT.log("FOCUSING");
        if (isDecorated()) {
            showDecorator();
        }
        return cast();
    }

    public DecoratedPrimitiveItem unFocus() {
        GWT.log("UN-FOCUSING");
        if (isDecorated()) {
            hideDecorator();
        }
        return cast();
    }

    public IPrimitive<?> getPrimitive() {
        return primitive;
    }

    T register(final HandlerRegistration registration) {
        registrations.register(registration);
        return cast();
    }

    @Override
    protected final void preDestroy() {
        super.preDestroy();
        initDecorator(null);
        destroyHandlers();
        primitive.removeFromParent();
    }

    private boolean isDecorated() {
        return null != this.decorator;
    }

    private void initDecorator(final DecoratorItem<?> decorator) {
        if (isDecorated()) {
            this.decorator.destroy();
        }
        this.decorator = decorator;
        if (isDecorated()) {
            asPrimitive().add(decorator.asPrimitive());
            if (getGroupItem().isVisible()) {
                showDecorator();
            } else {
                hideDecorator();
            }
        }
    }

    private void showDecorator() {
        if (null != this.decorator) {
            this.decorator.show();
        }
    }

    private void hideDecorator() {
        if (null != this.decorator) {
            this.decorator.hide();
        }
    }

    private void initHandlers(final IPrimitive<?> prim) {
        prim.setListening(true);
        registrations.register(
                prim.addNodeMouseEnterHandler(new NodeMouseEnterHandler() {
                    @Override
                    public void onNodeMouseEnter(NodeMouseEnterEvent event) {
                        focus();
                    }
                })
        );
        registrations.register(
                prim.addNodeMouseExitHandler(new NodeMouseExitHandler() {
                    @Override
                    public void onNodeMouseExit(NodeMouseExitEvent event) {
                        unFocus();
                    }
                })
        );
    }

    private void destroyHandlers() {
        registrations.removeHandler();
    }

    @SuppressWarnings("unchecked")
    private T cast() {
        return (T) this;
    }
}
