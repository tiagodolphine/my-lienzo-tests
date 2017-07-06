package org.roger600.lienzo.client.toolboxNew.impl;

import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationProperty;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.shape.ContainerNode;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.types.Point2D;
import org.roger600.lienzo.client.toolboxNew.ToolboxItem;

public class IPrimitiveItem extends AbstractToolboxIItem {

    public static final double ANIMATION_DURATION = 50;

    private final Group group;

    public IPrimitiveItem(final IPrimitive<?> node) {
        this.group = new Group();
        this.group.add(node);
    }

    @Override
    public IPrimitiveItem attachTo(ContainerNode<IPrimitive<?>, ?> parent) {
        group.removeFromParent();
        parent.add(group);
        return this;
    }

    @Override
    public IPrimitiveItem setLocation(Point2D location) {
        group.setX(location.getX());
        group.setY(location.getY());
        return this;
    }

    public IPrimitiveItem show(final double duration) {
        return animateGroupAlpha(1,
                                 duration);
    }

    public IPrimitiveItem hide(final double duration) {
        return animateGroupAlpha(0,
                                 duration);
    }

    public boolean isVisible() {
        return 1 == group.getAlpha();
    }

    private IPrimitiveItem animateGroupAlpha(final double alpha,
                                             final double duration) {
        group
                .animate(AnimationTweener.LINEAR,
                         AnimationProperties.toPropertyList(AnimationProperty.Properties.ALPHA(alpha)),
                         duration);
        return this;
    }

    public void destroy() {
        group.removeAll();
        group.removeFromParent();
    }

    public ToolboxItem show() {
        return show(ANIMATION_DURATION);
    }

    @Override
    public ToolboxItem hide() {
        return hide(ANIMATION_DURATION);
    }

    Group getGroup() {
        return group;
    }
}
