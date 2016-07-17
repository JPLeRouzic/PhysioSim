// 
//  
// 

package datamodel.perturbation;

import datamodel.CellType;
import datamodel.HostModel;
import datamodel.HumanBody;
import datamodel.Connection;
import datamodel.PhysioSystem;
import datamodel.Tissue;
import java.util.Iterator;
import utility.XMLIdManager;
import java.util.ArrayList;
import java.util.Collection;
import datamodel.CompartmentSBML;

public class SamplingLocation 
{
    
    protected String name;
    protected String identity;
    protected ArrayList<Connection> includedNodes;
    protected ArrayList<Connection> excludedNodes;
    
    protected ArrayList<Connection> includedVolumesLN ;
    protected ArrayList<CompartmentSBML> includedVolumesFC ;
    protected ArrayList<Device> includedVolumesDV ;
    protected ArrayList<Tissue> includedVolumesTS ;
    protected ArrayList<CellType> includedVolumesCT ;
    protected ArrayList<HostModel> includedVolumesHM ;
    protected ArrayList<HumanBody> includedVolumesHB ;
    protected ArrayList<PhysioSystem> includedVolumesPS ;
       
    protected ArrayList<Connection> excludedVolumesLN;
    protected ArrayList<CompartmentSBML> excludedVolumesFC;
    protected ArrayList<Device> excludedVolumesDV ;
    protected ArrayList<Tissue> excludedVolumesTS ;
    protected ArrayList<CellType> excludedVolumesCT ;
    protected ArrayList<HostModel> excludedVolumesHM ;
    protected ArrayList<HumanBody> excludedVolumesHB ;
    protected ArrayList<PhysioSystem> excludedVolumesPS ;
    
    protected String notes;
    private ArrayList<CompartmentSBML> samplingVolumes;
    private boolean setupSamplingVolumes;
    
    
    public SamplingLocation() {
        this.includedNodes = new ArrayList();
        this.includedVolumesFC = new ArrayList() ;
        this.includedVolumesDV = new ArrayList() ;
        this.includedVolumesTS = new ArrayList() ;
        this.includedVolumesCT = new ArrayList() ;
        this.includedVolumesLN = new ArrayList() ;
        this.includedVolumesHM = new ArrayList() ;
        this.includedVolumesHB = new ArrayList() ;
        this.includedVolumesPS = new ArrayList() ;

        this.excludedNodes = new ArrayList();
        this.excludedVolumesFC = new ArrayList();
        this.excludedVolumesDV = new ArrayList();
        this.excludedVolumesTS = new ArrayList();
        this.excludedVolumesCT = new ArrayList();
        this.excludedVolumesLN = new ArrayList();
        this.excludedVolumesHM = new ArrayList();
        this.excludedVolumesHB = new ArrayList();
        this.excludedVolumesPS = new ArrayList();
        
        this.notes = "";
        this.samplingVolumes = null;
        this.setupSamplingVolumes = true;
        this.setIdentity(XMLIdManager.getXMLId((Class)SamplingLocation.class));
    }
    
    public SamplingLocation(final String name) {
        this();
        this.name = name;
    }
    
