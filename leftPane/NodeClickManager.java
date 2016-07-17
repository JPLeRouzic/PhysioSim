/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leftPane;

import datamodel.CompartmentSBML;
import datamodel.NodeLeaf;
import home.GUIMain;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import parser.sbml.ParameterSBML;
import parser.sbml.ReactionsSBML;
import parser.sbml.SpeciesSBML;
import rightPane.SolverPanel;
import rightPane.MainPanel;

/**
 *
 * @author jplr
 */
public class NodeClickManager extends JPanel {

    public JTextField text;
    GUIMain guiMn;

    public NodeClickManager(GUIMain guiMain) {
        JFrame frameLoc = new JFrame();

        frameLoc.getContentPane().setLayout(new BorderLayout());

        frameLoc.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        text = new JTextField("No compartment is selected");

        frameLoc.add(text);

        guiMn = guiMain;
    }

    // 
    public void displaySBMLCompartment(ShowTree st, JLabel selectedLabel, DefaultMutableTreeNode selectedNode) {
        text.setText(selectedNode.getUserObject().toString());

    }

    public void narrativeEvent(DefaultMutableTreeNode selectedNode, NodeLeaf trois) {
        // first clear the narrative screen

        if (selectedNode.getChildCount() == 0) {
            // if it is a compartment (which is end leaf)
            compNarrative(selectedNode, trois);
        } else {
            pbpkNarrative(selectedNode, trois);
        }
    }

    // It is not a compartment (so it is not end leaves)
    private void pbpkNarrative(DefaultMutableTreeNode selectedNode, NodeLeaf trois) {
        NodeLeaf deux = (NodeLeaf) trois;

        /* display two leaves for each Tissue (== SBML model) selected */
        // arrays to collect parts for narrative
        ArrayList alComp = new ArrayList();
        ArrayList alSpec = new ArrayList();
        ArrayList alReac = new ArrayList();
        ArrayList alPara = new ArrayList();

        /**
         * * compartments **
         */
        // find all compartments that are part of Tissue's selectedNode
        Enumeration compNodes = selectedNode.breadthFirstEnumeration();
        while (compNodes.hasMoreElements()) {
            DefaultMutableTreeNode ichi = (DefaultMutableTreeNode) compNodes.nextElement();
            NodeLeaf ni = (NodeLeaf) (ichi).getUserObject();
            CompartmentSBML san = ni.getCompartment();
            alComp.add(san);
        }
    }

    // It is a compartment (an end leave)
    // FIXME find all parameters that are part of Tissue's selectedNode        
    private void compNarrative(DefaultMutableTreeNode selectedNode, NodeLeaf leaf) {
        // FIXME THIS SHOULD enable modification of the selected compartment/tissue parameters of simulation 
        NodeLeaf deux = (NodeLeaf) leaf;

        /* display two leaves for each Tissue (== SBML model) selected */
        // arrays to collect parts for narrative
        ArrayList alComp = new ArrayList();
        ArrayList alSpec = new ArrayList();
        ArrayList alReac = new ArrayList();
        ArrayList alPara = new ArrayList();

        /**
         * * species **
         */
        // find all species that are part of Tissue's selectedNode
        if (leaf.compartment.modl.getMapOfSpecies() != null) {
            if (!leaf.compartment.modl.getMapOfSpecies().isEmpty()) {
                Collection<SpeciesSBML> speciesList = leaf.compartment.modl.getMapOfSpecies().values();
                Iterator<SpeciesSBML> iter2 = speciesList.iterator();
                while (iter2.hasNext()) {
                    SpeciesSBML species = iter2.next();
                    alSpec.add(species);
                }
            }
        }

        /**
         * * reactions **
         *
         * Here we can change kinetic laws
         *
         */
        // find all reactions that are part of Tissue's selectedNode
        if (leaf.compartment.modl.getMapOfReactions() != null) {
            if (!leaf.compartment.modl.getMapOfReactions().isEmpty()) {
                Collection<ReactionsSBML> reactionsList = leaf.compartment.modl.getMapOfReactions().values();
                Iterator<ReactionsSBML> iter3 = reactionsList.iterator();
                while (iter3.hasNext()) {
                    ReactionsSBML reaction = iter3.next();
                    alReac.add(reaction);
                }
            }
        }

        // create a vector (functionstrings) with all kinetic laws
        Vector functionstrings = new Vector();
        Iterator un = alReac.iterator();
        while (un.hasNext()) {
            ReactionsSBML cinq = (ReactionsSBML) un.next();
            functionstrings.add(cinq.getKineticLaw());
        }

        // Find the initial value for those functions
        // There are two different kind of initial values for the ODE
        // The first is the initial value of the "X" dimension, it is 
        // the initial amount for a species in SBML AS IN:
        //         <species compartment="cytosol" id="ES" initialAmount="0" name="ES"/>
        // The second is the initial value of the "Y" dimension, as the function
        // works with tiny increments over previous value of the function, it has to start somewhere
        // So we have to compute the initial value of the function from the initial values of its variables
        Vector startvector = new Vector();

        // write back function on gui's textArea
        guiMn.mnPanel.funcTextA.setText(functionstrings.toString());
        guiMn.mnPanel.gPanel.setCalcMethod((String) guiMn.mnPanel.methodComboBox.getSelectedItem());
        guiMn.mnPanel.gPanel.setFunction(functionstrings, startvector);
        guiMn.mnPanel.gPanel.setView(250, 200, 30, 200, 10, 100);

        guiMn.mnPanel.checkTabbedPane();
        guiMn.mnPanel.tabbedAction.stateChanged(new ChangeEvent(guiMn.mnPanel.tabbedPane));

        /**
         * * parameters **
         */
        // find all parameters that are part of Tissue's selectedNode
        if (leaf.compartment.modl.getMapOfParameters() != null) {
            if (!leaf.compartment.modl.getMapOfParameters().isEmpty()) {
                Collection<ParameterSBML> paramList = leaf.compartment.modl.getMapOfParameters().values();
                Iterator<ParameterSBML> iter4 = paramList.iterator();
                while (iter4.hasNext()) {
                    ParameterSBML para = iter4.next();
                    alPara.add(para);
                }
            }
        }
    }
}
