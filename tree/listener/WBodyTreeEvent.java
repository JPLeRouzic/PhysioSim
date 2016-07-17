/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.listener;

import datamodel.NodeLeaf;
import home.GUIMain;
import javax.swing.JLabel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author jplr
 */
public class WBodyTreeEvent {

    GUIMain guiMn;

    public WBodyTreeEvent(GUIMain guiMain) {
        guiMn = guiMain;
    }

    public void buttonClicked(JLabel selectedLabel, DefaultMutableTreeNode selectedNode) {
        /* display Java id of the selected object, on the lower right panel */
        guiMn.scd.displaySBMLCompartment(guiMn.simMain.st, selectedLabel, selectedNode);

        Object trois = selectedNode.getUserObject();
        // restriction on which object can be visualized as a SBML compartment
        if (trois instanceof NodeLeaf) {
         guiMn.scd.narrativeEvent(selectedNode, (NodeLeaf) trois) ;
        }
        
        /* Process a click on ??? */
        else
        {
//            pbpkNarrative(selectedNode, trois) ;
        }
    }
}