    public SamplingLocation copy() {
        final SamplingLocation samplingLocation = new SamplingLocation();
        samplingLocation.setIdentity(XMLIdManager.getXMLId((Class)SamplingLocation.class));
        samplingLocation.notes = this.notes;
        
        // Connection
        for (final Connection locationNode : this.getIncludedNodesLN()) {
            samplingLocation.getIncludedNodesLN().add(locationNode);
        }
        for (final Connection locationNode : this.getExcludedNodesLN()) {
            samplingLocation.getExcludedNodesLN().add(locationNode);
        }
        
        // Tissues
        for (final Tissue volume : this.getIncludedVolumesTS()) {
            samplingLocation.getIncludedVolumesTS().add(volume);
        }
        for (final Tissue volume : this.getExcludedVolumesTS()) {
            samplingLocation.getExcludedVolumesTS().add(volume);
        }
        
        // Devices
        for (final Device volume : this.getIncludedVolumesDV()) {
            samplingLocation.getIncludedVolumesDV().add(volume);
        }
        for (final Device volume : this.getExcludedVolumesDV()) {
            samplingLocation.getExcludedVolumesDV().add(volume);
        }
        
        // CompartmentSBML
        for (final CompartmentSBML volume : this.getIncludedVolumesFC()) {
            samplingLocation.getIncludedVolumesFC().add(volume);
        }
        for (final CompartmentSBML volume : this.getExcludedVolumesFC()) {
            samplingLocation.getExcludedVolumesFC().add(volume);
        }
        
        // CellTypes
        for (final CellType volume : this.getIncludedVolumesCT()) {
            samplingLocation.getIncludedVolumesCT().add(volume);
        }
        for (final CellType volume : this.getExcludedVolumesCT()) {
            samplingLocation.getExcludedVolumesCT().add(volume);
        }
        
        // HostModel
        for (final HostModel volume : this.getIncludedVolumesHM()) {
            samplingLocation.getIncludedVolumesHM().add(volume);
        }
        for (final HostModel volume : this.getExcludedVolumesHM()) {
            samplingLocation.getExcludedVolumesHM().add(volume);
        }
        
        // HumanBody
        for (final HumanBody volume : this.getIncludedVolumesHB()) {
            samplingLocation.getIncludedVolumesHB().add(volume);
        }
        for (final HumanBody volume : this.getExcludedVolumesHB()) {
            samplingLocation.getExcludedVolumesHB().add(volume);
        }
        
        // PhysioSystem
        for (final PhysioSystem volume : this.getIncludedVolumesPS()) {
            samplingLocation.getIncludedVolumesPS().add(volume);
        }
        for (final PhysioSystem volume : this.getExcludedVolumesPS()) {
            samplingLocation.getExcludedVolumesPS().add(volume);
        }
        
        return samplingLocation;
    }
    
    public void updateConnectionTS(Tissue oldConnection, Tissue newConnection) {
        this.setupSamplingVolumes = true;
        
        updateConnection_1(oldConnection, newConnection) ;
            
            if (this.getIncludedVolumesTS().contains(oldConnection)) {
                (this.getIncludedVolumesTS()).remove(oldConnection);
                (this.getIncludedVolumesTS()).add(newConnection);
            }
            if (this.getExcludedVolumesTS().contains(oldConnection)) {
                (this.getExcludedVolumesTS()).remove(oldConnection);
                (this.getExcludedVolumesTS()).add(newConnection);
            }
    }

    public void updateConnectionPS(PhysioSystem oldConnection, PhysioSystem newConnection) {
        this.setupSamplingVolumes = true;
        
        updateConnection_1(oldConnection, newConnection) ;
            
            if (this.getIncludedVolumesPS().contains(oldConnection)) {
                (this.getIncludedVolumesPS()).remove(oldConnection);
                (this.getIncludedVolumesPS()).add(newConnection);
            }
            if (this.getExcludedVolumesPS().contains(oldConnection)) {
                (this.getExcludedVolumesPS()).remove(oldConnection);
                (this.getExcludedVolumesPS()).add(newConnection);
            }
    }

    public void updateConnectionHM(HostModel oldConnection, HostModel newConnection) {
        this.setupSamplingVolumes = true;
        
        updateConnection_1(oldConnection, newConnection) ;
            
            if (this.getIncludedVolumesHM().contains(oldConnection)) {
                (this.getIncludedVolumesHM()).remove(oldConnection);
                (this.getIncludedVolumesHM()).add(newConnection);
            }
            if (this.getExcludedVolumesHM().contains(oldConnection)) {
                (this.getExcludedVolumesHM()).remove(oldConnection);
                (this.getExcludedVolumesHM()).add(newConnection);
            }
    }

    public void updateConnectionHB(HumanBody oldConnection, HumanBody newConnection) {
        this.setupSamplingVolumes = true;
        
        updateConnection_1(oldConnection, newConnection) ;
            
            if (this.getIncludedVolumesHB().contains(oldConnection)) {
                (this.getIncludedVolumesHB()).remove(oldConnection);
                (this.getIncludedVolumesHB()).add(newConnection);
            }
            if (this.getExcludedVolumesHB().contains(oldConnection)) {
                (this.getExcludedVolumesHB()).remove(oldConnection);
                (this.getExcludedVolumesHB()).add(newConnection);
            }
    }

