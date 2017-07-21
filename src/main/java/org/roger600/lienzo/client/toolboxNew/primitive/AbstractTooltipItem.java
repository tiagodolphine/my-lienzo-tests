package org.roger600.lienzo.client.toolboxNew.primitive;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;

public abstract class AbstractTooltipItem<T extends TooltipItem> extends AbstractPrimitiveItem<T> {

    private IPrimitive<?> primitive;

    protected abstract void doDestroy();

    public AbstractTooltipItem forPrimitive(final IPrimitive<?> primitive) {
        this.primitive = primitive;
        return this;
    }

    @Override
    public void destroy() {
        doDestroy();
        primitive = null;
    }

    protected BoundingBox getBoundingBox() {
        return primitive.getBoundingBox();
    }

    protected Point2D getAbsoluteLocation() {
        return primitive.getComputedLocation();
    }
}
