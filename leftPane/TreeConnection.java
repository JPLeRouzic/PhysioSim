// 
// TreeConnection contains information about connections between tissues.
// It is filled in with the XML files pipes.xml and surfaces.xml
// It contains a ConnectionJTree which contains many TreeConnection in a vector
// 

package leftPane;

import datamodel.perturbation.Injection;
import datamodel.FluidPipe;
import home.GUIMain;
import java.awt.BasicStroke;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import javax.swing.tree.TreePath;
import java.awt.Color;

public class TreeConnection implements Comparable
{
    public static Color INJECTION_CONNECTION_COLOR;
    public static Color PIPE_CONNECTION_COLOR;
    public static Color SURFACE_CONNECTION_COLOR;
    public static Color SELECTED_CONNECTION_BACKTROUND;
    public static int CONNECTION_STROKE_WIDTH;
//    private final ConnectionJTree myTree;
    public TreePath startPath;
    public TreePath endPath;
    public int xLocation;
    private int xStart;
    private int xEnd;
    private int yEnd;
    private int yStart;
    public Stroke stroke;
    public Object userObj;
    boolean directional;
    boolean selected;
    boolean visibleEnds;
    public int selectionZoneWidth;
    public int leaderLength;
    public int handleSize;
    public int arrowLength;
    public int arrowWidth;
    private final GUIMain locGuiMn;
    
    public TreeConnection(GUIMain guiMn, final ConnectionJTree theTree) {
        this.selectionZoneWidth = 8;
        this.leaderLength = 5;
        this.handleSize = 6;
        this.arrowLength = 10;
        this.arrowWidth = 5;
        
        // record guiMain instance and the connection tree
        locGuiMn = guiMn ;
        guiMn.myTree = theTree;
        this.selected = false;
        this.directional = false;
    }
    
    public Object getUserObj() {
        return this.userObj;
    }
    
    public boolean select(final int x, final int y) {
        final int halfWidth = this.selectionZoneWidth / 2;
        final Polygon selectionZone = new Polygon();
        selectionZone.addPoint(this.xStart, this.yStart - halfWidth);
        selectionZone.addPoint(this.xLocation + halfWidth, this.yStart - halfWidth);
        selectionZone.addPoint(this.xLocation + halfWidth, this.yEnd + halfWidth);
        selectionZone.addPoint(this.xEnd, this.yEnd + halfWidth);
        selectionZone.addPoint(this.xEnd, this.yEnd - halfWidth);
        selectionZone.addPoint(this.xLocation - halfWidth, this.yEnd - halfWidth);
        selectionZone.addPoint(this.xLocation - halfWidth, this.yStart + halfWidth);
        selectionZone.addPoint(this.xStart, this.yStart + halfWidth);
        return selectionZone.contains(x, y);
    }
    
