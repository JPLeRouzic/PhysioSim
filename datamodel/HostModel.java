// 
// This is a model of a placentalia organism onto which will be applied various 
// mistreatments such as injections, measurements....
// The placentals are primarily distinguished from other mammals in that the fetus is carried 
// in the uterus of its mother, until a fully developed offspring occurs.
// Beyond the placentals physiology, the cells of mammalians have specific features, not found in other 
// four-limbed vertebrates.
// HumanModel is included in HostModelToSolve
// 

package datamodel;

import datamodel.perturbation.SamplingTime;
import datamodel.perturbation.SamplingLocation;
import datamodel.perturbation.MeasurementSet;
import datamodel.perturbation.Device;
import datamodel.perturbation.Injection;
import datamodel.perturbation.Drug;
import datamodel.perturbation.Molecule;
import java.util.Iterator;
import utility.XMLIdManager;
import java.util.ArrayList;
import java.util.Date;

public class HostModel extends Connection
{
    public static final double AVOGADROS_NUMBER = 6.0221415E23;
    
    protected HumanBody placentalia;
    protected ArrayList<FluidPipe> pipes;

    // Horrifying things done to the host
    public ArrayList<Injection> injections;
    public ArrayList<MeasurementSet> measurementSets;
    public ArrayList<SamplingTime> samplingTimes;
    protected ArrayList<SamplingLocation> samplingLocations;
    protected ArrayList<Device> devices;
    protected ArrayList<Drug> agents;

    // administrative stuff
    public String author;
    private Date creationDate;

    protected boolean saved;
    
    public HostModel() {
        this.devices = new ArrayList();
        this.placentalia = null;
        this.pipes = new ArrayList();
        this.agents = new ArrayList();
        this.samplingLocations = new ArrayList();
        this.author = "";
        this.creationDate = new Date();

        this.saved = true;
        this.setIdentity(XMLIdManager.getXMLId((Class)HostModel.class));
        this.setName(KeyWord.PHYSIOSIMMODEL.toString());
    }
    
    public HostModel(final String theModelName) {
        super(null, theModelName);
        this.devices = new ArrayList();
        this.placentalia = null;
        this.pipes = new ArrayList();
        this.agents = new ArrayList();
        this.samplingLocations = new ArrayList();
        this.author = "";
        this.creationDate = new Date();

        this.saved = true;
        this.setIdentity(XMLIdManager.getXMLId((Class)HostModel.class));
    }
    
    public HostModel copy(
            final Connection theParent, 
            final ArrayList<Connection> connections, 
            final ArrayList<SamplingLocation> samplingLocations, 
            final ArrayList<Connection> refLocations) 
        {
        final HostModel PhysioSimModel = new HostModel(this.getName());
        PhysioSimModel.setIdentity(XMLIdManager.getXMLId((Class)HostModel.class));
        PhysioSimModel.notes = this.notes;
        PhysioSimModel.author = this.author;
        PhysioSimModel.creationDate = (Date)this.creationDate.clone();

        if (refLocations.contains(this)) {
            refLocations.remove(this);
            refLocations.add(PhysioSimModel);
        }
        for (final SamplingLocation samplingLocation : samplingLocations) {
            samplingLocation.updateConnectionHM(this, PhysioSimModel);
        }

        PhysioSimModel.setPlacentalia(this.placentalia.copy(PhysioSimModel, connections, samplingLocations, refLocations));
        
        for (final Device device : this.devices) {
            PhysioSimModel.addDevice(device.copy(PhysioSimModel, connections, samplingLocations, refLocations));
        }
        return PhysioSimModel;
    }
    
