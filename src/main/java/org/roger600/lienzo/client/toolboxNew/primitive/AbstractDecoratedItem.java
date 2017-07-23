package org.roger600.lienzo.client.toolboxNew.primitive;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.types.BoundingBox;
import org.roger600.lienzo.client.toolboxNew.util.Supplier;

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

    public abstract T show(Runnable before,
                           Runnable after);

    public abstract T hide(Runnable before,
                           Runnable after);

    public abstract IPrimitive<?> getPrimitive();

    public abstract Supplier<BoundingBox> getBoundingBox();
}