    public void draw(final Graphics2D g) {
        final TreePath visibleStartPath = this.getVisiblePath(this.startPath);
        final TreePath visibleEndPath = this.getVisiblePath(this.endPath);
        this.visibleEnds = (visibleStartPath == this.startPath && visibleEndPath == this.endPath);
        final Rectangle fromBounds = locGuiMn.myTree.getPathBounds(visibleStartPath);
        final Rectangle toBounds = locGuiMn.myTree.getPathBounds(visibleEndPath);
        if (fromBounds != null && toBounds != null) {
            this.yStart = fromBounds.y + fromBounds.height / 2;
            this.yEnd = toBounds.y + toBounds.height / 2;
            this.xStart = this.xLocation - this.leaderLength;
            this.xEnd = this.xLocation - this.leaderLength;
            if (locGuiMn.myTree.isLeftMostConnection(this, visibleStartPath)) {
                this.xStart = fromBounds.x + fromBounds.width;
            }
            if (locGuiMn.myTree.isLeftMostConnection(this, visibleEndPath)) {
                this.xEnd = toBounds.x + toBounds.width;
            }
            if (this.selected) {
                g.setColor(TreeConnection.SELECTED_CONNECTION_BACKTROUND);
                final int halfWidth = this.selectionZoneWidth / 2;
                final int top = Math.min(this.yStart, this.yEnd) - TreeConnection.CONNECTION_STROKE_WIDTH;
                final int height = Math.abs(this.yStart - this.yEnd) + 2 * TreeConnection.CONNECTION_STROKE_WIDTH;
                g.fillRect(this.xStart, this.yStart - halfWidth, this.xLocation - this.xStart + halfWidth, this.selectionZoneWidth);
                g.fillRect(this.xLocation - halfWidth, top, this.selectionZoneWidth, height);
                g.fillRect(this.xEnd, this.yEnd - halfWidth, this.xLocation - this.xEnd + halfWidth, this.selectionZoneWidth);
            }
            g.setColor(this.getColor());
            g.setStroke(this.getStroke());
            g.drawLine(this.xStart, this.yStart, this.xLocation, this.yStart);
            g.drawLine(this.xLocation, this.yStart, this.xLocation, this.yEnd);
            g.drawLine(this.xLocation, this.yEnd, this.xEnd, this.yEnd);
            if (this.directional && this.yEnd != this.yStart) {
                final int verticalCenter = (this.yStart + this.yEnd) / 2;
                final int yDir = (this.yEnd > this.yStart) ? 1 : -1;
                final Polygon myArrow = new Polygon();
                myArrow.addPoint(this.xLocation, verticalCenter + this.arrowLength / 2 * yDir);
                myArrow.addPoint(this.xLocation + this.arrowWidth, verticalCenter - this.arrowLength / 2 * yDir);
                myArrow.addPoint(this.xLocation - this.arrowWidth, verticalCenter - this.arrowLength / 2 * yDir);
                g.fillPolygon(myArrow);
            }
        }
    }
    
    private BasicStroke getStroke() {
        if (this.visibleEnds) {
            return new BasicStroke(TreeConnection.CONNECTION_STROKE_WIDTH, 1, 1);
        }
        return new BasicStroke(TreeConnection.CONNECTION_STROKE_WIDTH, 1, 1, 2.0f, new float[] { 5.0f, 5.0f }, 2.0f);
    }
    
    private Color getColor() {
        if (this.userObj instanceof FluidPipe) {
            return TreeConnection.PIPE_CONNECTION_COLOR;
        }
        if (this.userObj instanceof Injection) {
            return TreeConnection.INJECTION_CONNECTION_COLOR;
        }
        return Color.BLACK;
    }
    
    public TreePath getVisiblePath(final TreePath path) {
        if (path == null) {
            return null;
        }
        if (locGuiMn.myTree.isVisible(path)) {
            return path;
        }
        return this.getVisiblePath(path.getParentPath());
    }
    
    public int getYSpan() {
        final TreePath visibleStartPath = this.getVisiblePath(this.startPath);
        final TreePath visibleEndPath = this.getVisiblePath(this.endPath);
        final Rectangle fromBounds = locGuiMn.myTree.getPathBounds(visibleStartPath);
        final Rectangle toBounds = locGuiMn.myTree.getPathBounds(visibleEndPath);
        return Math.abs(fromBounds.y - toBounds.y);
    }
    
    @Override
    public int compareTo(final Object o) {
        if (!(o instanceof TreeConnection)) {
            return 0;
        }
        final TreeConnection c = (TreeConnection)o;
        final int mySpan = this.getYSpan();
        final int oSpan = c.getYSpan();
        if (mySpan < oSpan) {
            return -1;
        }
        if (mySpan > oSpan) {
            return 1;
        }
        return 0;
    }
    
    static {
        TreeConnection.INJECTION_CONNECTION_COLOR = new Color(255, 204, 204);
        TreeConnection.PIPE_CONNECTION_COLOR = new Color(255, 102, 153);
        TreeConnection.SURFACE_CONNECTION_COLOR = new Color(51, 153, 255);
        TreeConnection.SELECTED_CONNECTION_BACKTROUND = new Color(184, 207, 229);
        TreeConnection.CONNECTION_STROKE_WIDTH = 3;
    }
}