    public HostModel copy(final ArrayList<Connection> refLocations) {
        final ArrayList<FluidPipe> copyOfPipes = new ArrayList<>();
        for (final FluidPipe pipe : this.pipes) {
            copyOfPipes.add(pipe.copy());
        }

        final ArrayList<SamplingLocation> copyOfSamplingLocations = new ArrayList<>();
        for (final SamplingLocation samplingLocation : this.samplingLocations) {
            copyOfSamplingLocations.add(samplingLocation.copy());
        }
        final ArrayList<Connection> connections = new ArrayList<>();
        for (final FluidPipe pipe3 : copyOfPipes) {
            connections.add((Connection)pipe3);
        }

        final HostModel PhysioSimModel = this.copy(null, connections, copyOfSamplingLocations, refLocations);
        PhysioSimModel.pipes = copyOfPipes;

        PhysioSimModel.samplingLocations = copyOfSamplingLocations;
        return PhysioSimModel;
    }
    
    public Class getChildNodeClass() {
        return HumanBody.class;
    }
    
    public Date getCreationDate() {
        return this.creationDate;
    }
    
    public void setCreationDate(final Date createDate) {
        this.creationDate = createDate;
    }
    
    public String getAuthor() {
        return this.author;
    }
    
    public void setAuthor(final String author) {
        this.author = author;
    }
    
    public HumanBody getPlacentalia() {
        return this.placentalia;
    }
    
    public void setPlacentalias(final HumanBody Placentaj) {
        this.placentalia = Placentaj;
    }
    
    public ArrayList<Device> getDevices() {
        return (ArrayList<Device>)this.devices;
    }
    
    public void setDevices(final ArrayList<Device> deviceSet) {
        this.devices = deviceSet;
    }
    
    public ArrayList<FluidPipe> getPipes() {
        return (ArrayList<FluidPipe>)this.pipes;
    }
    
    public void setPipes(final ArrayList<FluidPipe> pipes) {
        this.pipes = pipes;
    }
    
    public int getNumDevices() {
        if (this.devices != null) {
            return this.devices.size();
        }
        return 0;
    }
    
    public int getNumChildNodes() {
        int numChildren = 0;
        
        if (this.devices != null) {
            numChildren += this.devices.size();
        }
        return numChildren;
    }
    
    public Connection getChildNode(final int childNodeIndx) {
        
        if (childNodeIndx < this.devices.size()) {
            return (this.devices).get(childNodeIndx);
        }
        return null;
    }
    
    public int getChildNodeIndx(final Device theChildConnection) {
            return (this.devices).indexOf(theChildConnection);
    }
    
    public void addChildNode(final Connection theChildConnection) {
        if (theChildConnection != null) {
            if (theChildConnection instanceof HumanBody) {
                this.setPlacentalia((HumanBody)theChildConnection);
            }
            else if (theChildConnection instanceof Device) {
                this.addDevice((Device)theChildConnection);
            }
            theChildConnection.setName(theChildConnection.getName());
        }
    }
    
    public void insertChildNode(final Device theChildConnection, final int childNodeIndx) {
        if (theChildConnection != null) {
                theChildConnection.setParentNode(this);
                (this.devices).add(childNodeIndx, theChildConnection);
            theChildConnection.setName(theChildConnection.getName());
        }
    }
    
    public void removeChildNode(final Device theChildConnection) {
        if (theChildConnection != null) {            
                theChildConnection.setParentNode(null);
                (this.devices).remove(theChildConnection);
        }
    }
    
    public void moveChildNode(final Device theChildConnection, final int indxIncrement) {
        if (theChildConnection != null) {
            final int indx = this.getChildNodeIndx(theChildConnection);
                (this.devices).remove(theChildConnection);
                (this.devices).add(indx + indxIncrement, theChildConnection);
        }
    }
    
    public double getTotalMassGrams() {
        double sumMassGrams = 0.0;
        for (int i = 0; i < this.getNumChildNodes(); ++i) {
            // FIXME sumMassGrams += this.getChildNode(i).getTotalMassGrams();
        }
        return sumMassGrams;
    }
    
//    @Override
    public double getTotalNumber() {
        return 1.0;
    }
    
    public Device getDevice(final int deviceIndx) {
        if (this.devices != null && this.devices.size() > deviceIndx) {
            return (Device)(this.devices).get(deviceIndx);
        }
        return null;
    }
    
