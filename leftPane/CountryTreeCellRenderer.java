/*
* JTree uses different icons to represent leaf node and nodes with children. 
* What if we need to provide our own icons for this purpose? It is very much possible. 
* We need to make use of the renderer to do this.
* JTree delegates the display of its items to Renderers. 
* By default, a renderer is automatically created for a JTree to display all its items. 
* The rendering is represented by an interface called TreeCellRenderer. 
* The Swing API provides a default implementation of this interface known as DefaultTreeCellRenderer. 
*
*  copyright: Wayan Saryada 
*/

package leftPane;

import datamodel.NodeLeaf;
import java.awt.Component;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

    public class CountryTreeCellRenderer implements TreeCellRenderer {

        private final JLabel label;

        CountryTreeCellRenderer() {
            label = new JLabel();
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                boolean leafb, int row, boolean hasFocus) {
            Object o = ((DefaultMutableTreeNode) value).getUserObject();
            if (o instanceof NodeLeaf) {
                NodeLeaf leaf = (NodeLeaf) o;

                File imageUrl = new File(leaf.getFlagIcon());

                if (imageUrl != null) {
                    label.setIcon(new ImageIcon(leaf.getFlagIcon()));
                }
                String txt = leaf.getName() ;
                if(leaf.getCompartment() != null)
                {
                    txt = txt + ", " + leaf.getCompartment().getIdentity() ;
                }
                label.setText(txt);
            } else {
                label.setIcon(null);
                label.setText("" + value);
            }
            return label;
        }
    }