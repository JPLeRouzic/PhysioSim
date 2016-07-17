// 
//  
// 

package datamodel.perturbation;

public class MoleculeAddress 
{

    protected String agentName;

    protected String moleculeName;
    
    
    public MoleculeAddress() {
        this.agentName = "";
        this.moleculeName = "";
    }
    
    public MoleculeAddress(final Drug agent, final Molecule molecule) {
        this.agentName = "";
        this.moleculeName = "";
        if (agent != null) {
            this.agentName = agent.getName();
        }
        if (molecule != null) {
            this.moleculeName = molecule.getName();
        }
    }
    
    public String getDrugName() {
        return this.agentName;
    }
    
    public void setDrugName(final String agentName) {
        this.agentName = agentName;
    }
    
    public String getMoleculeName() {
        return this.moleculeName;
    }
    
    public void setMoleculeName(final String moleculeName) {
        this.moleculeName = moleculeName;
    }
}
