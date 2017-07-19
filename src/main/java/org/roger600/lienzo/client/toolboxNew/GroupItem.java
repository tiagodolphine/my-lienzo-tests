package org.roger600.lienzo.client.toolboxNew;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;

public class GroupItem extends AbstractItem<GroupItem, Group> implements Item<GroupItem> {

    private final Group group;

    public GroupItem() {
        this(new Group());
    }

    public GroupItem(final Group group) {
        this.group = group;
        doHide();
    }

    @Override
    public GroupItem show() {
        return show(new Runnable() {
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

    public GroupItem show(final Runnable pre) {
        if (!isVisible()) {
            pre.run();
            doShow();
        }
        return this;
    }

    public GroupItem hide(final Runnable pre) {
        if (isVisible()) {
            pre.run();
            doHide();
        }
        return this;
    }

    public boolean isVisible() {
        return 1 == group.getAlpha();
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

    private void doShow() {
        group.setAlpha(1);
    }

    private void doHide() {
        group.setAlpha(0);
    }
}
