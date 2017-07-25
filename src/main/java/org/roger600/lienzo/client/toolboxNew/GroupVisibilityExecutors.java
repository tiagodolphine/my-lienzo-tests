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

public class GroupVisibilityExecutors {

    public static AnimatedAlphaGroupExecutor alpha(final double targetAlphaValue) {
        return new AnimatedAlphaGroupExecutor(targetAlphaValue);
    }

    public static AnimatedScaleXGroupExecutor upScaleX() {
        return scaleX(1,
                      0.1,
                      1);
    }

    public static AnimatedScaleXGroupExecutor downScaleX() {
        return scaleX(0,
                      1,
                      0.1);
    }

    public static AnimatedScaleYGroupExecutor upScaleY() {
        return scaleY(1,
                      0.1,
                      1);
    }

    public static AnimatedScaleYGroupExecutor downScaleY() {
        return scaleY(0,
                      1,
                      0.1);
    }

    public static AnimatedScaleXGroupExecutor scaleX(final double targetAlphaValue,
                                                     final double startScale,
                                                     final double endScale) {
        return new AnimatedScaleXGroupExecutor(targetAlphaValue,
                                               startScale,
                                               endScale);
    }

    private static AnimatedScaleYGroupExecutor scaleY(final double targetAlphaValue,
                                                      final double startScale,
                                                      final double endScale) {
        return new AnimatedScaleYGroupExecutor(targetAlphaValue,
                                               startScale,
                                               endScale);
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

        protected abstract Point2D getEndScale();

        @Override
        protected AnimationProperties getProperties() {
            return AnimationProperties.toPropertyList(AnimationProperty.Properties.SCALE(getEndScale()));
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

        private final double start;
        private final double end;

        protected AnimatedScaleXGroupExecutor(final double alpha,
                                              final double start,
                                              final double end) {
            super(alpha);
            this.start = start;
            this.end = end;
        }

        @Override
        protected Point2D getInitialScale() {
            return new Point2D(start,
                               1);
        }

        @Override
        protected Point2D getEndScale() {
            return new Point2D(end,
                               1);
        }
    }

    public static class AnimatedScaleYGroupExecutor extends AnimatedScaleGroupExecutor<AnimatedScaleYGroupExecutor> {

        private final double start;
        private final double end;

        protected AnimatedScaleYGroupExecutor(final double alpha,
                                              final double start,
                                              final double end) {
            super(alpha);
            this.start = start;
            this.end = end;
        }

        @Override
        protected Point2D getInitialScale() {
            return new Point2D(1,
                               start);
        }

        @Override
        protected Point2D getEndScale() {
            return new Point2D(1,
                               end);
        }
    }
}