    public Device getDevice(final String theName) {
        for (final Device device : this.devices) {
            if (device.getName().equalsIgnoreCase(theName)) {
                return device;
            }
        }
        return null;
    }
    
    public int getDeviceIndx(final String theDeviceName) {
        for (final Device device : this.devices) {
            if (device.getName().equalsIgnoreCase(theDeviceName)) {
                return (this.devices).indexOf(device);
            }
        }
        return -1;
    }
    
    public HumanBody addPlacentalia(final String thePlacentaliaName) {
        final HumanBody Placentalia = new HumanBody(this, thePlacentaliaName);
        this.setPlacentalia(Placentalia);
        return Placentalia;
    }
    
    public Device addDevice(final String theDeviceName) {
        final Device device = new Device(this, theDeviceName);
        this.addDevice(device);
        return device;
    }
    
    public void setPlacentalia(final HumanBody thePlacentalia) {
        this.placentalia = thePlacentalia ;
    }
    
    public void addDevice(final Device theDevice) {
        if (theDevice != null && !this.devices.contains(theDevice)) {
            theDevice.setParentNode(this);
            (this.devices).add(theDevice);
            theDevice.setName(theDevice.getName());
        }
    }
    
    public void deletePlacentalia() {
        this.placentalia = null ;
    }
    
    public void deleteDevice(final Device theDevice) {
        if (theDevice != null) {
            theDevice.setParentNode(null);
            (this.devices).remove(theDevice);
        }
    }
    
    public ArrayList<CompartmentSBML> getListOfVolumes() {
    ArrayList<CompartmentSBML> volumeList ;
    volumeList = new ArrayList<>();
            
            return getListOfVolumes(volumeList) ;
    }
    
    public ArrayList<CompartmentSBML> getListOfVolumes(ArrayList<CompartmentSBML> volumeList) {
        if (volumeList == null) {
            volumeList = new ArrayList<>();
        }
            volumeList = this.placentalia.getListOfVolumes(volumeList);
        
        for (final Device device : this.devices) {
            volumeList = device.getListOfVolumes(volumeList);
        }
        return volumeList;
    }
    
//    @Override
    public ArrayList<CellType> getListOfCellTypes(ArrayList<CellType> cellTypeList) {
        if (cellTypeList == null) {
            cellTypeList = new ArrayList<>();
        }
        
            cellTypeList = this.placentalia.getListOfCellTypes(cellTypeList);
        
        for (final Device device : this.devices) {
            cellTypeList = (ArrayList<CellType>)device.getListOfCellTypes(cellTypeList);
        }
        return cellTypeList;
    }
        
    public void deleteSamplingLocations(final Connection parentNode) {
        final ArrayList<Connection> locationNodes = (ArrayList<Connection>)parentNode.getListOfAllConnections(null);
        for (final SamplingLocation samplingLocation : this.samplingLocations) {
            for (final Connection aNode : locationNodes) {
                samplingLocation.deleteConnection(aNode);
            }
        }
    }
    
    public static String getObjectLocationPath(final Object theObject, final boolean reversePath) {
        return getNodeLocationString(theObject, false, reversePath);
    }
    
    public static String getObjectLocationNameAndPath(final Object theObject, final boolean reversePath) {
        return getNodeLocationString(theObject, true, reversePath);
    }
    
    public static String getObjectLocationNameAndPath(final Object theObject) {
        return getNodeLocationString(theObject, true, false);
    }
    
