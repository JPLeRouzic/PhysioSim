// 
// A very basic connection between two points.
// Other classes develop this abstract class
// 

package datamodel;

import datamodel.type.ReferenceVolumeType;
import java.util.ArrayList;
import utility.XMLIdManager;

public abstract class Connection 
{
    protected String notes;
    protected String identity;
    protected String name;

    protected Connection parent;
    protected boolean solverCompartmentDefined;
    protected ReferenceVolumeType refVolumeForScaling;
    
    protected CompartmentSBML fromVolume;
    protected CompartmentSBML toVolume;
    
    // those two fields store temporaly the compartment name until the parsing
    // is finished and pointers to real compartments can be used
    // pointers to real compartments can not be used until all compartments have been parsed
    public String setToVolumeStr;
    public String setFromVolumeStr;
    
    public Connection() {
        this.notes = "";
        this.fromVolume = null;
        this.toVolume = null;
        this.refVolumeForScaling = ReferenceVolumeType.UNDEFINED;
        this.setIdentity(XMLIdManager.getXMLId((Class)Connection.class));
    }
    
    public Connection(final Connection theParent, final String theName) {
        this.notes = "";
        this.parent = null;
        this.name = "";
        this.solverCompartmentDefined = false;
        this.parent = theParent;
        this.name = theName;
        this.setIdentity(XMLIdManager.getXMLId((Class)Connection.class));
    }
    
    public Connection(final String theName, final CompartmentSBML theFromVolume, final CompartmentSBML theToVolume) {
        this.notes = "";
        this.fromVolume = null;
        this.toVolume = null;
        this.refVolumeForScaling = ReferenceVolumeType.UNDEFINED;
        this.name = theName;
        this.fromVolume = theFromVolume;
        this.toVolume = theToVolume;
        this.setIdentity(XMLIdManager.getXMLId((Class)Connection.class));
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
    
    public void setName(final String theName) {
        this.name = theName;
    }
    
    public Connection getRoot() {
        Connection Connection;
        for (Connection = this; Connection.getParentNode() != null; Connection = Connection.getParentNode()) {}
        return Connection;
    }
       
    public boolean hasAncestor(final Connection ancestor) {
        for (Connection Connection = this.getParentNode(); Connection != null; Connection = Connection.getParentNode()) {
            if (Connection == ancestor) {
                return true;
            }
        }
        return false;
    }
    
    public void setToVolume(final CompartmentSBML theToVolume) {
        this.toVolume = theToVolume;
    }
    
    public void setFromVolume(final CompartmentSBML theFromVolume) {
        this.fromVolume = theFromVolume;
    }
    
    public Connection getParentNode() {
        return this.parent;
    }
    
    public void setParentNode(final Connection theParent) {
        this.parent = theParent;
    }
    
    public CompartmentSBML getToVolume() {
        return this.toVolume;
    }
    
    public CompartmentSBML getFromVolume() {
        return this.fromVolume;
    }
    
    public CompartmentSBML getReferenceVolume() throws Exception {
        if (this.getRefVolumeForScaling() == ReferenceVolumeType.FROM_SPACE) {
            return this.getFromVolume();
        }
        if (this.getRefVolumeForScaling() == ReferenceVolumeType.TO_SPACE) {
            return this.getToVolume();
        }
        throw new Exception("Undefined Scaling Reference Volume Definition.");
    }
    
    public ReferenceVolumeType getRefVolumeForScaling() {
        return this.refVolumeForScaling;
    }
    
    public void setRefVolumeForScaling(final ReferenceVolumeType theRefVolumeForScaling) {
        this.refVolumeForScaling = theRefVolumeForScaling;
    }
    
    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(final String notes) {
        this.notes = notes;
    }
    
    public void addNote(final String note) {
        if (this.getNotes().length() > 0) {
            this.setNotes(this.getNotes() + "\r\n" + note);
        }
        else {
            this.setNotes(note);
        }
    }
    
    public String getReferenceConnectionClassName() throws Exception {
        if (this.getRefVolumeForScaling() == ReferenceVolumeType.FROM_SPACE) {
            return this.getFromVolume().getParentNode().getClassNameShortVersion();
        }
        if (this.getRefVolumeForScaling() == ReferenceVolumeType.TO_SPACE) {
            return this.getToVolume().getParentNode().getClassNameShortVersion();
        }
        throw new Exception("Undefined Scaling Reference Volume Definition.");
    }
    
    public String getClassNameShortVersion() {
        final String fullClassName = this.getClass().getName();
        return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
    }
    
    public boolean contains(final CompartmentSBML volume) {
        return volume != null && 
                (volume == this.getFromVolume() || 
                volume == this.getToVolume());
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public ArrayList<Connection> getChildNodes(final String childNodeName) {
        final ArrayList<Connection> vector = new ArrayList<Connection>();
        for (int i = 0; i < this.getNumChildNodes(); ++i) {
            if (this.getChildNode(i).getName().equalsIgnoreCase(childNodeName)) {
                vector.add(this.getChildNode(i));
            }
        }
        return vector;
    }
    
    public ArrayList<Connection> getListOfAllConnections(ArrayList<Connection> locationNodes) {
        if (locationNodes == null) {
            locationNodes = new ArrayList<Connection>();
        }
        locationNodes.add(this);
        for (int i = 0; i < this.getNumChildNodes(); ++i) {
            this.getChildNode(i).getListOfAllConnections((ArrayList)locationNodes);
        }
        return locationNodes;
    }

    void moveChildNode(Connection childConnection, int indxIncrement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    ArrayList<CompartmentSBML> getListOfVolumes(ArrayList<CompartmentSBML> volumeList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private int getNumChildNodes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Connection getChildNode(final int childNodeIndx) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<CompartmentSBML> getListOfVolumes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    }
