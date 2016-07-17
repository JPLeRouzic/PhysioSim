// 
//  
// 

package datamodel;

import datamodel.perturbation.SamplingLocation;
import datamodel.type.CellTypeMemberOfDefinitionType;
import utility.XMLIdManager;
import java.util.ArrayList;
import java.util.Collection;
import datamodel.CompartmentSBML;

public class CellType extends Connection
{

    protected boolean mobility;

    public double volumeFractionParentLocation;

    public double averageVolumePerCellML;

    public CellTypeMemberOfDefinitionType memberOf;
    
    protected ArrayList<CompartmentSBML> volumes ;
     
    
    public CellType() {
        this.mobility = false;
        this.volumeFractionParentLocation = 0.0;
        this.averageVolumePerCellML = 0.0;
        this.memberOf = CellTypeMemberOfDefinitionType.HOST;
        this.volumes = new ArrayList();
        this.setIdentity(XMLIdManager.getXMLId((Class)CellType.class));
    }
    
    public CellType(final Connection theParent, final String theCellTypeName) {
        super(theParent, theCellTypeName);
        this.mobility = false;
        this.volumeFractionParentLocation = 0.0;
        this.averageVolumePerCellML = 0.0;
        this.memberOf = CellTypeMemberOfDefinitionType.HOST;
        this.volumes = new ArrayList();
        this.setIdentity(XMLIdManager.getXMLId((Class)CellType.class));
    }
    
    public CellType copy(final Connection theParent, final ArrayList<Connection> connections, final ArrayList<SamplingLocation> samplingLocations, final ArrayList<Connection> refLocations) {
        final CellType cellType = new CellType(theParent, this.getName());
        cellType.setIdentity(XMLIdManager.getXMLId((Class)CellType.class));
        cellType.notes = this.notes;
        cellType.volumeFractionParentLocation = this.volumeFractionParentLocation;
        cellType.averageVolumePerCellML = this.averageVolumePerCellML;
        cellType.mobility = this.mobility;
        cellType.memberOf = this.memberOf;
        if (refLocations.contains(this)) {
            refLocations.remove(this);
            refLocations.add(cellType);
        }
        for (final SamplingLocation samplingLocation : samplingLocations) {
            samplingLocation.updateConnectionCT(this, cellType);
        }

        for (final CompartmentSBML volume : this.volumes) {
            cellType.addVolume(volume.copy(cellType, connections, samplingLocations, refLocations));
        }
        return cellType;
    }
    
    public Class getChildNodeClass() {
        return CompartmentSBML.class;
    }
    
    public boolean getMobility() {
        return this.mobility;
    }
    
    public boolean isMobile() {
        return this.mobility;
    }
    
    public void setMobility(final boolean isMobile) {
        this.mobility = isMobile;
    }
    
    public double getVolumeFractionParentLocation() {
        return this.volumeFractionParentLocation;
    }
    
    public void setVolumeFractionParentLocation(final double theVolumeFractionParentLocation) {
        this.volumeFractionParentLocation = theVolumeFractionParentLocation;
    }
    
    public void setNumberOfCells(final double numberOfCells) throws Exception {
        final CompartmentSBML volume = (CompartmentSBML)this.getParentNode();
        final double requiredTotalCellVolumeML = numberOfCells * this.getAverageVolumePerCellML();
        final double requiredVolumeFractionParentLocation = requiredTotalCellVolumeML / volume.getTotalVolumeML();
        this.setVolumeFractionParentLocation(requiredVolumeFractionParentLocation);
    }
    
    public double getAverageVolumePerCellML() {
        return this.averageVolumePerCellML;
    }
    
    public void setAverageVolumePerCellML(final double theAverageVolumePerCellML) {
        this.averageVolumePerCellML = theAverageVolumePerCellML;
    }
    
    public CellTypeMemberOfDefinitionType getMemberOf() {
        return this.memberOf;
    }
    
    public void setMemberOf(final CellTypeMemberOfDefinitionType memberOf) {
        this.memberOf = memberOf;
    }
    
    public ArrayList<CompartmentSBML> getVolumes() {
        return this.volumes;
    }
    
    public void setVolumes(final ArrayList<CompartmentSBML> volumes) {
        this.volumes = volumes;
    }
    
    public int getNumVolumes() {
        return this.getNumChildNodes();
    }
    
