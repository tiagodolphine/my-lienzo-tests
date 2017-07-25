package org.roger600.lienzo.client.toolboxNew;

import com.ait.lienzo.client.core.animation.AnimationCallback;
import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationProperty;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.animation.IAnimation;
import com.ait.lienzo.client.core.animation.IAnimationHandle;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.types.Point2D;
import org.roger600.lienzo.client.toolboxNew.util.BiConsumer;

public class GroupItemVisibilityExecutors {

    public static AnimatedAlphaGroupExecutor alpha(final double targetAlphaValue) {
        return new AnimatedAlphaGroupExecutor(targetAlphaValue);
    }

    public static AnimatedScaleXGroupExecutor scaleX(final double targetAlphaValue) {
        return new AnimatedScaleXGroupExecutor(targetAlphaValue);
    }

    public static AnimatedScaleYGroupExecutor scaleY(final double targetAlphaValue) {
        return new AnimatedScaleYGroupExecutor(targetAlphaValue);
    }

    public abstract static class AnimatedGroupExecutor<T extends AnimatedGroupExecutor>
            implements BiConsumer<Group, Runnable> {

        private double animationDuration;
        private AnimationTweener animationTweener;

        public AnimatedGroupExecutor() {
            this.animationTweener = AnimationTweener.LINEAR;
            this.animationDuration = 150;
        }

        protected abstract AnimationProperties getProperties();

        @Override
        public void apply(final Group group,
                          final Runnable callback) {
            animate(group,
                    callback);
        }

        private void animate(final Group group,
                             final Runnable callback) {
            group.animate(animationTweener,
                          getProperties(),
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

        public T setAnimationTweener(final AnimationTweener animationTweener) {
            this.animationTweener = animationTweener;
            return cast();
        }

        public T setAnimationDuration(final double millis) {
            this.animationDuration = millis;
            return cast();
        }

        @SuppressWarnings("unchecked")
        private T cast() {
            return (T) this;
        }
    }

    public static class AnimatedAlphaGroupExecutor extends AnimatedGroupExecutor<AnimatedAlphaGroupExecutor> {

        private double alpha;

        protected AnimatedAlphaGroupExecutor(final double alpha) {
            super();
            this.alpha = alpha;
        }

        @Override
        protected AnimationProperties getProperties() {
            return AnimationProperties.toPropertyList(AnimationProperty.Properties.ALPHA(alpha));
        }

        public AnimatedAlphaGroupExecutor setAlpha(final double alpha) {
            this.alpha = alpha;
            return this;
        }
    }

    public abstract static class AnimatedScaleGroupExecutor<T extends AnimatedScaleGroupExecutor>
            extends AnimatedGroupExecutor<T> {

        private final double alpha;

        protected AnimatedScaleGroupExecutor(final double alpha) {
            this.alpha = alpha;
        }

        protected abstract Point2D getInitialScale();

        @Override
        protected AnimationProperties getProperties() {
            return AnimationProperties.toPropertyList(AnimationProperty.Properties.SCALE(1,
                                                                                         1));
        }

        @Override
        public void apply(final Group group,
                          final Runnable callback) {
            group
                    .setScale(getInitialScale())
                    .setAlpha(1);
            super.apply(group,
                        new Runnable() {
                            @Override
                            public void run() {
                                group.setAlpha(alpha);
                                callback.run();
                            }
                        });
        }
    }

    public static class AnimatedScaleXGroupExecutor extends AnimatedScaleGroupExecutor<AnimatedScaleXGroupExecutor> {

        protected AnimatedScaleXGroupExecutor(final double alpha) {
            super(alpha);
        }

        @Override
        protected Point2D getInitialScale() {
            return new Point2D(0.1,
                               1);
        }
    }

    public static class AnimatedScaleYGroupExecutor extends AnimatedScaleGroupExecutor<AnimatedScaleYGroupExecutor> {

        protected AnimatedScaleYGroupExecutor(final double alpha) {
            super(alpha);
        }

        @Override
        protected Point2D getInitialScale() {
            return new Point2D(1,
                               0.1);
        }
    }
}
