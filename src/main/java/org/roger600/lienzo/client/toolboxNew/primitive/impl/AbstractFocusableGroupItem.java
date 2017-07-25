package org.roger600.lienzo.client.toolboxNew.primitive.impl;

import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.event.NodeMouseEnterEvent;
import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitEvent;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import org.roger600.lienzo.client.toolboxNew.GroupItem;
import org.roger600.lienzo.client.toolboxNew.GroupVisibilityExecutors;

public abstract class AbstractFocusableGroupItem<T extends AbstractFocusableGroupItem>
        extends AbstractGroupItem<T> {

    private final static int FOCUS_DELAY_MILLIS = 200;
    private static final double ALPHA_FOCUSED = 1d;
    private static final double ALPHA_UNFOCUSED = 0.5d;

    private final FocusGroupExecutor focusGroupExecutor;
    private int focusDelay;
    private int unFocusDelay;
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

    protected AbstractFocusableGroupItem(final GroupItem groupItem) {
        super(groupItem);
        this.focusDelay = FOCUS_DELAY_MILLIS;
        this.unFocusDelay = 0;
        this.focusGroupExecutor = new FocusGroupExecutor();
        useShowExecutor(focusGroupExecutor);
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
    public T show(final Runnable before,
                  final Runnable after) {
        getGroupItem().show(before,
                            after);
        return cast();
    }

    @Override
    public T hide(final Runnable before,
                  final Runnable after) {
        getGroupItem().hide(new Runnable() {
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

    @Override
    public void destroy() {
        cancelFocusTimer();
        cancelUnFocusTimer();
        super.destroy();
    }

    protected T setupFocusingHandlers() {
        getPrimitive().setListening(true);
        registrations().register(
                getPrimitive().addNodeMouseEnterHandler(new NodeMouseEnterHandler() {
                    @Override
                    public void onNodeMouseEnter(NodeMouseEnterEvent event) {
                        focus();
                    }
                })
        );
        registrations().register(
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

    private class FocusGroupExecutor
            extends GroupVisibilityExecutors.AnimatedAlphaGroupExecutor {

        public FocusGroupExecutor() {
            super(ALPHA_UNFOCUSED);
            setAnimationTweener(AnimationTweener.LINEAR);
        }

        public void focus() {
            GWT.log("FOCUSING");
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
            GWT.log("UN-FOCUSING");
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