    public CompartmentSBML getVolume(final int volumeIndx) {
        return (CompartmentSBML) this.getChildNode(volumeIndx);
    }
    
    public CompartmentSBML getVolume(final String theName) {
        for (final CompartmentSBML volume : this.volumes) {
            if (volume.getName().equalsIgnoreCase(theName)) {
                return volume;
            }
        }
        return null;
    }
    
    public void addVolume(final CompartmentSBML theVolume) {
        this.addChildNode(theVolume);
    }
    
    public void deleteVolume(final CompartmentSBML theVolume) {
        this.removeChildNode(theVolume);
    }
    
    public int getNumChildNodes() {
        if (this.volumes != null) {
            return this.volumes.size();
        }
        return 0;
    }
    
    public Connection getChildNode(final int childNodeIndx) {
        if (this.getNumChildNodes() > childNodeIndx) {
            CompartmentSBML res = (this.volumes).get(childNodeIndx);
            return res ;
        }
        return null;
    }
    
    public int getChildNodeIndx(final CompartmentSBML theChildConnection) {
        return (this.volumes).indexOf(theChildConnection);
    }
    
    public void addChildNode(final CompartmentSBML theChildConnection) {
        // there is no type problem, as CompartmentSBML extends Connection
        if (theChildConnection != null && !this.volumes.contains(theChildConnection)) {
            theChildConnection.setParentNode(this);
            (this.volumes).add(theChildConnection);
            theChildConnection.setName(theChildConnection.getName());
        }
    }
    
    public void insertChildNode(final CompartmentSBML theChildConnection, final int childNodeIndx) {
        if (theChildConnection != null) {
            theChildConnection.setParentNode(this);
            (this.volumes).add(childNodeIndx, theChildConnection);
            theChildConnection.setName(theChildConnection.getName());
        }
    }
    
    public void removeChildNode(final CompartmentSBML theChildConnection) {
        if (theChildConnection != null) {
            theChildConnection.setParentNode(null);
            (this.volumes).remove(theChildConnection);
        }
    }
    
    public void moveChildNode(final CompartmentSBML theChildConnection, final int indxIncrement) {
        if (theChildConnection != null) {
            final int indx = this.getChildNodeIndx(theChildConnection);
            (this.volumes).remove(theChildConnection);
            (this.volumes).add(indx + indxIncrement, theChildConnection);
        }
    }
    
    public ArrayList<CompartmentSBML> getListOfVolumes(ArrayList<CompartmentSBML> volumeList) {
        if (volumeList == null) {
            volumeList = new ArrayList<>();
        }
        for (final CompartmentSBML volume : this.volumes) {
            volumeList = volume.getListOfVolumes(volumeList);
        }
        return volumeList;
    }
    
    public ArrayList<CellType> getListOfCellTypes(ArrayList<CellType> cellTypeList) {
        if (cellTypeList == null) {
            cellTypeList = new ArrayList<>();
            cellTypeList.add(this);
        }
        else if (!cellTypeList.contains(this)) {
            cellTypeList.add(this);
        }
        for (final CompartmentSBML volume : this.volumes) {
            cellTypeList = (ArrayList<CellType>)volume.getListOfCellTypes(cellTypeList);
        }
        return cellTypeList;
    }
    
    public CompartmentSBML findVolume(final String parentName, final String volumeName) {
        CompartmentSBML searchResult = null;
        if (parentName.equals(this.getName())) {
            final ArrayList<Connection> childVolumes = (ArrayList<Connection>)this.getChildNodes(volumeName);
            if (this.getNumChildNodes() > 0) {
                searchResult = (CompartmentSBML)childVolumes.get(0);
            }
        }

        return searchResult;
    }
    
    public double getTotalVolumeML() {
        // FIXME
        return -1 ;
    }
    
    public double getTotalNumber() {
        if (this.averageVolumePerCellML > 0.0) {
            return this.getTotalVolumeML() / this.averageVolumePerCellML;
        }
        return 0.0;
    }
    
    public double getHydrodynamicRadiusInMicrons() {
        return 10000.0 * Math.pow(0.238732414637843 * this.averageVolumePerCellML, 0.3333333333333333);
    }
    
    public double getNetCharge(final double pH) {
        return 0.0;
    }
    
    public boolean isPolarized() {
        final CompartmentSBML apicalEndosome = this.getVolume(KeyWord.APICAL_ENDOSOME.toString());
        return apicalEndosome != null;
    }
    
}
