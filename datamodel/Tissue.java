// 
//  A Tissue could be composed of Tissues and CompartmentSBML
// 
package datamodel;

import datamodel.perturbation.SamplingLocation;
import utility.XMLIdManager;
import java.util.ArrayList;

/**
 * A tissue is a collection of CompartmentSBML
 *
 * @author Jean-Pierre Le Rouzic https://padiracinnovation.org/feedback/
 */
public class Tissue extends Connection {

    protected double totalVolumeML;
    protected ArrayList<CompartmentSBML> compartments;
    public Tissue partOf; // for hierarchical tissues
    public String partOfString;
    private String organSystem;

    public Tissue() {
        this.totalVolumeML = 0.0;
        this.compartments = new ArrayList<>();
        this.setIdentity(XMLIdManager.getXMLId(Tissue.class));
    }

    public Tissue(final Connection theParent, final String theTissueName) {
        super(theParent, theTissueName);
        this.totalVolumeML = 0.0;
        this.compartments = new ArrayList<>();
        this.setIdentity(XMLIdManager.getXMLId(Tissue.class));
    }

    public Tissue copy(final Connection theParent, final ArrayList<Connection> connections,
            final ArrayList<SamplingLocation> samplingLocations,
            final ArrayList<Connection> refLocations) {
        final Tissue tissue = new Tissue(theParent, this.getName());
        tissue.setIdentity(XMLIdManager.getXMLId(Tissue.class));
        tissue.notes = this.notes;
        tissue.totalVolumeML = this.totalVolumeML;
        if (refLocations.contains(this)) {
            refLocations.remove(this);
            refLocations.add(tissue);
        }
        for (final SamplingLocation samplingLocation : samplingLocations) {
            samplingLocation.updateConnectionTS(this, tissue);
        }

        for (final CompartmentSBML comp : this.compartments) {
            tissue.addCompartment(comp.copy(tissue, connections, samplingLocations, refLocations));
        }
        // dont forget isPartOf() for Tissue who are part of Tissue
        tissue.partOf = this.partOf;
        /* level of embedding of Tissue in Tissue
         tissue.level = this.level ; */
        return tissue;
    }

    public double getTotalVolumeML() {
        return this.totalVolumeML;
    }

    public void setTotalVolumeML(final double theTotalVolumeML) {
        this.totalVolumeML = theTotalVolumeML;
    }

    /* ******** about many CompartmentSBML (compartments are CompartmentSBML ******** */
    public ArrayList<CompartmentSBML> getInternVolumes() {
        return this.compartments;
    }

    public void setInternVolumes(final ArrayList<CompartmentSBML> volumes) {
        this.compartments = volumes;
    }

    public int getNumVolumes() {
        return this.getNumChildNodes();
    }

    @Override
    public ArrayList<CompartmentSBML> getListOfVolumes(ArrayList<CompartmentSBML> volumeList) {
        if (volumeList == null) {
            volumeList = new ArrayList<>();
        }
        for (final CompartmentSBML comp : this.compartments) {
            volumeList = comp.getListOfVolumes(volumeList);
        }
        return volumeList;
    }

    /* ******** about one CompartmentSBML ******** */
    public CompartmentSBML findCompartment(final String parentName, final String volumeName) {
        CompartmentSBML searchResult = null;
        if (parentName.equals(this.getName())) {
            final ArrayList<Connection> childVolumes = this.getChildNodes(volumeName);
            if (childVolumes != null && childVolumes.size() > 0) {
                searchResult = (CompartmentSBML) childVolumes.get(0);
            }
        }
        return searchResult;
    }

    public CompartmentSBML getCompartment(final String theName) {
        for (final CompartmentSBML volume : this.compartments) {
            if (volume.getName().equalsIgnoreCase(theName)) {
                return volume;
            }
        }
        return null;
    }

    public void addCompartment(final CompartmentSBML theVolume) {
        this.addChildNode(theVolume);
    }