    private static String getNodeLocationString(final Object theObject, final boolean includeObjectName, final boolean reversePath) {
        if (theObject instanceof HostModel) {
            return "";
        }
        if (theObject instanceof Device) {
            final Device device = (Device)theObject;
            return device.getName();
        }
        if (theObject instanceof HumanBody) {
            final HumanBody Placentalia = (HumanBody)theObject;
            return Placentalia.getName();
        }
        if (theObject instanceof PhysioSystem) {
            final PhysioSystem organSystem = (PhysioSystem)theObject;
            if (!includeObjectName) {
                return getNodeLocationString((Object)organSystem.getParentNode(), true, reversePath);
            }
            if (reversePath) {
                return organSystem.getName() + "." + getNodeLocationString((Object)organSystem.getParentNode(), true, reversePath);
            }
            return getNodeLocationString((Object)organSystem.getParentNode(), true, reversePath) + "." + organSystem.getName();
        }
        else if (theObject instanceof Tissue) {
            final Tissue tissue = (Tissue)theObject;
            if (!includeObjectName) {
                return getNodeLocationString((Object)tissue.getParentNode(), true, reversePath);
            }
            if (reversePath) {
                return tissue.getName() + "." + getNodeLocationString((Object)tissue.getParentNode(), true, reversePath);
            }
            return getNodeLocationString((Object)tissue.getParentNode(), true, reversePath) + "." + tissue.getName();
        }
        else if (theObject instanceof CompartmentSBML) {
            final CompartmentSBML volume = (CompartmentSBML)theObject;
            if (!includeObjectName) {
                return getNodeLocationString((Object)volume.getParentNode(), true, reversePath);
            }
            if (reversePath) {
                return volume.getName() + "." + getNodeLocationString((Object)volume.getParentNode(), true, reversePath);
            }
            return getNodeLocationString((Object)volume.getParentNode(), true, reversePath) + "." + volume.getName();
        }
        else {
            if (!(theObject instanceof CellType)) {
                return "?";
            }
            final CellType cellType = (CellType)theObject;
            if (!includeObjectName) {
                return getNodeLocationString((Object)cellType.getParentNode(), true, reversePath);
            }
            if (reversePath) {
                return cellType.getName() + "." + getNodeLocationString((Object)cellType.getParentNode(), true, reversePath);
            }
            return getNodeLocationString((Object)cellType.getParentNode(), true, reversePath) + "." + cellType.getName();
        }
    }
        
    public FluidPipe getPipe(final CompartmentSBML volume) {
        for (final FluidPipe pipe : this.pipes) {
            if (pipe.contains(volume)) {
                return pipe;
            }
        }
        return null;
    }
    
    public FluidPipe getPipe(final CompartmentSBML volume1, final CompartmentSBML volume2) {
        for (final FluidPipe pipe : this.pipes) {
            if (pipe.contains(volume1, volume2)) {
                return pipe;
            }
        }
        return null;
    }
    
    public ArrayList<FluidPipe> getPipeList(final CompartmentSBML volume) {
        final ArrayList<FluidPipe> pipeList = new ArrayList<>();
        for (final FluidPipe pipe : this.pipes) {
            if (pipe.contains(volume)) {
                pipeList.add(pipe);
            }
        }
        return pipeList;
    }
    
    public void addPipe(final FluidPipe thePipe) {
        if (thePipe != null /* && !this.pipes.contains(thePipe) */) {
            (this.pipes).add(thePipe);
        }
    }
    
    public void deletePipe(final FluidPipe thePipe) {
        if (thePipe != null) {
            (this.pipes).remove(thePipe);
        }
    }
    
    public void deletePipes(final ArrayList<FluidPipe> pipeList) {
        for (final FluidPipe pipe : pipeList) {
            this.deletePipe(pipe);
        }
    }
    
    public ArrayList<Drug> getDrugs() {
        return (ArrayList<Drug>)this.agents;
    }
    
    public void setDrugs(final ArrayList<Drug> agents) {
        this.agents = agents;
    }
    
    public ArrayList<Injection> getInjections() {
        return this.injections;
    }
    
    public void setInjections(final ArrayList<Injection> inject) {
        this.injections = inject;
    }
    
    public int getNumDrugs() {
        return this.getDrugs().size();
    }
    
    public void addDrug(final Drug agent) {
        final String theOrigName = agent.getName();
        int indx = 1;
        while (this.getDrug(agent.getName()) != null) {
            agent.setName(theOrigName + "_" + indx++);
        }
        this.getDrugs().add(agent);
        
    }
    
