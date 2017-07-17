package org.roger600.lienzo.client;

import com.ait.lienzo.client.core.Attribute;
import com.ait.lienzo.client.core.event.AnimationFrameAttributesChangedBatcher;
import com.ait.lienzo.client.core.event.AttributesChangedEvent;
import com.ait.lienzo.client.core.event.AttributesChangedHandler;
import com.ait.lienzo.client.core.event.IAttributesChangedBatcher;
import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.tooling.common.api.flow.Flows;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Panel;

import static com.ait.lienzo.client.core.AttributeOp.any;

public class LayersTests implements MyLienzoTest,
                                    HasButtons,
                                    NeedsThePanel {

    private static final Flows.BooleanOp XY_OP = any(Attribute.X,
                                                     Attribute.Y);
    private static final Flows.BooleanOp WH_OP = any(Attribute.WIDTH,
                                                     Attribute.HEIGHT);
    private final IAttributesChangedBatcher attributesChangedBatcher = new AnimationFrameAttributesChangedBatcher();

    private LienzoPanel lienzoPanel;
    private Layer layer;
    private Rectangle shape;
    private Group group;
    private Rectangle item1;

    @Override
    public void setButtonsPanel(Panel panel) {

    }

    public void test(Layer layer) {
        this.layer = layer;
        testFromPane();
    }

    private void testFromPane() {
        // Layer layer = lienzoPanel.getScene().getLayer();
        Layer overLayer = lienzoPanel.getScene().getOverLayer();
        Layer topLayer = lienzoPanel.getScene().getTopLayer();
        Layer dragLayer = lienzoPanel.getScene().getViewport().getDragLayer();
        //Layer backgroundLayer = new Layer();
        //lienzoPanel.setBackgroundLayer(backgroundLayer);

        shape = new Rectangle(50,
                              50)
                .setX(0)
                .setY(0)
                .setFillColor(ColorName.RED)
                .setDraggable(true);

        group = new Group()
                .setX(100)
                .setY(100);
        group.add(shape);
        layer.add(group);

        initAttChangedHandler(shape,
                              new Runnable() {
                                  @Override
                                  public void run() {
                                      updateItems();
                                  }
                              });

        item1 = new Rectangle(15,
                              15)
                .setFillColor(ColorName.BLACK)
                .setDraggable(true);

        item1.addNodeMouseClickHandler(new NodeMouseClickHandler() {
            @Override
            public void onNodeMouseClick(NodeMouseClickEvent nodeMouseClickEvent) {
                GWT.log("ITEM #1 CLICK!!");
            }
        });
        topLayer.add(item1);

        updateItems();
    }

    private void updateItems() {
        GWT.log("UPDATE TOOLBOX ITEMS!!");
        Point2D groupLoc = group.getComputedLocation();
        Point2D loc = new Point2D(shape.getX() + shape.getWidth(),
                                  shape.getY());
        item1.setLocation(loc.offset(groupLoc));
        item1.getLayer().batch();
    }

    private void initAttChangedHandler(final IPrimitive node,
                                       final Runnable callback) {
        node.setAttributesChangedBatcher(attributesChangedBatcher);

        final AttributesChangedHandler handler = new AttributesChangedHandler() {
            @Override
            public void onAttributesChanged(AttributesChangedEvent event) {
                GWT.log("HANDLER FIRED -> " + event.toJSONString());

                boolean eval = false;
                if (event.evaluate(XY_OP)) {
                    GWT.log("SHAPE MOVED TO [" + node.getX() + ", "
                                    + node.getY() + "]!!");
                    eval = true;
                }
                if (event.evaluate(WH_OP)) {
                    BoundingBox bb = node.getBoundingBox();
                    GWT.log("SHAPE RESIZED TO [" + bb.getWidth() + ", "
                                    + bb.getHeight() + "]!!");
                    eval = true;
                }
                if (eval) {
                    callback.run();
                }
            }
        };

        // Attribute change handlers.
        node.addAttributesChangedHandler(Attribute.X,
                                         handler);
        node.addAttributesChangedHandler(Attribute.Y,
                                         handler);
        node.addAttributesChangedHandler(Attribute.WIDTH,
                                         handler);
        node.addAttributesChangedHandler(Attribute.HEIGHT,
                                         handler);

        /*Attribute[] allAttrs = getAllAttributes();
        for ( final Attribute a : allAttrs ) {
            m_multi.addAttributesChangedHandler(a, handler);
        }*/

    }

    @Override
    public void setLienzoPanel(LienzoPanel lienzoPanel) {
        this.lienzoPanel = lienzoPanel;
    }
}
