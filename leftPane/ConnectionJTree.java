/*      
 * A Connection is a pipe, surface or injection.
 * 
 * Connection end points are to CompartmentSBML
 *
 */
package leftPane;

import datamodel.CompartmentSBML;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.Collections;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.Enumeration;
import datamodel.perturbation.Injection;
import datamodel.FluidPipe;
import datamodel.Connection;
import datamodel.NodeLeaf;
import home.GUIMain;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.tree.TreePath;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class ConnectionJTree extends JTree {

    private final int xSpacing;
    private int pathMaxX;
    // Vector class as ArrayList has concurrency problems
    private final Vector<TreeConnection> treeConnections;
    public JScrollPane scrollPane2;

    public ConnectionJTree() {
        this.xSpacing = 10;
        this.pathMaxX = 1;
        this.setEditable(false);
        this.setShowsRootHandles(true);
        this.treeConnections = new Vector<>();
    }

    public ConnectionJTree(DefaultTreeModel dtm) {
        super(dtm);
        this.xSpacing = 10;
        this.pathMaxX = 1;
        this.setEditable(false);
        this.setShowsRootHandles(true);
        this.treeConnections = new Vector<>();
    }

    /**
     * "theConnections" the parameter, is a list of pipes, surfaces, injections.
     * Each object in this list may (or may not) be also a connection object.
     * The algorithm traverses each node of the DefaultMutableTreeNode to find
     * if it is mentioned as a "from" or a "to" connection end point in the
     * connection object.
     *
     * Connection end points are to CompartmentSBML
     *
     * As a result of the call to setConnectionData(), this.treeConnections if
     * filled in with many TreeConnection
     *
     * @param guiMn
     * @param theConnections
     */
    public void setConnectionData(GUIMain guiMn, final ArrayList theConnections) {
        this.updatePathMaxX();
        this.treeConnections.clear();
        if (theConnections != null) {
            for (final Object obj : theConnections) {
                // several classes extend Connection in order to do not have long if chains here.
                if (obj instanceof Connection) {
                    boolean cont = buildTree(guiMn, obj);
                    if (cont == true) {
                        continue;
                    }
                } else if (obj instanceof Injection) {
                    boolean cont = buildTree(guiMn, obj);
                    if (cont == true) {
                        continue;
                    }
                }
            }
        }
    }

    /**
     * for each connection "obj" find the "TreePath" that corresponds
     *
     * @param guiMn
     * @param obj
     * @return
     */
    boolean buildTree(GUIMain guiMn, Object obj) {
        DefaultMutableTreeNode deux;
        TreePath[] paths = null;
        final Connection c = (Connection) obj;
        final CompartmentSBML fromSpace = c.getFromVolume();
        final CompartmentSBML toSpace = c.getToVolume();

        if ((fromSpace == null) && (toSpace == null)) {
            return true;
        }
        TreePath fromPath = null;
        TreePath toPath = null;

        // Compare each tree node with the fromSpace and toSpace
        // to find the tree node which is about those Spaces
        final DefaultMutableTreeNode root = guiMn.simMain.rootNode;
        final Enumeration enumeration = root.breadthFirstEnumeration();
        while (enumeration.hasMoreElements()) {
            // browse all subtrees in rootNode
            final DefaultMutableTreeNode aNode = (DefaultMutableTreeNode) enumeration.nextElement();
            Object userObj = aNode.getUserObject();
            Enumeration un = aNode.depthFirstEnumeration();

            // process userobject that is a NodeLeaf
            if (userObj instanceof NodeLeaf) {
                while (un.hasMoreElements()) {
                    paths = loopTruLeaves(un, fromSpace, toSpace);
                    if (fromPath == null) {
                        fromPath = paths[0];
                    }
                    if (toPath == null) {
                        toPath = paths[1];
                    }
                    // While one of "fromPath" or "toPath" is not found, cycle through the tree
                    if ((fromPath != null) && (toPath != null)) {
                        break;
                    }
                }
            }

            // While one of "fromPath" or "toPath" is not found, cycle through the tree
            if ((fromPath == null) || (toPath == null)) {
                continue;
            }
            // now create the TreeConnection from all that stuff
            final TreeConnection newConn = new TreeConnection(guiMn, this);
            newConn.startPath = fromPath;
            newConn.endPath = toPath;
            newConn.selected = false;
            newConn.userObj = c;
            if (obj instanceof FluidPipe) {
                newConn.directional = true;
            }
            this.treeConnections.add(newConn);
            return false;
        }
        return false;
    }

    private TreePath[] loopTruLeaves(
            Enumeration un, CompartmentSBML fromSpace, CompartmentSBML toSpace
    ) {
        DefaultMutableTreeNode deux = (DefaultMutableTreeNode) un.nextElement();
        TreePath[] paths = {null, null};
        TreePath fromPath = null, toPath = null;
        NodeLeaf leaf;
        if (deux.getUserObject() instanceof NodeLeaf) {
            leaf = (NodeLeaf) deux.getUserObject();
        if(fromSpace != null)  {
            fromPath = fromPath(deux,
                    leaf,
                    fromSpace);
        }
        if (toSpace != null) {
            toPath = toPath(deux,
                    leaf,
                    toSpace);
        }
        } else {
            int r = 0;
        }
        paths[0] = fromPath;
        paths[1] = toPath;
        return paths;
    }

    private TreePath fromPath(DefaultMutableTreeNode bNode,
            NodeLeaf leaf,
            CompartmentSBML fromSpace
    ) {
        return findTreePath(bNode, leaf, fromSpace);
    }

    private TreePath toPath(DefaultMutableTreeNode bNode,
            NodeLeaf leaf,
            CompartmentSBML toSpace
    ) {
        return findTreePath(bNode, leaf, toSpace);
    }

    /**
     * Compares the compartment in "leaf" with the compartment in "space" if
     * they are equal, returns the TreePath returns null otherwise.
     *
     * @param aNode
     * @param leaf
     * @param space
     * @return
     */
    private TreePath findTreePath(
            DefaultMutableTreeNode bNode,
            NodeLeaf leaf,
            CompartmentSBML space) {
        if (leaf.getCompartment() == null) {
//         System.out.println("error null compartment _1");
            return null;
        }
        if (space == null) {
            System.out.println("error null space");
        }
        if (leaf.getCompartment().getIdentity().contentEquals(space.getIdentity())) {
            TreePath path = new TreePath(bNode.getPath());
            {
                return path;
            }

        }
        return null;
    }

    public void selectConnection(final int pixelX, final int pixelY) {
        this.updatePathMaxX();
        if (this.treeConnections != null) {
            for (final TreeConnection c : this.treeConnections) {
                if (c != null) {
                    if (c.select(pixelX, pixelY)) {
                        final TreeConnectionSelectionEvent treeConnectionSelectionEvent = new TreeConnectionSelectionEvent(this, c.getUserObj());
                        this.fireValueChanged(treeConnectionSelectionEvent);
                    }
                }
            }
        }
        this.paint(this.getGraphics());
    }

    public void selectConnection(final Object connectionObject) {
        this.updatePathMaxX();
        for (final TreeConnection c : this.treeConnections) {
            if (c.getUserObj() == connectionObject) {
                c.selected = true;
            } else {
                c.selected = false;
            }
        }
        this.paint(this.getGraphics());
    }

    @Override
    public void paint(final Graphics g) {
        final Graphics2D graphicsContext = (Graphics2D) g;
        super.paint(g);
        this.drawConnections(graphicsContext);
    }

    public void drawConnections(final Graphics2D g) {
        if ((this.treeConnections != null) && ((this.treeConnections.size() > 0))) {
            Collections.sort(this.treeConnections);
            final Rectangle bounds = this.getBounds();
            g.setColor(Color.WHITE);
            g.fillRect(this.pathMaxX, 0, bounds.width, bounds.height);
            int nextX = this.pathMaxX + this.xSpacing;
            for (final TreeConnection c : this.treeConnections) {
                c.xLocation = nextX;
                c.draw(g);
                nextX += this.xSpacing;
            }
        }
    }

    public void updatePathMaxX() {
        this.pathMaxX = 1;
        for (int i = 0; i < this.getRowCount(); ++i) {
            final Rectangle rect = this.getRowBounds(i);
            final int width = (int) rect.getLocation().getX() + (int) rect.getWidth();
            if (this.pathMaxX < width) {
                this.pathMaxX = width;
            }
        }
    }

    public void updateExpandedTree() {
        this.updatePathMaxX();
    }

    public int getMaxWidth() {
        return this.pathMaxX;
    }

    public void addTreeConnectionSelectionListener(final TreeConnectionSelectionListener tsl) {
        this.listenerList.add(TreeConnectionSelectionListener.class, tsl);
    }

    public void removeTreeConnectionSelectionListener(final TreeConnectionSelectionListener tsl) {
        this.listenerList.remove(TreeConnectionSelectionListener.class, tsl);
    }

    public TreeConnectionSelectionListener[] getTreeConnectionSelectionListeners() {
        return this.listenerList.getListeners(TreeConnectionSelectionListener.class);
    }

    protected void fireValueChanged(final TreeConnectionSelectionEvent e) {
        final TreeConnectionSelectionListener[] listeners = this.getTreeConnectionSelectionListeners();
        if (listeners != null) {
            for (int i = 0; i < listeners.length; ++i) {
                listeners[i].valueChanged(e);
            }
        }
    }

    public boolean isLeftMostConnection(final TreeConnection theConnection, final TreePath visibleStartPath) {
        if (theConnection.xLocation == this.pathMaxX + this.xSpacing) {
            return true;
        }
        if (this.treeConnections != null) {
            for (final TreeConnection c : this.treeConnections) {
                if (c != theConnection) {
                    final TreePath start = c.getVisiblePath(c.startPath);
                    final TreePath end = c.getVisiblePath(c.endPath);
                    if (c.xLocation < theConnection.xLocation && (start.equals(visibleStartPath) || end.equals(visibleStartPath))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