    public void deleteDrug(final Drug agent) {
        
        this.getDrugs().remove(agent);
    }
    
    public Drug getDrug(final Molecule molecule) {
        for (final Drug agent : this.getDrugs()) {
            if (agent.getMolecules().contains(molecule)) {
                return agent;
            }
        }
        return null;
    }
    
    public Drug getDrug(final String agentName) {
        for (final Drug agent : this.getDrugs()) {
            if (agent.getName().equalsIgnoreCase(agentName)) {
                return agent;
            }
        }
        return null;
    }
    
    public void deleteMolecule(final Molecule molecule) {
        final Drug agent = this.getDrug(molecule);
        agent.getMolecules().remove(molecule);
    }
    
    public Molecule getMolecule(final String moleculeName) {
        for (final Drug agent : this.getDrugs()) {
            final Molecule molecule = agent.getMolecule(moleculeName);
            if (molecule != null) {
                return molecule;
            }
        }
        return null;
    }
    
    public ArrayList<Molecule> getListOfMolecules() {
        final ArrayList<Molecule> list = new ArrayList<>();
        for (final Drug agent : this.getDrugs()) {
            for (final Molecule molecule : agent.getMolecules()) {
                list.add(molecule);
            }
        }
        return list;
    }
    
    public ArrayList<SamplingLocation> getSamplingLocations() {
        return this.samplingLocations;
    }
    
    public void setSamplingLocations(final ArrayList<SamplingLocation> property1) {
        this.samplingLocations = property1;
    }
    
    public int getNumSamplingLocations() {
        int returnValue = 0;
        if (this.samplingLocations != null) {
            returnValue = this.samplingLocations.size();
        }
        return returnValue;
    }
    
    public SamplingLocation getSamplingLocation(final String theName) {
        if (this.samplingLocations != null) {
            for (final SamplingLocation samplingLocation : this.getSamplingLocations()) {
                if (samplingLocation.getName().equalsIgnoreCase(theName)) {
                    return samplingLocation;
                }
            }
        }
        return null;
    }
    
    public ArrayList<SamplingLocation> getAssociatedSamplingLocations(final Connection locationNode) {
        final ArrayList<SamplingLocation> locationList = new ArrayList<>();
        Connection referencedNode ;
        for (final SamplingLocation samplingLocation : this.samplingLocations) {
            if ((referencedNode = samplingLocation.findReferencedLocation(locationNode)) != null) {
                samplingLocation.deleteConnection(referencedNode);
                if (!samplingLocation.isEmpty()) {
                    continue;
                }
                locationList.add(samplingLocation);
            }
        }
        return locationList;
    }
    
    public void deleteSamplingConnectionReferences(final Connection locationNode) {
        final ArrayList<SamplingLocation> deletionList = new ArrayList<>();
        Connection referencedNode ;
        for (final SamplingLocation samplingLocation : this.samplingLocations) {
            if ((referencedNode = samplingLocation.findReferencedLocation(locationNode)) != null) {
                samplingLocation.deleteConnection(referencedNode);
                if (!samplingLocation.isEmpty()) {
                    continue;
                }
                deletionList.add(samplingLocation);
            }
        }
        if (deletionList.size() > 0) {
            this.samplingLocations.removeAll(deletionList);
        }
    }
    
    public void deleteSamplingLocations(final ArrayList<SamplingLocation> deletionList) {
        if (deletionList != null && deletionList.size() > 0) {
            this.samplingLocations.removeAll(deletionList);
        }
    }
    
    public void addSamplingLocation(final SamplingLocation samplingLocation) {
        this.addSamplingLocation(samplingLocation, -1);
    }
    
    public void addSamplingLocation(final SamplingLocation samplingLocation, final int indx) {
        final String theOrigName = samplingLocation.getName();
        int counter = 1;
        while (this.getSamplingLocation(samplingLocation.getName()) != null) {
            samplingLocation.setName(theOrigName + "_" + counter++);
        }
        ArrayList un =  this.getSamplingLocations();
        if (indx == -1) {
            un.add(samplingLocation);
        }
        else {
            un.add(indx, samplingLocation);
        }
    }
    
