package org.roger600.lienzo.client;

import com.ait.lienzo.client.core.event.NodeDragEndEvent;
import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragMoveEvent;
import com.ait.lienzo.client.core.event.NodeDragMoveHandler;
import com.ait.lienzo.client.core.event.NodeDragStartEvent;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
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
import org.roger600.lienzo.client.toolboxNew.grid.AutoGrid;
import org.roger600.lienzo.client.toolboxNew.grid.FixedLayoutGrid;
import org.roger600.lienzo.client.toolboxNew.impl2.item.ButtonItem;
import org.roger600.lienzo.client.toolboxNew.impl2.item.DecoratorsFactory;
import org.roger600.lienzo.client.toolboxNew.impl2.item.ItemFactory;
import org.roger600.lienzo.client.toolboxNew.impl2.toolbox.ToolboxFactory;
import org.roger600.lienzo.client.toolboxNew.impl2.toolbox.WiresShapeToolbox;

public class ToolboxTests implements MyLienzoTest,
                                     HasMediators,
                                     HasButtons {

    private static final double BUTTON_SIZE = 14;
    private static final double BUTTON_PADDING = 5;
    private static final int iCols = 2;
    private static final int iRows = 4;
    private static final Direction iAt = Direction.NORTH_EAST;
    private static final Direction iTowards = Direction.SOUTH_EAST;
    private static final Direction iAutoDirection = Direction.SOUTH;

    private Layer layer;
    private WiresManager wiresManager;
    private WiresShape shape1;
    private FixedLayoutGrid grid1;
    private AutoGrid autoGrid1;
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
        grid1 = new FixedLayoutGrid(BUTTON_PADDING,
                                    BUTTON_SIZE,
                                    iTowards,
                                    iRows,
                                    iCols);

        autoGrid1 = new AutoGrid.Builder()
                .withPadding(BUTTON_PADDING)
                .withIconSize(BUTTON_SIZE)
                .towards(iAutoDirection)
                .forShape(shape1)
                .build();

        toolbox1 = ToolboxFactory.forWiresShape(shape1)
                .attachTo(layer.getScene().getTopLayer())
                .at(iAt)
                .grid(grid1);
        addItem();
        addItem();
    }

    private void autoDirection(Direction direction) {
        toolbox1.grid(autoGrid1.direction(direction));
    }

    private void at(Direction direction) {
        toolbox1.at(direction);
    }

    private void towards(Direction direction) {
        toolbox1.grid(grid1.towards(direction));
    }

    private void rows(int rows) {
        toolbox1.grid(grid1.rows(rows));
    }

    private void cols(int cols) {
        toolbox1.grid(grid1.columns(cols));
    }

    private void show() {
        toolbox1.show();
    }

    private void hide() {
        toolbox1.hide();
    }

    private void addItem() {
        Rectangle prim = createButtonNode(ColorName.values()[Random.nextInt(ColorName.values().length)]);

        final ButtonItem item1 =
                ItemFactory.buttonFor(prim)
                        .decorate(DecoratorsFactory.box())
                        .onClick(new NodeMouseClickHandler() {
                            @Override
                            public void onNodeMouseClick(NodeMouseClickEvent event) {
                                GWT.log("BUTTON CLICK!!");
                            }
                        })
                .onDragStart(new NodeDragStartHandler() {
                    @Override
                    public void onNodeDragStart(NodeDragStartEvent event) {
                        GWT.log("BUTTON DRAG START!!");
                    }
                })
                .onDragMove(new NodeDragMoveHandler() {
                    @Override
                    public void onNodeDragMove(NodeDragMoveEvent event) {
                        GWT.log("BUTTON DRAG MOVE!!");

                    }
                })
                .onDragEnd(new NodeDragEndHandler() {
                    @Override
                    public void onNodeDragEnd(NodeDragEndEvent event) {
                        GWT.log("BUTTON DRAG END!!");
                        event.getDragContext().reset();
                    }
                });

        itemCount++;

        toolbox1.add(item1);
    }

    private void removeItem() {
        toolbox1.iterator().remove();
    }

    private void useFixedGrid() {
        toolbox1.grid(grid1);
    }

    private void useAutoGrid() {
        toolbox1.grid(autoGrid1);
    }

    private Rectangle createButtonNode(final IColor color) {
        return new Rectangle(BUTTON_SIZE,
                             BUTTON_SIZE)
                .setStrokeWidth(0)
                .setStrokeColor(ColorName.BLACK)
                .setFillColor(color)
                .setFillAlpha(0.2d);
    }

    private WiresShape newShape(final IColor color) {
        MultiPath path = TestsUtils.rect(new MultiPath().setFillColor(color),
                                         50,
                                         100,
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

        Button useFixedGridButton = new Button("Fixed grid");
        useFixedGridButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                useFixedGrid();
            }
        });
        hPanel1.add(useFixedGridButton);

        Button useAutoGridButton = new Button("Auto grid");
        useAutoGridButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                useAutoGrid();
            }
        });
        hPanel1.add(useAutoGridButton);

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

        final HorizontalPanel hPanel4 = new HorizontalPanel();
        hPanel4.setSpacing(5);
        vPanel.add(hPanel4);

        final Label autoDirectionLabel = new Label("Auto direction: ");
        final ListBox autoDirectionButton = new ListBox();
        for (Direction d : Direction.values()) {
            autoDirectionButton.addItem(d.name());
        }
        autoDirectionButton.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                int index = autoDirectionButton.getSelectedIndex();
                Direction direction = Direction.values()[index];
                autoDirection(direction);
            }
        });
        autoDirectionButton.setSelectedIndex(iAutoDirection.ordinal());
        hPanel4.add(autoDirectionLabel);
        hPanel4.add(autoDirectionButton);

        panel.add(vPanel);
    }
}
