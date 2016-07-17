// 
// For example urinary, respiratory, circulatory systems, which are composed of organs
// A physioSystem represents also connections to and from other PhysioSystems
// 

package datamodel;

import datamodel.perturbation.SamplingLocation;
import utility.XMLIdManager;
import java.util.ArrayList;

public class PhysioSystem extends Connection
{
    public ArrayList<Tissue> tissues;
    
    
    public PhysioSystem(final String theOrganName) {
        this.setName(theOrganName);
        this.tissues = new ArrayList();
        this.setIdentity(XMLIdManager.getXMLId((Class)PhysioSystem.class));
    }
    
    public PhysioSystem(final Connection theParent, final String theOrganName) {
        super(theParent, theOrganName);
        this.tissues = new ArrayList();
        this.setIdentity(XMLIdManager.getXMLId((Class)PhysioSystem.class));
    }
    
    public PhysioSystem(final String theOrganName, ArrayList<Tissue> tiss) {
        this.setName(theOrganName);
        this.tissues = tiss ;
        this.setIdentity(XMLIdManager.getXMLId((Class)PhysioSystem.class));
        
    }
    
    public PhysioSystem copy(final Connection theParent, final ArrayList<Connection> connections, final ArrayList<SamplingLocation> samplingLocations, final ArrayList<Connection> refLocations) {
        final PhysioSystem organSystem = new PhysioSystem(theParent, this.getName());
        organSystem.setIdentity(XMLIdManager.getXMLId((Class)PhysioSystem.class));
        organSystem.notes = this.notes;
        if (refLocations.contains(this)) {
            refLocations.remove(this);
            refLocations.add(organSystem);
        }
        for (final SamplingLocation samplingLocation : samplingLocations) {
            samplingLocation.updateConnectionPS(this, organSystem);
        }

        for (final Tissue tissue : this.tissues) {
            organSystem.addTissue(tissue.copy(organSystem, connections, samplingLocations, refLocations));
        }
        return organSystem;
    }
    
    public Class getChildNodeClass() {
        return Tissue.class;
    }
    
    public ArrayList<Tissue> getTissues() {
        return (ArrayList<Tissue>)this.tissues;
    }
    
    public void setTissues(final ArrayList<Tissue> tissues) {
        this.tissues = tissues;
    }
    
    public int getNumTissues() {
        return this.getNumChildNodes();
    }
    
    public Tissue getTissue(final int tissueIndx) {
        return (Tissue)this.getChildNode(tissueIndx);
    }
    
    public Tissue getTissue(final String theName) {
        for (final Tissue tissue : this.tissues) {
            if (tissue.getName().equalsIgnoreCase(theName)) {
                return tissue;
            }
        }
        return null;
    }
    
    public Tissue addTissue(final String theTissueName) {
        final Tissue tissue = new Tissue(this, theTissueName);
        this.addChildNode(tissue);
        return tissue;
    }
    
    public void addTissue(final Tissue theTissue) {
        this.addChildNode(theTissue);
    }
    
    public void deleteTissue(final Tissue theTissue) {
        this.removeChildNode(theTissue);
    }
    
    public int getNumChildNodes() {
        if (this.tissues != null) {
            return this.tissues.size();
        }
        return 0;
    }
    
    public Connection getChildNode(final int childNodeIndx) {
        if (this.getNumChildNodes() > childNodeIndx) {
            return (this.tissues).get(childNodeIndx);
        }
        return null;
    }
    
    public int getChildNodeIndx(final Connection theChildConnection) {
        return (this.tissues).indexOf(theChildConnection);
    }
    
    // add for example a Tissue to an PhysioSystem
    public void addChildNode(final Tissue theChildConnection) {
        if (theChildConnection != null && !this.tissues.contains(theChildConnection)) {
            theChildConnection.setParentNode(this);
            (this.tissues).add(theChildConnection);
            theChildConnection.setName(theChildConnection.getName());
        }
    }
    
    public void insertChildNode(final Tissue theChildConnection, final int childNodeIndx) {
        if (theChildConnection != null) {
            theChildConnection.setParentNode(this);
            (this.tissues).add(childNodeIndx, theChildConnection);
            theChildConnection.setName(theChildConnection.getName());
        }
    }
    
    public void removeChildNode(final Tissue theChildConnection) {
        if (theChildConnection != null) {
            theChildConnection.setParentNode(null);
            (this.tissues).remove(theChildConnection);
        }
    }
    
    public void moveChildNode(final Tissue theChildConnection, final int indxIncrement) {
        if (theChildConnection != null) {
            final int indx = this.getChildNodeIndx(theChildConnection);
            (this.tissues).remove(theChildConnection);
            (this.tissues).add(indx + indxIncrement, theChildConnection);
        }
    }
    
    public ArrayList<CompartmentSBML> getListOfVolumes(ArrayList<CompartmentSBML> volumeList) {
        if (volumeList == null) {
            volumeList = new ArrayList<>();
        }
        for (final Tissue tissue : this.tissues) {
            volumeList = tissue.getListOfVolumes(volumeList);
        }
        return volumeList;
    }
    
//    @Override
    public ArrayList<CellType> getListOfCellTypes(ArrayList<CellType> cellTypeList) {
        if (cellTypeList == null) {
            cellTypeList = new ArrayList<>();
        }
        for (final Tissue tissue : this.tissues) {
            cellTypeList = (ArrayList<CellType>)tissue.getListOfCellTypes(cellTypeList);
        }
        return cellTypeList;
    }
    
    public ArrayList<String> getListOfVolumeNamesForTissueNames(final ArrayList<String> tissueList, ArrayList<String> volumeList) {
        if (volumeList == null) {
            volumeList = new ArrayList<>();
        }
        for (final Tissue tissue : this.tissues) {
            final String tissueName = tissue.getName();
            if (tissueName != null && tissueList.contains(tissueName)) {
                final ArrayList<CompartmentSBML> tissueVolumes = tissue.getInternVolumes();
                for (final CompartmentSBML volume : tissueVolumes) {
                    final String volumeName = volume.getName();
                    if (volumeName != null && !volumeList.contains(volumeName)) {
                        volumeList.add(volumeName);
                    }
                }
            }
        }
        return volumeList;
    }
    
    public ArrayList<String> getListOfTissueNames(ArrayList<String> tissueList) {
        if (tissueList == null) {
            tissueList = new ArrayList<>();
        }
        for (final Tissue tissue : this.tissues) {
            final String tissueName = tissue.getName();
            if (tissueName != null && !tissueList.contains(tissueName)) {
                tissueList.add(tissueName);
            }
        }
        return tissueList;
    }
    
    public CompartmentSBML findVolume(final String parentName, final String volumeName) {
        CompartmentSBML searchResult = null;
        for (final Tissue tissue : this.tissues) {
            searchResult = tissue.findCompartment(parentName, volumeName);
            if (searchResult != null) {
                return searchResult;
            }
        }
        return searchResult;
    }
    
//    @Override
    public double getTotalNumber() {
        return 1.0;
    }
}
