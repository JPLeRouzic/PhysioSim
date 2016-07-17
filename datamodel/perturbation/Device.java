// 
//  
// 

package datamodel.perturbation;

import datamodel.CellType;
import datamodel.Connection;
import utility.XMLIdManager;
import java.util.ArrayList;
import datamodel.CompartmentSBML;

public class Device extends Connection
{
    protected ArrayList<CompartmentSBML> volumes;
    protected double totalVolumeML;
    protected double temperatureCelcius;
    
    
    public Device() {
        this.volumes = new ArrayList<>();
        this.totalVolumeML = 0.0;
        this.temperatureCelcius = 25.0;
        this.setIdentity(XMLIdManager.getXMLId((Class)Device.class));
    }
    
    public Device(final Connection theParent, final String theAssayName) {
        super(theParent, theAssayName);
        this.volumes = new ArrayList<>();
        this.totalVolumeML = 0.0;
        this.temperatureCelcius = 25.0;
        this.setIdentity(XMLIdManager.getXMLId((Class)Device.class));
    }
    
    public Device copy(final Connection theParent, final ArrayList<Connection> connections, final ArrayList<SamplingLocation> samplingLocations, final ArrayList<Connection> refLocations) {
        final Device device = new Device(theParent, this.getName());
        device.setIdentity(XMLIdManager.getXMLId((Class)Device.class));
        device.notes = this.notes;
        device.temperatureCelcius = this.temperatureCelcius;
        device.totalVolumeML = this.totalVolumeML;
        if (refLocations.contains(this)) {
            refLocations.remove(this);
            refLocations.add(device);
        }
        for (final SamplingLocation samplingLocation : samplingLocations) {
            samplingLocation.updateConnectionDV(this, device);
        }

        for (int i = 0; i < this.getNumVolumes(); ++i) {
            device.addVolume(this.getVolume(i).copy(device, connections, samplingLocations, refLocations));
        }
        return device;
    }
    
    public double getTotalVolumeML() {
        return this.totalVolumeML;
    }
    
    public void setTotalVolumeML(final double theTotalVolumeML) {
        this.totalVolumeML = theTotalVolumeML;
    }
    
    public double getTemperatureCelcius() {
        return this.temperatureCelcius;
    }
    
    public void setTemperatureCelcius(final double temperatureCelcius) {
        this.temperatureCelcius = temperatureCelcius;
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
    
    @Override
    public Connection getChildNode(final int childNodeIndx) {
        if (this.getNumChildNodes() > childNodeIndx) {
            return  (this.volumes).get(childNodeIndx);
        }
        return null;
    }
    
    public int getChildNodeIndx(final Connection theChildConnection) {
        return (this.volumes).indexOf(theChildConnection);
    }
    
    public void addChildNode(final CompartmentSBML theChildConnection) {
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
        }
        for (final CompartmentSBML volume : this.volumes) {
            cellTypeList = (ArrayList<CellType>)volume.getListOfCellTypes(cellTypeList);
        }
        return cellTypeList;
    }
    
    public double getTotalNumber() {
        return 1.0;
    }
}
