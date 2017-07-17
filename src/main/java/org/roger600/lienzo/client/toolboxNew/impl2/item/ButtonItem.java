package org.roger600.lienzo.client.toolboxNew.impl2.item;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import org.roger600.lienzo.client.toolboxNew.impl2.AbstractItem;
import org.roger600.lienzo.client.toolboxNew.impl2.DelegateItem;

public class ButtonItem extends DelegateItem<ButtonItem, Group> {

    private final HandlerRegistrationManager registrations = new HandlerRegistrationManager();
    private final PrimitiveItem item;
    private Runnable click;

    public ButtonItem(final IPrimitive<?> prim) {
        super();
        this.item = new PrimitiveItem(prim);
        initHandlers(item.asPrimitive());
    }

    public ButtonItem onClick(final Runnable click) {
        this.click = click;
        return this;
    }

    private void initHandlers(final IPrimitive<?> prim) {
        prim.setListening(true);
        registrations.register(
                prim.addNodeMouseClickHandler(new NodeMouseClickHandler() {
                    @Override
                    public void onNodeMouseClick(NodeMouseClickEvent event) {
                        if (null != click) {
                            click.run();
                        }
                    }
                })
        );
    }

    @Override
    public void destroy() {
        super.destroy();
        registrations.removeHandler();
        click = null;
    }

    @Override
    protected AbstractItem<?, Group> getDelegate() {
        return item;
    }
}
