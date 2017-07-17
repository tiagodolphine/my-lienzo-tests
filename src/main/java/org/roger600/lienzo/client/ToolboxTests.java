package org.roger600.lienzo.client;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.Direction;
import com.ait.lienzo.shared.core.types.IColor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.roger600.lienzo.client.toolboxNew.grid.LayoutGrid;
import org.roger600.lienzo.client.toolboxNew.impl2.ext.ToolboxFactory;
import org.roger600.lienzo.client.toolboxNew.impl2.ext.WiresShapeToolbox;
import org.roger600.lienzo.client.toolboxNew.impl2.item.ButtonItem;

public class ToolboxTests implements MyLienzoTest,
                                     HasMediators,
                                     HasButtons {

    private static final double BUTTON_SIZE = 14;
    private static final double BUTTON_PADDING = 5;
    private static final int iCols = 2;
    private static final int iRows = 4;
    private static final Direction iAt = Direction.NORTH_EAST;
    private static final Direction iTowards = Direction.SOUTH_EAST;

    private Layer layer;
    private WiresManager wiresManager;
    private WiresShape shape1;
    private LayoutGrid grid1;
    private WiresShapeToolbox toolbox1;
    private int itemCount = 0;

    public void test(Layer layer) {
        this.layer = layer;
        this.wiresManager = WiresManager.get(layer);

        shape1 = newShape(ColorName.BLUE);
        shape1.setX(50).setY(100);
        buildToolbox1();

        layer.batch();
    }

    private void buildToolbox1() {
        grid1 = new LayoutGrid(BUTTON_PADDING,
                               BUTTON_SIZE,
                               iTowards,
                               iRows,
                               iCols);
        toolbox1 = ToolboxFactory.forWiresShape(shape1)
                .attachTo(layer.getScene().getTopLayer())
                .at(iAt)
                .grid(grid1);
        addItem();
        addItem();
    }

    private void at(Direction direction) {
        toolbox1.at(direction);
    }

    private void towards(Direction direction) {
        toolbox1.grid(grid1.towards(direction));
    }

    private void rows(int rows) {
        toolbox1.grid(grid1.setRows(rows));
    }

    private void cols(int cols) {
        toolbox1.grid(grid1.setCols(cols));
    }

    private void show() {
        toolbox1.show();
    }

    private void hide() {
        toolbox1.hide();
    }

    private void addItem() {
        Rectangle b1 = createButtonNode(ColorName.values()[Random.nextInt(ColorName.values().length)]);
        final ButtonItem item1 = new ButtonItem(b1);
        item1.onClick(new Runnable() {
            @Override
            public void run() {
                GWT.log("BUTTON CLICK!!");
            }
        });
        itemCount++;

        toolbox1.add(item1);
    }

    private void removeItem() {
        toolbox1.iterator().remove();
    }

    private Rectangle createButtonNode(final IColor color) {
        return new Rectangle(BUTTON_SIZE,
                             BUTTON_SIZE)
                .setStrokeWidth(1)
                .setStrokeColor(ColorName.BLACK)
                .setFillColor(color)
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
        return shape;
    }

    public void setButtonsPanel(Panel panel) {

        final VerticalPanel vPanel = new VerticalPanel();
        vPanel.setSpacing(10);

        final HorizontalPanel hPanel1 = new HorizontalPanel();
        hPanel1.setSpacing(5);
        vPanel.add(hPanel1);

        Button showNewButton = new Button("Show");
        showNewButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                show();
            }
        });
        hPanel1.add(showNewButton);

        Button hideNewButton = new Button("Hide");
        hideNewButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
        hPanel1.add(hideNewButton);

        Button addItemButton = new Button("Add");
        addItemButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addItem();
            }
        });
        hPanel1.add(addItemButton);

        Button removeItemButton = new Button("Remove");
        removeItemButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                removeItem();
            }
        });
        hPanel1.add(removeItemButton);

        final HorizontalPanel hPanel2 = new HorizontalPanel();
        hPanel2.setSpacing(5);
        vPanel.add(hPanel2);

        final Label atLabel = new Label("At: ");
        final ListBox atButton = new ListBox();
        for (Direction d : Direction.values()) {
            atButton.addItem(d.name());
        }
        atButton.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                int index = atButton.getSelectedIndex();
                Direction direction = Direction.values()[index];
                at(direction);
            }
        });
        atButton.setSelectedIndex(iAt.ordinal());
        hPanel2.add(atLabel);
        hPanel2.add(atButton);

        final Label towardsLabel = new Label("Towards: ");
        final ListBox towardsButton = new ListBox();
        for (Direction d : Direction.values()) {
            towardsButton.addItem(d.name());
        }
        towardsButton.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                int index = towardsButton.getSelectedIndex();
                Direction direction = Direction.values()[index];
                towards(direction);
            }
        });
        towardsButton.setSelectedIndex(iTowards.ordinal());
        hPanel2.add(towardsLabel);
        hPanel2.add(towardsButton);

        final HorizontalPanel hPanel3 = new HorizontalPanel();
        hPanel3.setSpacing(5);
        vPanel.add(hPanel3);

        final Label rowsLabel = new Label("Rows: ");
        final TextBox rowsBox = new TextBox();
        rowsBox.setValue(String.valueOf(iRows));
        rowsBox.setVisibleLength(3);
        rowsBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                rows(Integer.valueOf(rowsBox.getValue()));
            }
        });
        hPanel3.add(rowsLabel);
        hPanel3.add(rowsBox);

        final Label colsLabel = new Label("Cols: ");
        final TextBox colsBox = new TextBox();
        colsBox.setValue(String.valueOf(iRows));
        colsBox.setVisibleLength(3);
        colsBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                cols(Integer.valueOf(colsBox.getValue()));
            }
        });
        hPanel3.add(colsLabel);
        hPanel3.add(colsBox);

        panel.add(vPanel);
    }
}
