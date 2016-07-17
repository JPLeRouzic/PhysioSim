/*
 * Transcode from a Map format to another Collection derived format
 * Created because it is not possible to iterate through a Map
 */
package utility;

import datamodel.Tissue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import datamodel.CompartmentSBML;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author jplr
 */
public class CollToArrayList {

    public ArrayList<Tissue> convertTissue(Map<Integer, Tissue> mapOf) {
        Collection<Tissue> deux = mapOf.values();        
        ArrayList<Tissue> un = new ArrayList<Tissue>(deux);        
        return un;
    }

    public ArrayList<Tissue> treeToTissue(Map<Tissue, DefaultMutableTreeNode> mapOf) {
        Collection<Tissue> deux = mapOf.keySet() ;        
        ArrayList<Tissue> un = new ArrayList<Tissue>(deux);        
        return un;
    }

    public ArrayList<CompartmentSBML> convertComp(Map<String, CompartmentSBML> mapOf) {
        Collection<CompartmentSBML> deux = mapOf.values();        
        ArrayList<CompartmentSBML> un = new ArrayList<CompartmentSBML>(deux);        
        return un;
    }
}
