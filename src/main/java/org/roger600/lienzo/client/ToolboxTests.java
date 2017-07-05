package org.roger600.lienzo.client;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.Direction;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import org.roger600.lienzo.client.toolbox.StaticToolbox;
import org.roger600.lienzo.client.toolbox.ToolboxButton;
import org.roger600.lienzo.client.toolbox.Toolboxes;
import org.roger600.lienzo.client.toolbox.builder.ButtonGrid;
import org.roger600.lienzo.client.toolbox.builder.ButtonsOrRegister;
import org.roger600.lienzo.client.toolbox.builder.On;
import org.roger600.lienzo.client.toolbox.event.ToolboxButtonEvent;
import org.roger600.lienzo.client.toolbox.event.ToolboxButtonEventHandler;
import org.roger600.lienzo.client.toolbox.grid.GridToolbox;

public class ToolboxTests implements MyLienzoTest,
                                     HasMediators,
                                     HasButtons {

    private static final double BUTTON_SIZE = 14;
    private static final int BUTTON_PADDING = 5;

    private Layer layer;
    private WiresManager wiresManager;
    private WiresShape shape;
    private GridToolbox gridToolbox;

    public void setButtonsPanel(Panel panel) {

        Button showButton = new Button("Show");
        showButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                gridToolbox.show();
            }
        });
        panel.add(showButton);

        Button hideButton = new Button("Hide");
        hideButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                gridToolbox.hide();
            }
        });
        panel.add(hideButton);
    }

    public void test(Layer layer) {
        this.layer = layer;
        this.wiresManager = WiresManager.get(layer);

        shape = newShape();
        shape.setX(50).setY(100);

        buildToolbox();

        StaticToolbox toolbox = (StaticToolbox) gridToolbox;

        layer.batch();
    }

    private void buildToolbox() {
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
                                                 new Rectangle(BUTTON_SIZE,
                                                               BUTTON_SIZE).setFillColor(ColorName.RED).setFillAlpha(0.7d),
                                                 BUTTON_PADDING,
                                                 (int) BUTTON_SIZE,
                                                 new ToolboxButtonEventHandler() {
                                                     @Override
                                                     public void fire(ToolboxButtonEvent event) {
                                                         GWT.log("CLICK");
                                                     }
                                                 },
                                                 new ToolboxButtonEventHandler() {
                                                     @Override
                                                     public void fire(ToolboxButtonEvent event) {
                                                         GWT.log("MOUSE DOWN");
                                                     }
                                                 },
                                                 new ToolboxButtonEventHandler() {
                                                     @Override
                                                     public void fire(ToolboxButtonEvent event) {
                                                         GWT.log("MOUSE ENTER");
                                                     }
                                                 },
                                                 new ToolboxButtonEventHandler() {
                                                     @Override
                                                     public void fire(ToolboxButtonEvent event) {
                                                         GWT.log("MOUSE EXIT");
                                                     }
                                                 });
        buttonsOrRegister.add(button);

        gridToolbox = buttonsOrRegister.register();
    }

    private WiresShape newShape() {
        MultiPath path = TestsUtils.rect(new MultiPath().setFillColor(ColorName.BLUE),
                                         150,
                                         50,
                                         5);
        final WiresShape shape = new WiresShape(path);
        wiresManager.register(shape);
        shape.setDraggable(true);
        wiresManager.getMagnetManager().createMagnets(shape);
        return shape;
    }
}
