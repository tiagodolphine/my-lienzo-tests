package org.roger600.lienzo.client;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.shape.wires.event.AbstractWiresResizeEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeEndEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeEndHandler;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStartEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStartHandler;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStepEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStepHandler;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.Direction;
import com.ait.lienzo.shared.core.types.IColor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import org.roger600.lienzo.client.toolboxNew.grid.LayoutGrid;
import org.roger600.lienzo.client.toolboxNew.impl2.GroupItem;
import org.roger600.lienzo.client.toolboxNew.impl2.ext.WiresShapeToolbox;

public class ToolboxTests implements MyLienzoTest,
                                     HasMediators,
                                     HasButtons {

    private static final double BUTTON_SIZE = 14;
    private static final double BUTTON_PADDING = 5;

    private Layer layer;
    private WiresManager wiresManager;
    private WiresShape shape1;
    private WiresShape shape2;
    private LayoutGrid grid2;
    private WiresShapeToolbox toolbox2;

    public void setButtonsPanel(Panel panel) {

        /*Button showButton = new Button("Show #1");
        showButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                gridToolbox.show();
            }
        });
        panel.add(showButton);

        Button hideButton = new Button("Hide #1");
        hideButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                gridToolbox.hide();
            }
        });
        panel.add(hideButton);
*/
        Button showNewButton = new Button("Show #2");
        showNewButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                toolbox2.show();
            }
        });
        panel.add(showNewButton);

        Button hideNewButton = new Button("Hide #2");
        hideNewButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                toolbox2.hide();
            }
        });
        panel.add(hideNewButton);

        Button changeLocationNewButton = new Button("Change #2 to WEST");
        changeLocationNewButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                LayoutGrid aGrid = grid2.towards(Direction.SOUTH_WEST);
                toolbox2
                        .at(Direction.NORTH_WEST)
                        .grid(aGrid);
            }
        });
        panel.add(changeLocationNewButton);

        Button changeGridNewButton = new Button("Change #2 to one column");
        changeGridNewButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                LayoutGrid aGrid = grid2.setCols(1);
                toolbox2.grid(aGrid);
            }
        });
        panel.add(changeGridNewButton);
    }

    public void test(Layer layer) {
        this.layer = layer;
        this.wiresManager = WiresManager.get(layer);

        shape1 = newShape(ColorName.BLUE);
        shape1.setX(50).setY(100);

        // buildNewToolbox(shape1);

        shape2 = newShape(ColorName.RED);
        shape2.setX(400).setY(100);

        buildNewToolbox(shape2);

        layer.batch();
    }

    private void buildNewToolbox(final WiresShape shape) {
        grid2 = new LayoutGrid(BUTTON_PADDING,
                               BUTTON_SIZE,
                               Direction.SOUTH_EAST,
                               4,
                               2);

        toolbox2 = new WiresShapeToolbox(shape)
                .at(Direction.NORTH_EAST)
                .grid(grid2);

        Rectangle b1 = createButtonNode();
        final GroupItem item1 = new GroupItem();
        item1.asPrimitive().add(b1);
        toolbox2.add(item1);

        /*item1.onFocus(new Runnable() {
            @Override
            public void run() {
                GWT.log("NEW #FOCUS 1");
            }
        });
        item1.onUnFocus(new Runnable() {
            @Override
            public void run() {
                GWT.log("NEW #UNFOCUS 1");
            }
        });
        item1.onClick(new Runnable() {
            @Override
            public void run() {
                GWT.log("NEW #CLICK 1");
            }
        });
        item1.onDown(new Runnable() {
            @Override
            public void run() {
                GWT.log("NEW #DOWN 1");
            }
        });*/

    }

    private void resizeShape(final AbstractWiresResizeEvent event) {
    }

    private Rectangle createButtonNode() {
        return new Rectangle(BUTTON_SIZE,
                             BUTTON_SIZE)
                .setFillColor(ColorName.BLACK)
                .setFillAlpha(1d);
    }

    private WiresShape newShape(final IColor color) {
        MultiPath path = TestsUtils.rect(new MultiPath().setFillColor(color),
                                         150,
                                         50,
                                         0);
        final WiresShape shape = new WiresShape(path);
        wiresManager.register(shape);
        shape.setDraggable(true);
        wiresManager.getMagnetManager().createMagnets(shape);
        TestsUtils.addResizeHandlers(shape);
        shape.addWiresResizeStartHandler(new WiresResizeStartHandler() {
            @Override
            public void onShapeResizeStart(WiresResizeStartEvent event) {
                resizeShape(event);
            }
        });
        shape.addWiresResizeStepHandler(new WiresResizeStepHandler() {
            @Override
            public void onShapeResizeStep(WiresResizeStepEvent event) {
                resizeShape(event);
            }
        });
        shape.addWiresResizeEndHandler(new WiresResizeEndHandler() {
            @Override
            public void onShapeResizeEnd(WiresResizeEndEvent event) {
                resizeShape(event);
            }
        });
        return shape;
    }
}
