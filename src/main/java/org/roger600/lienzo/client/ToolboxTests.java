package org.roger600.lienzo.client;

import com.ait.lienzo.client.core.event.NodeDragEndEvent;
import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragMoveEvent;
import com.ait.lienzo.client.core.event.NodeDragMoveHandler;
import com.ait.lienzo.client.core.event.NodeDragStartEvent;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.image.PictureLoadedHandler;
import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Picture;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.shape.wires.IContainmentAcceptor;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.Direction;
import com.ait.lienzo.shared.core.types.IColor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.roger600.lienzo.client.resources.LienzoTestsResources;
import org.roger600.lienzo.client.toolboxNew.grid.AutoGrid;
import org.roger600.lienzo.client.toolboxNew.grid.FixedLayoutGrid;
import org.roger600.lienzo.client.toolboxNew.primitive.ButtonGridItem;
import org.roger600.lienzo.client.toolboxNew.primitive.ButtonItem;
import org.roger600.lienzo.client.toolboxNew.primitive.LayerToolbox;
import org.roger600.lienzo.client.toolboxNew.primitive.factory.DecoratorsFactory;
import org.roger600.lienzo.client.toolboxNew.primitive.factory.ItemFactory;
import org.roger600.lienzo.client.toolboxNew.primitive.factory.ToolboxFactory;
import org.roger600.lienzo.client.toolboxNew.primitive.factory.TooltipFactory;
import org.roger600.lienzo.client.toolboxNew.primitive.tooltip.ToolboxTextTooltip;
import org.roger600.lienzo.client.toolboxNew.util.Consumer;
import org.roger600.lienzo.client.toolboxNew.util.Tooltip;

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
    private static final Direction iTooltipDirection = Direction.EAST;

    private Layer layer;
    private WiresManager wiresManager;
    private WiresShape parent;
    private WiresShape shape1;
    private FixedLayoutGrid grid1;
    private AutoGrid autoGridActions;
    private AutoGrid autoGridMorph;
    private AutoGrid autoGridDelete;
    private LayerToolbox toolboxActions;
    private LayerToolbox toolboxMorph;
    private LayerToolbox toolboxDelete;
    private ToolboxTextTooltip tooltipActions;
    private ToolboxTextTooltip tooltipMorph;
    private ToolboxTextTooltip tooltipDelete;
    private int itemCount = 0;
    private Tooltip tooltip;

    public void test(Layer layer) {
        this.layer = layer;
        this.wiresManager = WiresManager.get(layer);

        wiresManager.setContainmentAcceptor(IContainmentAcceptor.ALL);

        /*parent = newShape(ColorName.WHITE,
                          400,
                          300);
        parent.getPath().setStrokeWidth(3).setStrokeColor(ColorName.BLACK);
        parent.setX(150).setY(100);*/

        shape1 = newShape(ColorName.BLUE,

                          50,
                          100);
        shape1.setX(400).setY(100);
        buildToolbox1();

        layer.batch();
    }

    private AutoGrid buildAutoGrid(final Direction d) {
        return new AutoGrid.Builder()
                .withPadding(BUTTON_PADDING)
                .withIconSize(BUTTON_SIZE)
                .towards(d)
                .forShape(shape1)
                .build();
    }

    private ToolboxTextTooltip buildTooltip(final LayerToolbox toolbox) {
        return TooltipFactory.forToolbox(toolbox)
                //.setText("Toolbox tooltip text")
                .withText(new Consumer<Text>() {
                    @Override
                    public void apply(Text textPrim) {
                        textPrim
                                .setFontSize(12)
                                .setFontFamily("Verdana");
                    }
                });
    }

    private void buildToolbox1() {
        grid1 = new FixedLayoutGrid(BUTTON_PADDING,
                                    BUTTON_SIZE,
                                    iTowards,
                                    iRows,
                                    iCols);

        // Actions toolbox.
        autoGridActions = buildAutoGrid(iAutoDirection);
        toolboxActions = ToolboxFactory.forWiresShape(shape1)
                .attachTo(layer.getScene().getTopLayer())
                .at(iAt)
                //.decorate(DecoratorsFactory.box())
                .grid(autoGridActions);
        tooltipActions = buildTooltip(toolboxActions);
        addButtonItem(toolboxActions,
                      getButtonTitle());
        addButtonItem(toolboxActions,
                      getButtonTitle());
        addButtonItem(toolboxActions,
                      LienzoTestsResources.INSTANCE.taskUserComposite().getSafeUri(),
                      "USER",
                      BUTTON_SIZE,
                      BUTTON_SIZE);
        addButtonItem(toolboxActions,
                      LienzoTestsResources.INSTANCE.taskScriptComposite().getSafeUri(),
                      "SCRIPT",
                      BUTTON_SIZE,
                      BUTTON_SIZE);

        if (false) {
            return;
        }

        // Morph toolbox.
        autoGridMorph = buildAutoGrid(Direction.SOUTH_EAST);
        toolboxMorph = ToolboxFactory.forWiresShape(shape1)
                .attachTo(layer.getScene().getTopLayer())
                .at(Direction.SOUTH_WEST)
                .grid(autoGridMorph);
        tooltipMorph = buildTooltip(toolboxMorph);
        new Picture(LienzoTestsResources.INSTANCE.clockO().getSafeUri().asString(),
                    new PictureLoadedHandler() {
                        @Override
                        public void onPictureLoaded(Picture picture) {
                            scalePicture(picture,
                                         BUTTON_SIZE,
                                         BUTTON_SIZE);
                            Group picGroup = new Group().add(picture);
                            final ButtonGridItem dropDownItem = ItemFactory.dropDownFor(picGroup);
                            final FixedLayoutGrid dropDownItemGrid = new FixedLayoutGrid(BUTTON_PADDING,
                                                                                         BUTTON_SIZE,
                                                                                         Direction.SOUTH_EAST,
                                                                                         1,
                                                                                         7);
                            final ButtonItem dropDownItem1 = createButtonItem(getButtonTitle());
                            final ButtonItem dropDownItem2 = createButtonItem(getButtonTitle());
                            final ButtonItem dropDownItem3 = createButtonItem(getButtonTitle());
                            final ButtonItem dropDownItem4 = createButtonItem(getButtonTitle());
                            final ButtonItem dropDownItem5 = createButtonItem(getButtonTitle());
                            final ButtonItem dropDownItem6 = createButtonItem(getButtonTitle());
                            dropDownItem
                                    .grid(dropDownItemGrid)
                                    .onClick(new NodeMouseClickHandler() {
                                        @Override
                                        public void onNodeMouseClick(NodeMouseClickEvent event) {
                                            GWT.log("MORPHING ITEM GRID CLICK!!");
                                        }
                                    })
                                    .add(dropDownItem1,
                                         dropDownItem2,
                                         dropDownItem3,
                                         dropDownItem4,
                                         dropDownItem5,
                                         dropDownItem6)
                                    .decorate(DecoratorsFactory.box()
                                                      .setFillColor(ColorName.WHITE.getColorString()))
                                    .decorateGrid(DecoratorsFactory.box()
                                                          .setFillColor(ColorName.WHITE.getColorString())
                                                          .setPadding(15));
                            toolboxMorph.add(dropDownItem);
                        }
                    });

        // Delete toolbox.
        autoGridDelete = buildAutoGrid(Direction.SOUTH_WEST);
        toolboxDelete = ToolboxFactory.forWiresShape(shape1)
                .attachTo(layer.getScene().getTopLayer())
                .at(Direction.NORTH_WEST)
                .grid(autoGridDelete);
        tooltipDelete = buildTooltip(toolboxDelete);
        addButtonItem(toolboxDelete,
                      "Delete");





       /* final Rectangle r =
                new Rectangle(50,
                              50)
                        .setX(400)
                        .setY(150)
                        .setFillColor(ColorName.BLACK);
        layer.add(r);
        testTooltip(new Point2D(700,
                                150));*/
    }

    private void testTooltip(final Point2D location) {
        tooltip = new Tooltip();
        layer.add(tooltip.asPrimitive());

        tooltip
                .setLocation(location)
                .setDirection(iTooltipDirection)
                .withText(new Consumer<Text>() {
                    @Override
                    public void apply(Text text) {
                        text
                                .setFontSize(14)
                                .setFontFamily("Verdana")
                                .setStrokeWidth(1)
                                .setText("Un Roger");
                    }
                })
                .show()
                .asPrimitive()
                .setDraggable(true);
    }

    private void tooltipDirection(Direction direction) {
        tooltip.setDirection(direction);
        layer.batch();
    }

    private void autoDirection(Direction direction) {
        toolboxActions.grid(autoGridActions.direction(direction));
    }

    private void at(Direction direction) {
        toolboxActions.at(direction);
    }

    private void towards(Direction direction) {
        toolboxActions.grid(grid1.towards(direction));
    }

    private void rows(int rows) {
        toolboxActions.grid(grid1.rows(rows));
    }

    private void cols(int cols) {
        toolboxActions.grid(grid1.columns(cols));
    }

    private void show() {
        if (null != toolboxActions) {
            toolboxActions.show();
        }
        if (null != toolboxMorph) {
            toolboxMorph.show();
        }
        if (null != toolboxDelete) {
            toolboxDelete.show();
        }
    }

    private void hide() {
        if (null != toolboxActions) {
            toolboxActions.hide();
        }
        if (null != toolboxMorph) {
            toolboxMorph.hide();
        }
        if (null != toolboxDelete) {
            toolboxDelete.hide();
        }
    }

    private ButtonItem createButtonItem(String title) {
        Shape<?> prim = createButtonNode(ColorName.values()[Random.nextInt(ColorName.values().length)]);
        Group bGroup = new Group()
                .add(prim);
        return createButtonItem(bGroup,
                                title);
    }

    private String getButtonTitle() {
        return "Button" + itemCount;
    }

    private ButtonItem createButtonItem(Group bGroup,
                                        String title) {
        final ButtonItem item1 =
                ItemFactory.buttonFor(bGroup)
                        .decorate(DecoratorsFactory.box())
                        .tooltip(tooltipActions.createIem(title))
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
        return item1;
    }

    private void addButtonItem(final LayerToolbox toolbox,
                               final Group bGroup,
                               final String title) {
        final ButtonItem item1 = createButtonItem(bGroup,
                                                  title);
        toolbox.add(item1);
    }

    private void addButtonItem(final LayerToolbox toolbox,
                               final SafeUri uri,
                               final String title,
                               final double w,
                               final double h) {
        createButtonItem(uri,
                         title,
                         w,
                         h,
                         new Consumer<ButtonItem>() {
                             @Override
                             public void apply(ButtonItem item) {
                                 toolbox.add(item);
                             }
                         });
    }

    private void createButtonItem(final SafeUri uri,
                                  final String title,
                                  final double w,
                                  final double h,
                                  final Consumer<ButtonItem> itemConsumer) {
        new Picture(uri.asString(),
                    new PictureLoadedHandler() {
                        @Override
                        public void onPictureLoaded(Picture picture) {
                            scalePicture(picture,
                                         w,
                                         h);
                            final ButtonItem picItem =
                                    createButtonItem(new Group().add(picture),
                                                     title);
                            itemConsumer.apply(picItem);
                        }
                    });
    }

    private void addButtonItem(final LayerToolbox toolbox,
                               final String title) {
        final ButtonItem item1 = createButtonItem(title);
        toolbox.add(item1);
    }

    private void addDropDownItem(String title) {

        final double radius = BUTTON_SIZE / 2;
        final Circle circle = new Circle(radius)
                .setX(radius)
                .setY(radius)
                .setStrokeWidth(0)
                .setStrokeColor(ColorName.BLACK)
                .setFillColor(ColorName.GREEN)
                .setFillAlpha(0.8d);

        new Picture(LienzoTestsResources.INSTANCE.taskUserComposite().getSafeUri().asString(),
                    new PictureLoadedHandler() {
                        @Override
                        public void onPictureLoaded(Picture picture) {
                            scalePicture(picture,
                                         BUTTON_SIZE,
                                         BUTTON_SIZE);

                            final ButtonGridItem item =
                                    ItemFactory.dropDownFor(new Group().add(picture));

                            final FixedLayoutGrid grid = new FixedLayoutGrid(BUTTON_PADDING,
                                                                             BUTTON_SIZE,
                                                                             iTowards,
                                                                             iRows,
                                                                             iCols);

                            final ButtonItem item1 = createButtonItem(getButtonTitle());
                            final ButtonItem item2 = createButtonItem(getButtonTitle());
                            final ButtonItem item3 = createButtonItem(getButtonTitle());
                            final ButtonItem item4 = createButtonItem(getButtonTitle());
                            final ButtonItem item5 = createButtonItem(getButtonTitle());
                            final ButtonItem item6 = createButtonItem(getButtonTitle());

                            item
                                    .grid(grid)
                                    .onClick(new NodeMouseClickHandler() {
                                        @Override
                                        public void onNodeMouseClick(NodeMouseClickEvent event) {
                                            GWT.log("BUTTON ITEM GRID CLICK!!");
                                        }
                                    })
                                    .add(item1,
                                         item2,
                                         item3,
                                         item4,
                                         item5,
                                         item6)
                                    .decorate(DecoratorsFactory.box());

                            itemCount++;

                            toolboxActions.add(item);
                        }
                    });
    }

    private void removeItem() {
        toolboxActions.iterator().remove();
    }

    private void useFixedGrid() {
        toolboxActions.grid(grid1);
    }

    private void useAutoGrid() {
        toolboxActions.grid(autoGridActions);
    }

    private Rectangle createButtonNode(final IColor color) {
        return new Rectangle(BUTTON_SIZE,
                             BUTTON_SIZE)
                .setStrokeWidth(0)
                .setStrokeColor(ColorName.BLACK)
                .setFillColor(color)
                .setFillAlpha(0.8d);
    }

    private WiresShape newShape(final IColor color,
                                final double width,
                                final double height) {
        MultiPath path = TestsUtils.rect(new MultiPath().setFillColor(color),
                                         width,
                                         height,
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

        com.google.gwt.user.client.ui.Button showNewButton = new com.google.gwt.user.client.ui.Button("Show");
        showNewButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                show();
            }
        });
        hPanel1.add(showNewButton);

        com.google.gwt.user.client.ui.Button hideNewButton = new com.google.gwt.user.client.ui.Button("Hide");
        hideNewButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
        hPanel1.add(hideNewButton);

        com.google.gwt.user.client.ui.Button addItemButton = new com.google.gwt.user.client.ui.Button("Add button");
        addItemButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addButtonItem(toolboxActions,
                              getButtonTitle());
            }
        });
        hPanel1.add(addItemButton);

        com.google.gwt.user.client.ui.Button addItemDropDown = new com.google.gwt.user.client.ui.Button("Add drop-down");
        addItemDropDown.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addDropDownItem("DropDown" + getButtonTitle());
            }
        });
        hPanel1.add(addItemDropDown);

        com.google.gwt.user.client.ui.Button removeItemButton = new com.google.gwt.user.client.ui.Button("Remove");
        removeItemButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                removeItem();
            }
        });
        hPanel1.add(removeItemButton);

        com.google.gwt.user.client.ui.Button useFixedGridButton = new com.google.gwt.user.client.ui.Button("Fixed grid");
        useFixedGridButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                useFixedGrid();
            }
        });
        hPanel1.add(useFixedGridButton);

        com.google.gwt.user.client.ui.Button useAutoGridButton = new com.google.gwt.user.client.ui.Button("Auto grid");
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

        // Tooltip.
        final Label tooltipDirectionLabel = new Label("Tooltip direction: ");
        final ListBox tooltipDirectionButton = new ListBox();
        tooltipDirectionButton.addItem(Direction.NORTH.name());
        tooltipDirectionButton.addItem(Direction.SOUTH.name());
        tooltipDirectionButton.addItem(Direction.EAST.name());
        tooltipDirectionButton.addItem(Direction.WEST.name());
        tooltipDirectionButton.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                int index = tooltipDirectionButton.getSelectedIndex();
                Direction direction = Direction.values()[index];
                tooltipDirection(direction);
            }
        });
        tooltipDirectionButton.setSelectedIndex(iTooltipDirection.ordinal());
        // hPanel4.add(tooltipDirectionLabel);
        // hPanel4.add(tooltipDirectionButton);

        panel.add(vPanel);
    }

    private static void scalePicture(final Picture picture,
                                     final double width,
                                     final double height) {
        final BoundingBox bb = picture.getBoundingBox();
        final double[] scale = getScaleFactor(bb.getWidth(),
                                              bb.getHeight(),
                                              width,
                                              height);
        picture.setScale(scale[0],
                         scale[1]);
    }

    private static double[] getScaleFactor(final double width,
                                           final double height,
                                           final double targetWidth,
                                           final double targetHeight) {
        return new double[]{
                width > 0 ? targetWidth / width : 1,
                height > 0 ? targetHeight / height : 1};
    }
}
