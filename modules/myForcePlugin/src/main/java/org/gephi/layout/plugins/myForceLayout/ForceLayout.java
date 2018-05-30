/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gephi.layout.plugins.myForceLayout;

import java.util.ArrayList;
import java.util.List;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.layout.spi.Layout;
import org.gephi.layout.spi.LayoutBuilder;
import org.gephi.layout.spi.LayoutProperty;
/**
 *
 * @author Mian
 */
public class ForceLayout implements Layout{
    //Architecture
    private final LayoutBuilder builder;
    private GraphModel graphModel;
    //Flags
    private boolean executing = false;
    //Properties
    private int areaSize;
    private float speed;

    public ForceLayout(ForceLayoutBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void resetPropertiesValues() {
        areaSize = 1000;
        speed = 1f;
    }

    @Override
    public void initAlgo() {
        executing = true;
    }

    @Override
    public void goAlgo() {
        Graph graph = graphModel.getGraphVisible();
        graph.readLock();
        
        int nodeCount = graph.getNodeCount();
        Node[] nodes = graph.getNodes().toArray();

        int rows = (int) Math.round(Math.sqrt(nodeCount)) + 1;
        int cols = (int) Math.round(Math.sqrt(nodeCount)) + 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols && (i * rows + j) < nodes.length; j++) {
                Node node = nodes[i * rows + j];
                float x = (-areaSize / 2f) + ((float) j / cols) * areaSize;
                float y = (areaSize / 2f) - ((float) i / rows) * areaSize;
                float px = node.x();
                float py = node.y();
                node.setX(px + (x - px) * (speed / 10000f));
                node.setY(py + (y - py) * (speed / 10000f));
//                System.out.println(node.getLabel());
                System.out.println("node   "+node.getAttribute("modularity_class"));
//                System.out.println(node.getAttributeKeys());
            }
        }

        graph.readUnlock();
    }

    @Override
    public void endAlgo() {
        executing = false;
    }

    @Override
    public boolean canAlgo() {
        return executing;
    }

    @Override
    public LayoutProperty[] getProperties() {
        List<LayoutProperty> properties = new ArrayList<LayoutProperty>();
        final String GRIDLAYOUT = "changing";

        try {
            properties.add(LayoutProperty.createProperty(
                    this, Integer.class,
                    "Area size",
                    GRIDLAYOUT,
                    "The area size",
                    "getAreaSize", "setAreaSize"));
            properties.add(LayoutProperty.createProperty(
                    this, Float.class,
                    "Speed",
                    GRIDLAYOUT,
                    "How fast are moving nodes",
                    "getSpeed", "setSpeed"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return properties.toArray(new LayoutProperty[0]);
    }

    @Override
    public LayoutBuilder getBuilder() {
        return builder;
    }

    @Override
    public void setGraphModel(GraphModel gm) {
        this.graphModel = gm;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Integer getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(Integer area) {
        this.areaSize = area;
    }
}