    public void updateConnectionDV(final Device oldConnection, final Device newConnection) {
        this.setupSamplingVolumes = true;
        
        updateConnection_1(oldConnection, newConnection) ;
            
            if (this.getIncludedVolumesDV().contains(oldConnection)) {
                (this.getIncludedVolumesDV()).remove(oldConnection);
                (this.getIncludedVolumesDV()).add(newConnection);
            }
            if (this.getExcludedVolumesDV().contains(oldConnection)) {
                (this.getExcludedVolumesDV()).remove(oldConnection);
                (this.getExcludedVolumesDV()).add(newConnection);
            }
    }
    
    public void updateConnectionCT(final CellType oldConnection, final CellType newConnection) {
        this.setupSamplingVolumes = true;
        
        updateConnection_1(oldConnection, newConnection) ;
            
            if (this.getIncludedVolumesCT().contains(oldConnection)) {
                (this.getIncludedVolumesCT()).remove(oldConnection);
                (this.getIncludedVolumesCT()).add(newConnection);
            }
            if (this.getExcludedVolumesCT().contains(oldConnection)) {
                (this.getExcludedVolumesCT()).remove(oldConnection);
                (this.getExcludedVolumesCT()).add(newConnection);
            }
    }
    
    public void updateConnectionFC(
            final CompartmentSBML oldConnection, 
            final CompartmentSBML newConnection) {
        this.setupSamplingVolumes = true;
        
        updateConnection_1(oldConnection, newConnection) ;
            if (this.getIncludedVolumesFC().contains(oldConnection)) {
                (this.getIncludedVolumesFC()).remove(oldConnection);
                (this.getIncludedVolumesFC()).add(newConnection);
            }
            if (this.getExcludedVolumesFC().contains(oldConnection)) {
                (this.getExcludedVolumesFC()).remove(oldConnection);
                (this.getExcludedVolumesFC()).add(newConnection);
            }
    }
    
    public void updateConnection_1(final Connection oldConnection, final Connection newConnection) {
        this.setupSamplingVolumes = true;
        if (this.getIncludedNodesLN().contains(oldConnection)) {
            (this.getIncludedNodesLN()).remove(oldConnection);
            (this.getIncludedNodesLN()).add(newConnection);
        }
        if (this.getExcludedNodesLN().contains(oldConnection)) {
            (this.getExcludedNodesLN()).remove(oldConnection);
            (this.getExcludedNodesLN()).add(newConnection);
        }

    }
    
    public void removeAllConnections() {
        this.setupSamplingVolumes = true;
        (this.getIncludedNodesLN()).clear();
        (this.getExcludedNodesLN()).clear();
        (this.getIncludedVolumesFC()).clear();
        (this.getExcludedVolumesFC()).clear();
        (this.getIncludedVolumesTS()).clear();
        (this.getExcludedVolumesTS()).clear();
        (this.getIncludedVolumesPS()).clear();
        (this.getExcludedVolumesPS()).clear();
        (this.getIncludedVolumesDV()).clear();
        (this.getExcludedVolumesDV()).clear();
        (this.getIncludedVolumesCT()).clear();
        (this.getExcludedVolumesCT()).clear();
        (this.getIncludedVolumesHM()).clear();
        (this.getExcludedVolumesHM()).clear();
        (this.getIncludedVolumesHB()).clear();
        (this.getExcludedVolumesHB()).clear();
    }
    
    public ArrayList<Connection> getIncludedVolumesLN() {
        this.setupSamplingVolumes = true;
        return this.includedVolumesLN;
    }
    
    public ArrayList<Connection> getExcludedVolumesLN() {
        this.setupSamplingVolumes = true;
        return this.excludedVolumesLN;
    }
    
    public ArrayList<HostModel> getIncludedVolumesHM() {
        this.setupSamplingVolumes = true;
        return this.includedVolumesHM;
    }
    
    public ArrayList<HostModel> getExcludedVolumesHM() {
        this.setupSamplingVolumes = true;
        return this.excludedVolumesHM;
    }
    
    public ArrayList<HumanBody> getIncludedVolumesHB() {
        this.setupSamplingVolumes = true;
        return this.includedVolumesHB;
    }
    
    public ArrayList<HumanBody> getExcludedVolumesHB() {
        this.setupSamplingVolumes = true;
        return this.excludedVolumesHB;
    }
    
