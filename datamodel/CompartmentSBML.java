/*
 * A compartment in SBML represents a bounded volume in which species are located
 * and where a reaction occurs
 */

package datamodel;

import datamodel.perturbation.SamplingLocation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import parser.sbml.ModelSBML;
import parser.sbml.ParameterSBML;
import parser.sbml.ReactionsSBML;
import parser.sbml.SpeciesSBML;
import utility.XMLIdManager;

/**
 *
 * @author Jean-Pierre Le Rouzic https://padiracinnovation.org/feedback/
 */
public class CompartmentSBML extends Connection {

    // SBML
    public String outside; // is from Tissue where it is named "partOf"
    public String units ;
    public String constant;
    public String size; // volume
    private String metaid;
    public String sboTerm;
    private String dimensions;
    
    public int solverCompArrayIndx;
    public boolean isUsedBySolver;

    ArrayList<ConcentrationSample> concentrations;  
    
    // annotations
    public String spatialDimensions;
    public String parentIdRef;
    String unitCenterToEdgeDistanceMicrons; 
    Double unitVolumeML; 
    String volumeFractionParentLocation; 
    public double volume; 
    String averageVolumePerCellML;
    public String mobility;
    String volumeFractionMineral;
    String volumeFractionRNA;
    
    // which model does it belong?
    public ModelSBML modl ;
    
    public CompartmentSBML(ModelSBML model)
    {
        solverCompArrayIndx = -1;
        isUsedBySolver = false;
        modl = model ;
        setIdentity(XMLIdManager.getXMLId((Class) CompartmentSBML.class));        
    }

    CompartmentSBML(ModelSBML model, String theVolumeName) {
        this.setName(theVolumeName);
        solverCompArrayIndx = -1;
        isUsedBySolver = false;
        modl = model ;
        setIdentity(XMLIdManager.getXMLId((Class) CompartmentSBML.class));        
    }
            
    /**
     * 
     * @param i_metaid
     * @param i_id
     * @param i_name
     * @param i_sboTerm
     * @param i_const 
     * @param i_dims 
     * @param size 
     * @param i_constant 
     * @param i_outside 
     */
/* this is missing for standard SBML           
     * CDATA #IMPLIED spatialDimensions (2.4)
     * CDATA #IMPLIED size
     * CDATA #IMPLIED constant
     * CDATA #IMPLIED outside
*/         
    public CompartmentSBML(String i_metaid, String i_id, String i_name,  
            String i_sboTerm, String i_const, String i_dims, String i_size, 
            String i_constant, String i_outside) {
    setIdentity(i_id) ;
    constant = i_const ;
    sboTerm = i_sboTerm ;
    metaid = i_metaid;
    setName(i_name) ;
    dimensions = i_dims ;
    size = i_size ;
    constant = i_constant ;
    outside = i_outside ;
    solverCompArrayIndx = -1;
    isUsedBySolver = false;
    setIdentity(XMLIdManager.getXMLId((Class) CompartmentSBML.class));        
    }

    public final int getSolverCompArrayIndx() {
        return this.solverCompArrayIndx;
    }
    
    public boolean getUsedBySolver() {
        return this.isUsedBySolver ;
    }

    public boolean isUsedBySolver() {
        return isUsedBySolver;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(final double spac) {
        volume = spac;
    }

    public CompartmentSBML copy(
            final Connection theParent, 
            final ArrayList<Connection> connections, 
            final ArrayList<SamplingLocation> samplingLocations, 
            final ArrayList<Connection> refLocations
            ) {
        final CompartmentSBML volume ;
        volume = new CompartmentSBML(this.modl);
        volume.setIdentity(XMLIdManager.getXMLId((Class)CompartmentSBML.class));
        volume.notes = this.notes;
        volume.volumeFractionParentLocation = this.volumeFractionParentLocation;
        volume.unitVolumeML = this.unitVolumeML;
        volume.unitCenterToEdgeDistanceMicrons = this.unitCenterToEdgeDistanceMicrons;
        for (final Connection connection : connections) {
            if (connection.getFromVolume() == this) {
                connection.setFromVolume(volume);
            }
            if (connection.getToVolume() == this) {
                connection.setToVolume(volume);
            }
        }
        if (refLocations.contains(this)) {
            refLocations.remove(this);
            refLocations.add((Connection)volume);
        }
        for (final SamplingLocation samplingLocation : samplingLocations) {
            samplingLocation.updateConnectionFC(this, volume);
        }

        if (theParent != null) {

        }
        return volume;
    }
    
    @Override
    public ArrayList<CompartmentSBML> getListOfVolumes(ArrayList<CompartmentSBML> volumeList) {
        if (volumeList == null) {
            volumeList = new ArrayList<>();
            volumeList.add(this);
        }
        return volumeList;
    }
    
    public ArrayList<CellType> getListOfCellTypes(ArrayList<CellType> cellTypeList) {
        if (cellTypeList == null) {
            cellTypeList = new ArrayList<>();
        }
       return cellTypeList;
    }
    
    public double getTotalNumber() {
        // FIXME
        return -1 ;
    } 
    
    public double getTotalVolumeML() {
        // FIXME
        return -1 ;
    }

        public final void setMetaid(final String str) {
        this.metaid = str;
    }
    
        public final String getMetaid() {
        return metaid ;
    }

    public void setSolverCompArrayIndx(int compIndx) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setUsedBySolver(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Object getSpace() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Collection getConcentrations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