    public void deleteSamplingLocation(final SamplingLocation samplingLocation) {
        this.getSamplingLocations().remove(samplingLocation);
    }
    
    public double getImplicitBoundHydrodynamicRadiusInMicrons(final Molecule molecule) {
        return Molecule.calcHydrodynamicRadiusInMicrons(69000.0 + molecule.getMolecularWeightDaltons(), 1.35);
    }
    
    public double getImplicitBoundNetCharge() {
        return -20.0;
    }
    
    public double getImplicitBoundPartitionCoefficient() {
        return 1.0;
    }
    
    public boolean isSaved() {
        for (final Drug agent : this.getDrugs()) {
            if (!agent.isSaved()) {
                return this.saved = false;
            }
        }
        return this.saved;
    }
    
    public void setSaved(final boolean saved) {
        this.saved = saved;
        if (saved) {
            for (final Drug agent : this.getDrugs()) {
                agent.setSaved(saved);
            }
        }
    }

    public ArrayList<SamplingTime> getSamplingTimes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deleteMeasurementSet(final MeasurementSet measurementSet) {
        this.getMeasurementSets().remove(measurementSet);
    }

    public void addMeasurementSet(final MeasurementSet measurementSet) {
        this.getMeasurementSets().add(measurementSet);
    }

    public void setMeasurementSets(final ArrayList<MeasurementSet> ms) {
        measurementSets = ms;
    }

    public MeasurementSet getMeasurementSet(final SamplingLocation samplingLocation) {
        for (final MeasurementSet measurementSet : this.getMeasurementSets()) {
            if (measurementSet.getSamplingLocation() == samplingLocation) {
                return measurementSet;
            }
        }
        return null;
    }

    public MeasurementSet getMeasurementSet(final SamplingLocation samplingLocation, final Molecule molecule) {
        final MeasurementSet measurementSet = this.getMeasurementSet(samplingLocation);
        if (measurementSet != null && measurementSet.getMolecule() == molecule) {
            return measurementSet;
        }
        return null;
    }

    public ArrayList<MeasurementSet> getMeasurementSets() {
        return (ArrayList<MeasurementSet>) measurementSets;
    }

    public ArrayList<MeasurementSet> getMeasurementSets(final SamplingLocation samplingLocation, final Molecule molecule) {
        final ArrayList<MeasurementSet> matchingSets = new ArrayList<>();
        for (final MeasurementSet measurementSet : this.getMeasurementSets()) {
            if (measurementSet.getSamplingLocation() == samplingLocation && measurementSet.getMolecule() == molecule) {
                matchingSets.add(measurementSet);
            }
        }
        return matchingSets;
    }

    public void deleteMeasurementSetForMolecule(final Molecule molecule) {
        final ArrayList<MeasurementSet> deletionList = new ArrayList<>();
        for (final MeasurementSet measurementSet : measurementSets) {
            if (measurementSet.getMolecule() == molecule) {
                deletionList.add(measurementSet);
            }
        }
        if (deletionList.size() > 0) {
            measurementSets.removeAll(deletionList);
        }
    }

    public void deleteMeasurementSetAtLocation(final SamplingLocation referenceLocation) {
        final ArrayList<MeasurementSet> deletionList = new ArrayList<>();
        for (final MeasurementSet measurementSet : measurementSets) {
            if (measurementSet.getSamplingLocation() == referenceLocation) {
                deletionList.add(measurementSet);
            }
        }
        if (deletionList.size() > 0) {
            measurementSets.removeAll(deletionList);
        }
    }

    void deleteMeasurementSetSamplingTimeReferences(final SamplingTime referenceTime) {
        final ArrayList<MeasurementSet> deletionList = new ArrayList<>();
        for (final MeasurementSet measurementSet : measurementSets) {
            measurementSet.deleteConcentrationSampleTimeReferences(referenceTime);
            if (measurementSet.getNumSamplingTimes() < 1) {
                deletionList.add(measurementSet);
            }
        }
        if (deletionList.size() > 0) {
            measurementSets.removeAll(deletionList);
        }
    }

