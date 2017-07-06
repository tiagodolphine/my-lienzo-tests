package org.roger600.lienzo.client;

import com.ait.lienzo.client.core.shape.IPrimitive;
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
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import org.roger600.lienzo.client.toolbox.ToolboxButton;
import org.roger600.lienzo.client.toolbox.Toolboxes;
import org.roger600.lienzo.client.toolbox.builder.ButtonGrid;
import org.roger600.lienzo.client.toolbox.builder.ButtonsOrRegister;
import org.roger600.lienzo.client.toolbox.builder.On;
import org.roger600.lienzo.client.toolbox.event.ToolboxButtonEvent;
import org.roger600.lienzo.client.toolbox.event.ToolboxButtonEventHandler;
import org.roger600.lienzo.client.toolbox.grid.GridToolbox;
import org.roger600.lienzo.client.toolboxNew.Grid;
import org.roger600.lienzo.client.toolboxNew.ItemsToolbox;
import org.roger600.lienzo.client.toolboxNew.Toolbox;
import org.roger600.lienzo.client.toolboxNew.impl.AbstractToolboxIItem;
import org.roger600.lienzo.client.toolboxNew.impl.ButtonItem;
import org.roger600.lienzo.client.toolboxNew.impl.DecoratedButtonItem;
import org.roger600.lienzo.client.toolboxNew.impl.IPrimitiveItemsToolbox;
import org.roger600.lienzo.client.toolboxNew.impl.WiresShapeItemsToolbox;

public class ToolboxTests implements MyLienzoTest,
                                     HasMediators,
                                     HasButtons {

    private static final double BUTTON_SIZE = 14;
    private static final int BUTTON_PADDING = 5;

    private Layer layer;
    private WiresManager wiresManager;
    private WiresShape shape1;
    private WiresShape shape2;
    private GridToolbox gridToolbox;
    private ItemsToolbox<IPrimitive<?>, AbstractToolboxIItem, ?> newToolbox;

    public void setButtonsPanel(Panel panel) {

        Button showButton = new Button("Show old");
        showButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                gridToolbox.show();
            }
        });
        panel.add(showButton);

        Button hideButton = new Button("Hide old");
        hideButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                gridToolbox.hide();
            }
        });
        panel.add(hideButton);

        Button showNewButton = new Button("Show new");
        showNewButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                newToolbox.show();
            }
        });
        panel.add(showNewButton);

        Button hideNewButton = new Button("Hide new");
        hideNewButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                newToolbox.hide();
            }
        });
        panel.add(hideNewButton);

        Button changeLocationNewButton = new Button("Change new to WEST");
        changeLocationNewButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                newToolbox.at(Direction.NORTH_WEST)
                        .towards(Direction.SOUTH_WEST);
            }
        });
        panel.add(changeLocationNewButton);


        Button changeGridNewButton = new Button("Change new to one column");
        changeGridNewButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final Grid grid = new Grid(BUTTON_PADDING,
                                     (int) BUTTON_SIZE,
                                     4,
                                     1);
                newToolbox.grid(grid);
            }
        });
        panel.add(changeGridNewButton);

    }

    public void test(Layer layer) {
        this.layer = layer;
        this.wiresManager = WiresManager.get(layer);

        shape1 = newShape(ColorName.BLUE);
        shape1.setX(50).setY(100);

        buildGridToolbox(shape1);

        shape2 = newShape(ColorName.RED);
        shape2.setX(400).setY(100);

        buildNewToolbox(shape2);

        layer.batch();
    }

    private void buildNewToolbox(final WiresShape shape) {
        Grid grid = new Grid(BUTTON_PADDING,
                             (int) BUTTON_SIZE,
                             4,
                             2);

        newToolbox = new WiresShapeItemsToolbox(shape)
                .attachTo(layer)
                .at(Direction.NORTH_EAST)
                .towards(Direction.SOUTH_EAST)
                .grid(grid);

        final ButtonItem item1 =
                new ButtonItem(createButtonNode());
        item1.onFocus(new Runnable() {
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
        });

        final DecoratedButtonItem item2 =
                new DecoratedButtonItem(createButtonNode(),
                                        BUTTON_SIZE,
                                        BUTTON_SIZE);
        item2.onFocus(new Runnable() {
            @Override
            public void run() {
                GWT.log("NEW #FOCUS 2");
            }
        });
        item2.onUnFocus(new Runnable() {
            @Override
            public void run() {
                GWT.log("NEW #UNFOCUS 2");
            }
        });
        item2.onClick(new Runnable() {
            @Override
            public void run() {
                GWT.log("NEW #CLICK 2");
            }
        });
        item2.onDown(new Runnable() {
            @Override
            public void run() {
                GWT.log("NEW #DOWN 2");
            }
        });

        newToolbox.add(item1);
        newToolbox.add(item2);
    }

    private void buildGridToolbox(final WiresShape shape) {
        On on = Toolboxes.staticToolBoxFor(layer,
                                           shape);

        on.attachTo(shape.getPath());

        ButtonGrid buttonGrid = on.on(Direction.NORTH_EAST)
                .towards(Direction.SOUTH_EAST);

        ButtonsOrRegister buttonsOrRegister = buttonGrid.grid(BUTTON_PADDING,
                                                              (int) BUTTON_SIZE,
                                                              4,
                                                              2);
        // Add toolbox buttons.
        ToolboxButton button = new ToolboxButton(layer,
                                                 createButtonNode(),
                                                 BUTTON_PADDING,
                                                 (int) BUTTON_SIZE,
                                                 new ToolboxButtonEventHandler() {
                                                     @Override
                                                     public void fire(ToolboxButtonEvent event) {
                                                         GWT.log("OLD #CLICK");
                                                     }
                                                 },
                                                 new ToolboxButtonEventHandler() {
                                                     @Override
                                                     public void fire(ToolboxButtonEvent event) {
                                                         GWT.log("OLD #MOUSE DOWN");
                                                     }
                                                 },
                                                 new ToolboxButtonEventHandler() {
                                                     @Override
                                                     public void fire(ToolboxButtonEvent event) {
                                                         GWT.log("OLD #MOUSE ENTER");
                                                     }
                                                 },
                                                 new ToolboxButtonEventHandler() {
                                                     @Override
                                                     public void fire(ToolboxButtonEvent event) {
                                                         GWT.log("OLD #MOUSE EXIT");
                                                     }
                                                 });
        buttonsOrRegister.add(button);

        gridToolbox = buttonsOrRegister.register();
    }

    private void resizeShape(final AbstractWiresResizeEvent event) {
    }

    private Rectangle createButtonNode() {
        return new Rectangle(BUTTON_SIZE,
                             BUTTON_SIZE)
                .setFillColor(ColorName.RED)
                .setFillAlpha(0.7d);
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
