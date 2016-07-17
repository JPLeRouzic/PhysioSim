/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author jplr
 */
public class NodeLeaf {

    private String name;
    private String iconPath;
    public CompartmentSBML compartment;
//    private final Tissue tissue;
// new variables
    private ImageIcon pict;
    public int height;
    public int width;

    public NodeLeaf(PhysioSystem organsys, String icon) {
//        this.tissue = null;
        this.name = organsys.name;
        this.iconPath = icon;
    }

// new code
    public NodeLeaf(Tissue tiss, String filePath) {
//        this.tissue = tiss;
        this.name = tiss.name;
        this.iconPath = filePath;
        // Load Icon
        File imageUrl = new File(filePath);
        if (imageUrl != null) {
            pict = new ImageIcon(filePath);
        }
        // Use constants as default values for height and width
        helper(filePath, 20, 20);
    }

    public NodeLeaf(String namu, int wodth, int heoght) {
//        this.tissue = null;
        this.name = namu;
        this.width = wodth;
        this.height = heoght;
    }

    public NodeLeaf(String namu, int wodth, int heoght, String filePath) {
//        this.tissue = null;
        this.name = namu;
        this.iconPath = filePath;
        helper(filePath, wodth, heoght);
    }

    public NodeLeaf(PhysioSystem organsys, String filePath, int wodth, int heoght) {
//        this.tissue = null;
        this.name = organsys.name;
        this.iconPath = filePath;
        helper(filePath, wodth, heoght);
    }

    final void helper(String filePath, int heoght, int wodth) {
        File imageUrl = new File(filePath);
        if (imageUrl != null) {
            pict = new ImageIcon(filePath);
        }
        if (heoght > pict.getIconHeight()) {
            this.height = heoght;
        } else {
            this.height = pict.getIconHeight();
        }
        if (wodth > pict.getIconWidth()) {
            this.width = wodth;
        } else {
            this.width = pict.getIconWidth();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlagIcon() {
        return iconPath;
    }

    public void setFlagIcon(String icon) {
        this.iconPath = icon;
    }

    public ImageIcon getImageIcon() {
        return pict;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String icon) {
        this.iconPath = icon;
    }

    public void setCompartment(CompartmentSBML compNode) {
        compartment = compNode;
    }

    public CompartmentSBML getCompartment() {
        return compartment;
    }

/*
* Compartment: A container of finite volume for well-
stirred substances where reactions take place.
    
Species: A chemical substance or entity that takes part in
a reaction. Some example species are ions such as
calcium ions and molecules such as ATP.
    
Reaction: A statement describing some transformation,
transport or binding process that can change one or
more species. Reactions have associated rate laws
describing the manner in which they take place.
*/
    public Narrative getNarrative() {
        // FIXME create a narrative to be drawn on right panel
        return null ;
    }

}
