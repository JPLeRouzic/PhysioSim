/**
 * JTree basic tutorial and example,
 *
 * several copyrights: Wayan Saryada, wwww.codejava.net
 *
 */
package leftPane;

import tree.listener.WBodyTreeModelListener;
import datamodel.CompartmentSBML;
import datamodel.HostModel;
import datamodel.HumanBody;
import datamodel.Tissue;
import datamodel.NodeLeaf;
import datamodel.PhysioSystem;
import home.GUIMain;
import home.MenuAction.DoNewInsertOpenModel;
import home.MenuDoAction;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import tree.listener.WBodyTreeEvent;
import utility.CollToArrayList;

public class ShowTree extends JComponent {

    /**
     * Build an empty model at start
     *
     * @param guiMn
     * @throws HeadlessException
     */
    public ShowTree(GUIMain guiMn) throws HeadlessException {
        setSize(200, 400);

        guiMn.simMain.rootNode = new DefaultMutableTreeNode("Physiology systems");
        guiMn.simMain.treeModel = new DefaultTreeModel(guiMn.simMain.rootNode);
        guiMn.simMain.treeModel.addTreeModelListener(new WBodyTreeModelListener());

        guiMn.myTree = new ConnectionJTree(guiMn.simMain.treeModel);

        guiMn.myTree.setEditable(true);
        guiMn.myTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        guiMn.myTree.setCellRenderer(new CountryTreeCellRenderer());

        // even rootNode could be foldable
        guiMn.myTree.setShowsRootHandles(true);

        // make it possible to select a insideLeaf
        JLabel selectedLabel = new JLabel();
        add(selectedLabel, BorderLayout.SOUTH);

        /*
         * this call the routine managing clicks on tree's elements
         */
        guiMn.myTree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) guiMn.myTree.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    // A button has been clicked
                    WBodyTreeEvent wbte = new WBodyTreeEvent(guiMn);
                    wbte.buttonClicked(selectedLabel, selectedNode);
                }
            }

        });

    }

    private TreePath getPath(DefaultMutableTreeNode treeNode) {
        ArrayList<Object> nodes = new ArrayList<>();
        if (treeNode != null) {
            nodes.add(treeNode);
            treeNode = (DefaultMutableTreeNode) treeNode.getParent();
            while (treeNode != null) {
                nodes.add(0, treeNode);
                treeNode = (DefaultMutableTreeNode) treeNode.getParent();
            }
        }

        return nodes.isEmpty() ? null : new TreePath(nodes.toArray());
    }

    /**
     * Add child to the currently selected node.
     *
     * @param guiMn
     * @param child
     * @return
     */
    public DefaultMutableTreeNode addObject(GUIMain guiMn, Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = guiMn.myTree.getSelectionPath();

        if (parentPath == null) {
            parentNode = guiMn.simMain.rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
        }

        return addObject(guiMn, parentNode, child, true);
    }

    /**
     * Add child to the parent 
     * 
     * @param guiMn
     * @param parent
     * @param child
     * @param shouldBeVisible
     * @return 
     */
    public DefaultMutableTreeNode addObject(GUIMain guiMn, DefaultMutableTreeNode parent,
            Object child,
            boolean shouldBeVisible) {
        
        DefaultMutableTreeNode childNode
                = new DefaultMutableTreeNode(child);

        if(child instanceof DefaultMutableTreeNode)
        {
        parent.add((MutableTreeNode) child);
        return childNode;
        }
        
        if (parent == null) {
            parent = guiMn.simMain.rootNode;
        }

        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
        guiMn.simMain.treeModel.insertNodeInto(childNode, parent,
                parent.getChildCount());

        //Make sure the user can see the lovely new node.
        if (shouldBeVisible) {
            guiMn.myTree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }

    public void removeObject(
            GUIMain guiMn,
            DefaultMutableTreeNode objTree,
            boolean shouldBeVisible
    ) {

        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
        guiMn.simMain.treeModel.removeNodeFromParent(objTree);

        // Make sure the user can see the lovely new node.
        guiMn.myTree.scrollPathToVisible(new TreePath(guiMn.simMain.rootNode.getPath()));
    }

    public void removeAllObjects(
            GUIMain guiMn,
            boolean shouldBeVisible
    ) {
        DefaultMutableTreeNode firstChild, lastChild;
        boolean un = true;

        while (un == true) {
            firstChild = guiMn.simMain.rootNode.getFirstLeaf();
            if (guiMn.simMain.rootNode.getDepth() == 0) {
                break;
            }
            lastChild = firstChild;

            //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
            guiMn.simMain.treeModel.removeNodeFromParent(lastChild);
        }

        // Make sure the user can see the lovely new node.
        guiMn.myTree.scrollPathToVisible(new TreePath(guiMn.simMain.rootNode.getPath()));
    }

    /**
     * This adds nodes on JTree
     *
     * @param guiMn
     * @param menu
     * @param doNewInsertOpenModel
     */
    public void drawTree(GUIMain guiMn, MenuDoAction menu, DoNewInsertOpenModel doNewInsertOpenModel) {
        DefaultMutableTreeNode tissueBranch;
        HumanBody body = null;

        if (guiMn.simMain.solver == null) {
            System.err.println("The model files are not correct\n");
        }
        HostModel host = guiMn.simMain.solver.getHostModelToSolve().getHostModel();
        body = host.getPlacentalia();
        Map<Tissue, DefaultMutableTreeNode> ancestor = new HashMap<>();
        if (body.getNumOrganSystems() > 0) {
            Iterator<PhysioSystem> iter = body.getOrganSystems().iterator();

            // loop over PhysioSystems (usually there is only one)
            while (iter.hasNext()) {
                PhysioSystem organsys = iter.next();
                NodeLeaf organSystem = new NodeLeaf(organsys, "./data/images/iconOrganSystem.gif");
                DefaultMutableTreeNode organBranch = guiMn.simMain.st.addObject(guiMn, organSystem);
                ArrayList<Tissue> tissues = organsys.tissues;
                Iterator<Tissue> tissueIterator = tissues.iterator();

                // loop over Tissues in this PhysioSystem
                while (tissueIterator.hasNext()) {
                    NodeLeaf leaf = null;

                    Tissue tiss = tissueIterator.next();
                    if (tiss.getName() == null) {
                        System.out.println("Error null Tissue");
                    }
                    NodeLeaf leafTissue = new NodeLeaf(tiss, "./data/images/iconTissue.gif");
                    tissueBranch = guiMn.simMain.st.addObject(guiMn, organBranch, leafTissue, true);
                    ancestor.put(tiss, tissueBranch);
                    ArrayList<CompartmentSBML> comps = tiss.getInternVolumes();
                    Iterator<CompartmentSBML> compIterator = comps.iterator();

                    // loop over compartments in this Tissues
                    while (compIterator.hasNext()) {
                        CompartmentSBML compa = compIterator.next();
                        leaf = processComp(guiMn, compa, tissueBranch);
                    }
                    // Post processing for "outside" field
                    ArrayList<CompartmentSBML> compbis = tiss.getInternVolumes();
                    Iterator<CompartmentSBML> postIterator = compbis.iterator();
                    // loop over compartments in this Tissue
                    CompartmentSBML compInside;
                    while (postIterator.hasNext()) {
                        compInside = postIterator.next();
                        if(compInside == null)
                            {
                                System.out.println("No id in model: " + compInside.getName());
                            }
                        postProcessComp(guiMn, tiss, tissueBranch, compInside);
                    }
                    // now there are still compartments in root that are at the wrong place
                } // end of Tissue loop
            } // end of Physiological systems loop
        }
        // when a tissue is embedded in another tissue    
        careForPartOf(guiMn, ancestor);
    }

    /**
     * when a tissue is embedded in another tissue
     *
     * @param guiMn
     * @param ancestor
     */
    public void careForPartOf(GUIMain guiMn, Map<Tissue, DefaultMutableTreeNode> ancestor) {
        CollToArrayList convert = new CollToArrayList();
        Tissue tiss = null;
        ArrayList<Tissue> tissArray = convert.treeToTissue(ancestor);
        Iterator<Tissue> iter2 = tissArray.iterator();
        while (iter2.hasNext()) {
            tiss = iter2.next();
            NodeLeaf leaf = new NodeLeaf(tiss, "./data/images/iconTissue.gif");
            if (tiss.partOf != null) {
                DefaultMutableTreeNode tissTree = null;

                // first add the tissue
                DefaultMutableTreeNode tree = ancestor.get(tiss);
                guiMn.simMain.st.removeObject(guiMn, tree, true);
                DefaultMutableTreeNode otherTissueTree = ancestor.get(tiss.partOf);
                DefaultMutableTreeNode newParent = (DefaultMutableTreeNode) otherTissueTree;
                tissTree = guiMn.simMain.st.addObject(guiMn, newParent, leaf, true);

                // now add compartments
                ArrayList<CompartmentSBML> listComps = tiss.getInternVolumes();
                // loop over compartments in this Tissues
                Iterator<CompartmentSBML> compIterator = listComps.iterator();
                while (compIterator.hasNext()) {
                    CompartmentSBML comp = compIterator.next();
                    if (comp == null) {
                        System.out.println("error null compartment 2");
                    }
                    leaf = new NodeLeaf(tiss, "./data/images/compartment.gif");
                    leaf.setName(comp.getName());
                    leaf.setCompartment(comp);
                    if (comp.outside == null) {
                        DefaultMutableTreeNode compBranch = guiMn.simMain.st.addObject(guiMn, tissTree, leaf, true);
//                        System.out.println("add leaf (careForPartOf): " + leaf.getName() + " to tissue " + tissTree);
                    }
                }

            }
        }
    }

    /**
     * Display a compartment (with "outside" == null) on whole tree
     *
     * @param guiMn
     * @param tiss
     * @param compIterator
     * @param tissueBranch
     * @param leaf
     */
    private NodeLeaf processComp(GUIMain guiMn, CompartmentSBML comp, DefaultMutableTreeNode tissueBranch) {
        NodeLeaf leaf;
        if (comp == null) {
            // FIXME System.out.println("error null compartment");
        }
        Tissue tiss = new Tissue();
        leaf = new NodeLeaf(tiss, "./data/images/compartment.gif");
        leaf.setName(comp.getName()); // NAME, NOT IDENTITY
        leaf.setCompartment(comp);
        // create compartment's node on wholebody tree
        DefaultMutableTreeNode compBranch = guiMn.simMain.st.addObject(
                guiMn, tissueBranch, leaf, true);
//        System.out.println("add leaf (processComp): " + leaf.getName() + " to tissue " + tissueBranch);
        return leaf;
    }

    /**
     * Loop over compartments in this Tissue, to display compartments with
     * "outside" field != null
     *
     * @param guiMn
     * @param tiss
     * @param tissueBranch
     * @param insideComp
     * @param rightToAdd
     */
    private void postProcessComp(GUIMain guiMn, Tissue tiss,
            DefaultMutableTreeNode tissueBranch, CompartmentSBML insideComp
    ) {
//        System.out.println("\nentering postProcessComp, insideComp id: " + insideComp.getIdentity());
        if (insideComp.getIdentity().contentEquals("C_c")) {
            int y = 0;
        }
        NodeLeaf insideLeaf;
        if (insideComp.outside != null) {
            CompartmentSBML outsideComp;

            outsideComp = findOutsideComp(tiss, insideComp);
                if (outsideComp == null) {
                    System.out.println("\noutsideCompDMT call to processComp: " + insideComp.getIdentity());
                    return ;
                }

            DefaultMutableTreeNode outsideCompDMT = null;
            while (outsideCompDMT == null) {
                outsideCompDMT = compToTree(tissueBranch, outsideComp);
                if (outsideCompDMT == null) {
//                    System.out.println("\noutsideCompDMT call to processComp: " + insideComp.getIdentity());
                    processComp(guiMn, outsideComp, tissueBranch);
                }
            }
            
            String outsideCompId = outsideComp.getIdentity();
            if (insideComp.outside.contentEquals(outsideCompId)) { // FIXME 
                // we found the compartment pointer
                insideLeaf = new NodeLeaf(tiss, "./data/images/compartment.gif");
                String namu = insideComp.getIdentity();
                insideLeaf.setName(namu);
                insideLeaf.setCompartment(insideComp);

                // does this object already exist, if so attach its children to this object parent
                DefaultMutableTreeNode pathExistLeaf = guiMn.simMain.st.contains(tissueBranch, insideLeaf);
                if (pathExistLeaf == null) {
                    guiMn.simMain.st.addObject(guiMn, outsideCompDMT, insideLeaf, true);
//                    System.out.println("add insideLeaf 3: " + insideLeaf + " to outsideCompDMT; " + outsideCompDMT);
                    return;
                } else {
                    // this object already exists, remove it from its old position
                    guiMn.simMain.st.removeObject(guiMn, pathExistLeaf, true);
                    
                    // attach its children to this object parent
                    guiMn.simMain.st.addObject(guiMn, outsideCompDMT, pathExistLeaf, true);
//                    System.out.println("add pathExistLeaf to outsideCompDMT; " + outsideCompDMT);
                    // FIXME remove old pathExistLeaf
                    return;
                }
            } else {
//                System.out.println("leaving 1");
            }
        } else {
//            System.out.println("leaving 2");
        }

        return;
    }

        private CompartmentSBML findOutsideComp(Tissue tiss, CompartmentSBML compInside) {
        // find the tree node of the "outside" compartment
        // by looping over all nodes in Tissue
        if(compInside.outside == null)
        {
            return null ;
        }
        
        ArrayList<CompartmentSBML> children = tiss.getInternVolumes();

        Iterator<CompartmentSBML> ishtar = children.iterator();

        while (ishtar.hasNext()) {
            CompartmentSBML leafComp = null;

            leafComp = ishtar.next();
            // Post processing for "outside" field
            String compId = leafComp.getIdentity();
            if (compInside.outside.contentEquals(compId)) {
                // we found it
                return leafComp;
            }
        }
        // we found nothing
        return null;
    }

    private DefaultMutableTreeNode compToTree(DefaultMutableTreeNode tissueBranch, CompartmentSBML compInside) {

        DefaultMutableTreeNode deux = null;
        // find the tree node of the "outside" compartment
        Enumeration children = tissueBranch.children();
        while (children.hasMoreElements()) {
            DefaultMutableTreeNode un = (DefaultMutableTreeNode) children.nextElement();

            // as we cannot get a true enumeration of *all* nodes
            DefaultMutableTreeNode six = compToTree(un, compInside);
            if (six != null) {
                return six;
            }

            CompartmentSBML leafComp = null;

            // Post processing for "outside" field
            Object unUObj = un.getUserObject();
            if (unUObj instanceof CompartmentSBML) {
                leafComp = (CompartmentSBML) un.getUserObject();
                deux = un;
            } else if (unUObj instanceof NodeLeaf) {
                NodeLeaf leaf = (NodeLeaf) un.getUserObject();
                leafComp = (CompartmentSBML) leaf.getCompartment();
                deux = un;
            } else {
                return null;
            }

            String compId = leafComp.getIdentity();
            if(compId == null)
            {
                                System.out.println("No id in model: " + leafComp.getName());
            }
            if(compInside == null)
            {
                                System.out.println("No id in model: " + compInside.getName());
            }
            if (compInside.getIdentity().contentEquals(compId)) {
                // we found the compartment pointer
                return deux;
            }
        }
        // we didn't find a tree, because there is none at the moment
        return null;
    }

    /**
     * Check if this compartment is already in the tree
     * 
     * @param tissueBranch
     * @param insideLeaf
     * @return 
     */
    private DefaultMutableTreeNode contains(DefaultMutableTreeNode tissueBranch, NodeLeaf insideLeaf) {

        DefaultMutableTreeNode deux = null;
        // find the tree node of the "outside" compartment
        Enumeration children = tissueBranch.children();
        while (children.hasMoreElements()) {
            DefaultMutableTreeNode un = (DefaultMutableTreeNode) children.nextElement();
//            if (un.isLeaf()) {
                String namu = ((NodeLeaf) un.getUserObject()).getCompartment().getIdentity();
                if (insideLeaf.getName().contentEquals(namu)) {
                    int r = 0;
                    return un;
                }
//            }
        }
        return null;
    }
}