    public ArrayList<CompartmentSBML> getIncludedVolumesFC() {
        this.setupSamplingVolumes = true;
        return this.includedVolumesFC;
    }
    
    public ArrayList<CompartmentSBML> getExcludedVolumesFC() {
        this.setupSamplingVolumes = true;
        return this.excludedVolumesFC;
    }
    
    public ArrayList<Tissue> getIncludedVolumesTS() {
        this.setupSamplingVolumes = true;
        return (ArrayList<Tissue>)this.includedVolumesTS;
    }
    
    public ArrayList<Tissue> getExcludedVolumesTS() {
        this.setupSamplingVolumes = true;
        return (ArrayList<Tissue>)this.excludedVolumesTS;
    }
    
    public ArrayList<PhysioSystem> getIncludedVolumesPS() {
        this.setupSamplingVolumes = true;
        return this.includedVolumesPS;
    }
    
    public ArrayList<PhysioSystem> getExcludedVolumesPS() {
        this.setupSamplingVolumes = true;
        return this.excludedVolumesPS;
    }
    
    public ArrayList<Device> getIncludedVolumesDV() {
        this.setupSamplingVolumes = true;
        return (ArrayList<Device>)this.includedVolumesDV;
    }
    
    public ArrayList<Device> getExcludedVolumesDV() {
        this.setupSamplingVolumes = true;
        return (ArrayList<Device>)this.excludedVolumesDV;
    }
    
    public ArrayList<CellType> getIncludedVolumesCT() {
        this.setupSamplingVolumes = true;
        return (ArrayList<CellType>)this.includedVolumesCT;
    }
    
    public ArrayList<CellType> getExcludedVolumesCT() {
        this.setupSamplingVolumes = true;
        return (ArrayList<CellType>)this.excludedVolumesCT;
    }
    
    public boolean isEmpty() {
        return this.includedNodes.isEmpty() && this.excludedNodes.isEmpty() && 
                this.includedVolumesFC.isEmpty() && this.excludedVolumesFC.isEmpty() &&
                this.includedVolumesTS.isEmpty() && this.excludedVolumesTS.isEmpty() &&
                this.includedVolumesDV.isEmpty() && this.excludedVolumesDV.isEmpty() &&
                this.includedVolumesCT.isEmpty() && this.excludedVolumesCT.isEmpty();
    }
    
    public void deleteConnection(final Connection locationNode) {
        this.setupSamplingVolumes = true;
        (this.getIncludedNodesLN()).remove(locationNode);
        (this.getExcludedNodesLN()).remove(locationNode);
        (this.getIncludedVolumesFC()).remove(locationNode);
        (this.getExcludedVolumesFC()).remove(locationNode);
        (this.getIncludedVolumesTS()).remove(locationNode);
        (this.getExcludedVolumesTS()).remove(locationNode);
        (this.getIncludedVolumesDV()).remove(locationNode);
        (this.getExcludedVolumesDV()).remove(locationNode);
        (this.getIncludedVolumesCT()).remove(locationNode);
        (this.getExcludedVolumesCT()).remove(locationNode);
    }
    
    public ArrayList<Connection> getIncludedNodesLN() {
        this.setupSamplingVolumes = true;
        return (ArrayList<Connection>)this.includedNodes;
    }
    
    public void setIncludedNodes(final ArrayList<Connection> property1) {
        this.setupSamplingVolumes = true;
        this.includedNodes = property1;
    }
    
    public ArrayList<Connection> getExcludedNodesLN() {
        this.setupSamplingVolumes = true;
        return (ArrayList<Connection>)this.excludedNodes;
    }
    
    public void setExcludedNodes(final ArrayList<Connection> property1) {
        this.setupSamplingVolumes = true;
        this.includedNodes = property1;
    }

    private boolean contains(final Collection objList, final Connection locationNode) {
        return objList == null || objList.isEmpty() || objList.contains(locationNode);
    }
    
    public boolean contains(final Connection locationNode) {
        final Connection referencedNode = null;
        return this.contains(
                this.includedNodes, locationNode) || 
                this.contains(this.excludedNodes, locationNode) || 
                this.contains(this.includedVolumesLN, locationNode) || 
                this.contains(this.excludedVolumesLN, locationNode
                );
    }
    
