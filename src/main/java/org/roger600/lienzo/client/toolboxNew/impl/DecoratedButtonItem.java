package org.roger600.lienzo.client.toolboxNew.impl;

import com.ait.lienzo.client.core.shape.ContainerNode;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.types.Point2D;
import org.roger600.lienzo.client.toolboxNew.ToolboxItem;

public class DecoratedButtonItem extends AbstractCallbacksToolboxIItem<DecoratedButtonItem> {

    public interface Decorator {

        IPrimitive<?> getPrimitive();

        void show();

        void hide();
    }

    private final ButtonItem item;
    private final Decorator decorator;

    public DecoratedButtonItem(final IPrimitive<?> shape,
                               final double width,
                               final double height) {
        this(shape,
             ButtonDecorators.box(width,
                                  height));
    }

    public DecoratedButtonItem(final IPrimitive<?> shape,
                               final Decorator decorator) {
        this.item = new ButtonItem(shape);
        this.decorator = decorator;
        init();
    }

    private void init() {
        attachDecorator();
        item.onHighlight(new Runnable() {
            @Override
            public void run() {
                decorator.show();
            }
        });
        item.onUnHighlight(new Runnable() {
            @Override
            public void run() {
                decorator.hide();
            }
        });
    }

    private void attachDecorator() {
        item.getGroup().add(decorator.getPrimitive());
    }

    @Override
    public DecoratedButtonItem attachTo(final ContainerNode<IPrimitive<?>, ?> parent) {
        item.attachTo(parent);
        return this;
    }

    @Override
    public DecoratedButtonItem setLocation(final Point2D location) {
        item.setLocation(location);
        return this;
    }

    @Override
    public DecoratedButtonItem onFocus(final Runnable callback) {
        item.onFocus(callback);
        return this;
    }

    @Override
    public DecoratedButtonItem onUnFocus(final Runnable callback) {
        item.onUnFocus(callback);
        return this;
    }

    @Override
    public DecoratedButtonItem onClick(final Runnable callback) {
        item.onClick(callback);
        return this;
    }

    @Override
    public DecoratedButtonItem onDown(final Runnable callback) {
        item.onDown(callback);
        return this;
    }

    @Override
    public ToolboxItem show() {
        item.show();
        return this;
    }

    @Override
    public ToolboxItem hide() {
        item.hide();
        return this;
    }

    @Override
    public void destroy() {
        item.destroy();
    }
}
