package org.roger600.lienzo.client.toolboxNew.primitive;

import com.ait.lienzo.client.core.shape.Shape;

public abstract class AbstractDecoratedItem<T extends DecoratedItem>
        extends AbstractPrimitiveItem<T>
        implements DecoratedItem<T> {

    @Override
    public T show() {
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
    public T hide() {
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

    public T show(final Runnable after) {
        return show(new Runnable() {
                        @Override
                        public void run() {
                        }
                    },
                    after);
    }

    public T hide(final Runnable after) {
        return hide(new Runnable() {
                        @Override
                        public void run() {
                        }
                    },
                    after);
    }

    public abstract T show(Runnable before,
                           Runnable after);

    public abstract T hide(Runnable before,
                           Runnable after);

    public abstract Shape<?> getAttachable();
}