    private Connection findReferencedLocation(
            final ArrayList<Connection> objList, 
            final Connection locationNode) {
        if (objList == null || objList.isEmpty()) {
            return null;
        }
        if (objList.contains(locationNode)) {
            return locationNode;
        }
        for (final Connection nodeloc : objList) {
            final Connection node = nodeloc;
            if (node.hasAncestor(locationNode)) {
                return node;
            }
        }
        return null;
    }
    
    public Connection findReferencedLocation(final Connection locationNode) {
        Connection referencedNode = null;
        if ((referencedNode = this.findReferencedLocation(this.includedNodes, locationNode)) != null) {
            return referencedNode;
        }
        if ((referencedNode = this.findReferencedLocation(this.excludedNodes, locationNode)) != null) {
            return referencedNode;
        }
        if ((referencedNode = this.findReferencedLocation(this.includedVolumesLN, locationNode)) != null) {
            return referencedNode;
        }
        if ((referencedNode = this.findReferencedLocation(this.excludedVolumesLN, locationNode)) != null) {
            return referencedNode;
        }
        return null;
    }
    
    public boolean remove(final Connection locationNode) {
        this.setupSamplingVolumes = true;
        return this.includedNodes.remove(locationNode) || 
                this.excludedNodes.remove(locationNode) || 
                this.includedVolumesLN.remove(locationNode) || 
                this.excludedVolumesLN.remove(locationNode);
    }
    
    public void addToIncludedNodes(final Connection locationNode) {
        this.setupSamplingVolumes = true;
        this.remove(locationNode);
        this.includedNodes.add(locationNode);
    }
    
    public void addToIncludedVolumes(final CompartmentSBML volume) {
        this.setupSamplingVolumes = true;
        this.remove(volume);
        this.includedVolumesFC.add(volume);
    }
    
    public void addToExcludedNodes(final Connection locationNode) {
        this.setupSamplingVolumes = true;
        this.remove(locationNode);
        this.excludedNodes.add(locationNode);
    }
    
    public void addToExcludedVolumes(final CompartmentSBML volume) {
        this.setupSamplingVolumes = true;
        this.remove(volume);
        this.excludedVolumesFC.add(volume);
    }
    
    public String getIdentity() {
        return this.identity;
    }
    
    public final void setIdentity(final String xmlId) {
        this.identity = xmlId;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
    
    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(final String notes) {
        this.notes = notes;
    }
    
    private void doSamplingVolumeSetup() {
        this.samplingVolumes = new ArrayList();
        for (final CompartmentSBML volume : this.getIncludedVolumesFC()) {
            if (!this.samplingVolumes.contains(volume)) {
                this.samplingVolumes.add(volume);
            }
        }
        for (final Connection includedConnection : this.getIncludedNodesLN()) {
            final ArrayList<CompartmentSBML> includedVolumes = includedConnection.getListOfVolumes();
            for (final CompartmentSBML volume2 : includedVolumes) {
                if (!this.samplingVolumes.contains(volume2)) {
                    this.samplingVolumes.add(volume2);
                }
            }
        }
        for (final CompartmentSBML volume : this.getExcludedVolumesFC()) {
            this.samplingVolumes.remove(volume);
        }
        for (final Connection excludeConnection : this.getExcludedNodesLN()) {
            final ArrayList<CompartmentSBML> excludedVolumes = excludeConnection.getListOfVolumes();
            for (final CompartmentSBML volume2 : excludedVolumes) {
                this.samplingVolumes.remove(volume2);
            }
        }
        this.setupSamplingVolumes = false;
    }
    
    public ArrayList<CompartmentSBML> getSamplingLocationVolumes() {
        if (this.setupSamplingVolumes || this.samplingVolumes == null) {
            this.doSamplingVolumeSetup();
        }
        return (ArrayList<CompartmentSBML>)this.samplingVolumes;
    }
    
    public boolean isModelMatch(final SamplingLocation anotherLocation) {
        if (anotherLocation == null) {
            return false;
        }
        final String anotherId = anotherLocation.getIdentity();
        final String anotherName = anotherLocation.getName();
        return this.identity.equals(anotherId) || this.name.equals(anotherName);
    }
}
