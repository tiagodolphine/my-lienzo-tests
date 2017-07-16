package org.roger600.lienzo.client.toolboxNew.impl2;

import com.ait.lienzo.client.core.shape.Group;
import org.roger600.lienzo.client.toolboxNew.Item;

public class GroupItem extends AbstractItem<GroupItem, Group> implements Item<GroupItem> {

    private final Group group;

    public GroupItem() {
        this.group = new Group();
        hide();
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

    GroupItem show(final Runnable pre) {
        if (!isVisible()) {
            pre.run();
            group.setAlpha(1);
        }
        return this;
    }

    GroupItem hide(final Runnable pre) {
        if (isVisible()) {
            pre.run();
            group.setAlpha(0);
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
}
