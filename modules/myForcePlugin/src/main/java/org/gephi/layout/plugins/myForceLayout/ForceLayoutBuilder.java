/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gephi.layout.plugins.myForceLayout;

import javax.swing.Icon;
import javax.swing.JPanel;
import org.gephi.layout.spi.Layout;
import org.gephi.layout.spi.LayoutBuilder;
import org.gephi.layout.spi.LayoutUI;
import org.openide.util.lookup.ServiceProvider;
/**
 *
 * @author Mian
 */
@ServiceProvider(service = LayoutBuilder.class)
public class ForceLayoutBuilder implements LayoutBuilder  {
     @Override
    public String getName() {
        return "My Force Layout";
    }

    @Override
    public LayoutUI getUI() {
        return new LayoutUI() {

            @Override
            public String getDescription() {
                return "";
            }

            @Override
            public Icon getIcon() {
                return null;
            }

            @Override
            public JPanel getSimplePanel(Layout layout) {
                return null;
            }

            @Override
            public int getQualityRank() {
                return -1;
            }

            @Override
            public int getSpeedRank() {
                return -1;
            }
        };
    }

    @Override
    public Layout buildLayout() {
        return new ForceLayout(this);
    }
}