    public void deleteCompartment(final CompartmentSBML theVolume) {
        this.removeChildNode(theVolume);
    }

    /* ******** about ChildNodes ******** */
    public int getNumChildNodes() {
        if (this.compartments != null) {
            return this.compartments.size();
        }
        return 0;
    }

    @Override
    public Connection getChildNode(final int childNodeIndx) {
        if (this.getNumChildNodes() > childNodeIndx) {
            return (this.compartments).get(childNodeIndx);
        }
        return null;
    }

    public int getChildNodeIndx(final Connection theChildConnection) {
        return (this.compartments).indexOf(theChildConnection);
    }

    public void addChildNode(final CompartmentSBML theChildConnection) {
        if (theChildConnection != null && !this.compartments.contains(theChildConnection)) {
            theChildConnection.setParentNode(this);
            (this.compartments).add(theChildConnection);
            theChildConnection.setName(theChildConnection.getName());
        }
    }

    public void insertChildNode(final CompartmentSBML theChildConnection, final int childNodeIndx) {
        if (theChildConnection != null) {
            theChildConnection.setParentNode(this);
            (this.compartments).add(childNodeIndx, theChildConnection);
            theChildConnection.setName(theChildConnection.getName());
        }
    }

    public void removeChildNode(final CompartmentSBML theChildConnection) {
        if (theChildConnection != null) {
            theChildConnection.setParentNode(null);
            (this.compartments).remove(theChildConnection);
        }
    }

    /* ******** about Tissue's Cells Types ******** */
    public ArrayList<CellType> getListOfCellTypes(ArrayList<CellType> cellTypeList) {
        if (cellTypeList == null) {
            cellTypeList = new ArrayList<>();
        }
        for (final CompartmentSBML comp : this.compartments) {
            cellTypeList = comp.getListOfCellTypes(cellTypeList);
        }
        return cellTypeList;
    }

    public ArrayList<CellType> getCellTypes(final String cellTypeName) {
        final ArrayList<CellType> cellTypeList = this.getListOfCellTypes(null);
        for (final CellType cellType : this.getListOfCellTypes(null)) {
            if (cellType.getName().equalsIgnoreCase(cellTypeName)) {
                cellTypeList.add(cellType);
            }
        }
        return cellTypeList;
    }

    /**
     * This enables Tissue to be part of another Tissue. It also register this
     * new tissue as part of a physiological system
     *
     * @param organSystem
     * @param theTissueName
     * @param organ
     * @return
     */
    public Tissue addTissue(
            final PhysioSystem organSystem,
            Tissue organ,
            final String theTissueName
    ) {
        final Tissue tissue = new Tissue(this, theTissueName);
        tissue.partOfTissue(tissue, organ);
        // all children are added at the same level in OrganSystem
        // presentation will provide the correct (embedded) view
        organSystem.addChildNode(tissue);
        return tissue;
    }

    /**
     * This enables Tissue to be part of another Tissue designated by "organ".
     *
     * @param theTissueName
     * @param organ
     * @return
     */
    public Tissue addTissue(
            Tissue organ,
            final String theTissueName
    ) {
        final Tissue tissue = new Tissue(this, theTissueName);
        tissue.partOfTissue(tissue, organ);
        // all children are added at the same level in OrganSystem
        // presentation will provide the correct (embedded) view
        return tissue;
    }

    private void partOfTissue(final Tissue tissue, Tissue organ) {
        tissue.partOf = organ;
    }

    public void setPartOfStr(String mof) {
        this.partOfString = mof;
    }

    public String getPartOfStr() {
        return partOfString ;
    }

    /**
     * Tells if the Tissue is part of another Tissue like a Nephron is part of a
     * Kidney
     *
     * @return
     */
    public boolean isPartOf() {
        if (partOf == null) {
            return false;
        } else {
            return true;
        }
    }

    public void setOrganSystemStr(String attributeValue) {
        organSystem = attributeValue;
    }

    public String getOrganSystemStr() {
        return organSystem;
    }

}