    public void sortSamplingTimes() {
        final SamplingTime[] sortedTimes = this.getSamplingTimesSortedByTime();
        samplingTimes.clear();
        for (int i = 0; i < sortedTimes.length; ++i) {
            samplingTimes.add(sortedTimes[i]);
        }
    }

    public void setSamplingTimes(final ArrayList<SamplingTime> spt) {
        samplingTimes = spt;
    }

    public void setSamplingTimes(final double[] samplingTimeInMinutes) {
        samplingTimes.clear();
        for (int i = 0; i < samplingTimeInMinutes.length; ++i) {
            samplingTimes.add(new SamplingTime(samplingTimeInMinutes[i]));
        }
        this.sortSamplingTimes();
    }

    public SamplingTime getSamplingTime(final double timeInMinutes) {
        for (final SamplingTime samplingTime : getSamplingTimes()) {
            if (samplingTime.equals(timeInMinutes)) {
                return samplingTime;
            }
        }
        return null;
    }

    public SamplingTime addSamplingTime(final double samplingTimeInMinutes) {
        return this.addSamplingTime(new SamplingTime(samplingTimeInMinutes));
    }

    public SamplingTime addSamplingTime(final SamplingTime samplingTime) {
        final SamplingTime equivalentSamplingTime = this.getSamplingTime(samplingTime.getTimeInMinutes());
        if (equivalentSamplingTime != null) {
            return equivalentSamplingTime;
        }
        samplingTimes.add(samplingTime);
        this.sortSamplingTimes();
        return samplingTime;
    }

    public void clearSamplingTimes() {
        if (samplingTimes != null && !samplingTimes.isEmpty()) {
            samplingTimes.clear();
        }
    }

    public SamplingTime getInitialSamplingTime() {
        SamplingTime initialTime = null;
        double initialTimeInMinutes = 0.0;
        for (final SamplingTime samplingTime : getSamplingTimes()) {
            if (initialTime == null || samplingTime.getTimeInMinutes() < initialTimeInMinutes) {
                initialTime = samplingTime;
                initialTimeInMinutes = initialTime.getTimeInMinutes();
            }
        }
        return initialTime;
    }

    public SamplingTime[] getSamplingTimesSortedByTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<SamplingTime> getSamplingTimes(final double startTime, final double endTime) {
        final ArrayList<SamplingTime> matchingTimes = new ArrayList<>();
        final SamplingTime[] sortedTimes = this.getSamplingTimesSortedByTime();
        for (int i = 0; i < sortedTimes.length; ++i) {
            final double time = sortedTimes[i].getTimeInMinutes();
            if (time >= startTime && time <= endTime) {
                matchingTimes.add(sortedTimes[i]);
            }
        }
        return matchingTimes;
    }

    public SamplingTime getOrAddSamplingTime(final double timeInMinutes) {
        for (final SamplingTime samplingTime : samplingTimes) {
            if (samplingTime.equals(timeInMinutes)) {
                return samplingTime;
            }
        }
        final SamplingTime samplingTime2 = new SamplingTime(timeInMinutes);
        this.addSamplingTime(samplingTime2);
        return samplingTime2;
    }

    public int getNumSamplingTimes() {
        int returnValue = 0;
        if (samplingTimes != null) {
            returnValue = samplingTimes.size();
        }
        return returnValue;
    }

    public void deleteSamplingTime(final SamplingTime samplingTime) {
        samplingTimes.remove(samplingTime);
        deleteMeasurementSetSamplingTimeReferences(samplingTime);
    }

    public void deleteSamplingTime(final double samplingTimeInMinutes) {
        final SamplingTime samplingTime = this.getSamplingTime(samplingTimeInMinutes);
        this.deleteSamplingTime(samplingTime);
    }
}
