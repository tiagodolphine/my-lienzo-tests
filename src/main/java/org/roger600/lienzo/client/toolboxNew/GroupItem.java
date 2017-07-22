package org.roger600.lienzo.client.toolboxNew;

import com.ait.lienzo.client.core.animation.AnimationCallback;
import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationProperty;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.animation.IAnimation;
import com.ait.lienzo.client.core.animation.IAnimationHandle;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import org.roger600.lienzo.client.toolboxNew.util.BiConsumer;

public class GroupItem extends AbstractItem<GroupItem, Group> implements Item<GroupItem> {

    private final Group group;
    private BiConsumer<Group, Runnable> showExecutor;
    private BiConsumer<Group, Runnable> hideExecutor;

    public GroupItem() {
        this(new Group());
    }

    public GroupItem(final Group group) {
        this.group = group;
        this.showExecutor = new AnimatedGroupExecutor()
                .setAnimationTweener(AnimationTweener.LINEAR)
                .setAlpha(1)
                .setAnimationDuration(150);
        this.hideExecutor = new AnimatedGroupExecutor()
                .setAnimationTweener(AnimationTweener.LINEAR)
                .setAlpha(0)
                .setAnimationDuration(150);
        doHide(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    public GroupItem useShowExecutor(final BiConsumer<Group, Runnable> executor) {
        this.showExecutor = executor;
        return this;
    }

    public GroupItem useHideExecutor(final BiConsumer<Group, Runnable> executor) {
        this.hideExecutor = executor;
        return this;
    }

    @Override
    public GroupItem show() {
        return show(new Runnable() {
                        @Override
                        public void run() {
                        }
                    },
                    new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
    }

    @Override
    public GroupItem hide() {
        return hide(new Runnable() {
                        @Override
                        public void run() {
                        }
                    },
                    new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
    }

    public GroupItem add(final IPrimitive<?> iPrimitive) {
        group.add(iPrimitive);
        return this;
    }

    public GroupItem remove(final IPrimitive<?> iPrimitive) {
        group.remove(iPrimitive);
        return this;
    }

    public GroupItem show(final Runnable before,
                          final Runnable after) {
        if (!isVisible()) {
            before.run();
            doShow(after);
        }
        return this;
    }

    public GroupItem hide(final Runnable before,
                          final Runnable after) {
        if (isVisible()) {
            before.run();
            doHide(after);
        }
        return this;
    }

    public boolean isVisible() {
        return group.getAlpha() > 0;
    }

    @Override
    public void destroy() {
        group.removeAll();
        group.removeFromParent();
    }

    @Override
    public Group asPrimitive() {
        return group;
    }

    private void doShow(final Runnable callback) {
        showExecutor.apply(group,
                           callback);
    }

    private void doHide(final Runnable callback) {
        hideExecutor.apply(group,
                           callback);
    }

    public static class AnimatedGroupExecutor implements BiConsumer<Group, Runnable> {

        private double animationDuration;
        private double alpha;
        private AnimationTweener animationTweener;

        public AnimatedGroupExecutor() {
            this.animationDuration = 150;
            this.alpha = 1;
        }

        @Override
        public void apply(final Group group,
                          final Runnable callback) {
            group.animate(animationTweener,
                          AnimationProperties.toPropertyList(AnimationProperty.Properties.ALPHA(alpha)),
                          animationDuration,
                          new AnimationCallback() {
                              @Override
                              public void onClose(IAnimation animation,
                                                  IAnimationHandle handle) {
                                  super.onClose(animation,
                                                handle);
                                  callback.run();
                              }
                          });
        }

        public AnimatedGroupExecutor setAnimationTweener(final AnimationTweener animationTweener) {
            this.animationTweener = animationTweener;
            return this;
        }

        public AnimatedGroupExecutor setAlpha(final double alpha) {
            this.alpha = alpha;
            return this;
        }

        public AnimatedGroupExecutor setAnimationDuration(final double millis) {
            this.animationDuration = millis;
            return this;
        }
    }
}
