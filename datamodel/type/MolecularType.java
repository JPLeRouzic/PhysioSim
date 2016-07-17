// 
//  
// 

package datamodel.type;

import datamodel.Connection;
import datamodel.perturbation.Molecule;
import datamodel.perturbation.MoleculeAddress;
import utility.XMLIdManager;
import java.util.ArrayList;
import java.util.Collection;

// RootElement
public class MolecularType 
{
    protected Collection<MolecularInstance> instances;
    protected Collection<MolecularSubstrate> substrates;
    // IDREF
    // Attribute(name = "moleculeIdRef", required = false)
    protected Molecule molecule;
    // Element(required = false)
    protected MoleculeAddress moleculeAddress;
    // Attribute(required = false)
    protected boolean mobility;
    // Attribute(required = false)
    protected boolean mobileOnlyForDefinedInstances;
    private static final long serialVersionUID = 65020000001L; // JPLR
    
    public MolecularType() {
        this.instances = new ArrayList();
        this.substrates = new ArrayList();
        this.moleculeAddress = new MoleculeAddress();
        this.mobility = true;
        this.mobileOnlyForDefinedInstances = true;
        this.setXmlId(XMLIdManager.getXMLId((Class)MolecularType.class));
    }
    
    public void setupXmlId() {
        this.setXmlId(XMLIdManager.getXMLId((Class)MolecularType.class));
        for (final MolecularInstance instance : this.getInstances()) {
            instance.setupXmlId();
        }
    }
    

    
    public Molecule getMolecule() {
        return this.molecule;
    }
    
    public void setMolecule(final Molecule molecule) {
        this.molecule = molecule;
    }
    
    public MoleculeAddress getMoleculeAddress() {
        return this.moleculeAddress;
    }
    
    public void setMoleculeAddress(final MoleculeAddress moleculeAddress) {
        this.moleculeAddress = moleculeAddress;
    }
    
    public void setName(final String name) {
        if (this.molecule != null) {
            this.molecule.setName(name);
        }
    }
    
    public String getName() {
        if (this.molecule != null) {
            return this.molecule.getName();
        }
        return null;
    }
    
    public String getNotes() {
        if (this.molecule != null) {
            return this.molecule.getNotes();
        }
        return null;
    }
    
    
    public void setNotes(final String notes) {
        if (this.molecule != null) {
            this.molecule.setNotes(notes);
        }
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
    
    
    public boolean isRadioactive() {
        return this.molecule.isRadioactive();
    }
    
    public boolean isMembraneBound() {
        return !this.mobility;
    }
    
    public void setMembraneBound(final boolean isMembraneBound) {
        this.mobility = !isMembraneBound;
    }
    
    public boolean getMobileOnlyForDefinedInstances() {
        return this.mobileOnlyForDefinedInstances;
    }
    
    
    public boolean isMobileOnlyForDefinedInstances() {
        return this.mobileOnlyForDefinedInstances;
    }
    
    public void setMobileOnlyForDefinedInstances(final boolean mobileOnlyForDefinedInstances) {
        this.mobileOnlyForDefinedInstances = mobileOnlyForDefinedInstances;
    }
    
    
    public double getMolecularWeightDaltons() {
        return this.molecule.getMolecularWeightDaltons();
    }
    
    public void setMolecularWeightDaltons(final double molecularWeightDaltons) {
        this.molecule.setMolecularWeightGramsPerMole(molecularWeightDaltons);
    }
    
    
    public double getMolecularWeightGramsPerMole() {
        return this.molecule.getMolecularWeightGramsPerMole();
    }
    
    
    public double getDensityGramsPerCM3() {
        return this.molecule.getDensityGramsPerCM3();
    }
    
    public void setDensityGramsPerCM3(final double densityGramsPerCM3) {
        this.molecule.setDensityGramsPerCM3(densityGramsPerCM3);
    }
    
    
    public double getNetCharge(final Molecule elementBoundMolecule, final double pH) {
        return this.molecule.getNetCharge(pH) + elementBoundMolecule.getNetCharge(pH);
    }
    
    public Collection<MolecularInstance> getInstances() {
        return (Collection<MolecularInstance>)this.instances;
    }
    
    public void setInstances(final Collection<MolecularInstance> instances) {
        this.instances = instances;
    }
    
    public Collection<MolecularSubstrate> getSubstrates() {
        return (Collection<MolecularSubstrate>)this.substrates;
    }
    
    public void setSubstrates(final Collection<MolecularSubstrate> substrates) {
        this.substrates = substrates;
    }
    
    
    public boolean isSubstrate(final Molecule molecule) {
        for (final MolecularSubstrate molecularSubstrate : this.getSubstrates()) {
            if (molecularSubstrate.getMolecule() == molecule) {
                return true;
            }
        }
        return false;
    }
    
    public boolean doesInstanceExist(final Connection locationNode) {
        for (final MolecularInstance molecularInstance : this.getInstances()) {
            if (molecularInstance.getConnection() == locationNode) {
                return true;
            }
        }
        return false;
    }
    
    public MolecularSubstrate getSubstrate(final Molecule molecule) {
        for (final MolecularSubstrate molecularSubstrate : this.getSubstrates()) {
            if (molecularSubstrate.getMolecule() == molecule) {
                return molecularSubstrate;
            }
        }
        return null;
    }
    
    public void deleteMolecularReferenceSubstrates(final Molecule molecule) {
        final ArrayList<MolecularSubstrate> deletionList = new ArrayList<>();
        for (final MolecularSubstrate substrate : this.substrates) {
            if (substrate.getMolecule() == molecule) {
                deletionList.add(substrate);
            }
        }
        if (deletionList.size() > 0) {
            this.substrates.removeAll(deletionList);
        }
    }
    
    private void setXmlId(String xmlId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
