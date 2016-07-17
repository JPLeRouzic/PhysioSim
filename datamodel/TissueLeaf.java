/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

/**
 *
 * @author jplr
 */
public class TissueLeaf {

    NodeLeaf nodeLeaf;

    private final Tissue tissue;

    TissueLeaf(Tissue tiss, NodeLeaf leaf) {
        tissue = tiss;
        nodeLeaf = leaf;
    }

    public Tissue getTissue() {
        return tissue;
    }
}
